package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class Row {

    private final Table table;
    private final Database db;
    private final Column column;
    private final String item;
    private HashMap<String, SearchResult> exportMap;

    public Row(Table table, Column column, String item) {
        this.db = table.getDatabase();
        this.table = table;
        this.column = column;
        this.item = item;
        this.exportMap = new HashMap<>();
    }

    public HashMap<String, SearchResult> get() {
        if (exportMap.isEmpty()) {
            try {
                List<Object> conn =  MYSQL.connection(this.getDatabase());
                Statement statement = (Statement) conn.get(0);
                Connection connect = (Connection) conn.get(1);
                ResultSet rs = statement.executeQuery("SELECT * FROM `" + this.getTable().getName() + "` WHERE " + this.column.getName() + " = '" + this.item + "';");
                ResultSetMetaData rsMetaData = rs.getMetaData();
                rs.next();
                if (rs != null) {
                    for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                        if (rs.getString(rsMetaData.getColumnName(i)) != null) {
                            exportMap.put(rsMetaData.getColumnName(i), new SearchResult( rs.getString(rsMetaData.getColumnName(i))));
                        } else {
                            exportMap.put(rsMetaData.getColumnName(i), null);
                        }
                    }
                } else {
                    return null;
                }
                MYSQL.close(null, connect, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this.exportMap;
    }

    public void setExportMap(HashMap<String, SearchResult> newMap) {
        exportMap = newMap;
    }

    public void set(Column column, String item) {
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET `" + column.getName() + "` = '" + item + "' WHERE `" + this.column.getName() + "` = '" + this.item + "';");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Column> showColumns() {
        return table.getColumns();
    }

    public List<Column> getColumns() {
        return table.getColumns();
    }

    public Database getDatabase() {
        return this.db;
    }

    public Table getTable() {
        return this.table;
    }
}
