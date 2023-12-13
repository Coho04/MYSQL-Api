package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.enums.Permissions;
import de.goldendeveloper.mysql.interfaces.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockSettings;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * Test class that validates the behavior of the {@link User} class, mainly focusing on the "drop" method.
 */
public class UserTest implements TestHelper {
    private User user;
    private MYSQL mysql;

    @BeforeEach
    public void setUp() {
        this.mysql = setupMysql();
        this.user = mysql.getOrCreateUser("testUser", "password");
    }

    @Test
    public void setsAllPermissions() {
        this.user.setPermission(Permissions.ALL);
        verify(mysql).executeUpdate("GRANT ALL PRIVILEGES ON * TO 'testUser'@'localhost';", mysql);
    }

    @Test
    public void setsDeletePermission() {
        this.user.setPermission(Permissions.DELETE);
        verify(mysql).executeUpdate("GRANT DELETE ON * TO 'testUser'@'localhost';", mysql);
    }

    @Test
    public void dropsUserAndDatabase() {
        this.user.drop(true);
        verify(mysql).executeUpdate("DROP USER 'testUser'@'localhost';", mysql);
        verify(mysql).executeUpdate("DROP DATABASE testUser;", mysql);
    }

    @Test
    public void dropsUserOnly() {
        this.user.drop(false);
        verify(mysql).executeUpdate("DROP USER 'testUser'@'localhost';", mysql);
        verify(mysql, never()).executeUpdate("DROP DATABASE testUser;", mysql);
    }

    @Test
    public void setsPassword() {
        this.user.setPassword("password");
        verify(mysql).executeUpdate("SET PASSWORD FOR 'testUser'@'localhost' = PASSWORD('password');", mysql);
    }

    @Test
    public void setsPermissionToDatabase() {
        Database database = Mockito.mock(Database.class);
        when(database.getName()).thenReturn("testDatabase");
        this.user.setPermissionToDatabase(Permissions.ALL, database);
        verify(mysql).executeUpdate("GRANT ALL PRIVILEGES ON testDatabase TO 'testUser'@'localhost';", mysql);
    }

    @Test
    public void removesAllPermissions() {
        this.user.removePermission(Permissions.ALL);
        verify(mysql).executeUpdate("REVOKE ALL PRIVILEGES ON * FROM 'testUser'@'localhost';", mysql);
    }

    @Test
    public void removesPermissionToDatabase() {
        Database database =Mockito.mock(Database.class, "testDatabase");

        when(database.getName()).thenReturn("testDatabase");
        this.user.removePermissionToDatabase(Permissions.ALL, database);
        verify(mysql).executeUpdate("REVOKE ALL PRIVILEGES ON testDatabase TO 'testUser'@'localhost';", mysql);
    }
}