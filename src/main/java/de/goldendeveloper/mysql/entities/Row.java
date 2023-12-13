package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.interfaces.QueryHelper;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a row in a MySQL database table.
 */
@SuppressWarnings("unused")
public class Row implements QueryHelper {

    private final Table table;
    private final Database db;
    private final Column column;
    private final String item;
    private HashMap<String, SearchResult> exportMap;
    private final MYSQL mysql;

    /**
     * Represents a row in a MySQL database table.
     */
    public Row(Table table, Column column, MYSQL mysql, String item) {
        this.db = table.getDatabase();
        this.table = table;
        this.column = column;
        this.item = item;
        this.exportMap = new HashMap<>();
        this.mysql = mysql;
    }

    /**
     * Retrieves data from the MySQL database.
     *
     * @return A HashMap containing the retrieved data. The keys of the HashMap are the column names, and the values are SearchResult objects representing the data in the corresponding
     * columns.
     */
    public HashMap<String, SearchResult> getData() {
        if (exportMap.isEmpty()) {
            String query = "SELECT * FROM `" + this.getTable().getName() + "` WHERE `" + this.column.getName() + "` = '" + this.item + "';";
            exportMap = executeQuery(query, rs -> {
                HashMap<String, SearchResult> map = new HashMap<>();
                ResultSetMetaData rsMetaData = rs.getMetaData();
                if (rs.next()) {
                    for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                        if (rs.getString(rsMetaData.getColumnName(i)) != null) {
                            map.put(rsMetaData.getColumnName(i), new SearchResult(rs.getString(rsMetaData.getColumnName(i))));
                        } else {
                            map.put(rsMetaData.getColumnName(i), null);
                        }
                    }
                }
                return map;
            }, mysql);
        }
        return this.exportMap != null ? this.exportMap : new HashMap<>();
    }

    /**
     * Sets the export map for the Row.
     *
     * @param newMap The HashMap containing the new export map.
     */
    public void setExportMap(HashMap<String, SearchResult> newMap) {
        exportMap = newMap;
    }

    /**
     * Sets the value of a specific item in the column.
     *
     * @param column The Column object representing the column to set the value for.
     * @param item The new value to set for the column.
     */
    public void set(Column column, Object item) {
        executeUpdate("UPDATE `" + this.getTable().getName() + "` SET `" + column.getName() + "` = '" + item.toString() + "' WHERE `" + this.column.getName() + "` = '" + this.item + "';", mysql);
    }

    /**
     * Retrieves the list of columns in the table.
     *
     * @return The list of Column objects representing the columns in the table.
     */
    public List<Column> showColumns() {
        return table.getColumns();
    }

    /**
     * Retrieves the list of columns in the table.
     *
     * @return The list of Column objects representing the columns in the table.
     */
    public List<Column> getColumns() {
        return table.getColumns();
    }

    /**
     * Retrieves the database associated with this Row.
     *
     * @return the database associated with this Row
     */
    public Database getDatabase() {
        return this.db;
    }

    /**
     * Retrieves the table associated with this Row.
     *
     * @return the table associated with this Row
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * Retrieves the id associated with this Row.
     *
     * @return the id associated with this Row
     */
    public int getId() {
        return getData().get("id").getAsInt();
    }

    /**
     * Deletes the row from the database table associated with this object.
     * The deletion is performed based on the value of the column in the row.
     * The SQL query executed is as follows:
     *   DELETE FROM `tableName` WHERE `columnName` = 'columnValue';
     *
     * @see Row#getTable() to retrieve the table associated with this row
     * @see Row#column to retrieve the column associated with this row
     * @see Row#item to retrieve the value of the column in this row
     * @see QueryHelper#executeUpdate(String, MYSQL) for executing the deletion query
     */
    public void drop() {
        executeUpdate("DELETE FROM `" + this.getTable().getName() + "` WHERE `" + this.column.getName() + "` = '" + this.item + "';", mysql);
    }
}
