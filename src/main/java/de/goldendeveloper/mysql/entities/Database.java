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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("ALTER DATABASE " + this.name + " MODIFY NAME = " + name + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    public void rename(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("ALTER DATABASE " + this.name + " Modify Name = " + name + ";");
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
            statement.execute("DROP DATABASE " + this.name + ";");
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + this.name + ";");
            ResultSet rs = statement.executeQuery("show tables;");
            while (rs.next()) {
                Table table = new Table(rs.getString(1), this);
                tables.add(table);
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public ResultSet describeTable(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " +  this.name + ";");
            return statement.executeQuery("describe " + name + ";");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTable(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + getName() + ";");
            statement.execute("CREATE TABLE `" + name + "` (id int NOT NULL AUTO_INCREMENT,PRIMARY KEY (id));");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Boolean existsTable(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + getName() + ";");
            statement.executeQuery("SELECT * FROM `" + name + "`;");
            MYSQL.close(null, connect, statement);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
