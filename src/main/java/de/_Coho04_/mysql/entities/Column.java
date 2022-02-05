package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.SQLException;
import java.sql.Statement;

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

    public void drop() {
        try {
            statement.execute("alter table " + this.table.getName() + " drop COLUMN " + this.name + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO
    public void setItemNull(String name) {

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
