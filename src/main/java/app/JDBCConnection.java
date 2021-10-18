package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

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
    
    public HashMap<String, Integer> getLgaY12IndigHashMap() {
        HashMap<String, Integer> y12LGA = new HashMap<String, Integer>();

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
                
                String  LgaName      = results.getString("Name");
                String  y12CountTemp = results.getString("Indig_Y12");
                Integer y12Count     = Integer.parseInt(y12CountTemp);

                // For now we will just store the movieName and ignore the id
                y12LGA.put(LgaName, y12Count);
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

    /** outcomeBuilder is here to build the ArrayList that will store the OutcomeTracker objects
     * at the moment it only populates the education metric
     * this method will need to change if we hardcode/store the metrics for each outcome
     * However, we could adapt it to read these hardcoded values.
     * */
    public ArrayList<OutcomeTracker> outcomeBuilder() {
        ArrayList<OutcomeTracker> y12LGA = new ArrayList<OutcomeTracker>();

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
                // Store the results of this query
                String  lgaName         = results.getString("Name");
                int lgaCode             = Integer.parseInt(results.getString("Code"));
                int y12Count            = Integer.parseInt(results.getString("Indig_Y12"));

                // Create a new OutcomeTracker object and set the appropriate values
                OutcomeTracker myObject = new OutcomeTracker();
                myObject.setOutcomes("raw", 5, y12Count);
                myObject.setLga(lgaName, lgaCode);

                // Add this OutcomeTracker object to the methods ArrayList
                y12LGA.add(myObject);
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
     * 
     * @param stateInteger
     *  1 - NSW
        2 - Victoria
        3 - QLD
        4 - South Australia
        5 - Western Australia
        6 - Tasmania
        7 - Northern Territory
        8 - ACT
        9 - Other Australian Territories
     * @return
     */
    public int outcome5State(int stateInteger) {
        int myValue = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT SUM(count) as result FROM SchoolStatistics "
            +              "WHERE substr(lga_code16 , 1,1) = '" + stateInteger
            +              "' AND indigenous_status = 'indig' AND School = 'y12_equiv';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                myValue = results.getInt("result");
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
        return myValue;
    }
}