package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static Statement statement = MYSQL.statement;




    public boolean existsDatabase(String database) {
        try {
            statement.execute("CREATE DATABASE " + database);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
