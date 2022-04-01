package de.goldendeveloper.mysql.entities;

import java.util.HashMap;
import java.util.List;

public class Row {

    private final Table table;

    private final Database db;

    public HashMap<String, String> map;

    public Row(Table table) {
        this.db = table.getDatabase();
        this.table = table;
        map = new HashMap<>();
    }

    public List<Column> showColumns() {
        return table.getColumns();
    }

    public List<Column> getColumns() {
        return table.getColumns();
    }

    public Row with(Column column, String item) {
        map.put(column.getName(), item);
        return this;
    }

    public Database getDatabase() {
        return this.db;
    }

    public Table getTable() {
        return this.table;
    }
}
