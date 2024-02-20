package de.goldendeveloper.mysql.interfaces;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface QueryHelper {

    /**
     * Executes a query on the database and returns the result.
     *
     * @param query   the SQL query to execute
     * @param handler the ResultSetHandler to process the query result
     * @param mysql   the MYSQL object representing the database connection
     * @param <T>     the type of the query result
     * @return the processed query result, or null if an exception occurred
     */
//    default <T> T executeQuery(String query, ResultSetHandler<T> handler, MYSQL mysql) {
//        try {
//            Statement statement = mysql.getConnect().createStatement();
//            ResultSet rs = statement.executeQuery(query);
//            T result = null;
//            if (rs.next()) {
//                result = handler.handle(rs);
//            }
//            mysql.closeRsAndSt(rs, statement);
//            return result;
//        } catch (Exception e) {
//            mysql.callException(e);
//        }
//        return null;
//    }


    default <T> List<T> executeQuery(String query, ResultSetHandler<T> handler, MYSQL mysql) {
        List<T> results = new ArrayList<>();
        try {
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) { // Ändern Sie `if` zu `while`, um alle Zeilen zu verarbeiten
                T result = handler.handle(rs);
                if (result != null) {
                    results.add(result);
                }
            }
            mysql.closeRsAndSt(rs, statement);
        } catch (Exception e) {
            mysql.callException(e);
        }
        return results; // Gibt eine Liste von Ergebnissen zurück
    }

    /**
     * Executes an update query on the database.
     *
     * @param query the SQL update query to execute
     * @param mysql the MYSQL object representing the database connection
     */
    default  void executeUpdate(String query, MYSQL mysql) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute(query);
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            mysql.callException(e);
        }
    }

    @FunctionalInterface
    interface ResultSetHandler<T> {
        /**
         * Handles the result set returned from a database query.
         *
         * @param rs the result set to handle
         * @return the processed result of type T
         * @throws Exception if an error occurs while handling the result set
         */
        T handle(ResultSet rs) throws Exception;
    }
}
