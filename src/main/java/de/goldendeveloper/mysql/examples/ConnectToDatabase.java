package de.goldendeveloper.mysql.examples;

import de.goldendeveloper.mysql.MYSQL;

public class ConnectToDatabase {

    public void main(String[] args) {
        MYSQL mysql;
        //Connect to Mysql Server without password
        mysql = new MYSQL("127.0.0.1", "root", "", 3306);
        //Connect to Mysql Server with password
        mysql = new MYSQL("127.0.0.1", "root", "password", 3306);
        //Connect with Custom ErrorHandling
        mysql = new MYSQL("127.0.0.1", "root", "password", 3306, new CustomExceptionHandler());

        String databaseName = "YOUR_DATABASE_NAME";

        if (mysql.existsDatabase(databaseName)) {
            /** Your Code here **/
        }

        String newDatabaseName = "NEW_DATABASE_NAME";
        mysql.switchDatabase(mysql.getDatabase(newDatabaseName));
        if (mysql.existsDatabase(newDatabaseName)) {
            /** Your Code here **/
        }
    }
}
