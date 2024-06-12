package io.github.coho04.mysql.entities;

import java.util.HashMap;

/**
 * The RowBuilder class is used to build a row for database insertion.
 * It allows users to insert key-value pairs into a HashMap using the with() method, and retrieve the built HashMap using the build() method.
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
