package io.github.coho04.mysql.exceptions;

/**
 * The NoConnectionException is an exception thrown when there is no connection to the MySQL server.
 * It extends the Exception class.
 */
public class NoConnectionException extends Exception {

    /**
     * NoConnectionException is an exception thrown when there is no connection to the MySQL server.
     * It extends the Exception class.
     */
    public NoConnectionException(String message) {
        super(message);
        System.out.println("No Connection to MYSQL Server");
    }

    /**
     * Creates a new instance of NoConnectionException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method). A null value is permitted.
     */
    public NoConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
