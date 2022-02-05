package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Table {

    private String name;

    private Database db;

    private static Statement statement = MYSQL.statement;

    public Table(String name, Database database) {
        this.name = name;
        this.db = database;
    }

    public String getName() {
        return this.name;
    }

    public Integer countRow() {
        try {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM " + this.name + ";");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void drop() {
        try {
            statement.execute("DROP TABLE " + this.name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Object> getRow(Column column, String item) {
        HashMap<String, Object> map = new HashMap();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM " + this.name + " WHERE " + column.getName() + " = '" + item + "';");
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.next();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                map.put(rsmd.getColumnName(i), rs.getString(rsmd.getColumnName(i)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

/*    public List getRowByItem(String column, String name) {
        List list = new ArrayList();
        try {
            statement.executeQuery("use " + this.getDatabase().getName() + ";");
            ResultSet rs = statement.executeQuery("SELECT * FROM " + this.name + " WHERE " + column + " = \"" + name + "\"");
            if (rs.next()) {
                list.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }*/

    public List<Column> showColumnsInTable() {
        List<Column> list = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("show columns from " + this.name + ";");
            while (rs.next()) {
                list.add(new Column(rs.getString(1), this, this.getDatabase()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Column getColumn(String name) {
        if (columnExists(name)) {
            return new Column(name, this, this.db);
        }
        return null;
    }

    public Boolean columnExists(String name) {
        try {
            statement.executeQuery("SELECT "+ name + " FROM "+ this.name + ";");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void addColumn(String name, Integer MysqlType) {
        try {
            statement.execute("ALTER TABLE " + this.name + " ADD `" + name + "` " + MysqlTypes.getPermissionName(MysqlType) + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addColumn(String name, Integer MysqlType, int size) {
        try {
            statement.execute("ALTER TABLE " + this.name + " ADD `" + name + "` " + MysqlTypes.getPermissionName(MysqlType) + " (" + size + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean hasColumns() {
        int a = this.showColumnsInTable().size();
        if (a <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public void insert(Row row) {
        String keys = "";
        String items = "";
        for (String key : row.map.keySet()) {
            if (keys.isBlank()) {
                keys = "`" + key +"`";
            } else {
                keys = keys + ",`" + key +"`";
            }
        }
        for(String item : row.map.values()) {
            if (items.isBlank()) {
                items = "'" + item +"'";
            } else {
                items = items + ",'" + item +"'";
            }
        }
        try {
            statement.execute("INSERT INTO " + this.name + " (" + keys +")VALUES (" + items + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase() {
        return this.db;
    }
}
