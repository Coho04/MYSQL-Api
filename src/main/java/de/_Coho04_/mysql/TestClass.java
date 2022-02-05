package de._Coho04_.mysql;

import de._Coho04_.mysql.entities.*;

public class TestClass {

    public static void TEST(String[] bot) {
        String hostname = ID.hostname;
        String user = ID.user;
        String password = ID.password;
        int port = ID.port;

        MYSQL mysql = new MYSQL(hostname, user, password, 3306);

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
        database.createTable(TableNAME, "", MysqlTypes.INT);
        database.getTables();
        database.tableExists(TableNAME);
        database.drop();
        database.getTable(TableNAME);

        Table table = database.getTable(TableNAME);
        table.getName();
        table.countRow();
        table.drop();
//        table.getRowByItem("NAME", "NAME");
        table.showColumnsInTable();
        table.columnExists("NAME");
        table.addColumn("NAME", MysqlTypes.INT);
        table.addColumn("NAME", MysqlTypes.INT, 20);
        table.getDatabase();
        table.getColumn("NAME");
        table.insert(new Row(table, database).with("", "").with("", ""));

        Column cmn = table.getColumn("NAME");
        cmn.drop();
        cmn.setItemNull("NAME");
        cmn.setNull();
        cmn.setName("NAME");
        cmn.getDatabase();
        cmn.getTable();
        cmn.getName();

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

    public static void createColumns(Table table) {
        table.addColumn("BIT", MysqlTypes.BIT);
        table.addColumn("TINYINT", MysqlTypes.TINYINT);
        table.addColumn("SMALLINT", MysqlTypes.SMALLINT);
        table.addColumn("INT", MysqlTypes.INT);
        table.addColumn("BIGINT", MysqlTypes.BIGINT);
        table.addColumn("DECIMAL", MysqlTypes.DECIMAL);
        table.addColumn("NUMERIC", MysqlTypes.NUMERIC);
        table.addColumn("FLOAT", MysqlTypes.FLOAT);
        table.addColumn("REAL", MysqlTypes.REAL);
        table.addColumn("DOUBLE", MysqlTypes.DOUBLE);
        table.addColumn("DATE", MysqlTypes.DATE);
        table.addColumn("TIME", MysqlTypes.TIME);
        table.addColumn("DATETIME", MysqlTypes.DATETIME);
        table.addColumn("TIMESTAMP", MysqlTypes.TIMESTAMP);
        table.addColumn("YEAR", MysqlTypes.YEAR);
        table.addColumn("CHAR", MysqlTypes.CHAR);
        table.addColumn("VARCHAR", MysqlTypes.VARCHAR, 20);
        table.addColumn("TEXT", MysqlTypes.TEXT);
        table.addColumn("NCHAR", MysqlTypes.NCHAR);
        table.addColumn("NVARCHAR", MysqlTypes.NVARCHAR, 20);
        table.addColumn("BINARY", MysqlTypes.BINARY);
        table.addColumn("VARBINARY", MysqlTypes.VARBINARY, 20);
        table.addColumn("BLOB", MysqlTypes.BLOB);
        table.addColumn("JSON", MysqlTypes.JSON);
        table.addColumn("BOOLEAN", MysqlTypes.BOOLEAN);
    }
}
