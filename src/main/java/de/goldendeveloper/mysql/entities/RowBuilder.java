package de.goldendeveloper.mysql.entities;

import java.util.HashMap;

public class RowBuilder {

    private final HashMap<String, String> insertMap = new HashMap<>();

    public RowBuilder with(Column column, Object item) {
        this.insertMap.put(column.getName(), String.valueOf(item));
        return this;
    }

    public HashMap<String, String> build() {
        return insertMap;
    }
}
