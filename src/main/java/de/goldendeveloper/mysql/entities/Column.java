package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Column {

    private String name;
    private final Table table;
    private final Database db;
    private final MYSQL mysql;

    public Column(String name, Table table, MYSQL mysql) {
        this.name = name;
        this.db = table.getDatabase();
        this.table = table;
        this.mysql = mysql;
    }

    public SearchResults getAll() {
        List<SearchResult> list = new ArrayList<>();
        try {
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT `" + this.name + "` FROM `" + getTable().getName() + "`;");
            while (rs.next()) {
                list.add(new SearchResult(rs.getString(1)));
            }
            mysql.closeRsAndSt(rs, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return new SearchResults(list);
    }

    public void drop() {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("ALTER TABLE `" + this.table.getName() + "` DROP COLUMN `" + this.name + "`;");
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Object getRandom() {
        try {
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + this.name + " FROM `" + this.getTable().getName() + "` ORDER BY RAND() LIMIT " + this.getTable().countRows());
            rs.next();
            Object obj = rs.getObject(1);
            mysql.closeRsAndSt(rs, statement);
            return obj;
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void setItemNull(int ID) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET `" + this.getName() + "` = NULL where `id` = " + ID + ";");
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setNull() {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET `" + this.getName() + "` = NULL where `" + this.getName() + "` IS NOT NULL;");
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Boolean getAsBoolean(int id) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT `" + this.name + "` FROM `" + getTable().getName() + "`;");
            while (rs.next()) {
                if (rs.getObject(1).toString().equalsIgnoreCase("true")) {
                    return true;
                } else if (rs.getObject(1).toString().equalsIgnoreCase("false")) {
                    return false;
                } else {
                    return null;
                }
            }
            mysql.closeRsAndSt(rs, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String getAsString(int id) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT `" + this.name + "` FROM `" + getTable().getName() + "`;");
            while (rs.next()) {
                return "" + rs.getObject(1) + "";
            }
            mysql.closeRsAndSt(rs, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void setName(String name) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("ALTER TABLE `" + this.getTable().getName() + "` CHANGE " + this.name + name + " varchar (50)");
            this.name = name;
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
