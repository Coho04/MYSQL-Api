package de._Coho04_.mysql;

/*
 *
 * http://g2pc1.bu.edu/~qzpeng/manual/MySQL%20Commands.htm
 *
 * */

import de._Coho04_.mysql.entities.Database;
import de._Coho04_.mysql.entities.Table;
import de._Coho04_.mysql.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQL {

    public static Connection connect = null;
    public static Statement statement = null;
    private static ResultSet resultSet = null;

    public MYSQL(String hostname, String username, String password, int port) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port, username, password);
            statement = connect.createStatement();
            System.out.println("MySQL Connected [Hostname]: " + hostname + " [Port]: " + port + " [Username]: " + username + "  !");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public Object customQueryExecute(String SQL) {
        try {
            return statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //	show databases; || List all databases on the sql server.
    public Database getDatabase(String name) {
         return new Database(name);
    }

    //create database [databasename]; || Create a database on the sql server.
    public void createDatabase(String database) {
        try {
            statement.execute("CREATE DATABASE " + database);
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
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SELECT * FROM [table name]; || Show all data in a table.
    public Object getFrom(String item, String table) {
        try {
            resultSet = statement.executeQuery("SELECT " + item + " FROM " + table);
            return resultSet;
        } catch (SQLException e) {
            return null;
        }
    }

    //    FLUSH PRIVILEGES; || Update database permissions/privilages.
    public void onFlushPrivileges() {
        try {
            statement.execute("FLUSH PRIVILEGES");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //	use [db name];  || Switch to a database.
    public void switchDatabase(String name) {
        try {
            statement.execute("use " + name + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // show tables; || To see all the tables in the db.
    public ResultSet showTables() {
        try {
            return statement.executeQuery("show tables;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // describe [table name]; || To see database's field formats.
    public ResultSet describeTable(String name) {
        try {
            return statement.executeQuery("describe " + name + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // mysql.getDatabase("NAME").getTable("NAME").getRow().getColumns();


    public void createUser(String username, String password, Boolean database) {
        try {
            statement.execute("CREATE USER " +  "'" + username + "'@'localhost' IDENTIFIED BY '" + password + "';");
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

//    SELECT * FROM [table name] WHERE [field name] = "whatever"; ||  Show certain selected rows with the value "whatever".
//    SELECT * FROM [table name] WHERE name = "Bob" AND phone_number = '3444444'; || Show all records containing the name "Bob" AND the phone number '3444444'.
//    SELECT * FROM [table name] WHERE name != "Bob" AND phone_number = '3444444' order by phone_number; || Show all records not containing the name "Bob" AND the phone number '3444444' order by the phone_number field.
//    SELECT * FROM [table name] WHERE name like "Bob%" AND phone_number = '3444444'; || Show all records starting with the letters 'bob' AND the phone number '3444444'.
//    SELECT * FROM [table name] WHERE rec RLIKE "^a$"; || Use a regular expression to find records. Use "REGEXP BINARY" to force case-sensitivity. This finds any record beginning with a.
//    SELECT DISTINCT [column name] FROM [table name]; || Show unique records.
//    SELECT [col1],[col2] FROM [table name] ORDER BY [col2] DESC; || Show selected records sorted in an ascending (asc) or descending (desc).
//    INSERT INTO [table name] (Host,User,Password) VALUES('%','user',PASSWORD('password')); || Join tables on common columns.
//
//
//    INSERT INTO [table name] (Host,Db,User,Select_priv,Insert_priv,Update_priv,Delete_priv,Create_priv,Drop_priv) VALUES ('%','db','user','Y','Y','Y','Y','Y','N');  || Change a users password.(from MySQL prompt).
//    UPDATE [table name] SET Select_priv = 'Y',Insert_priv = 'Y',Update_priv = 'Y' where [field name] = 'user';  || Switch to mysql db.Give user privilages for a db.
//    DELETE from [table name] where [field name] = 'whatever';  || To update info already in a table.

//    alter table [table name] add unique ([column name]);
//    alter table [table name] modify [column name] VARCHAR(3);
//    alter table [table name] drop index [colmn name];
//    LOAD DATA INFILE '/tmp/filename.csv' replace INTO TABLE [table name] FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (field1,field2,field3);
//	  [mysql dir]/bin/mysqldump -u root -ppassword --opt >/tmp/alldatabases.sql
//    [mysql dir]/bin/mysqldump -u username -ppassword --databases databasename >/tmp/databasename.sql
//    [mysql dir]/bin/mysqldump -c -u username -ppassword databasename tablename > /tmp/databasename.tablename.sql
//    [mysql dir]/bin/mysql -u username -ppassword databasename < /tmp/databasename.sql
//    CREATE TABLE [table name] (firstname VARCHAR(20), middleinitial VARCHAR(3), lastname VARCHAR(35),suffix VARCHAR(3), officeid VARCHAR(10),userid VARCHAR(15),username VARCHAR(8),email VARCHAR(35),phone VARCHAR(25), groups VARCHAR(15),datestamp DATE,timestamp time,pgpemail VARCHAR(255));
//    create table [table name] (personid int(50) not null auto_increment primary key,firstname varchar(35),middlename varchar(50),lastname varchar(50) default 'bato');
}
/*    private void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String user = resultSet.getString("myuser");
            String website = resultSet.getString("webpage");
            String summary = resultSet.getString("summary");
            Date date = resultSet.getDate("datum");
            String comment = resultSet.getString("comments");
            System.out.println("User: " + user);
            System.out.println("Website: " + website);
            System.out.println("summary: " + summary);
            System.out.println("Date: " + date);
            System.out.println("Comment: " + comment);
  }
}*/
