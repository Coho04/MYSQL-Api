package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Column {

    private String name;
    private final Table table;
    private final Database db;


    public Column(String name, Table table, Database db) {
        this.name = name;
        this.db = db;
        this.table = table;
    }

    public List<Object> getAll() {
        List<Object> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            ResultSet rs = statement.executeQuery("SELECT " + this.name + " FROM `" + getTable().getName() + "`;");
            while (rs.next()) {
                list.add(rs.getObject(1));
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void set(String item, int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET " + this.name + " = '" + item + "' WHERE id = " + id + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void drop() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("ALTER TABLE `" + this.table.getName() + "` DROP COLUMN " + this.name + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setItemNull(String item) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET " + this.name + " = NULL where " + this.getName() + " = " + name);
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setNull() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET " + this.name + " = NULL");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setName(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.getDatabase().getName());
            statement.execute("ALTER TABLE `" + this.getTable().getName() + "` CHANGE " + this.name + name + " varchar (50)");
            this.name = name;
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase() {
        return this.db;
    }

    public Table getTable() {
        return this.table;
    }

    public String getName() {
        return this.name;
    }
}
