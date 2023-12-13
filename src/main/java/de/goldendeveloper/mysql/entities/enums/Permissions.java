package de.goldendeveloper.mysql.entities.enums;

/**
 * Represents different permissions that can be assigned to a user or database.
 */
@SuppressWarnings("unused")
public enum Permissions {
    /**
     * Represents the permission for all operations.
     *
     * The ALL variable is a constant of the Permissions enum class and has a value of 0.
     * It represents the permission that grants all operations to a user or database.
     * Use this permission to assign all available permissions to a user or database.
     *
     * Example usage:
     *   Permissions allPermission = Permissions.ALL;
     *   int permissionValue = allPermission.getValue();
     */
    ALL(0),
    /**
     *
     */
    DELETE(1),
    /**
     * Represents the permission for inserting data into a database.
     *
     * The INSERT variable is a constant of the Permissions enum class and has a value of 2.
     * It represents the permission that allows a user or database to insert data into tables.
     *
     * Example usage:
     *   Permissions insertPermission = Permissions.INSERT;
     *   int permissionValue = insertPermission.getValue();
     */
    INSERT(2),

    /**
     *
     */
    REFERENCES(3),
    /**
     * The SELECT variable represents the permission for querying or retrieving data from a database.
     *
     * The SELECT variable is a constant of the Permissions enum class and has a value of 4.
     * It represents the permission that allows a user or database to execute SELECT statements on tables and views,
     * retrieving data from specific columns or all columns.
     *
     * Example usage:
     *   Permissions selectPermission = Permissions.SELECT;
     *   int permissionValue = selectPermission.getValue();
     */
    SELECT(4),
    /**
     * Represents the permission for creating and managing triggers in a database.
     *
     * The TRIGGER variable is a constant of the Permissions enum class and*/
    TRIGGER(5),
    /**
     * Represents the permission for updating data in a database.
     *
     * The UPDATE variable is a constant of the Permissions enum class and has a value of 6.
     * It represents the permission that allows a user or database to execute UPDATE statements on tables, modifying data.
     *
     * Example usage:
     *   Permissions updatePermission = Permissions.UPDATE;
     *   int permissionValue = updatePermission.getValue();
     */
    UPDATE(6),
    /**
     * Represents the permission for executing a procedure or function in a database.
     *
     * The EXECUTE variable is a constant of the Permissions enum class and has a value of 7.
     * It represents the permission that allows a user or database to execute stored procedures or functions.
     * This permission enables the user or database to invoke pre-defined logic in the database.
     *
     * Example usage:
     *     Permissions executePermission = Permissions.EXECUTE;
     *     int permissionValue = executePermission.getValue();
     */
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