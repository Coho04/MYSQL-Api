package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String name;
    private static Statement statement = MYSQL.statement;

    public Database(String name) {
        this.name = name;
    }

    // Return the Name of the Table
    public String getName() {
        return this.name;
    }

/*
    public void setName(String name) {
        try {
            statement.execute("ALTER DATABASE " + this.name + " MODIFY NAME = " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.name = name;
    }
    public void rename(String name) {
        try {
            statement.execute("ALTER DATABASE ip_ent_site Modify Name = ip_ent_site1" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.name = name;
    }
*/

    // drop database [database name]; || To delete a db.
    public void drop() {
        try {
            statement.execute("DROP DATABASE " + this.name + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Return table if table exists
    public Table getTable(String name) {
        if (this.tableExists(name)) {
            return new Table(name, this);
        } else {
            return null;
        }
    }

    public List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        try {
            statement.executeQuery("use " + this.name + ";");
            ResultSet rs = statement.executeQuery("show tables;");
            while (rs.next()) {
                Table table = new Table(rs.getString(1), this);
                tables.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public Boolean tableExists(String name) {
        try {
            statement.executeQuery("use " + this.name + ";");
            statement.executeQuery("SELECT * FROM " + name + ";");
            return false;
        } catch (SQLException e) {
            return true;
        }
    }
}
