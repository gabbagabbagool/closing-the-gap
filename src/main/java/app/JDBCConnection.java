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
     * 
     * I think later, this method should be put in main, called once, creating an object that each page can reference?
     * */
    public ArrayList<lgaOutcomeTracker> outcomeBuilder() {
        ArrayList<lgaOutcomeTracker> y12LGA = new ArrayList<lgaOutcomeTracker>();

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
                String y12Count            = results.getString("Indig_Y12");

                // Create a new OutcomeTracker object and set the appropriate values
                lgaOutcomeTracker myObject = new lgaOutcomeTracker();
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

    /***
     * @param OutcomeList is the list of lga's with attached outcomes that this method will help build
     * @param inputQuery 
     * @param outcomeNum (1,5,6 or 8) is the number  of the outcome we are interested in
     * @param outcomeType (raw/proportional)
     * @see this method requires the SQL columns to be lgaCode, value and lgaName
     * @return nothing, the arraylist will be modified
     */
    public void theLgaHookUp(ArrayList<lgaOutcomeTracker> OutcomeList, String inputQuery, int outcomeNum, String outcomeType) {

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
                int    lgaCode = results.getInt("lgaCode");
                String   value = results.getString("value");
                String lgaName = results.getString("lgaName");
                boolean  found = false;

                for (lgaOutcomeTracker entry : OutcomeList) {
                    if (entry.getLgaCode() == lgaCode){
                        entry.setOutcomes(outcomeType, outcomeNum, value);
                        found = true;
                    }
                }

                if (found == false){
                    // Create a new OutcomeTracker object and set the appropriate values
                    lgaOutcomeTracker myObject = new lgaOutcomeTracker();
                    myObject.setOutcomes(outcomeType, outcomeNum, value);
                    myObject.setLga(lgaName, lgaCode);
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


    /***
     * @param OutcomeList is the list of state's with attached outcomes that this method will help build
     * @param inputQuery is the query to pass to SQL
     * @param outcomeNum (1,5,6 or 8) is the number  of the outcome we are interested in
     * @param outcomeType (raw/proportional)
     * @see this method requires the SQL columns to be stateCode, value and stateName
     * @return nothing, the arraylist will be modified
     */
    public void theStateHookUp(ArrayList<stateOutcomeTracker> OutcomeList, String inputQuery, int outcomeNum, String outcomeType) {

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
                int    stateCode = results.getInt("stateCode");
                String     value = results.getString("value");
                String stateName = results.getString("stateName");
                boolean    found = false;
                

                for (stateOutcomeTracker entry : OutcomeList) {
                    if (entry.getStateCode() == stateCode){
                        entry.setOutcomes(outcomeType, outcomeNum, value);
                        found = true;
                    }
                }

                if (found == false){
                    // Create a new OutcomeTracker object and set the appropriate values
                    stateOutcomeTracker myObject = new stateOutcomeTracker();
                    myObject.setOutcomes(outcomeType, outcomeNum, value);
                    myObject.setState(stateName, stateCode);
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

    public HashMap<String, Integer> outcome6Lga() {
        HashMap<String, Integer> out6LGA = new HashMap<String, Integer>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT q.lga_code16 AS Code, LGAs.lga_name16 AS Name, SUM(q.count) AS Indig_Qual" +
            "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16" +
            "WHERE q.indigenous_status = 'indig'" +
            "GROUP BY q.lga_code16;";
            
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
                String  out6CountTemp = results.getString("Indig_Qual");
                Integer out6Count     = Integer.parseInt(out6CountTemp);

                // For now we will just store the movieName and ignore the id
                out6LGA.put(LgaName, out6Count);
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
        return out6LGA;
    }
}