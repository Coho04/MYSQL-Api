package de.goldendeveloper.mysql.examples;

import de.goldendeveloper.mysql.errors.ExceptionHandler;

public class CustomExceptionHandler extends ExceptionHandler {

    @Override
    public void callException(Exception exception) {
        /*
        *
        *  Your Code here
        *
        * */

        // For Sentry use the following code
        /*
         *
         *  Sentry.captureException(exception);
         *
         * */
    }
}
