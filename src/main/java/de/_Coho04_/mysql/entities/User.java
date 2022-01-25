package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.SQLException;
import java.sql.Statement;

public class User {

    private static Statement statement = MYSQL.statement;
    private String name;

    // SELECT user, host FROM mysql.user;
    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void drop(Boolean database) {
        try {
            statement.execute("DROP USER '" + this.name + "'@'localhost';");
            if (database) {
                statement.execute("DROP DATABASE " + this.name + ";");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPassword(String password) {
        try {
            statement.execute("SET PASSWORD FOR '" + this.name +"'@'localhost' = PASSWORD('" + password + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
