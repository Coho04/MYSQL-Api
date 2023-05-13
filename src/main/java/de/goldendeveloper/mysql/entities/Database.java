package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String name;
    private final MYSQL mysql;

    public Database(String name, MYSQL mysql) {
        this.name = name;
        this.mysql = mysql;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        rename(name);
    }

    public void rename(String name) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("ALTER DATABASE `" + this.name + "` Modify Name = `" + name + "`;");
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.name = name;
    }

    public void drop() {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("DROP DATABASE `" + this.name + "`;");
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Table getTable(String name) {
        if (this.existsTable(name)) {
            return new Table(name, this, mysql);
        } else {
            return null;
        }
    }

    public List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        try {
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SHOW TABLES;");
            while (rs.next()) {
                Table table = new Table(rs.getString(1), this, mysql);
                tables.add(table);
            }
            mysql.closeRsAndSt(rs, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return tables;
    }

    public void createTable(String name) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("CREATE TABLE `" + name + "` (id int NOT NULL AUTO_INCREMENT,PRIMARY KEY (id));");
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createTable(String name, List<String> columns) {
        this.createTable(name);
        for (String column : columns) {
            this.getTable(name).addColumn(column);
        }
    }


    public Boolean existsTable(String name) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.executeQuery("SELECT * FROM `" + name + "`;");
            mysql.closeRsAndSt(null, statement);
            return true;
        } catch (SQLException e) {
            return false;
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}
