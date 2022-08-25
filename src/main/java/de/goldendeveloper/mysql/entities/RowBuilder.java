package de.goldendeveloper.mysql.entities;

import java.util.HashMap;

public class RowBuilder {

    private final HashMap<String, String> insertMap;

    public RowBuilder() {
        this.insertMap = new HashMap<>();
    }

    public RowBuilder with(Column column, String item) {
        this.insertMap.put(column.getName(), item);
        return this;
    }

    public RowBuilder with(Column column, Boolean item) {
        this.insertMap.put(column.getName(), String.valueOf(item));
        return this;
    }

    public RowBuilder with(Column column, Long item) {
        this.insertMap.put(column.getName(), String.valueOf(item));
        return this;
    }

    public RowBuilder with(Column column, Integer item) {
        this.insertMap.put(column.getName(), String.valueOf(item));
        return this;
    }

    public HashMap<String, String> build() {
        return insertMap;
    }
}
