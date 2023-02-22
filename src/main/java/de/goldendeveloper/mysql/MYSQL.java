package de.goldendeveloper.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQL {

    private String password;
    private String hostname;
    private String username;
    private int port = 3306;

    private static final String jbcUrl = "jdbc:mysql://";
    private Connection connection = null;
    private final HikariDataSource ds;

    public MYSQL(String hostname, String username, String password, int port) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jbcUrl + this.hostname + ":" + this.port);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setMaximumPoolSize(3);
        config.setMaxLifetime(10000);
        config.setIdleTimeout(5000);
        this.ds = new HikariDataSource(config);
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + this.hostname + " [Port]: " + this.port + " [Username]: " + this.username + "  !");
    }

    public MYSQL(String hostname, String username, String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jbcUrl + this.hostname + ":" + this.port);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setMaximumPoolSize(3);
        config.setMaxLifetime(10000);
        config.setIdleTimeout(5000);

        this.ds = new HikariDataSource(config);
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + this.hostname + " [Port]: " + this.port + " [Username]: " + this.username + "  !");
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getPort() {
        return port;
    }

    public String getHostname() {
        return hostname;
    }

    public String getVersion() {
        try {
            Statement statement = getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT @@VERSION AS 'SQL Server Version Details'");
            if (rs.next()) {
                return rs.getString(1);
            }
            close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        try {
            Statement statement = getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT user FROM mysql.user;");
            while (rs.next()) {
                list.add(new User(rs.getString(1), this));
            }
            close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean existsDatabase(String name) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("CREATE DATABASE " + name + ";");
            statement.execute("DROP DATABASE " + name + ";");
            close(null, statement);
            return false;
        } catch (SQLException e) {
            return true;
        }
    }

    public Boolean existsUser(String name) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("CREATE USER '" + name + "'@'localhost' IDENTIFIED BY 'password';");
            statement.execute("DROP USER '" + name + "'@'localhost';");
            close(null, statement);
            return false;
        } catch (SQLException e) {
            return true;
        }
    }

    public void customExecute(String SQL) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute(SQL);
            close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase(String name) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("use " + name + ";");
            close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Database(name, this);
    }

    public void createDatabase(String database) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("CREATE DATABASE " + database + ";");
            close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onFlushPrivileges() {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("FLUSH PRIVILEGES;");
            close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchDatabase(Database database) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("use " + database.getName() + ";");
            close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUser(String username, String password, Boolean database) {
        try {
            Statement statement = getConnect().createStatement();
            statement.execute("CREATE USER " + "'" + username + "'@'localhost' IDENTIFIED BY '" + password + "';");
            if (database) {
                this.createDatabase(username);
            }
            close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            close(rs, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbs;
    }

    public User getUser(String name) {
        return new User(name, this);
    }

    public void close(ResultSet resultSet, Statement statement) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnect() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    connection = ds.getConnection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
