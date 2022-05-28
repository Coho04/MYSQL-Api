package de.goldendeveloper.mysql;

import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQL {

    public static String password;
    public static String hostname;
    public static String username;
    public static int port;


    public MYSQL(String hostname, String username, String password, int port) {
        MYSQL.hostname = hostname;
        MYSQL.username = username;
        MYSQL.password = password;
        MYSQL.port = port;
        System.out.println("[Golden-Developer][MYSQL-API] Created [Hostname]: " + MYSQL.hostname + " [Port]: " + MYSQL.port + " [Username]: " + MYSQL.username + "  !");
    }

    public void setPassword(String password) {
        MYSQL.password = password;
    }

    public void setHostname(String hostname) {
        MYSQL.hostname = hostname;
    }

    public void setPort(int port) {
        MYSQL.port = port;
    }

    public void setUsername(String username) {
        MYSQL.username = username;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT @@VERSION AS 'SQL Server Version Details'");
            if (rs.next()) {
                return rs.getString(1);
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT user FROM mysql.user;");
            while (rs.next()) {
                list.add(new User(rs.getString(1)));
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean existsDatabase(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("CREATE DATABASE " + name + ";");
            statement.execute("DROP DATABASE " + name + ";");
            MYSQL.close(null, connect, statement);
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            return true;
        }
    }

    public Boolean existsUser(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("CREATE USER '" + name + "'@'localhost' IDENTIFIED BY 'password';");
            statement.execute("DROP USER '" + name + "'@'localhost';");
            MYSQL.close(null, connect, statement);
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            return true;
        }
    }

    public void customExecute(String SQL) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute(SQL);
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase(String name) {
        return new Database(name);
    }

    public void createDatabase(String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("CREATE DATABASE " + database + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onFlushPrivileges() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("FLUSH PRIVILEGES;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void switchDatabase(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("use " + name + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createUser(String username, String password, Boolean database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            statement.execute("CREATE USER " + "'" + username + "'@'localhost' IDENTIFIED BY '" + password + "';");
            if (database) {
                this.createDatabase(username);
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Database> getDatabases() {
        List<Database> dbs = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SHOW DATABASES;");
            rs.next();
            while (rs.next()) {
                Database db = new Database(rs.getString(1));
                dbs.add(db);
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dbs;
    }

    public User getUser(String name) {
        return new User(name);
    }

    public static void close(ResultSet resultSet, Connection connection, Statement statement) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Object> connection(String database) {
        List<Object> stuff = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://" + MYSQL.hostname + ":" + MYSQL.port, MYSQL.username, MYSQL.password);
            Statement statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.execute("use `" + database + "`");
            stuff.add(statement);
            stuff.add(connect);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return stuff;
    }
}
