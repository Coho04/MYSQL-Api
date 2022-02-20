package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class Row {

    private Table table;

    private Database db;

    public HashMap<String, String> map;

    private static Statement statement = MYSQL.statement;

    public Row(Table table, Database db) {
        this.db = db;
        this.table = table;
        map = new HashMap<>();
    }

    public List<Column> showColumns() {
        return table.getColumns();
    }

    public List<Column> getColumns() {
        return table.getColumns();
    }

    public Row with(String column, String item) {
        map.put(column, item);
        return this;
    }

        /*
        BIT
TINYINT
SMALLINT
INT
BIGINT
DECIMAL
NUMERIC
FLOAT
REAL
DOUBLE
DATE
TIME
DATETIME
TIMESTAMP
YEAR
CHAR
TEXT
NCHAR
NVARCHAR
BINARY
VARBINARY
BLOB
JSON
BOOLEAN

    List<Row> rowList = res.getRows();
    for(Row r : rowList) {
        System.out.println(r.get("column1") + "   " + r.get("column2"));
    }
*/

    public Database getDatabase() {
        return this.db;
    }

    public Table getTable() {
        return this.table;
    }
}
