package de.goldendeveloper.mysql.examples;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.*;


public class InsertDataIntoTable {

    public InsertDataIntoTable() {
        String databaseName = "DATABASE-NAME";
        String tableName = "TABLE-NAME";
        MYSQL mysql = new MYSQL("hostname", "username", "PASSWORD", 3306);
        if (mysql.existsDatabase(databaseName)) {
            Database database = mysql.getDatabase(databaseName);
            if (database.existsTable(tableName)) {
                Table table = database.getTable(tableName);
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

