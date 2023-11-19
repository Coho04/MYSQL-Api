package de.goldendeveloper.mysql.exceptions;

public class NoConnectionException extends Exception {
    public NoConnectionException(String message) {
        super(message);
        System.out.println("No Connection to MYSQL Server");
    }

    public NoConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
