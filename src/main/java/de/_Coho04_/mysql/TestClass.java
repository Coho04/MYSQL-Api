package de._Coho04_.mysql;

import de._Coho04_.mysql.entities.Database;
import de._Coho04_.mysql.entities.Table;
import de._Coho04_.mysql.entities.User;

public class TestClass {

    public static void main(String[] bot) {
        MYSQL mysql = new MYSQL("138.201.202.55", "Basti", "basti?hibst", 3306);

        // DATABASE
        if (!mysql.existsDatabase("FISCH")) {
            mysql.createDatabase("FISCH");
        }

        Database base = mysql.getDatabase("school_laravel");
        for (Table t : base.getTables()) {
            System.out.println("[Name]: " + t.getName());
            System.out.println("[Rows]: " + t.countRow() + "\n");
        }
        if (mysql.existsDatabase("FISCH")) {
//            base.drop();
        }

        // User
        //Create User without new Database
        mysql.createUser("One", "password", false);
        User One = mysql.getUser("One");
        One.setPassword("PASSWORD");

        //Create User with new Database
        mysql.createUser("Two", "password", true);
        User two = mysql.getUser("Two");
        two.setPassword("PASSWORD");

        //System.out.println(mysql.existsUser("ENTE"));
        for (User u : mysql.getUsers()) {
            System.out.println("[Username]: " + u.getName());
        }

        //Drop User without Database
        mysql.getUser("One").drop(false);

        //Drop User with Database
        mysql.getUser("Two").drop(true);

//        System.out.println("[Version]: " + mysql.getVersion());
        // END
        mysql.disconnect();
    }
}
