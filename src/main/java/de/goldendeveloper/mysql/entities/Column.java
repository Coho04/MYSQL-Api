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
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            ResultSet rs = statement.executeQuery("SELECT `" + this.name + "` FROM `" + getTable().getName() + "`;");
            while (rs.next()) {
                list.add(rs.getObject(1));
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void set(int id, String item) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET `" + this.name + "` = '" + item + "' WHERE id = " + id + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop() {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("ALTER TABLE `" + this.table.getName() + "` DROP COLUMN `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setItemNull(int ID) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET `" + this.getName() + "` = NULL where `id` = " + ID + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setNull() {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("UPDATE `" + this.getTable().getName() + "` SET `" + this.getName() + "` = NULL where `" + this.getName() + "` IS NOT NULL;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean getAsBoolean(int id) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
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
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAsString(int id) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            ResultSet rs = statement.executeQuery("SELECT `" + this.name + "` FROM `" + getTable().getName() + "`;");
            while (rs.next()) {
                return "" + rs.getObject(1) + "";
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setName(String name) {
        try {
            Statement statement = (Statement) MYSQL.stuff(this.getDatabase().getName()).get(0);
            Connection connect = (Connection) MYSQL.stuff(this.getDatabase().getName()).get(1);
            statement.execute("ALTER TABLE `" + this.getTable().getName() + "` CHANGE " + this.name + name + " varchar (50)");
            this.name = name;
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
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
