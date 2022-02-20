package de._Coho04_.mysql.entities;

import de._Coho04_.mysql.MYSQL;

import java.sql.SQLException;
import java.sql.Statement;

public class User {

    private static Statement statement = MYSQL.statement;
    private String name;

    // SELECT user, host FROM mysql.user;
    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void drop(Boolean database) {
        try {
            statement.execute("DROP USER '" + this.name + "'@'localhost';");
            if (database) {
                statement.execute("DROP DATABASE " + this.name + ";");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPermission(int PERMISSION) {
        try {
            switch (PERMISSION) {
                case 0 -> statement.execute("GRANT ALL PRIVILEGES ON * TO '" + this.name + "'@'localhost';");
                case 1 -> statement.execute("GRANT DELETE ON * TO '" + this.name + "'@'localhost';");
                case 2 -> statement.execute("GRANT INSERT ON * TO '" + this.name + "'@'localhost';");
                case 3 -> statement.execute("GRANT REFERENCES ON * TO '" + this.name + "'@'localhost';");
                case 4 -> statement.execute("GRANT SELECT ON * TO '" + this.name + "'@'localhost';");
                case 5 -> statement.execute("GRANT TRIGGER ON * TO '" + this.name + "'@'localhost';");
                case 6 -> statement.execute("GRANT UPDATE ON * TO '" + this.name + "'@'localhost';");
                case 7 -> statement.execute("GRANT EXECUTE ON * TO '" + this.name + "'@'localhost';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPermissionToDatabase(int PERMISSION, Database database) {
        try {
            switch (PERMISSION) {
                case 0 -> statement.execute("GRANT ALL PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 1 -> statement.execute("GRANT DELETE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 2 -> statement.execute("GRANT INSERT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 3 -> statement.execute("GRANT REFERENCES PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 4 -> statement.execute("GRANT SELECT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 5 -> statement.execute("GRANT TRIGGER PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 6 -> statement.execute("GRANT UPDATE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 7 -> statement.execute("GRANT EXECUTE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePermission(int PERMISSION) {
        try {
            switch (PERMISSION) {
                case 0 -> statement.execute("REVOKE ALL PRIVILEGES ON * FROM '" + this.name + "'@'localhost';");
                case 1 -> statement.execute("REVOKE DELETE ON * FROM '" + this.name + "'@'localhost';");
                case 2 -> statement.execute("REVOKE INSERT ON * FROM '" + this.name + "'@'localhost';");
                case 3 -> statement.execute("REVOKE REFERENCES ON * FROM '" + this.name + "'@'localhost';");
                case 4 -> statement.execute("REVOKE SELECT ON * FROM '" + this.name + "'@'localhost';");
                case 5 -> statement.execute("REVOKE TRIGGER ON * FROM '" + this.name + "'@'localhost';");
                case 6 -> statement.execute("REVOKE UPDATE ON * FROM '" + this.name + "'@'localhost';");
                case 7 -> statement.execute("REVOKE EXECUTE ON * FROM '" + this.name + "'@'localhost';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePermissionToDatabase(int PERMISSION, Database database) {
        try {
            switch (PERMISSION) {
                case 0 -> statement.execute("REVOKE ALL PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 1 -> statement.execute("REVOKE DELETE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 2 -> statement.execute("REVOKE INSERT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 3 -> statement.execute("REVOKE REFERENCES PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 4 -> statement.execute("REVOKE SELECT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 5 -> statement.execute("REVOKE TRIGGER PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 6 -> statement.execute("REVOKE UPDATE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                case 7 -> statement.execute("REVOKE EXECUTE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPassword(String password) {
        try {
            statement.execute("SET PASSWORD FOR '" + this.name + "'@'localhost' = PASSWORD('" + password + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
