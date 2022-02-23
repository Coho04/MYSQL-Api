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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        try {
            statement.execute("ALTER DATABASE " + this.name + " MODIFY NAME = " + name + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    public void rename(String name) {
        try {
            statement.execute("ALTER DATABASE " + this.name + " Modify Name = " + name + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    public void drop() {
        try {
            statement.execute("DROP DATABASE " + this.name + ";");
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

    public void createTable(String name) {
        try {
            statement.execute("use " + getName() + ";");
            statement.execute("CREATE TABLE " + name + "CREATE TABLE Orders (id int NOT NULL AUTO_INCREMENT,PRIMARY KEY (id));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean existsTable(String name) {
        try {
            statement.execute("use " + getName() + ";");
            statement.executeQuery("SELECT * FROM " + name + ";");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
