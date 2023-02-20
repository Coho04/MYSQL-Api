package de.goldendeveloper.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQL {

    private static String password;
    private static String hostname;
    private static String username;
    private static int port = 3306;

    private static String jbcUrl = "jdbc:mysql://";
    private Connection connection = null;
    private HikariConfig config;

    public MYSQL(String hostname, String username, String password, int port) {
        MYSQL.hostname = hostname;
        MYSQL.username = username;
        MYSQL.password = password;
        MYSQL.port = port;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port);
        config.setUsername(MYSQL.username);
        config.setPassword(MYSQL.password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.config = config;
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + MYSQL.hostname + " [Port]: " + MYSQL.port + " [Username]: " + MYSQL.username + "  !");
    }

    public MYSQL(String hostname, String username, String password) {
        MYSQL.hostname = hostname;
        MYSQL.username = username;
        MYSQL.password = password;
        MYSQL.port = 3306;


        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port);
        config.setUsername(MYSQL.username);
        config.setPassword(MYSQL.password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.config = config;
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + MYSQL.hostname + " [Port]: " + MYSQL.port + " [Username]: " + MYSQL.username + "  !");
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
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase(String name) {
        return new Database(name, this);
    }

    public void createDatabase(String database) {
        try {
            Statement statement = getConnect().createStatement();

            statement.execute("CREATE DATABASE " + database + ";");
            MYSQL.close(null, statement);
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
                System.out.println("NAME: " + db.getName());
            }
            close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbs;
    }

    public User getUser(String name) {
        return new User(name, this);
    }

    public static void close(ResultSet resultSet, Statement statement) {
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
                HikariDataSource ds = new HikariDataSource(config);
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
