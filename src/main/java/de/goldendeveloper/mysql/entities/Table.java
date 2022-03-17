package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {

    private final String name;
    private final Database db;

    public Table(String name, Database database) {
        this.name = name;
        this.db = database;
    }

    public String getName() {
        return this.name;
    }

    public void drop() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("DROP TABLE `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Object> getRow(Column column, String item) {
        HashMap<String, Object> map = new HashMap();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            ResultSet rs = statement.executeQuery("SELECT * FROM `" + this.name + "` WHERE " + column.getName() + " = '" + item + "';");
            ResultSetMetaData rsMetaData = rs.getMetaData();
            rs.next();
            if (rs != null) {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    if (rs.getString(rsMetaData.getColumnName(i)) != null) {
                        map.put(rsMetaData.getColumnName(i), rs.getString(rsMetaData.getColumnName(i)));
                    } else {
                        map.put(rsMetaData.getColumnName(i), null);
                    }
                }
            } else {
                return null;
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public int countRows() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM `" + this.name + "`;");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException | ClassNotFoundException e) {
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("DELETE FROM `" + this.name + "` where id = `" + id + "`;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object getRandomFromColumn(String column) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            ResultSet rs = statement.executeQuery("SELECT " + column + " FROM `" + this.name + "` ORDER BY RAND() LIMIT " + countRows());
            rs.next();
            return rs.getObject(1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Column> getColumns() {
        List<Column> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            ResultSet rs = statement.executeQuery("show columns from `" + this.name + "`;");
            while (rs.next()) {
                list.add(new Column(rs.getString(1), this, this.getDatabase()));
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Column getColumn(String name) {
        if (existsColumn(name)) {
            return new Column(name, this, this.db);
        }
        return null;
    }

    public Boolean existsColumn(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.executeQuery("SELECT " + name + " FROM `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    public void addColumn(String name, Integer MysqlType) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("ALTER TABLE `" + this.name + "` ADD `" + name + "` " + MysqlTypes.getMysqlTypeName(MysqlType) + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setUniqueColumn(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("ALTER IGNORE TABLE `" + this.name + "` ADD UNIQUE (" + name + ");");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addColumn(String name, Integer MysqlType, int size) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("ALTER TABLE `" + this.name + "` ADD `" + name + "` " + MysqlTypes.getMysqlTypeName(MysqlType) + " (" + size + ");");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Boolean hasColumns() {
        int a = this.getColumns().size();
        if (a <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean hasColumn(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.executeQuery("SELECT " + name + " FROM `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());

            statement.execute("INSERT INTO `" + this.name + "` (" + keys + ")VALUES (" + items + ");");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase() {
        return this.db;
    }
}
