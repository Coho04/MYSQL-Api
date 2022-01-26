package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;
    private Database db;
    private static Statement statement = MYSQL.statement;

    public Table(String name, Database database) {
        this.name = name;
        this.db = database;
    }

    public String getName() {
        return this.name;
    }

    //SELECT COUNT(*) FROM [table name];  || Count rows.
    public Integer countRow() {
        try {
           ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM " + this.name + ";" );
           rs.next();
           return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // drop table [table name]; ||  To delete a table.
    public void drop() {
        try {
            statement.execute("DROP TABLE " + this.name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Row getRow(String name) {
        return new Row(name, this, this.getDatabase());
    }

    public List<Column> showColumnsInTable() {
        List<Column> list = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("show columns from " + this.name + ";");
            while (rs.next()) {
                list.add(new Column(rs.getString(1), this, this.getDatabase()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Database getDatabase() {
        return this.db;
    }
}
