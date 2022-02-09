package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Column {

    private  String name;

    private  Table table;

    private  Database db;

    private static Statement statement = MYSQL.statement;

    public Column(String name, Table table, Database db) {
        this.name = name;
        this.db = db;
        this.table = table;
    }

    public List<Object> getAll() {
        List<Object> list = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT " + this.name + " FROM " + getTable().getName() + ";");
            while (rs.next()) {
                list.add(rs.getObject(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void drop() {
        try {
            statement.execute("ALTER TABLE " + this.table.getName() + " DROP COLUMN " + this.name + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO
    public void setItemNull(String item) {
        try {
            statement.execute("UPDATE " + this.getTable() + " SET " + this.name + " = NULL where " + this.getName() + " = " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO
    public void setNull() {
        try {
            statement.execute("UPDATE " + this.getTable() + " SET " + this.name + " = NULL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setName(String name) {
        try {
            statement.execute("ALTER TABLE " + this.getTable().getName() + " CHANGE " + this.name + name + " varchar (50)");
            this.name = name;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase(){
        return this.db;
    }

    public Table getTable(){
        return this.table;
    }

    public String getName() {
        return this.name;
    }
}
