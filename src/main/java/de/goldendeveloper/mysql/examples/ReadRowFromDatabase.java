package de.goldendeveloper.mysql.examples;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.SearchResult;
import de.goldendeveloper.mysql.entities.Table;

import java.util.HashMap;

public class ReadRowFromDatabase {

    public ReadRowFromDatabase() {
        String databaseName = "DATABASE-NAME";
        String tableName = "TABLE-NAME";
        MYSQL mysql = new MYSQL("hostname", "username", "PASSWORD", 3306);
        if (mysql.existsDatabase(databaseName)) {
            Database database = mysql.getDatabase(databaseName);
            if (database.existsTable(tableName)) {
                Table table = database.getTable(tableName);
                Row row = table.getRow(table.getColumn("COLUMN Name"), "SEARCH-VALUE");
                HashMap<String, SearchResult> rowData = row.getData();

                String resultOne = rowData.get("COLUMN-NAME").getAsString();
                System.out.println(resultOne);
            }
        }
    }
}
