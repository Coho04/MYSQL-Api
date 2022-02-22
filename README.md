# MYSQL-Api (English)
JMA (Java MYSQL API)
The MYSQL api also called JMA is responsible for communication between a MYSQL server and your Java project. Important: To be able to use the Api optimally SQL knowledge is recommended but not necessary.

To connect to the MYSQL-Api a new MYSQL instance is created with the server Ip, the user name, a password, and the port:
            
        MYSQL mysql = new MYSQL("hostname", "user", "password", 3306);
        
        String DatabaseNAME = "exampleDatabase";
        if (mysql.existsDatabase(DatabaseNAME)) {
                /** Your Code here **/
        }

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
       
To get or edit a database see code below: 

        Database database = mysql.getDatabase(DatabaseNAME);
        database.getName();
        database.setName("BETTINA");
        database.rename("HANS");
        database.createTable(TableNAME, "", MysqlTypes.INT);
        database.getTables();
        database.tableExists(TableNAME);
        database.drop();
        database.getTable(TableNAME);
        
To get or edit tables a database instance is needed (see above).: 

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
        table.insert(new Row(table, database).with("ColumnNameOne", "ColumnTwoWert").with("ColumnNameTwo", "ColumnTwoWert"));

        Column cmn = table.getColumn("NAME");
        cmn.setItemNull("NAME");
        cmn.setNull();
        cmn.setName("NAME");
        cmn.getDatabase();
        cmn.getTable();
        cmn.getName();
        
when creating a user a password is set and a true / false query if the corresponding database should be created. This means that both a password and a username are required: 
        
        mysql.createUser("NAME", "NAME",false);
        user.setPassword("PASSWORD");
         
        User usr = mysql.getUser("NAME");
              
        usr.setPermission(Permissions.ALL);
        usr.setPermissionToDatabase(Permissions.EXECUTE, database);
        
        usr.removePermission(Permissions.REFERENCES);
        usr.removePermissionToDatabase(Permissions.EXECUTE, database);
    
        usr.getName();
        
To delete objects the method drop is attached to an object: 

        user.drop();
        table.drop();
        database.drop();
        column.drop();
      
To terminate the connection to the server the method disconnect is appended to MYSQL:

        mysql.disconnect();



# MYSQL-Api (German)
JMA (Java MYSQL API)
Die MYSQL-Api auch genannt JMA ist zu kommunikation zwischen einem MYSQL Server und deinem Java Projekt zuständig. Wichtig: Um die Api optimal nutzen zu können sind SQL vorwissen empfohlen aber nicht nötig

Um mit der MYSQL-Api eine verbindung aufzubauen wird eine neue MYSQL instance erstellet mit der Server Ip dem User name, einem passwort, und dem port:
        
        MYSQL mysql = new MYSQL("hostname", "user", "password", 3306);        
        
        String DatabaseNAME = "exampleDatabase";
        if (mysql.existsDatabase(DatabaseNAME)) {
                /** Your Code here **/
        }

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
       
Um eine Datenbank zu bekommen beziehungsweise zu bearbeiten siehe code unten: 

        Database database = mysql.getDatabase(DatabaseNAME);
        database.getName();
        database.setName("BETTINA");
        database.rename("HANS");
        database.createTable(TableNAME, "", MysqlTypes.INT);
        database.getTables();
        database.tableExists(TableNAME);
        database.drop();
        database.getTable(TableNAME);
        
Um Tabellen zu bekommen beziehungsweise zu bearbeiten wird eine Database instance benötigt (siehe oben): 

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

Beim erstellen eines Benutzers wird ein Passwort gesetzt und eine true /false abfrage ob die dazu gehörige Datenbank erstellt werden soll. Dass bedeutet das sowohl ein Passwort als auch ein Benutzername benötigt wird: 
        
        mysql.createUser("NAME", "NAME",false);
        user.setPassword("PASSWORD");
                 
        User usr = mysql.getUser("NAME");
            
        usr.setPermission(Permissions.ALL);
        usr.setPermissionToDatabase(Permissions.EXECUTE, database);
        
        usr.removePermission(Permissions.REFERENCES);
        usr.removePermissionToDatabase(Permissions.EXECUTE, database);
        
        usr.getName();
        
Um Objekte zu löschen wird einem Objekt die Methode drop angehangen: 

        user.drop();
        table.drop();
        database.drop();
        column.drop();
      
Um die Verbindung zum Server zu beenden wird dem MYSQL die Methode disconnect angehangen:

        mysql.disconnect();




