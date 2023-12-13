package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.interfaces.QueryHelper;
import de.goldendeveloper.mysql.entities.enums.Permissions;

/**
 * Represents a User in the application.
 */
@SuppressWarnings("unused")
public class User implements QueryHelper {

    private final String name;
    private final MYSQL mysql;

    /**
     * Represents a User in the application.
     */
    public User(String name, MYSQL mysql) {
        this.name = name;
        this.mysql = mysql;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Drops the user from the database. If the database parameter is true, it also drops the corresponding database.
     *
     * @param database true if the database should also be dropped, false otherwise
     */
    public void drop(boolean database) {
        executeUpdate("DROP USER '" + this.name + "'@'localhost';", mysql);
        if (database) {
            executeUpdate("DROP DATABASE " + this.name + ";", mysql);
        }
    }

    /**
     * Sets the permissions for the user.
     *
     * @param permissions The permissions to be set for the user.
     */
    public void setPermission(Permissions permissions) {
        String query = "";
        switch (permissions) {
            case ALL -> query = "GRANT ALL PRIVILEGES ON * TO '" + this.name + "'@'localhost';";
            case DELETE -> query = "GRANT DELETE ON * TO '" + this.name + "'@'localhost';";
            case INSERT -> query = "GRANT INSERT ON * TO '" + this.name + "'@'localhost';";
            case REFERENCES -> query = "GRANT REFERENCES ON * TO '" + this.name + "'@'localhost';";
            case SELECT -> query = "GRANT SELECT ON * TO '" + this.name + "'@'localhost';";
            case TRIGGER -> query = "GRANT TRIGGER ON * TO '" + this.name + "'@'localhost';";
            case UPDATE -> query = "GRANT UPDATE ON * TO '" + this.name + "'@'localhost';";
            case EXECUTE -> query = "GRANT EXECUTE ON * TO '" + this.name + "'@'localhost';";
        }
        if (!query.isBlank()) {
            executeUpdate(query, mysql);
        }
    }

    /**
     * Sets the specified permissions to the given database for the current user.
     *
     * @param permissions The permissions to be set for the database.
     * @param database    The database for which the permissions are to be set.
     */
    public void setPermissionToDatabase(Permissions permissions, Database database) {
        String query = "";
        switch (permissions) {
            case ALL ->
                    query = "GRANT ALL PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case DELETE ->
                    query = "GRANT DELETE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case INSERT ->
                    query = "GRANT INSERT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case REFERENCES ->
                    query = "GRANT REFERENCES PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case SELECT ->
                    query = "GRANT SELECT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case TRIGGER ->
                    query = "GRANT TRIGGER PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case UPDATE ->
                    query = "GRANT UPDATE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case EXECUTE ->
                    query = "GRANT EXECUTE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
        }
        if (!query.isBlank()) {
            executeUpdate(query, mysql);
        }
    }

    /**
     * Removes the specified permissions from the user.
     *
     * @param permissions The permissions to be removed from the user.
     */
    public void removePermission(Permissions permissions) {
        String query = "";
        switch (permissions) {
            case ALL -> query = "REVOKE ALL PRIVILEGES ON * FROM '" + this.name + "'@'localhost';";
            case DELETE -> query = "REVOKE DELETE ON * FROM '" + this.name + "'@'localhost';";
            case INSERT -> query = "REVOKE INSERT ON * FROM '" + this.name + "'@'localhost';";
            case REFERENCES -> query = "REVOKE REFERENCES ON * FROM '" + this.name + "'@'localhost';";
            case SELECT -> query = "REVOKE SELECT ON * FROM '" + this.name + "'@'localhost';";
            case TRIGGER -> query = "REVOKE TRIGGER ON * FROM '" + this.name + "'@'localhost';";
            case UPDATE -> query = "REVOKE UPDATE ON * FROM '" + this.name + "'@'localhost';";
            case EXECUTE -> query = "REVOKE EXECUTE ON * FROM '" + this.name + "'@'localhost';";
        }
        if (!query.isBlank()) {
            executeUpdate(query, mysql);
        }
    }

    /**
     * Removes the specified permissions from the user for the given database.
     *
     * @param PERMISSION The permissions to be removed from the user.
     * @param database   The database from which to remove the permissions.
     */
    public void removePermissionToDatabase(Permissions PERMISSION, Database database) {
        String query = "";
        switch (PERMISSION) {
            case ALL ->
                    query = "REVOKE ALL PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case DELETE ->
                    query = "REVOKE DELETE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case INSERT ->
                    query = "REVOKE INSERT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case REFERENCES ->
                    query = "REVOKE REFERENCES PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case SELECT ->
                    query = "REVOKE SELECT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case TRIGGER ->
                    query = "REVOKE TRIGGER PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case UPDATE ->
                    query = "REVOKE UPDATE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
            case EXECUTE ->
                    query = "REVOKE EXECUTE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';";
        }
        if (!query.isBlank()) {
            executeUpdate(query, mysql);
        }
    }

    /**
     * Sets the password for the user.
     *
     * @param password The password to be set for the user.
     */
    public void setPassword(String password) {
        executeUpdate("SET PASSWORD FOR '" + this.name + "'@'localhost' = PASSWORD('" + password + "');", mysql);
    }
}
