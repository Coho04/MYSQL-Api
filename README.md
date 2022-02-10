# MYSQL-Api
JMA (Java MYSQL API)

Verbindung aufbauen:

        String DatabaseNAME = "exampleDatabase";
        
        MYSQL mysql = new MYSQL("hostname", "user", "password", 3306);
        if (mysql.existsDatabase(DatabaseNAME)) {
                /** Your Code here **/
        }
      

        
        String TableNAME = "testTable";

        mysql.getVersion();
        mysql.getUsers();
        mysql.existsUser("NAME");
        mysql.customExecute("SQL");
        mysql.customQueryExecute("SQL");
        mysql.getFrom("NAME", "NAME");
        mysql.onFlushPrivileges();
        mysql.showTables();
        mysql.describeTable("NAME");
        
        mysql.getUser("NAME");
        mysql.createDatabase(DatabaseNAME);
        mysql.existsDatabase(DatabaseNAME);
        mysql.switchDatabase(DatabaseNAME);
        mysql.getDatabase(DatabaseNAME);
       
Database bekommen / bearbeiten: 

        Database database = mysql.getDatabase(DatabaseNAME);
        database.getName();
        database.setName("BETTINA");
        database.rename("HANS");
        database.createTable(TableNAME, "", MysqlTypes.INT);
        database.getTables();
        database.tableExists(TableNAME);
        database.drop();
        database.getTable(TableNAME);
        
Tabelle bekommen / bearbeiten: 

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
        cmn.setItemNull("NAME");
        cmn.setNull();
        cmn.setName("NAME");
        cmn.getDatabase();
        cmn.getTable();
        cmn.getName();
User:
Erstellen / Passwort setzen: 
        
        mysql.createUser("NAME", "NAME",false);
        user.setPassword("PASSWORD");
         
Getten:        
        User usr = mysql.getUser("NAME");
Set Permissions:      
        usr.setPermission(Permissions.ALL);
        usr.setPermissionToDatabase(Permissions.EXECUTE, database);
        
Remove Permissions: 
        usr.removePermission(Permissions.REFERENCES);
        usr.removePermissionToDatabase(Permissions.EXECUTE, database);
        
    
        usr.getName();
        
Löschen: 

        user.drop();
        table.drop();
        database.drop();
        column.drop();
      
Verbindung trennen:

        mysql.disconnect();
