package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database. Allows SQL
 * queries to be used with the SQLLite Databse in Java.
 * 
 * This is an example JDBC Connection that has a single query for the Movies
 * Database This is similar to the project workshop JDBC examples.
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
     * @param OutcomeList       is the list of lga's with attached outcomes that
     *                          this method will help build
     * @param inputQuery
     * @param outcomeNumAndType (1,5,6 or 8) + ('r' or 'p') is the number of the
     *                          outcome we are interested in
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
            int indexer = 0;
            while (results.next()) {
                int lgaCode;
                Double value;
                String lgaName;

                // Store the results of this query
                lgaCode = results.getInt("areaCode");
                value = Double.parseDouble(results.getString("value"));
                lgaName = results.getString("areaName");

                if (OutcomeList.size() != 0 && OutcomeList.get(indexer).areaCode == lgaCode) {
                    OutcomeList.get(indexer).setOutcomes(outcomeNumAndType, value);
                    OutcomeList.get(indexer).areaCode = lgaCode;
                    OutcomeList.get(indexer).areaName = lgaName;
                    indexer += 1;
                } else {
                    boolean found = false;
                    for (var obj : OutcomeList) {
                        if (obj.areaCode == lgaCode) {
                            obj.setOutcomes(outcomeNumAndType, value);
                            found = true;
                        }
                    }
                    if (found == false) {
                        thymeleafOutcomes myObject = new thymeleafOutcomes();
                        myObject.setOutcomes(outcomeNumAndType, value);
                        myObject.areaCode = lgaCode;
                        myObject.areaName = lgaName;
                        // Add this OutcomeTracker object to the methods ArrayList
                        OutcomeList.add(myObject);
                    }
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
     *
     */
    public void filterHookUp(ArrayList<filterOutcomes> OutcomeList, String inputQuery, String outcomeNumAndType) {

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
                int lgaCode;
                Double value;
                Double proportion;
                String lgaName;
                Double lgaPopulation;
                boolean found;

                try {
                    // Store the results of this query
                    lgaCode = results.getInt("areaCode");
                    value = Double.parseDouble(results.getString("value"));
                    proportion = Double.parseDouble(results.getString("proportion"));
                    lgaName = results.getString("areaName");
                    lgaPopulation = Double.parseDouble(results.getString("pValue"));
                    found = false;
                } catch (Exception e) {
                    lgaCode = 42069;
                    value = null;
                    proportion = null;
                    lgaName = null;
                    lgaPopulation = null;
                    found = false;
                }

                for (filterOutcomes entry : OutcomeList) {
                    if (entry.areaCode == lgaCode) {
                        entry.setOutcomes(outcomeNumAndType, value, proportion, lgaPopulation);
                        entry.setGap();
                        found = true;
                    }
                }

                if (found == false) {
                    // Create a new OutcomeTracker object and set the appropriate values
                    filterOutcomes myObject = new filterOutcomes();
                    myObject.setOutcomes(outcomeNumAndType, value, proportion, lgaPopulation);
                    myObject.setGap();
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

    /***
     * @param inputQuery Is a query which will return details of a given lgacode
     * 
     * @see this method requires the SQL column to be areaCode
     * @return a hashmap with values area_sqkm, lga_type16, population
     */
    public HashMap<String, String> level3LGADetails(String inputQuery) {
        HashMap<String, String> SimilarDetails = new HashMap<>();
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

            while (results.next()) {
                SimilarDetails.put("area_sqkm", results.getString("area_sqkm"));
                SimilarDetails.put("lga_type16", results.getString("lga_type16"));
                SimilarDetails.put("population", results.getString("population"));
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
        return SimilarDetails;
    }

    /***
     * @param LGACodeList Is the list of LGA codes that match the condition
     * @param inputQuery  Is a query which will return lgaCodes of matching similar
     *                    LGAs
     * 
     * @see this method requires the SQL column to be areaCode
     * @return nothing, the arraylist will be modified
     */
    public void level3LGAHashSet(HashSet<Integer> LGACodeList, String inputQuery) {

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

            while (results.next()) {
                Integer lgaCode;

                lgaCode = results.getInt("areaCode");

                LGACodeList.add(lgaCode);
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

    public void level3LGALocationTool(ArrayList<thymeleafOutcomes> OutcomeList, HashMap<String,Double> inputDetails, int inputLGACode) {


        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String inputQuery = "SELECT lga_code16 as areaCode, longitude, latitude FROM LGAs;";
            // Get Result
            ResultSet results = statement.executeQuery(inputQuery);

            while (results.next()) {
                Double latitude= results.getDouble("latitude");
                Double longitude= results.getDouble("longitude");
                int areaCode = results.getInt("areaCode");
                for (thymeleafOutcomes obj : OutcomeList) {
                    if(obj.areaCode == areaCode){
                        obj.latitude = latitude;
                        obj.longitude = longitude;
                        if (obj.areaCode == inputLGACode){
                            inputDetails.put("latitude",latitude);
                            inputDetails.put("longitude",longitude);
                        }
                        break;
                    }
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