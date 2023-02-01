package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String name;

    public Database(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        rename(name);
    }

    public void rename(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("ALTER DATABASE `" + this.name + "` Modify Name = `" + name + "`;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    public void drop() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("DROP DATABASE `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Table getTable(String name) {
        if (this.existsTable(name)) {
            return new Table(name, this);
        } else {
            return null;
        }
    }

    public List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        try {
            List<Object> conn =  MYSQL.connection(this);
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            ResultSet rs = statement.executeQuery("SHOW TABLES;");
            while (rs.next()) {
                Table table = new Table(rs.getString(1), this);
                tables.add(table);
            }
            MYSQL.close(rs, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public void createTable(String name) {
        try {
            List<Object> conn =  MYSQL.connection(this);
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.execute("CREATE TABLE `" + name + "` (id int NOT NULL AUTO_INCREMENT,PRIMARY KEY (id));");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean existsTable(String name) {
        try {
            List<Object> conn =  MYSQL.connection(this);
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.executeQuery("SELECT * FROM `" + name + "`;");
            MYSQL.close(null, connect, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
