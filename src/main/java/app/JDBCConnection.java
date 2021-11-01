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

    /***
     * @param OutcomeList is the list of lga's with attached outcomes that this method will help build
     * @param inputQuery 
     * @param outcomeNumAndType (1,5,6 or 8) + ('r' or 'p') is the number  of the outcome we are interested in
     * @see this method requires the SQL columns to be areaCode, value and areaName
     * @return nothing, the arraylist will be modified
     */
    public void thymeleafHookUp(ArrayList<thymeleafOutcomes> OutcomeList, String inputQuery, String outcomeNumAndType) {

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get Result
            ResultSet results = statement.executeQuery(inputQuery);

            // Process all of the results
            // The "results" variable is similar to an array
            // We can iterate through all of the database query results
            while (results.next()) {

                // Store the results of this query
                int    lgaCode = results.getInt("areaCode");
                Double   value = Double.parseDouble(results.getString("value"));
                String lgaName = results.getString("areaName");
                boolean  found = false;

                for (thymeleafOutcomes entry : OutcomeList) {
                    if (entry.areaCode == lgaCode){
                        entry.setOutcomes(outcomeNumAndType, value);
                        found = true;
                    }
                }

                if (found == false){
                    // Create a new OutcomeTracker object and set the appropriate values
                    thymeleafOutcomes myObject = new thymeleafOutcomes();
                    myObject.setOutcomes(outcomeNumAndType, value);
                    myObject.areaCode = lgaCode;
                    myObject.areaName = lgaName;
                    // Add this OutcomeTracker object to the methods ArrayList
                    OutcomeList.add(myObject);
                }
                


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
    }    
    /**
     * create a view in the SQL database
     */
    public void createSqlView(String query) {
        
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // Generate view in data
            ResultSet results = statement.executeQuery(query);

            // Process all of the results

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
    }
}