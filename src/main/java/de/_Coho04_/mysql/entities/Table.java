package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Table {

    private String name;
    private static Statement statement = MYSQL.statement;

    public Table(String name, Database base) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    //    SELECT COUNT(*) FROM [table name];  || Count rows.
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
}
