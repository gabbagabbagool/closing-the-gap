package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 * 
 * This is an example JDBC Connection that has a single query for the Movies Database
 * This is similar to the project workshop JDBC examples.
 *
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    private static final String DATABASE = "jdbc:sqlite:database/ctg.db";

    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get LGA that starts with Ba - placeholder
     */
    public ArrayList<String> getMovies() {
        ArrayList<String> y12LGA = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT LGAs.lga_name16 AS Name FROM LGAs WHERE LGAs.lga_name16 LIKE 'Ba%';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            // The "results" variable is similar to an array
            // We can iterate through all of the database query results
            while (results.next()) {
                // We can lookup a column of the a single record in the
                // result using the column name
                // BUT, we must be careful of the column type!
                
                String LgaName     = results.getString("Name");
               

                // For now we will just store the movieName and ignore the id
                y12LGA.add(LgaName);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return y12LGA;
    }

/**
     * Get LGA Y12 indig for level 2 - orange data
     */
    public ArrayList<String> getLgaY12Indig() {
        ArrayList<String> y12LGA = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT s.lga_code16 AS Code, LGAs.lga_name16 AS Name, SUM(s.count) AS Indig_Y12 " +
                        "FROM SchoolStatistics AS s JOIN LGAs ON s.lga_code16 = LGAs.lga_code16 " +
                        "WHERE s.School = 'y12_equiv' AND s.indigenous_status = 'indig' " +
                        "GROUP BY s.lga_code16, s.indigenous_status, s.School;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            // The "results" variable is similar to an array
            // We can iterate through all of the database query results
            while (results.next()) {
                // We can lookup a column of the a single record in the
                // result using the column name
                // BUT, we must be careful of the column type!
                
                String LgaName     = results.getString("Name");
                String y12Count     = results.getString("Indig_Y12");

                // For now we will just store the movieName and ignore the id
                y12LGA.add(LgaName);
                y12LGA.add(y12Count);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return y12LGA;
    }
}