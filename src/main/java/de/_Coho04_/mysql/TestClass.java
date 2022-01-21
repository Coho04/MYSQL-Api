package de._Coho04_.mysql;

import de._Coho04_.mysql.entities.Database;

public class TestClass {

    public static void main(String[] bot) {
        MYSQL mysql = new MYSQL("138.201.202.55", "Basti", "basti?hibst", 3306);
        mysql.createDatabase("FISCH");
        Database date = mysql.getDatabase("");
//        mysql.dropDatabase("FISCH");
//        mysql.dropTable("");
//        mysql.getFrom("test", "FISCH").toString();
        mysql.close();
    }
}
