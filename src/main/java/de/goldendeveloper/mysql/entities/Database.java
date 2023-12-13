package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.interfaces.QueryHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Database class represents a database in a MySQL server.
 * It provides methods to perform various operations on the database,
 * such as renaming, dropping, creating tables, retrieving tables,
 * checking table existence, and getting a specific table.
 */
@SuppressWarnings("unused")
public class Database implements QueryHelper {

    private String name;
    private final MYSQL mysql;

    /**
     * Creates a new instance of the Database class.
     *
     * @param name   the name of the database
     * @param mysql  the MYSQL object used to interact with the database
     */
    public Database(String name, MYSQL mysql) {
        this.name = name;
        this.mysql = mysql;
    }

    /**
     * Retrieves the name of the database.
     *
     * @return the name of the database
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the database.
     *
     * @param name the new name for the database
     */
    public void setName(String name) {
        rename(name);
    }

    /**
     * Renames the database.
     *
     * @param name the new name for the database
     */
    public void rename(String name) {
        executeUpdate("ALTER DATABASE `" + this.name + "` Modify Name = `" + name + "`;", mysql);
        this.name = name;
    }


    /**
     * Drops the database.
     */
    public void drop() {
        executeUpdate("DROP DATABASE `" + this.name + "`;", mysql);
    }

    /**
     * Retrieves a table from the database with the given name.
     *
     * @param name the name of the table to retrieve
     * @return the table with the given name, or null if it does not exist
     */
    public Table getTable(String name) {
        if (this.existsTable(name)) {
            return new Table(name, this, mysql);
        } else {
            return null;
        }
    }

    /**
     * Retrieves a list of tables from the database.
     *
     * @return a list of tables in the database
     */
    public List<Table> getTables() {
        String query = "SHOW TABLES;";
        List<Table> tables = executeQuery(query, rs -> {
            List<Table> list = new ArrayList<>();
            do  {
                list.add(new Table(rs.getString(1), this, mysql));
            } while (rs.next());
            return list;
        }, mysql);
        return tables != null ? tables : new ArrayList<>();
    }

    /**
     * Creates a new table with the given name.
     *
     * @param name the name of the table to create
     */
    public void createTable(String name) {
        executeUpdate("CREATE TABLE `" + name + "` (id int NOT NULL AUTO_INCREMENT,PRIMARY KEY (id));", mysql);
    }

    /**
     * Creates a new table with the given name and columns (if specified).
     * If the table does not exist, it is created with a primary key column named "id".
     * If the table exists, it checks if the specified columns exist in the table and adds them if they don't.
     *
     * @param name    the name of the table to create or modify
     * @param columns an array of column names to add to the table (optional)
     */
    public void createTable(String name, String[] columns) {
        if (!this.existsTable(name)) {
            this.createTable(name);
        }
        if (columns.length > 0) {
            Table table = this.getTable(name);
            for (String column : columns) {
                if (!table.hasColumn(column)) {
                    table.addColumn(column);
                }
            }
        }
    }

    /**
     * Creates a new table with the given name and columns.
     * If the table does not exist, it is created with a primary key column named "id".
     * If the table exists, it checks if the specified columns exist in the table and adds them if they don't.
     *
     * @param name    the name of the table to create or modify
     * @param columns a list of column names to add to the table (optional)
     */
    public void createTable(String name, List<String> columns) {
        this.createTable(name);
        columns.forEach(column -> this.getTable(name).addColumn(column));
    }

    /**
     * Checks if a table exists in the database.
     *
     * @param name the name of the table to check
     * @return true if the table exists, false otherwise
     */
    public boolean existsTable(String name) {
        try (Connection connection = mysql.getConnect()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, name, new String[] {"TABLE"});
            boolean exists = resultSet.next();
            resultSet.close();
            return exists;
        } catch (Exception e) {
            try {
                mysql.getExceptionHandlerClass().callException(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return false;
        }
    }
}
