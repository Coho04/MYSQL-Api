# MYSQL-Api
JMA (Java MYSQL API)

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
        database.createTable(TableNAME, "", MysqlTypes.INT);
        database.getTables();
        database.tableExists(TableNAME);
        database.drop();
        database.getTable(TableNAME);

        Table table = database.getTable(TableNAME);
        table.getName();
        table.countRow();
        table.drop();
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
