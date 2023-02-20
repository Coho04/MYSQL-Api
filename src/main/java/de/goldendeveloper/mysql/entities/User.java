package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

    private final String name;
    private final MYSQL mysql;

    public User(String name, MYSQL mysql) {
        this.name = name;
        this.mysql = mysql;
    }

    public String getName() {
        return this.name;
    }

    public void drop(Boolean database) {
        try {
            Connection connect = mysql.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("DROP USER '" + this.name + "'@'localhost';");
            if (database) {
                statement.execute("DROP DATABASE " + this.name + ";");
            }
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPermission(int PERMISSION) {
        try {
            Connection connect = mysql.getConnect();
            Statement statement = connect.createStatement();
            switch (PERMISSION) {
                case 0:
                    statement.execute("GRANT ALL PRIVILEGES ON * TO '" + this.name + "'@'localhost';");
                    break;
                case 1:
                    statement.execute("GRANT DELETE ON * TO '" + this.name + "'@'localhost';");
                    break;
                case 2:
                    statement.execute("GRANT INSERT ON * TO '" + this.name + "'@'localhost';");
                    break;
                case 3:
                    statement.execute("GRANT REFERENCES ON * TO '" + this.name + "'@'localhost';");
                    break;
                case 4:
                    statement.execute("GRANT SELECT ON * TO '" + this.name + "'@'localhost';");
                    break;
                case 5:
                    statement.execute("GRANT TRIGGER ON * TO '" + this.name + "'@'localhost';");
                    break;
                case 6:
                    statement.execute("GRANT UPDATE ON * TO '" + this.name + "'@'localhost';");
                    break;
                case 7:
                    statement.execute("GRANT EXECUTE ON * TO '" + this.name + "'@'localhost';");
                    break;
            }
            MYSQL.close(null, statement);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public void setPermissionToDatabase(int PERMISSION, Database database) {
        try {
            Connection connect = mysql.getConnect();
            Statement statement = connect.createStatement();
            switch (PERMISSION) {
                case 0:
                    statement.execute("GRANT ALL PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 1:
                    statement.execute("GRANT DELETE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 2:
                    statement.execute("GRANT INSERT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 3:
                    statement.execute("GRANT REFERENCES PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 4:
                    statement.execute("GRANT SELECT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 5:
                    statement.execute("GRANT TRIGGER PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 6:
                    statement.execute("GRANT UPDATE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 7:
                    statement.execute("GRANT EXECUTE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
            }
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePermission(int PERMISSION) {
        try {
            Connection connect = mysql.getConnect();
            Statement statement = connect.createStatement();
            switch (PERMISSION) {
                case 0:
                    statement.execute("REVOKE ALL PRIVILEGES ON * FROM '" + this.name + "'@'localhost';");
                    break;
                case 1:
                    statement.execute("REVOKE DELETE ON * FROM '" + this.name + "'@'localhost';");
                    break;
                case 2:
                    statement.execute("REVOKE INSERT ON * FROM '" + this.name + "'@'localhost';");
                    break;
                case 3:
                    statement.execute("REVOKE REFERENCES ON * FROM '" + this.name + "'@'localhost';");
                    break;
                case 4:
                    statement.execute("REVOKE SELECT ON * FROM '" + this.name + "'@'localhost';");
                    break;
                case 5:
                    statement.execute("REVOKE TRIGGER ON * FROM '" + this.name + "'@'localhost';");
                    break;
                case 6:
                    statement.execute("REVOKE UPDATE ON * FROM '" + this.name + "'@'localhost';");
                    break;
                case 7:
                    statement.execute("REVOKE EXECUTE ON * FROM '" + this.name + "'@'localhost';");
                    break;
            }
            MYSQL.close(null, statement);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public void removePermissionToDatabase(int PERMISSION, Database database) {
        try {
            Connection connect = mysql.getConnect();
            Statement statement = connect.createStatement();
            switch (PERMISSION) {
                case 0:
                    statement.execute("REVOKE ALL PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 1:
                    statement.execute("REVOKE DELETE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 2:
                    statement.execute("REVOKE INSERT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 3:
                    statement.execute("REVOKE REFERENCES PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 4:
                    statement.execute("REVOKE SELECT PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 5:
                    statement.execute("REVOKE TRIGGER PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 6:
                    statement.execute("REVOKE UPDATE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
                case 7:
                    statement.execute("REVOKE EXECUTE PRIVILEGES ON " + database.getName() + "  TO '" + this.name + "'@'localhost';");
                    break;
            }
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPassword(String password) {
        try {
            Connection connect = mysql.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("SET PASSWORD FOR '" + this.name + "'@'localhost' = PASSWORD('" + password + "');");
            MYSQL.close(null, statement);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }
}
