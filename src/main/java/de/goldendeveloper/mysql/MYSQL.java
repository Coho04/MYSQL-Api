package de.goldendeveloper.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.User;
import de.goldendeveloper.mysql.errors.ExceptionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQL {

    private String password;
    private String hostname;
    private String username;
    private int port = 3306;

    private ExceptionHandler exceptionHandlerClass;

    private static final String jbcUrl = "jdbc:mysql://";
    private Connection connection = null;
    private HikariDataSource ds = null;

    public MYSQL(String hostname, String username, String password, int port) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.exceptionHandlerClass = new ExceptionHandler();
        createConnectionConfig();
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + this.hostname + " [Port]: " + this.port + " [Username]: " + this.username + "  !");
    }

    public MYSQL(String hostname, String username, String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.exceptionHandlerClass = new ExceptionHandler();
        createConnectionConfig();
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + this.hostname + " [Port]: " + this.port + " [Username]: " + this.username + "  !");
    }

    public <T extends ExceptionHandler> MYSQL(String hostname, String username, String password, int port, T exceptionHandlerClass) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.exceptionHandlerClass = exceptionHandlerClass;
        createConnectionConfig();
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + this.hostname + " [Port]: " + this.port + " [Username]: " + this.username + "  !");
    }

    @SuppressWarnings("unused")
    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("unused")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @SuppressWarnings("unused")
    public void setPort(int port) {
        this.port = port;
    }

    @SuppressWarnings("unused")
    public void setUsername(String username) {
        this.username = username;
    }

    @SuppressWarnings("unused")
    public String getUsername() {
        return username;
    }

    @SuppressWarnings("unused")
    public int getPort() {
        return port;
    }

    @SuppressWarnings("unused")
    public String getHostname() {
        return hostname;
    }

    @SuppressWarnings("unused")
    public String getVersion() {
        try {
            Statement statement = getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT @@VERSION AS 'SQL Server Version Details'");
            if (rs.next()) {
                return rs.getString(1);
            }
            closeRsAndSt(null, statement);
        } catch (Exception e) {
            callException(e);
        }
        return null;
    }

    @SuppressWarnings("unused")
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        try {
            Statement statement = getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT user FROM mysql.user;");
            while (rs.next()) {
                list.add(new User(rs.getString(1), this));
            }
            closeRsAndSt(null, statement);
        } catch (Exception e) {
            callException(e);
        }
        return list;
    }

    public Boolean existsDatabase(String name) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("CREATE DATABASE " + name + ";");
            statement.execute("DROP DATABASE " + name + ";");
            closeRsAndSt(null, statement);
            return false;
        } catch (SQLException e) {
            return true;
        } catch (Exception e) {
            callException(e);
            return true;
        }
    }

    @SuppressWarnings("unused")
    public Boolean existsUser(String name) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("CREATE USER '" + name + "'@'localhost' IDENTIFIED BY 'password';");
            statement.execute("DROP USER '" + name + "'@'localhost';");
            closeRsAndSt(null, statement);
            return false;
        } catch (SQLException e) {
            return true;
        } catch (Exception e) {
            callException(e);
            return false;
        }
    }

    @SuppressWarnings("unused")
    public void customExecute(String SQL) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute(SQL);
            closeRsAndSt(null, statement);
        } catch (Exception e) {
            callException(e);
        }
    }

    public Database getDatabase(String name) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("use `" + name + "`;");
            closeRsAndSt(null, statement);
        } catch (Exception e) {
            callException(e);
        }
        return new Database(name, this);
    }

    public void createDatabase(String database) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("CREATE DATABASE " + database + ";");
            closeRsAndSt(null, statement);
        } catch (Exception e) {
            callException(e);
        }
    }

    @SuppressWarnings("unused")
    public void onFlushPrivileges() {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("FLUSH PRIVILEGES;");
            closeRsAndSt(null, statement);
        } catch (Exception e) {
            callException(e);
        }
    }

    public void switchDatabase(Database database) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("use " + database.getName() + ";");
            closeRsAndSt(null, statement);
        } catch (Exception e) {
            callException(e);
        }
    }

    @SuppressWarnings("unused")
    public void createUser(String username, String password, Boolean database) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("CREATE USER " + "'" + username + "'@'localhost' IDENTIFIED BY '" + password + "';");
            if (database) {
                this.createDatabase(username);
            }
            closeRsAndSt(null, statement);
        } catch (Exception e) {
            callException(e);
        }
    }

    @SuppressWarnings("unused")
    public void createUser(String username, String password) {
        this.createUser(username, password, false);
    }

    public List<Database> getDatabases() {
        List<Database> dbs = new ArrayList<>();
        try {
            Statement statement = getConnect().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("SHOW DATABASES;");
            while (rs.next()) {
                Database db = new Database(rs.getString(1), this);
                dbs.add(db);
            }
            closeRsAndSt(rs, statement);
        } catch (Exception e) {
            callException(e);
        }
        return dbs;
    }

    @SuppressWarnings("unused")
    public User getUser(String name) {
        return new User(name, this);
    }

    @SuppressWarnings("unused")
    public <T extends ExceptionHandler> void setExceptionHandlerClass(T exceptionHandler) {
        this.exceptionHandlerClass = exceptionHandler;
    }

    public ExceptionHandler getExceptionHandlerClass() {
        return exceptionHandlerClass;
    }

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

    public void close() {
        try {
            getConnect().close();
        } catch (Exception e) {
            callException(e);
        }
    }

    @SuppressWarnings("unused")
    public void closeConnection() {
        close();
    }

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

    private void createConnectionConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jbcUrl + this.hostname + ":" + this.port);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setMaximumPoolSize(3);
        config.setMaxLifetime(10000);
        config.setIdleTimeout(5000);
        try {
            this.ds = new HikariDataSource(config);
        } catch (Exception e) {
            callException(e);
        }
    }

    public void callException(Exception exception) {
        try {
            exceptionHandlerClass.callException(exception);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
