package io.github.coho04.mysql.entities;

/**
 * The SearchResult class represents a single search result item.
 * It provides methods to retrieve the item in different data types.
 */
public class SearchResult {

    private final String item;

    /**
     * The SearchResult class represents a single search result item.
     * It provides methods to retrieve the item in different data types.
     */
    public SearchResult(String item) {
        this.item = item;
    }

    /**
     * Retrieves the item as a string.
     *
     * @return The item as a string.
     */
    public String getAsString() {
        return this.item;
    }

    /**
     * Retrieves the item as a boolean value.
     *
     * @return The item as a boolean value.
     */
    public Boolean getAsBoolean() {
        return Boolean.parseBoolean(item);
    }

    /**
     * Retrieves the item as an integer.
     *
     * @return The item as an integer.
     */
    public int getAsInt() {
        return Integer.parseInt(item);
    }

    /**
     * Retrieves the item as a long value.
     *
     * @return The item as a long value.
     */
    public long getAsLong() {
        return Long.parseLong(item);
    }

    /**
     * Retrieves the item as a double value.
     *
     * @return The item as a double value.
     */
    public double getAsDouble() {
        return Double.parseDouble(item);
    }

    /**
     * Retrieves the item as a short value.
     *
     * @return The item as a short value.
     */
    public Short getAsShort() {
        return Short.parseShort(item);
    }

    /**
     * Retrieves the item as a float value.
     *
     * @return The item as a float value.
     */
    public float getAsFloat() {
        return Float.parseFloat(item);
    }

    /**
     * Retrieves the item as an Object.
     *
     * @return The item as an Object.
     */
    public Object getAsObject() {
        return item;
    }

    /**
     * Retrieves the item as a byte value.
     *
     * @return The item as a byte value.
     */
    public byte getAsByte() {
        return Byte.parseByte(item);
    }
}
