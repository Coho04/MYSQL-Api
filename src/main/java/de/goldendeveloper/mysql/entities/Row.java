package de.goldendeveloper.mysql.entities;

import java.util.HashMap;
import java.util.List;

public class Row {

    private final Table table;

    private final Database db;

    public HashMap<String, String> map;

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

    public Database getDatabase() {
        return this.db;
    }

    public Table getTable() {
        return this.table;
    }
}
