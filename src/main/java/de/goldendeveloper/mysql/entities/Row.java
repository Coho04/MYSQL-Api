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
    private final MYSQL mysql;

    public Row(Table table, Column column, MYSQL mysql, String item) {
        this.db = table.getDatabase();
        this.table = table;
        this.column = column;
        this.item = item;
        this.exportMap = new HashMap<>();
        this.mysql = mysql;
    }

    public HashMap<String, SearchResult> getData() {
        if (exportMap.isEmpty()) {
            try {
                Connection connect = mysql.getConnect();
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM `" + this.getTable().getName() + "` WHERE " + this.column.getName() + " = '" + this.item + "';");
                ResultSetMetaData rsMetaData = rs.getMetaData();
                rs.next();
                if (rs != null) {
                    for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                        if (!rs.isClosed()) {
                            if (rs.getString(rsMetaData.getColumnName(i)) != null) {
                                exportMap.put(rsMetaData.getColumnName(i), new SearchResult(rs.getString(rsMetaData.getColumnName(i))));
                            } else {
                                exportMap.put(rsMetaData.getColumnName(i), null);
                            }
                        }
                    }
                } else {
                    return null;
                }
                MYSQL.close(rs, statement);
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
            Connection connect = mysql.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET `" + column.getName() + "` = '" + item + "' WHERE `" + this.column.getName() + "` = '" + this.item + "';");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void set(Column column, int item) {
        set(column, String.valueOf(item));
    }

    public void set(Column column, Boolean item) {
        set(column, String.valueOf(item));
    }

    public void set(Column column, float item) {
        set(column, String.valueOf(item));
    }

    public void set(Column column, char item) {
        set(column, String.valueOf(item));
    }

    public void set(Column column, long item) {
        set(column, String.valueOf(item));
    }

    public void set(Column column, double item) {
        set(column, String.valueOf(item));
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

    public int getId() {
         return getData().get("id").getAsInt();
    }

    public void drop() {
        try {
            Connection connect = mysql.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("DELETE FROM `" + this.getTable().getName() + "` where id = " + this.getData().get("id").getAsInt() + ";");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
