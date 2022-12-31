MYSQL-Api (English)

The MYSQL api also called JMA is responsible for communication between a MYSQL server and your Java project. Important: To be able to use the Api optimally SQL knowledge is recommended but not necessary.

JMA (Java MYSQL API)

[![](https://img.shields.io/badge/License-BSD--2-informational.svg)](LICENSE)
[![](https://jitpack.io/v/Golden-Developer/MYSQL-Api.svg)](https://jitpack.io/#Golden-Developer/MYSQL-Api)
[![](https://img.shields.io/badge/Java-18-success?logo=java)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![](https://img.shields.io/badge/Golden--Developer-Mysql--Api-brightgreen?logo=golden-developer.de/img/Golden-Developer-logo.png)](https://www.golden-developer.de/)
[![](https://jitpack.io/v/Golden-Developer/MYSQL-Api/month.svg)](https://jitpack.io/#Golden-Developer/MYSQL-Api)

![a](https://repository-images.githubusercontent.com/456002691/dbb9c775-7291-4d06-aa96-6ef3d0901630)

Maven Integration: 

Repository:

	<repository>
        <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
            
Dependency:

    <dependency>
	    <groupId>com.github.golden-developer</groupId>
	    <artifactId>MYSQL-Api</artifactId>
	    <version>{$version}</version>
	</dependency>

To connect to the MYSQL-Api a new MYSQL instance is created with the server Ip, the username, a password, and the port:
            
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
        database.createTable(TableName);
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
        table.addColumn("NAME");
        table.getDatabase();
        table.getColumn("NAME");
        table.insert(new RowBuilder()
                        .with(table.getColumn("ColumnNameOne"), "ColumnTwoValue")
                        .with(table.getColumn("ColumnNameTwo"), "ColumnTwoValue")
                    .build();        
                    );

        Column column = table.getColumn("NAME");
        column.setItemNull("NAME");
        column.setNull();
        column.getRandom().toString();
        column.setName("NAME");
        column.getDatabase();
        column.getTable();
        column.getName();

To read the data from the table see the following figure:

    Row row = table.getRow(table.getColumn(EXAMPLE_COLUMN_NAME), VALUE_IN_COLUMN);
    HashMap<String, SearchResult> rowData = row.getData();
    SearchResult result = rowData.get(EXAMPLE_COLUMN_NAME);
    int i = result.getAsInt();
    System.out.println(i);
        
when creating a user a password is set and a true / false query if the corresponding database should be created. This means that both a password and a username are required: 
        
        mysql.createUser("NAME", "NAME",false);
        user.setPassword("PASSWORD");
         
        User user = mysql.getUser("NAME");
              
        user.setPermission(Permissions.ALL);
        user.setPermissionToDatabase(Permissions.EXECUTE, database);
        
        user.removePermission(Permissions.REFERENCES);
        user.removePermissionToDatabase(Permissions.EXECUTE, database);
    
        user.getName();
        
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

Um mit der MYSQL-Api eine verbindung aufzubauen wird eine neue MYSQL instance erstellt mit der Server Ip dem User name, einem passwort, und dem port:
        
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
       
Um eine Datenbank zu bekommen, beziehungsweise zu bearbeiten siehe code unten: 

        Database database = mysql.getDatabase(DatabaseNAME);
        database.getName();
        database.setName("BETTINA");
        database.rename("HANS");
        database.createTable(TableName);
        database.getTables();
        database.tableExists(TableNAME);
        database.drop();
        database.getTable(TableNAME);
        
Um Tabellen zu bekommen, beziehungsweise zu bearbeiten wird eine Database instance benötigt (siehe oben): 

        Table table = database.getTable(TableNAME);
        table.getName();
        table.countRow();
        table.drop();
        table.showColumnsInTable();
        table.columnExists("NAME");
        table.addColumn("NAME");
        table.getDatabase();
        table.getColumn("NAME");
        table.insert(new RowBuilder()
                        .with(table.getColumn("ColumnNameOne"), "ColumnTwoValue")
                        .with(table.getColumn("ColumnNameTwo"), "ColumnTwoValue")
                    .build();        
                    );

        Column cmn = table.getColumn("NAME");
        cmn.setItemNull("NAME");
        cmn.setNull();
        cmn.setName("NAME");
        cmn.getRandom().toString();
        cmn.getDatabase();
        cmn.getTable();
        cmn.getName();

Um die Daten aus der Tabelle auslesen zu können siehe folgende Abbildung:
    
    Row row = table.getRow(table.getColumn(EXAMPLE_COLUMN_NAME), VALUE_IN_COLUMN);
    HashMap<String, SearchResult> rowData = row.getData();
    SearchResult result = rowData.get(EXAMPLE_COLUMN_NAME);
    int i = result.getAsInt();
    System.out.println(i);

Beim Erstellen eines Benutzers wird ein Passwort gesetzt und eine true /false abfrage, ob die dazugehörige Datenbank erstellt werden soll. Dass bedeutet das sowohl ein Passwort als auch ein Benutzername benötigt wird: 
        
        mysql.createUser("NAME", "NAME",false);
        user.setPassword("PASSWORD");
                 
        User user = mysql.getUser("NAME");
            
        user.setPermission(Permissions.ALL);
        user.setPermissionToDatabase(Permissions.EXECUTE, database);
        
        user.removePermission(Permissions.REFERENCES);
        user.removePermissionToDatabase(Permissions.EXECUTE, database);
        
        user.getName();
        
Um Objekte zu löschen wird einem Objekt die Methode drop angehangen: 

        user.drop();
        table.drop();
        database.drop();
        column.drop();
      
Um die Verbindung zum Server zu beenden wird dem MYSQL die Methode disconnect angehangen:

        mysql.disconnect();




