package de.goldendeveloper.mysql.examples;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.SearchResult;
import de.goldendeveloper.mysql.entities.Table;

import java.util.HashMap;

public class ReadRowFromDatabase {

    public static void main(String[] args) {
        MYSQL mysql = new MYSQL("hostname", "username", "PASSWORD", 3306);
        if (mysql.existsDatabase("DATABASE-NAME")) {
            Database database = mysql.getDatabase("DATABASE-NAME");
            if (database.existsTable("TABLE-NAME")) {
                Table table = database.getTable("");
                Row row = table.getRow(table.getColumn("COLUMN Name"), "SEARCH-VALUE");
                HashMap<String, SearchResult> rowData = row.getData();

                String resultOne = rowData.get("COLUMN-NAME").getAsString();
                System.out.println(resultOne);
            }
        }
    }
}
