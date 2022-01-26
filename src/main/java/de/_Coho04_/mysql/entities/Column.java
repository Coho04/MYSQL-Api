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

    //alter table [table name] drop column [column name]; ||
    public void drop() {
        try {
            statement.execute("use " + db.getName() + ";");
            statement.execute("alter table " + this.table.getName() + " drop COLUMN " + this.name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //alter table [table name] change [old column name] [new column name] varchar (50);
    public void setName(String name) {
        try {
            statement.execute("use " + db.getName() + ";");
            statement.execute("alter table " + this.table.getName() + " change " + this.name + name + " varchar (50)");
            this.name = name;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }
}
