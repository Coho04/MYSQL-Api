package de._Coho04_.mysql;

import de._Coho04_.mysql.entities.*;

import java.util.HashMap;

public class TestClass {


    public static void TEST(String[] args) {
        MYSQL mysql = new MYSQL("hostname", "user", "password", 3306);

        String DatabaseNAME = "GERHARD";
        String TableNAME = "UTA";

        mysql.getVersion();
        mysql.getUsers();
        mysql.existsUser("NAME");
        mysql.customExecute("SQL");
        mysql.customQueryExecute("SQL");
        mysql.getFrom("NAME", "NAME");
        mysql.onFlushPrivileges();
        mysql.showTables();
        mysql.describeTable("NAME");
        mysql.createUser("NAME", "NAME",false);
        mysql.getUser("NAME");
        mysql.createDatabase(DatabaseNAME);
        mysql.existsDatabase(DatabaseNAME);
        mysql.switchDatabase(DatabaseNAME);
        mysql.getDatabase(DatabaseNAME);

        Database database = mysql.getDatabase(DatabaseNAME);
        database.getName();
        database.setName("BETTINA");
        database.rename("HANS");
        database.createTable(TableNAME);
        database.getTables();
        database.existsTable(TableNAME);
        database.drop();
        database.getTable(TableNAME);

        Table table = database.getTable(TableNAME);
        table.getName();
        table.countRow();
        table.drop();
        table.getRow(table.getColumn("NAME"), "NAME");
        table.getColumns();
        table.existsColumn("NAME");
        table.addColumn("NAME", MysqlTypes.INT);
        table.addColumn("NAME", MysqlTypes.INT, 20);
        table.getDatabase();
        table.getColumn("NAME");
        table.getColumn("NAME").getAll();
        table.insert(new Row(table, database).with("", "").with("", ""));

        // TODO
//        table.setItemByID("ID", "COLUMN", "ITEM");


        Column cmn = table.getColumn("NAME");
        cmn.drop();
        cmn.setItemNull("NAME");
        cmn.setNull();
        cmn.setName("NAME");
        cmn.getDatabase();
        cmn.getTable();
        cmn.getName();
        cmn.getAll();

        User usr = mysql.getUser("NAME");
        usr.drop(true);
        usr.setPermission(Permissions.ALL);
        usr.setPermissionToDatabase(Permissions.EXECUTE, database);
        usr.removePermission(Permissions.REFERENCES);
        usr.removePermissionToDatabase(Permissions.EXECUTE, database);
        usr.setPassword("PASSWORD");
        usr.getName();

        mysql.disconnect();
    }

    public static void DatabaseBeispiel() {
        /* Get Database */
        MYSQL mysql = new MYSQL("hostname", "user", "password", 3306);
        String databaseName = "TestDatabase";
        if (mysql.existsDatabase(databaseName)) {
            Database database = mysql.getDatabase(databaseName);
                /* Your Code here */
        }
    }

    public static void TableBeispiel() {
        /* Get Table */
        MYSQL mysql = new MYSQL("hostname", "user", "password", 3306);
        String databaseName = "TestDatabase";
        if (mysql.existsDatabase(databaseName)) {
            Database database = mysql.getDatabase(databaseName);
            String tableName = "TestTable";
            if (database.existsTable(tableName)) {
                Table table = database.getTable(tableName);
            }
        }
    }

    public static void ColumnBeispiel() {
        /* Get Column */
        MYSQL mysql = new MYSQL("hostname", "user", "password", 3306);
        String databaseName = "TestDatabase";
        if (mysql.existsDatabase(databaseName)) {
            Database database = mysql.getDatabase(databaseName);
            String tableName = "TestTable";
            if (database.existsTable(tableName)) {
                Table table = database.getTable(tableName);
                String columnName = "TestColumn";
                if (table.existsColumn(columnName)) {
                    Column column = table.getColumn(columnName);
                }
            }
        }
    }

    public static void InsertTableBeispiel() {
        /* Insert Table */
        MYSQL mysql = new MYSQL("hostname", "user", "password", 3306);
        String databaseName = "TestDatabase";
        if (mysql.existsDatabase(databaseName)) {
            Database database = mysql.getDatabase(databaseName);
            String tableName = "TestTable";
            if (database.existsTable(tableName)) {
                Table table = database.getTable(tableName);
                String columnName = "TestColumn";
                if (!table.existsColumn(columnName)) {
                    table.insert(new Row(table,database).with(columnName, "TestItem"));
                }
            }
        }
    }
}
