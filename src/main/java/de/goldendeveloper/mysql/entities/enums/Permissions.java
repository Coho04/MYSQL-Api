package de.goldendeveloper.mysql.entities.enums;

/**
 * Represents different permissions that can be assigned to a user or database.
 */
@SuppressWarnings("unused")
public enum Permissions {
    ALL(0),
    DELETE(1),
    INSERT(2),
    REFERENCES(3),
    SELECT(4),
    TRIGGER(5),
    UPDATE(6),
    EXECUTE(7);

    private final int value;

    /**
     * Represents different permissions that can be assigned to a user or database.
     * Use the Permissions class to assign specific permissions to a user or database.
     */
    Permissions(int value) {
        this.value = value;
    }

    /**
     * Retrieves the value of a permission.
     *
     * @return The value of the permission.
     */
    public int getValue() {
        return value;
    }
}