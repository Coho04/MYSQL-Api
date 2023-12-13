package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.interfaces.QueryHelper;
import de.goldendeveloper.mysql.entities.enums.MysqlTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * The Table class represents a table in a database.
 * It provides methods to perform various operations on the table,
 * such as retrieving the table description, dropping the table,
 * getting rows, columns, and checking table and column existence.
 */
@SuppressWarnings("unused")
public class Table implements QueryHelper {

    private final String name;
    private final Database db;
    private final MYSQL mysql;

    /**
     * The Table class represents a table in a database.
     * It provides methods to perform various operations on the table,
     * such as retrieving the table description, dropping the table,
     * getting rows, columns, and checking table and column existence.
     */
    public Table(String name, Database database, MYSQL mysql) {
        this.name = name;
        this.db = database;
        this.mysql = mysql;
    }

    /**
     * Returns the description of the table.
     *
     * @return a ResultSet containing the description of the table
     */
    public List<String> describe() {
        String query = "DESCRIBE `" + this.name + "`;";
        List<String> description = executeQuery(query, rs -> {
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        }, mysql);
        return description != null ? description : new ArrayList<>();
    }

    /**
     * Retrieves the name of the table.
     *
     * @return The name of the table.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Drops the table from the database.
     * This method executes a SQL query to drop the table with the given name.
     */
    public void drop() {
        this.executeUpdate("DROP TABLE `" + this.name + "`;", mysql);
    }

    /**
     * Retrieves a row from the table based on a specific column and item.
     *
     * @param column The Column object representing the column to search in.
     * @param item   The item to search for in the specified column.
     * @return A Row object representing the retrieved row.
     */
    public Row getRow(Column column, Object item) {
        return new Row(this, column, mysql, String.valueOf(item));
    }

    /**
     * Retrieves the list of rows from the table.
     *
     * @return A List of Row objects representing the rows in the table.
     */
    public List<Row> getRows() {
        List<Row> rows = new ArrayList<>();
        Column column = this.getColumn("id");
        for (int i = 1; i <= countRows(); i++) {
            rows.add(getRow(column, "SELECT * FROM " + this.getName() + " where id = '" + i + "';"));
        }
        return rows;
    }

    /**
     * Retrieves a map of search results based on the provided statement, column, and item.
     *
     * @param column The Column object representing the column to search in.
     * @param item   The item to search for in the specified column.
     * @return A HashMap<String, SearchResult> containing the search results, where the key is the column name
     * and the value is a SearchResult object representing the retrieved item.
     */
    public HashMap<String, SearchResult> getMap(Column column, String item) {
        String query = "SELECT * FROM `" + this.getName() + "` WHERE " + column.getName() + " = '" + item + "';";
        HashMap<String, SearchResult> exportMap = executeQuery(query, rs -> {
            HashMap<String, SearchResult> map = new HashMap<>();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            for (int i = 1; i <= this.countColumn(); i++) {
                if (!rsMetaData.getColumnName(i).isEmpty()) {
                    System.out.println("ColumnName: " + rsMetaData.getColumnName(i));
                    if (rs.getString(rsMetaData.getColumnName(i)) != null) {
                        System.out.println("Value: " + rs.getString(rsMetaData.getColumnName(i)));
                        map.put(rsMetaData.getColumnName(i), new SearchResult(rs.getString(rsMetaData.getColumnName(i))));
                    } else {
                        System.out.println("Value: null");
                        map.put(rsMetaData.getColumnName(i), null);
                    }
                }
            }
            return map;
        }, mysql);
        return exportMap != null ? exportMap : new HashMap<>();
    }

    /**
     * Retrieves the number of rows in the database table.
     *
     * @return The number of rows in the table.
     */
    public int countRows() {
        return executeQuery("SELECT COUNT(*) FROM `" + this.name + "`;", rs -> rs.getInt(1), mysql);
    }

    /**
     * Retrieves the number of columns in the table.
     *
     * @return The number of columns in the table.
     */
    public int countColumn() {
        return this.getColumns().size();
    }

    /**
     * Checks if the table is empty.
     *
     * @return true if the table is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.countRows() <= 0;
    }

    /**
     * Drops a row from the database table based on the provided ID.
     *
     * @param id The ID of the row to be dropped.
     */
    public void dropRow(int id) {
        executeUpdate("DELETE FROM `" + this.name + "` where id = " + id + ";", mysql);
    }

    /**
     * Retrieves the list of columns for the table.
     *
     * @return A List of Column objects representing the columns in the table.
     */
    public List<Column> getColumns() {
        String query = "SHOW COLUMNS FROM `" + this.name + "` ;";
        List<Column> columns = executeQuery(query, rs -> {
            List<Column> list = new ArrayList<>();
            do {
                list.add(new Column(rs.getString(1), this, mysql));
            } while (rs.next());
            return list;
        }, mysql);
        return columns != null ? columns : new ArrayList<>();
    }

    /**
     * Retrieves a Column object with the given name from the table.
     *
     * @param name The name of the column to retrieve.
     * @return A Column object representing the retrieved column, or null if the column does not exist.
     */
    public Column getColumn(String name) {
        if (existsColumn(name)) {
            return new Column(name, this, mysql);
        }
        return null;
    }

    /**
     * Checks if a column exists in the table.
     *
     * @param name The name of the column to check.
     * @return {@code true} if the column exists, {@code false} otherwise.
     */
    public boolean existsColumn(String name) {
        String query = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + this.name + "' AND COLUMN_NAME = '" + name + "';";
        Boolean exists = executeQuery(query, rs -> true, mysql);
        return exists != null && exists;
    }


