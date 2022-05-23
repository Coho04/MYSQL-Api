package de.goldendeveloper.mysql.entities;

public class SearchResult {

    private final String item;

    public SearchResult(String item) {
        this.item = item;
    }

    public String getAsString() {
        return this.item;
    }

    public Boolean getAsBoolean() {
        return Boolean.getBoolean(item);
    }

    public int getAsInt() {
        return Integer.parseInt(item);
    }

    public long getAsLong() {
        return Long.getLong(item);
    }

    public double getAsDouble() {
        return Double.parseDouble(item);
    }

    public Short getAsShort() {
        return Short.parseShort(item);
    }

    public float getAsFloat() {
        return Float.parseFloat(item);
    }

    public Object getAsObject() {
        return item;
    }

    public byte getAsByte() {
        return Byte.parseByte(item);
    }
}
