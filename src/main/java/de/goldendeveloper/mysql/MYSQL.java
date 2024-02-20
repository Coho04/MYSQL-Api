package de.goldendeveloper.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.User;
import de.goldendeveloper.mysql.errors.ExceptionHandler;
import de.goldendeveloper.mysql.interfaces.QueryHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The MYSQL class provides methods for connecting to a MySQL server and performing operations on the server.
 */
@SuppressWarnings("unused")
public class MYSQL implements QueryHelper {

    private String password;
    private String hostname;
    private String username;
    private int port = 3306;

    private ExceptionHandler exceptionHandlerClass;

    private static final String jbcUrl = "jdbc:mysql://";
    private Connection connection = null;
    private HikariDataSource ds = null;

    /**
     * Constructor for creating a MYSQL object with the specified hostname, username, password, and port.
     *
     * @param hostname The hostname of the MySQL server.
     * @param username The username to connect to the MySQL server.
     * @param password The password to connect to the MySQL server.
     * @param port     The port of the MySQL server.
     */
    public MYSQL(String hostname, String username, String password, int port) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.exceptionHandlerClass = new ExceptionHandler();
        createConnectionConfig();
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + this.hostname + " [Port]: " + this.port + " [Username]: " + this.username + "  !");
    }

    /**
     * Constructor for creating a MYSQL object with the specified hostname, username, and password.
     *
     * @param hostname The hostname of the MySQL server.
     * @param username The username to connect to the MySQL server.
     * @param password The password to connect to the MySQL server.
     */
    public MYSQL(String hostname, String username, String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.exceptionHandlerClass = new ExceptionHandler();
        createConnectionConfig();
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + this.hostname + " [Port]: " + this.port + " [Username]: " + this.username + "  !");
    }

    /**
     * Constructs a MYSQL object with the specified hostname, username, password, and port.
     *
     * @param hostname              The hostname of the MySQL server.
     * @param username              The username to connect to the MySQL server.
     * @param password              The password to connect to the MySQL server.
     * @param port                  The port of the MySQL server.
     * @param exceptionHandlerClass The ExceptionHandler class for handling exceptions.
     * @param <T>                   The type of the ExceptionHandler class.
     */
    public <T extends ExceptionHandler> MYSQL(String hostname, String username, String password, int port, T exceptionHandlerClass) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.exceptionHandlerClass = exceptionHandlerClass;
        createConnectionConfig();
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + this.hostname + " [Port]: " + this.port + " [Username]: " + this.username + "  !");
    }

    /**
     * Sets the password for the MYSQL class.
     *
     * @param password The password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the hostname for the MYSQL class.
     *
     * @param hostname The hostname to be set.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Sets the port for the MYSQL class.
     *
     * @param port The port to be set for the MYSQL class.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Sets the username for the MYSQL class.
     *
     * @param username The username to be set for the MYSQL class.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the username associated with the MySQL server.
     *
     * @return The username of the MySQL server.
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method is used to retrieve the port of the MySQL server.
     *
     * @return The port of the MySQL server.
     */
    public int getPort() {
        return port;
    }

    /**
     * This method returns the hostname of the MySQL server.
     *
     * @return The hostname of the MySQL server.
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * This method is used to get the version of the MySQL server.
     *
     * @return The version of the MySQL server.
     */
    public String getVersion() {
        List<String> results = executeQuery("SELECT @@VERSION AS 'SQL Server Version Details'", rs -> rs.getString(1), this);
        return !results.isEmpty() ? results.get(0) : null;
    }

    /**
     * Retrieves a list of User objects representing the users in the MySQL server.
     *
     * @return A list of User objects representing the users in the MySQL server.
     */
    public List<User> getUsers() {
        return executeQuery("SELECT user FROM mysql.user;", rs -> new User(rs.getString(1), this), this);
    }

    /**
     * Checks if a database with the given name exists.
     *
     * @param databaseName The name of the database to check.
     * @return {@code true} if the database exists, {@code false} otherwise.
     */
    public boolean existsDatabase(String databaseName) {
        try (Connection connection = getConnect()) {
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(databaseName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            callException(e);
        }
        return false;
    }

    /**
     * Checks if a user with the given name exists in the MySQL server.
     *
     * @param name The name of the user to check.
     * @return {@code true} if the user exists, {@code false} otherwise.
     */
    public boolean existsUser(String name) {
        List<Integer> results = executeQuery("SELECT COUNT(*) FROM mysql.user WHERE user = '" + name + "';", rs -> rs.getInt(1), this);
        return !results.isEmpty() && results.get(0) > 0;
    }

    /**
     * Executes the given SQL statement.
     *
     * @param SQL The SQL statement to be executed.
     */
    public void customExecute(String SQL) {
        executeUpdate(SQL, this);
    }

    /**
     * Retrieves a Database object with the given name.
     *
     * @param name The name of the database to retrieve.
     * @return The Database object with the given name, or null if it doesn't exist.
     */
    public Database getDatabase(String name) {
        executeUpdate("use `" + name + "`;", this);
        if (existsDatabase(name))
            return new Database(name, this);
        return null;
    }


    /**
     * Retrieves an existing Database object with the given name or creates a new one if it doesn't exist.
     *
     * @param name The name of the database to retrieve or create.
     * @return An instance of the Database class representing the database with the given name.
     */
    public Database getOrCreateDatabase(String name) {
        executeUpdate("use `" + name + "`;", this);
        if (!existsDatabase(name))
            createDatabase(name);
        return new Database(name, this);
    }

    /**
     * This method is used to create a new database.
     *
     * @param database The name of the database to be created.
     */
    public void createDatabase(String database) {
        executeUpdate("CREATE DATABASE " + database + ";", this);
    }

    /**
     * This method is used to flush the MySQL server privileges.
     */
    public void onFlushPrivileges() {
        executeUpdate("FLUSH PRIVILEGES;", this);
    }

    /**
     * Switches the current database to the specified database.
     *
     * @param database The database to switch to.
     */
    public void switchDatabase(Database database) {
        executeUpdate("use " + database.getName() + ";", this);
    }

    /**
     * Creates a user in the MySQL server.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @param database {@code true} if a new database should be created with the same name as the username,
     *                 {@code false} otherwise.
     */
    public void createUser(String username, String password, Boolean database) {
        executeUpdate("CREATE USER " + "'" + username + "'@'localhost' IDENTIFIED BY '" + password + "';", this);
        if (database) {
            this.createDatabase(username);
        }
    }

    /**
     * Creates a user in the MySQL server.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     */
    public void createUser(String username, String password) {
        this.createUser(username, password, false);
    }

    /**
     * Retrieves an existing User object with the given username or creates a new one if it doesn't exist.
     *
     * @param username The username of the user to retrieve or create.
     * @param password The password of the user to retrieve or create.
     * @return An instance of the User class representing the user with the given username.
     */
    public User getOrCreateUser(String username, String password) {
        if (!existsUser(username)) {
            this.createUser(username, password, false);
        }
        return new User(username, this);
    }

    /**
     * Retrieves a list of databases from the MySQL server.
     *
     * @return a list of Database objects representing the databases in the MySQL server.
     */
    public List<Database> getDatabases() {
        return executeQuery("SHOW DATABASES;", rs -> new Database(rs.getString(1), this), this);
    }

    /**
     * This method is used to get a User object with the given name.
     *
     * @param name The name of the user.
     * @return The User object with the given name.
     */
    public User getUser(String name) {
        return new User(name, this);
    }

    /**
     * Sets the ExceptionHandler class for handling exceptions.
     *
     * @param exceptionHandler The instance of the ExceptionHandler class.
     * @param <T>              The type of the ExceptionHandler class.
     */
    public <T extends ExceptionHandler> void setExceptionHandlerClass(T exceptionHandler) {
        this.exceptionHandlerClass = exceptionHandler;
    }

    /**
     * This method is used to get the ExceptionHandler class associated with the MYSQL class.
     *
     * @return The ExceptionHandler class instance.
     */
    public ExceptionHandler getExceptionHandlerClass() {
        return exceptionHandlerClass;
    }

    /**
     * Closes the given ResultSet and Statement objects.
     * If any exception occurs during the close operation, it is passed to the {@link #callException(Exception)} method.
     *
     * @param resultSet The ResultSet object to be closed.
     * @param statement The Statement object to be closed.
     */
    public void closeRsAndSt(ResultSet resultSet, Statement statement) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            callException(e);
        }
    }

    /**
     * Closes the connection to the MySQL server. If an exception occurs during the close operation,
     * it will be passed to the {@link #callException(Exception)} method.
     */
    public void close() {
        try {
            getConnect().close();
        } catch (Exception e) {
            callException(e);
        }
    }

    /**
     * Closes the connection to the MySQL server. If an exception occurs during the close operation,
     * it will be passed to the {@link #callException(Exception)} method.
     */
    public void closeConnection() {
        close();
    }

    /**
     * This method is used to get the connection to the MySQL server.
     *
     * @return The connection to the MySQL server.
     */
    public Connection getConnect() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    connection = ds.getConnection();
                } catch (Exception e) {
                    callException(e);
                }
            }
        } catch (Exception e) {
            callException(e);
        }
        return connection;
    }


    /**
     * This method is used to create the configuration for the connection to the MySQL server.
     */
    private void createConnectionConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jbcUrl + this.hostname + ":" + this.port);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(30));
        config.setIdleTimeout(TimeUnit.MINUTES.toMillis(10));
        config.setMaxLifetime(TimeUnit.MINUTES.toMillis(30));
        config.setInitializationFailTimeout(0);
        config.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(60));
        config.setUsername(this.username);
        config.setPassword(this.password);
        try {
            this.ds = new HikariDataSource(config);
        } catch (Exception e) {
            callException(e);
        }
    }

    /**
     * This method is used to handle exceptions.
     *
     * @param exception The exception to be handled.
     */
    public void callException(Exception exception) {
        try {
            exceptionHandlerClass.callException(exception);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
