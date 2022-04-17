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

    public HashMap<String, String> insertMap;
    public HashMap<String, Object> exportMap;

    public Row(Table table, Column column, String item) {
        this.db = table.getDatabase();
        this.table = table;
        this.column = column;
        this.item = item;
        this.insertMap = new HashMap<>();
        this.exportMap = new HashMap<>();
    }

    public List<Column> showColumns() {
        return table.getColumns();
    }

    public List<Column> getColumns() {
        return table.getColumns();
    }

    public Row with(Column column, String item) {
        this.insertMap.put(column.getName(), item);
        return this;
    }

    public Database getDatabase() {
        return this.db;
    }

    public Row where(Column column, String item) {

        return this;
    }

    public HashMap<String, Object> get() {
        if (exportMap.isEmpty()) {
            try {
                Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
                Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
                ResultSet rs = statement.executeQuery("SELECT * FROM `" + this.getTable().getName() + "` WHERE " + this.column.getName() + " = '" + this.item + "';");
                ResultSetMetaData rsMetaData = rs.getMetaData();
                rs.next();
                if (rs != null) {
                    for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                        if (rs.getString(rsMetaData.getColumnName(i)) != null) {
                            exportMap.put(rsMetaData.getColumnName(i), rs.getString(rsMetaData.getColumnName(i)));
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

    public Table getTable() {
        return this.table;
    }
}
