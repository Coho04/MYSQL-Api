package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.interfaces.QueryHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a column in a database table.
 */
@SuppressWarnings("unused")
public class Column implements QueryHelper {

    private String name;
    private final Table table;
    private final Database db;
    private final MYSQL mysql;

    /**
     * @param name  - name of the column
     * @param table - table of the column
     * @param mysql - mysql instance
     */
    public Column(String name, Table table, MYSQL mysql) {
        this.name = name;
        this.db = table.getDatabase();
        this.table = table;
        this.mysql = mysql;
    }

    /**
     * Retrieves all search results from the database.
     *
     * @return A SearchResults object containing all search results.
     */
    public List<SearchResult> getAll() {
        List<SearchResult> list = new ArrayList<>();
        executeQuery("SELECT `" + this.name + "` FROM `" + table.getName() + "`;", rs -> list.add(new SearchResult(rs.getString(1))), mysql);
        return list;
    }

    /**
     * Drops the column from the table in the database.
     * It executes an SQL query to alter the table and drop the specified column.
     */
    public void drop() {
        executeUpdate("ALTER TABLE `" + this.getTable().getName() + "` DROP COLUMN `" + this.name + "`;", mysql);
    }

    /**
     * Retrieves a random value from the specified column in the database table.
     *
     * @return The random value retrieved from the column.
     */
    public Object getRandom() {
        String query = "SELECT " + this.name + " FROM `" + this.table.getName() + "` ORDER BY RAND() LIMIT " + this.table.countRows();
        return executeQuery(query, (rs) -> rs.getObject(1), mysql);
    }

    /**
     * Sets the value of a specific item in the column to null.
     *
     * @param id ID of the item to update.
     */
    public void setItemNull(int id) {
        executeUpdate("UPDATE `" + this.getTable().getName() + "` SET `" + this.getName() + "` = NULL where `id` = " + id + ";", mysql);
    }

    /**
     * Sets the value of the column to null for all rows where the column is not already null.
     * This method updates the specified column in the table to null for all rows where the column is not already null.
     */
    public void setNull() {
        executeUpdate("UPDATE `" + this.getTable().getName() + "` SET `" + this.getName() + "` = NULL where `" + this.getName() + "` IS NOT NULL;", mysql);
    }

    /**
     * Retrieves the boolean value of the specified column for a given ID.
     *
     * @param id The ID of the column value to retrieve.
     * @return The boolean value of the column for the specified ID, or null if the ID does not exist or there is an error.
     */
    public boolean getAsBoolean(int id) {
        return executeQuery("SELECT `" + this.name + "` FROM `" + table.getName() + "`;", rs -> rs.getObject(1).toString().equalsIgnoreCase("true") ? Boolean.TRUE : rs.getObject(1).toString().equalsIgnoreCase("false") ? false : null, mysql);
    }

    /**
     * Retrieves the string value of the specified column for a given ID.
     *
     * @param id The ID of the column value to retrieve.
     * @return The string value of the column for the specified ID, or null if the ID does not exist or there is an error.
     */
    public String getAsString(int id) {
        return executeQuery("SELECT `" + this.name + "` FROM `" + table.getName() + "`;", rs -> rs.getObject(1).toString(), mysql);
    }

    /**
     * Sets the name of the column.
     *
     * @param name The new name for the column.
     */
    public void setName(String name) {
        executeUpdate("ALTER TABLE `" + this.getTable().getName() + "` CHANGE " + this.name + name + " varchar (50)", mysql);
        this.name = name;
    }

    /**
     * Retrieves the Database object associated with this Column.
     *
     * @return The Database object associated with this Column.
     */
    public Database getDatabase() {
        return this.db;
    }

    /**
     * Retrieves the Table object associated with this Column.
     *
     * @return The Table object associated with this Column.
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * Retrieves the name of the column.
     *
     * @return The name of the column.
     */
    public String getName() {
        return this.name;
    }
}
