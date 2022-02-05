package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.Statement;
import java.util.HashMap;

public class Row {

// private String name;

    private Table table;

    private Database db;

    public HashMap<String, String> map;

    private static Statement statement = MYSQL.statement;

    public Row(/*String name,*/ Table table, Database db) {
//        this.name = name;
        this.db = db;
        this.table = table;
        map = new HashMap<>();
    }

/*    public List<Column> showColumns() {
        List<Column> list = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SHOW COLUMNS IN "+ this.name + " from " + this.name + ";");
            while (rs.next()) {
                list.add(new Column(rs.getString(1), this.getTable(), this.getDatabase()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }*/

    public Row with(String column, String item) {
        map.put(column, item);
        return this;
    }

    /*
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
