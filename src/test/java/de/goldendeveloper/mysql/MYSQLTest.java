package de.goldendeveloper.mysql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MYSQLTest {

    private MYSQL setupTest() {
        return new MYSQL("localhost", "root", "", 3306);
    }

    @Test
    void setPassword() {
        setupTest().setPassword("password");
    }

    @Test
    void setHostname() {
        setupTest().setHostname("localhost");
    }

    @Test
    void setPort() {
        setupTest().setPort(3306);
    }

    @Test
    void setUsername() {
        setupTest().setUsername("root");
    }

    @Test
    void getUsername() {
        assertEquals("root", setupTest().getUsername());
    }

    @Test
    void getPort() {
        assertEquals(3306, setupTest().getPort());
    }

    @Test
    void getHostname() {
        assertEquals("localhost", setupTest().getHostname());
    }

    @Test
    void getVersion() {
        assertFalse(setupTest().getVersion().isEmpty());
    }

    @Test
    void getUsers() {
        assertFalse(setupTest().getUsers().isEmpty());
    }

    @Test
    void existsDatabase() {
        if (!setupTest().existsDatabase("test_mysql_api"))
            setupTest().createDatabase("test_mysql_api");
        assertTrue(setupTest().existsDatabase("test_mysql_api"));
    }

    @Test
    void existsUser() {
        assertTrue(setupTest().existsUser("root"));
    }

    @Test
    void customExecute() {
        setupTest().customExecute("CREATE TABLE IF NOT EXISTS test_mysql_api.test_table (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(30) NOT NULL)");
    }

    @Test
    void getDatabase() {
        if (!setupTest().existsDatabase("test_mysql_api"))
            setupTest().createDatabase("test_mysql_api");
        assertNotNull(setupTest().getDatabase("test_mysql_api"));
    }

    @Test
    void createDatabase() {
        if (!setupTest().existsDatabase("test_mysql_api"))
            setupTest().createDatabase("test_mysql_api");
        assertTrue(setupTest().existsDatabase("test_mysql_api"));
    }

    @Test
    void onFlushPrivileges() {
        setupTest().onFlushPrivileges();
    }

    @Test
    void switchDatabase() {
        MYSQL mysql = setupTest();
        if (!mysql.existsDatabase("test_mysql_api"))
            mysql.createDatabase("test_mysql_api");
        mysql.switchDatabase(setupTest().getDatabase("test_mysql_api"));
        assertEquals("test_mysql_api", mysql.getDatabase("test_mysql_api").getName());
    }

    @Test
    void createUser() {
        MYSQL mysql = setupTest();
        if (mysql.existsUser("test_user"))
            assertNotNull(mysql.getUser("test_user"));
    }

    @Test
    void getDatabases() {
        MYSQL mysql = setupTest();
        if (!mysql.existsDatabase("test_mysql_api"))
            mysql.createDatabase("test_mysql_api");
        assertFalse(mysql.getDatabases().isEmpty());
    }

    @Test
    void getUser() {
        MYSQL mysql = setupTest();
        if (!mysql.existsUser("test_user"))
            mysql.createUser("test_user", "test_password", false);
        assertNotNull(mysql.getUser("test_user"));
    }

    @Test
    void getExceptionHandlerClass() {
        assertNotNull(setupTest().getExceptionHandlerClass());
    }

    @Test
    void close() {
        setupTest().close();
    }

    @Test
    void getConnect() {
        assertNotNull(setupTest().getConnect());
    }
}