package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.Statement;

public class User {

    private final String name;
    private final MYSQL mysql;

    public User(String name, MYSQL mysql) {
        this.name = name;
        this.mysql = mysql;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("unused")
    public void drop(Boolean database) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("DROP USER '" + this.name + "'@'localhost';");
            if (database) {
                statement.execute("DROP DATABASE " + this.name + ";");
            }
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            mysql.callException(e);
        }
    }

    @SuppressWarnings("unused")
    public void setPermission(Permissions PERMISSION) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            switch (PERMISSION) {
                case ALL -> statement.execute("GRANT ALL PRIVILEGES ON * TO '" + this.name + "'@'localhost';");
                case DELETE -> statement.execute("GRANT DELETE ON * TO '" + this.name + "'@'localhost';");
                case INSERT -> statement.execute("GRANT INSERT ON * TO '" + this.name + "'@'localhost';");
                case REFERENCES -> statement.execute("GRANT REFERENCES ON * TO '" + this.name + "'@'localhost';");
                case SELECT -> statement.execute("GRANT SELECT ON * TO '" + this.name + "'@'localhost';");
                case TRIGGER -> statement.execute("GRANT TRIGGER ON * TO '" + this.name + "'@'localhost';");
                case UPDATE -> statement.execute("GRANT UPDATE ON * TO '" + this.name + "'@'localhost';");
                case EXECUTE -> statement.execute("GRANT EXECUTE ON * TO '" + this.name + "'@'localhost';");
            }
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            mysql.callException(e);
        }
    }

    @SuppressWarnings("unused")
    public void setPermissionToDatabase(Permissions PERMISSION, Database database) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            switch (PERMISSION) {
                case ALL ->
                        statement.execute("GRANT ALL PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case DELETE ->
                        statement.execute("GRANT DELETE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case INSERT ->
                        statement.execute("GRANT INSERT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case REFERENCES ->
                        statement.execute("GRANT REFERENCES PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case SELECT ->
                        statement.execute("GRANT SELECT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case TRIGGER ->
                        statement.execute("GRANT TRIGGER PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case UPDATE ->
                        statement.execute("GRANT UPDATE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case EXECUTE ->
                        statement.execute("GRANT EXECUTE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
            }
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            mysql.callException(e);
        }
    }

    @SuppressWarnings("unused")
    public void removePermission(Permissions PERMISSION) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            switch (PERMISSION) {
                case ALL -> statement.execute("REVOKE ALL PRIVILEGES ON * FROM '" + this.name + "'@'localhost';");
                case DELETE -> statement.execute("REVOKE DELETE ON * FROM '" + this.name + "'@'localhost';");
                case INSERT -> statement.execute("REVOKE INSERT ON * FROM '" + this.name + "'@'localhost';");
                case REFERENCES -> statement.execute("REVOKE REFERENCES ON * FROM '" + this.name + "'@'localhost';");
                case SELECT -> statement.execute("REVOKE SELECT ON * FROM '" + this.name + "'@'localhost';");
                case TRIGGER -> statement.execute("REVOKE TRIGGER ON * FROM '" + this.name + "'@'localhost';");
                case UPDATE -> statement.execute("REVOKE UPDATE ON * FROM '" + this.name + "'@'localhost';");
                case EXECUTE -> statement.execute("REVOKE EXECUTE ON * FROM '" + this.name + "'@'localhost';");
            }
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            mysql.callException(e);
        }
    }

    @SuppressWarnings("unused")
    public void removePermissionToDatabase(Permissions PERMISSION, Database database) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            switch (PERMISSION) {
                case ALL ->
                        statement.execute("REVOKE ALL PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case DELETE ->
                        statement.execute("REVOKE DELETE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case INSERT ->
                        statement.execute("REVOKE INSERT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case REFERENCES ->
                        statement.execute("REVOKE REFERENCES PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case SELECT ->
                        statement.execute("REVOKE SELECT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case TRIGGER ->
                        statement.execute("REVOKE TRIGGER PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case UPDATE ->
                        statement.execute("REVOKE UPDATE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case EXECUTE ->
                        statement.execute("REVOKE EXECUTE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
            }
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            mysql.callException(e);
        }
    }

    @SuppressWarnings("unused")
    public void setPassword(String password) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("SET PASSWORD FOR '" + this.name + "'@'localhost' = PASSWORD('" + password + "');");
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            mysql.callException(e);
        }
    }
}
