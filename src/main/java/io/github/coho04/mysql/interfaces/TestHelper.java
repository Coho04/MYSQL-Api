package io.github.coho04.mysql.interfaces;


import io.github.coho04.mysql.MYSQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public interface TestHelper {

    default MYSQL setupMysql() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("db.properties"));
            String host = properties.getProperty("db.host");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            int port = Integer.parseInt(properties.getProperty("db.port"));
            return new MYSQL(host, user, password, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
