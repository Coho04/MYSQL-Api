package de.goldendeveloper.mysql;

import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQL {

    private String password;
    private String hostname;
    private String username;
    private int port;

    private static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    public MYSQL(String hostname, String username, String password, int port) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port, this.username, this.password);
            statement = null;
            statement = connect.createStatement();
            System.out.println("MySQL Connected [Hostname]: " + this.hostname + " [Port]: " + this.port + " [Username]: " + this.username + "  !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  Connection getConnect() {
        return connect;
    }

    public static Statement getStatement() {
        return statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
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
            ResultSet rs = statement.executeQuery("SELECT @@VERSION AS 'SQL Server Version Details'");
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT user FROM mysql.user;");
            while (rs.next()) {
                list.add(new User(rs.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean existsDatabase(String name) {
        try {
            statement.execute("CREATE DATABASE " + name + ";");
            statement.execute("DROP DATABASE " + name + ";");
            return false;
        } catch (SQLException e) {
            return true;
        }
    }

    public Boolean existsUser(String name) {
        try {
            statement.execute("CREATE USER '" + name + "'@'localhost' IDENTIFIED BY 'password';");
            statement.execute("DROP USER '" + name + "'@'localhost';");
            return false;
        } catch (SQLException e) {
            return true;
        }
    }

    public void customExecute(String SQL) {
        try {
            statement.execute(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet customQueryExecute(String SQL) {
        try {
            ResultSet rs = statement.executeQuery(SQL);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Database getDatabase(String name) {
        return new Database(name);
    }

    public void createDatabase(String database) {
        try {
            statement.execute("CREATE DATABASE " + database + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement = null;/*.close();*/
            }
            if (connect != null) {
                connect = null;/*.close();*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFlushPrivileges() {
        try {
            statement.execute("FLUSH PRIVILEGES;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchDatabase(String name) {
        try {
            statement.execute("use " + name + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUser(String username, String password, Boolean database) {
        try {
            statement.execute("CREATE USER " + "'" + username + "'@'localhost' IDENTIFIED BY '" + password + "';");
            if (database) {
                this.createDatabase(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String name) {
        return new User(name);
    }
}
