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
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("ALTER DATABASE `" + this.name + "` Modify Name = `" + name + "`;");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    public void drop() {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("DROP DATABASE `" + this.name + "`;");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
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
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SHOW TABLES;");
            while (rs.next()) {
                Table table = new Table(rs.getString(1), this);
                tables.add(table);
            }
            MYSQL.close(rs, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public void createTable(String name) {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("CREATE TABLE `" + name + "` (id int NOT NULL AUTO_INCREMENT,PRIMARY KEY (id));");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String name, List<String> columns) {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("CREATE TABLE `" + name + "` (id int NOT NULL AUTO_INCREMENT,PRIMARY KEY (id));");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (String column : columns) {
            this.getTable(name).addColumn(column);
        }
    }


    public Boolean existsTable(String name) {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.executeQuery("SELECT * FROM `" + name + "`;");
            MYSQL.close(null, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
