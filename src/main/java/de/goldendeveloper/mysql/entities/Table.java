package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private final String name;
    private final Database db;

    public Table(String name, Database database) {
        this.name = name;
        this.db = database;
    }

    public ResultSet describe() {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getName()).get(0);
            return statement.executeQuery("DESCRIBE `" + this.name + "`;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public void drop() {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("DROP TABLE `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public Row getRow(Column column, String item) {
        return new Row(this, column, item);
    }

    public int countRows() {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM `" + this.name + "`;");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countColumn() {
        return this.getColumns().size();
    }

    public boolean isEmpty() {
        if (this.countRows() > 0) {
            return false;
        }
        return true;
    }

    public void dropRow(int id) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("DELETE FROM `" + this.name + "` where id = `" + id + "`;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Column> getColumns() {
        List<Column> list = new ArrayList<>();
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            ResultSet rs = statement.executeQuery("show columns from `" + this.name + "` ;");
            while (rs.next()) {
                list.add(new Column(rs.getString(1), this));
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Column getColumn(String name) {
        if (existsColumn(name)) {
            return new Column(name, this);
        }
        return null;
    }

    public Boolean existsColumn(String name) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.executeQuery("select `" + name + "` from `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void addColumn(String name, Integer MysqlType) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("ALTER TABLE `" + this.name + "` ADD `" + name + "` " + MysqlTypes.getMysqlTypeName(MysqlType) + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUniqueColumn(String name) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("ALTER IGNORE TABLE `" + this.name + "` ADD UNIQUE (" + name + ");");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addColumn(String name, Integer MysqlType, int size) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("ALTER TABLE `" + this.name + "` ADD `" + name + "` " + MysqlTypes.getMysqlTypeName(MysqlType) + " (" + size + ");");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean hasColumns() {
        int a = this.getColumns().size();
        return a > 0;
    }

    public Boolean hasColumn(String name) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.executeQuery("SELECT " + name + " FROM `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void insert(Row row) {
        String keys = "";
        String items = "";
        for (String key : row.map.keySet()) {
            if (keys.isEmpty()) {
                keys = "`" + key + "`";
            } else {
                keys = keys + ",`" + key + "`";
            }
        }
        for (String item : row.map.values()) {
            if (items.isEmpty()) {
                items = "'" + item + "'";
            } else {
                items = items + ",'" + item + "'";
            }
        }
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("INSERT INTO `" + this.name + "` (" + keys + ")VALUES (" + items + ");");
            MYSQL.close(null, connect, statement);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase() {
        return this.db;
    }
}