    /**
     * Checks if a row exists in the database table based on the provided column and item.
     *
     * @param column The Column object representing the column to search in.
     * @param item   The item to search for in the specified column.
     * @return true if the row exists in the table, false otherwise.
     */
    public boolean existsRow(Column column, String item) {
        String query = "SELECT EXISTS(SELECT * FROM `" + this.getName() + "` WHERE `" + column.getName() + "` = '" + item + "')";
        Boolean exists = executeQuery(query, rs -> rs.next() && rs.getBoolean(1), mysql);
        return exists != null && exists;
    }


    /**
     * Sets a unique constraint on the specified column in the table.
     * This method executes an SQL query to add a unique constraint to the table.
     *
     * @param name The name of the column to set as unique.
     */
    public void setUniqueColumn(String name) {
        executeUpdate("ALTER IGNORE TABLE `" + this.name + "` ADD UNIQUE (" + name + ");", mysql);
    }

    /**
     * Adds a column to the table with the given name.
     *
     * @param name the name of the column to add
     */
    public void addColumn(String name) {
        executeUpdate("ALTER TABLE `" + this.name + "` ADD `" + name + "` " + MysqlTypes.TEXT.getMysqlTypeName() + " (" + 65555 + ");", mysql);
    }

    /**
     * Checks if the table has any columns.
     *
     * @return {@code true} if the table has any columns, {@code false} otherwise.
     */
    public boolean hasColumns() {
        return !this.getColumns().isEmpty();
    }

    /**
     * Checks if a column exists in the table.
     *
     * @param name The name of the column to check.
     * @return {@code true} if the column exists, {@code false} otherwise.
     */
    public boolean hasColumn(String name) {
        try (Connection connection = mysql.getConnect()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, this.name, name);
            boolean hasColumn = resultSet.next();
            resultSet.close();
            return hasColumn;
        } catch (SQLException e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return false;
        }
    }


    /**
     * Inserts a row into the table with the provided data.
     *
     * @param rowBuilder a HashMap containing the column names as keys and the corresponding values as values.
     *                   The column names and values must be of type String.
     */
    public void insert(HashMap<String, String> rowBuilder) {
        StringBuilder keys = new StringBuilder();
        StringBuilder items = new StringBuilder();
        rowBuilder.keySet().forEach(key -> {
            if (keys.isEmpty()) {
                keys.append("`").append(key).append("`");
            } else {
                keys.append(",`").append(key).append("`");
            }
        });
        rowBuilder.values().forEach(item -> {
            if (items.isEmpty()) {
                items.append("'").append(item).append("'");
            } else {
                items.append(",'").append(item).append("'");
            }
        });
        executeUpdate("INSERT INTO `" + this.name + "` (" + keys + ")VALUES (" + items + ");", mysql);
    }

    /**
     * Retrieves the database associated with the table.
     *
     * @return The Database object representing the database associated with the table.
     */
    public Database getDatabase() {
        return this.db;
    }

    /**
     * Retrieves a row from the table based on the provided ID.
     *
     * @param id The ID of the row to retrieve.
     * @return A Row object representing the retrieved row.
     */
    public Row getRowById(int id) {
        return new Row(this, this.getColumn("id"), mysql, String.valueOf(id));
    }

    /**
     * Retrieves the latest row from the table.
     *
     * @return A Row object representing the latest row.
     */
    public Row getLastestRow() {
        String query = "SELECT * FROM `" + this.getName() + "` WHERE id = ( SELECT MAX(`id`) FROM `" + this.getName() + "`);";
        return getRow(this.getColumn("id"), query);
    }

    /**
     * Fills a HashMap with search results from a ResultSet.
     *
     * @param rs The ResultSet containing the search results.
     * @return A HashMap<String, SearchResult> containing the search results,
     * where the key is the column name and the value is a SearchResult object representing the retrieved item.
     */
    private HashMap<String, SearchResult> fillMap(ResultSet rs) {
        HashMap<String, SearchResult> map = new HashMap<>();
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            for (int b = 1; b <= this.countColumn(); b++) {
                if (!rsMetaData.getColumnName(b).isEmpty()) {
                    if (rs.getString(rsMetaData.getColumnName(b)) != null && !rs.getString(rsMetaData.getColumnName(b)).isEmpty()) {
                        System.out.println("ColumnName: " + rsMetaData.getColumnName(b));
                        map.put(rsMetaData.getColumnName(b), new SearchResult(rs.getString(rsMetaData.getColumnName(b))));
                    } else {
                        System.out.println("ColumnName: " + rsMetaData.getColumnName(b));
                        map.put(rsMetaData.getColumnName(b), null);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Retrieves the oldest row from the table.
     *
     * @return A Row object representing the oldest row in the table. Returns null if an error occurs.
     */
    public Row getOldestRow() {
        String query = "SELECT * FROM `" + this.getName() + "` WHERE id = ( SELECT MIN(`id`) FROM `" + this.getName() + "`);";
        return getRow(this.getColumn("id"), query);
    }

    private Row getRow(Column column, String query) {
        HashMap<String, SearchResult> exportMap = executeQuery(query, this::fillMap, mysql);
        String id = exportMap.get("id").getAsString();
        Row row = new Row(this, column, mysql, id);
        row.setExportMap(exportMap);
        return row;
    }

    /**
     * Retrieves a random row from the table.
     *
     * @return A Row object representing a randomly selected row from the table.
     */
    public Row getRandomRow() {
        return this.getRowById(new Random().nextInt(this.countRows()));
    }
}
