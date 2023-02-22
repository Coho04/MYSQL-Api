package de.goldendeveloper.mysql.examples;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.*;


public class InsertDataIntoTable {

    public static void main(String[] args) {
        MYSQL mysql = new MYSQL("hostname", "username", "PASSWORD", 3306);
        if (mysql.existsDatabase("DATABASE-NAME")) {
            Database database = mysql.getDatabase("DATABASE-NAME");
            if (database.existsTable("TABLE-NAME")) {
                Table table = database.getTable("");
                table.insert(
                        new RowBuilder()
                                .with(table.getColumn("ColumnOne-Name"), "VALUE")
                                .with(table.getColumn("ColumnTwo-Name"), "VALUE")
                                .with(table.getColumn("ColumnThree-Name"), "VALUE")
                                .build()
                );
            }
        }
    }
}

