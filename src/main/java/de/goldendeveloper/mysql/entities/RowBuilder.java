package de.goldendeveloper.mysql.entities;

import java.util.HashMap;

/**
 * Represents a builder class for building rows to be inserted into a database table.
 */
public class RowBuilder {

    private final HashMap<String, String> insertMap = new HashMap<>();

    /**
     * Inserts a key-value pair into the insertMap HashMap.
     * The key is the name of the given column, and the value is converted to a String using String.valueOf().
     *
     * @param column The Column object representing the column in the database table.
     * @param item   The value to be inserted into the column.
     * @return The updated RowBuilder object.
     */
    public RowBuilder with(Column column, Object item) {
        this.insertMap.put(column.getName(), String.valueOf(item));
        return this;
    }

    /**
     * Builds and returns a HashMap containing the key-value pairs that were inserted using the with() method.
     *
     * @return A HashMap containing the key-value pairs.
     */
    public HashMap<String, String> build() {
        return insertMap;
    }
}
