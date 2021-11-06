package app;

import java.util.ArrayList;
import java.util.Collections;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Temporary HTML as an example page.
 * 
 * Based on the Project Workshop code examples. This page currently: - Provides
 * a link back to the index page - Displays the list of movies from the Movies
 * Database using the JDBCConnection
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class level3LGA implements Handler {

    // URL of this page relative to http://localhost:7000/
    public static final String URL = "/level3LGA.html";

    private static final String TEMPLATE = ("level3LGA.html");

    @Override
    public void handle(Context context) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        JDBCConnection jdbc = new JDBCConnection();

        ArrayList<thymeleafOutcomes> level3LGA = new ArrayList<>();

        // If we visit this page from a GET request, e.g. a navigation from navbar
        if (context.method().equalsIgnoreCase("get")) {
            // Populate default selections for checkbox & radio
            model.put("outcome1", "true");
            model.put("outcome5", "true");
            model.put("outcome6", "true");
            model.put("outcome8", "true");
            model.put("radio", "p");
        }
        // If we visit this page from a POST request
        else {
            // Populate chosen selections for checkbox & radio
            model.put("outcome1", context.formParam("checkboxOutcome1"));
            model.put("outcome5", context.formParam("checkboxOutcome5"));
            model.put("outcome6", context.formParam("checkboxOutcome6"));
            model.put("outcome8", context.formParam("checkboxOutcome8"));
            model.put("radio", context.formParam("radio"));
        }
        // Store the form options that were selected for sorting
        String sortSelect = context.formParam("outcomeSortSelect");
        String outcomeSortOrder = context.formParam("outcomeSortOrder");

        populateData(model, jdbc, level3LGA, sortSelect);

        similarities(context, model, jdbc, level3LGA);

        sorting(model, level3LGA, sortSelect, outcomeSortOrder);

        model.put("sortSelect", sortSelect);
        model.put("outcomeSortOrder", outcomeSortOrder);
        model.put("tableData", level3LGA);
        model.put("currentPage", "level3LGA");

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.render(TEMPLATE, model);
    }

    private void populateData(Map<String, Object> model, JDBCConnection jdbc, ArrayList<thymeleafOutcomes> level3LGA,
            String sortSelect) {
        String inputQuery;
        // If outcome 1 has been selected
        if ((model.get("outcome1") != null) || (sortSelect.equals("1"))) {
            if (model.get("outcome1") == null) {
                model.put("outcome1", "true");
                model.put("error",
                        "Outcome 1 was chosen to sort, whilst not selected to display. The site has included outcome 1 in the results");
            }

            // If the raw radio is selected
            if (model.get("radio").equals("r")) {
                // We ask the database to return a raw count
                inputQuery = "SELECT LGAs.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(p.count) AS value FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 WHERE p.indigenous_status = 'indig' AND  p.age = '_65_yrs_ov' GROUP BY LGAs.lga_code16;";
            } else {
                inputQuery = "SELECT LGAs.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, IFNULL(ROUND(SUM(indig.over_65) * 1.0 / SUM(every.over_65) * 100.0, 2),0) AS value FROM all_over_65 AS every JOIN indig_over_65 AS indig ON every.lga_code16 = indig.lga_code16 JOIN LGAs ON every.lga_code16 = LGAs.lga_code16 GROUP BY LGAs.lga_code16 ORDER BY LGAs.lga_code16;";
            }
            String outcomeNumAndType = "1";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level3LGA, inputQuery, outcomeNumAndType);
        }
        // if outcome 5 has been selected
        if ((model.get("outcome5") != null) || (sortSelect.equals("5"))) {
            if (model.get("outcome5") == null) {
                model.put("outcome5", "true");
                model.put("error",
                        "Outcome 5 was chosen to sort, whilst not selected to display. The site has included outcome 5 in the results");
            }
            // If the raw radio is selected
            if (model.get("radio").equals("r")) {
                // We ask the database to return a raw count
                inputQuery = "SELECT LGAs.lga_code16 AS areaCode, SUM(Indig_Y12.total) AS value, LGAs.lga_name16 AS areaName FROM Indig_Y12 JOIN LGAs ON Indig_Y12.Code = LGAs.lga_code16 GROUP BY LGAs.lga_code16; ";
            } else {
                inputQuery = "SELECT LGAs.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, IFNULL(ROUND(SUM(Indig_Y12.Total) * 1.0 / (SUM(indig_finish_hs.finish_hs) ) * 100, 2), -42069) AS value FROM Indig_Y12 JOIN indig_finish_hs ON Code = LGAs.lga_code16 JOIN  LGAs ON indig_finish_hs.lga_code16 = LGAs.lga_code16 GROUP BY Indig_Y12.Code ORDER BY Indig_Y12.Code;";
            }
            String outcomeNumAndType = "5";
            outcomeNumAndType += model.get("radio");
            try {
                jdbc.thymeleafHookUp(level3LGA, inputQuery, outcomeNumAndType);
            } catch (Exception e) {
                System.err.println("Error running thymeleafHookup");
            }
        }
        if ((model.get("outcome6") != null) || (sortSelect.equals("6"))) {
            if (model.get("outcome6") == null) {
                model.put("outcome6", "true");
                model.put("error",
                        "Outcome 6 was chosen to sort, whilst not selected to display. The site has included outcome 6 in the results");
            }
            // If the raw radio is selected
            if (model.get("radio").equals("r")) {
                // We ask the database to return a raw count
                inputQuery = "SELECT LGAs.lga_code16 AS areaCode, SUM(certIII.certIII) AS value, LGAs.lga_name16 AS areaName FROM indig_certIII AS certIII JOIN LGAs ON certIII.lga_code16 = LGAs.lga_code16 GROUP BY LGAs.lga_code16;";
            } else {
                inputQuery = "SELECT LGAs.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, IFNULL(ROUND(SUM(certIII.certIII) * 1.0 / SUM(all_qual.all_qual) * 100.0, 2), -42069) AS value FROM indig_all_qual AS all_qual JOIN indig_certIII AS certIII ON all_qual.lga_code16 = certIII.lga_code16 JOIN LGAs ON certIII.lga_code16 = LGAs.lga_code16 GROUP BY LGAs.lga_code16 ORDER BY LGAs.lga_code16;";
            }
            String outcomeNumAndType = "6";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level3LGA, inputQuery, outcomeNumAndType);
        }
        if ((model.get("outcome8")) != null || (sortSelect.equals("8"))) {
            if (model.get("outcome8") == null) {
                model.put("outcome8", "true");
                model.put("error",
                        "Outcome 8 was chosen to sort, whilst not selected to display. The site has included outcome 8 in the results");
            }
            // If the raw radio is selected
            if (model.get("radio").equals("r")) {
                // We ask the database to return a raw count
                inputQuery = "SELECT sum(employed_indig) AS value, LGAs.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName FROM labour_force_indig JOIN LGAs ON  labour_force_indig.lga_code16 = LGAs.lga_code16 GROUP BY LGAs.lga_code16;";
            } else {
                inputQuery = "SELECT IFNULL(ROUND(100 - (100.0 * unemployed_indig / employed_indig), 1), -42069) AS value, LGAs.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName FROM unemployed_indig NATURAL JOIN labour_force_indig JOIN LGAs ON labour_force_indig.lga_code16 = LGAs.lga_code16 GROUP BY LGAs.lga_code16";
            }
            String outcomeNumAndType = "8";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level3LGA, inputQuery, outcomeNumAndType);
        }
    }

    private void similarities(Context context, Map<String, Object> model, JDBCConnection jdbc,
             ArrayList<thymeleafOutcomes> level3LGA) {
        String inputQuery;
        model.put("inputLGA", context.formParam("inputLGA"));

        if (context.formParam("LGAradio") != null) {
            model.put("LGAradio", context.formParam("LGAradio"));
        } else {
            model.put("LGAradio", "similar");
        }

        // If input is not blank, nor null
        if ((model.get("inputLGA") != "") && (model.get("inputLGA") != null)) {
            Integer inputLGACode = -42069;
            // query for the LGA code

            // Find the LGA with this name and store the code
            boolean found = false;
            for (thymeleafOutcomes obj : level3LGA) {
                // A case insensitive search through the table data for a matching lga name
                if (obj.areaName.toLowerCase().equals(model.get("inputLGA").toString().toLowerCase())) {
                    inputLGACode = obj.areaCode;
                    found = true;
                    break;
                }
            }
            if (found == false) {
                String errorMessage = "An LGA with the name \"";
                errorMessage += model.get("inputLGA");
                errorMessage += "\" was not found";
                model.put("error", errorMessage);
            } else if (context.formParam("LGAradio").equals("similar")) {
                // Based on this LGA code, find population, sqkm and lga type
                inputQuery = "SELECT LGAs.area_sqkm, LGAs.lga_type16, SUM(PopulationStatistics.count) as population FROM LGAs NATURAL JOIN PopulationStatistics WHERE LGAs.lga_code16 = ";
                inputQuery += inputLGACode.toString();
                inputQuery += " GROUP BY LGAs.lga_code16;";
                HashMap<String, String> LGAdetails = jdbc.level3LGADetails(inputQuery);

                Double myValue = Double.parseDouble(LGAdetails.get("area_sqkm")) * .50;
                String areaLowerBound = myValue.toString();
                myValue = Double.parseDouble(LGAdetails.get("area_sqkm")) * 1.50;
                String areaUpperBound = myValue.toString();

                myValue = Double.parseDouble(LGAdetails.get("population")) * .50;
                String populationLowerBound = myValue.toString();
                myValue = Double.parseDouble(LGAdetails.get("population")) * 1.50;
                String populationUpperBound = myValue.toString();

                // create a list of LGA codes where the LGAs have similar population, similar
                // sqkm and the same type
                inputQuery = "SELECT LGAs.lga_code16 as areaCode FROM LGAs NATURAL JOIN entire_population WHERE (LGAs.area_sqkm > ";
                inputQuery += areaLowerBound;
                inputQuery += ") AND  (LGAs.area_sqkm < ";
                inputQuery += areaUpperBound;
                inputQuery += ") AND  LGAs.lga_type16 = '";
                inputQuery += LGAdetails.get("lga_type16");
                inputQuery += "' AND (entire_population.population > ";
                inputQuery += populationLowerBound;
                inputQuery += ") AND (entire_population.population < ";
                inputQuery += populationUpperBound;
                inputQuery += ") GROUP BY LGAs.lga_code16;";

                HashSet<Integer> similarLGAs = new HashSet<Integer>();
                jdbc.level3LGAHashSet(similarLGAs, inputQuery);

                // This is to remove LGA's that do not match the similarity
                Iterator<thymeleafOutcomes> itr = level3LGA.iterator();
                while (itr.hasNext()) {
                    thymeleafOutcomes obj = itr.next();
                    if (!similarLGAs.contains(Integer.valueOf(obj.areaCode))) {
                        itr.remove();
                    }
                }
            } else if (context.formParam("LGAradio").equals("distance")) {
                Double inputDistance = Double.parseDouble(context.formParam("inputDistance"));
                model.put("inputDistance", inputDistance);
                HashMap<String,Double> inputDetails = new HashMap<>();

                jdbc.level3LGALocationTool(level3LGA, inputDetails, inputLGACode);
                
                Iterator<thymeleafOutcomes> itr = level3LGA.iterator();
                while (itr.hasNext()) {
                    thymeleafOutcomes obj = itr.next();
                    // Uses pythagoras theorem to calculate distance between two points in units of latitude, then multiplies by 111 for units in kilometers
                    double distance = (Math.pow((Math.pow((inputDetails.get("latitude") - obj.latitude),2) + Math.pow((inputDetails.get("longitude") - obj.longitude),2)), .5)) * 111;
                    if (distance > inputDistance) {
                        itr.remove();
                    }
                }
            }
        }
    }

    private void sorting(Map<String, Object> model, ArrayList<thymeleafOutcomes> level3LGA, String sortSelect,
            String outcomeSortOrder) {
        // If there was a selection
        if ((sortSelect != null) && (!sortSelect.equals("null"))) {

            // Switch case to find the outcome to sort
            switch (sortSelect) {
            case "1":
                // Ascending/Descending branching
                if (outcomeSortOrder.equals("ascending")) {
                    // Raw/Proportional branching
                    if (model.get("radio").equals("r")) {
                        Collections.sort(level3LGA, new sortOutcome1RawAscending());
                    } else {
                        Collections.sort(level3LGA, new sortOutcome1ProportionalAscending());
                    }
                    break;
                }
                // Descending branch
                if (model.get("radio").equals("r")) {
                    Collections.sort(level3LGA, new sortOutcome1RawDescending());
                } else {
                    Collections.sort(level3LGA, new sortOutcome1ProportionalDescending());
                }
                break;
            case "5":
                // Ascending/Descending branching
                if (outcomeSortOrder.equals("ascending")) {
                    // Raw/Proportional branching
                    if (model.get("radio").equals("r")) {
                        Collections.sort(level3LGA, new sortOutcome5RawAscending());
                    } else {
                        Collections.sort(level3LGA, new sortOutcome5ProportionalAscending());
                    }
                    break;
                }
                // Descending branch
                if (model.get("radio").equals("r")) {
                    Collections.sort(level3LGA, new sortOutcome5RawDescending());
                } else {
                    Collections.sort(level3LGA, new sortOutcome5ProportionalDescending());
                }
                break;
            case "6":
                // Ascending/Descending branching
                if (outcomeSortOrder.equals("ascending")) {
                    // Raw/Proportional branching
                    if (model.get("radio").equals("r")) {
                        Collections.sort(level3LGA, new sortOutcome6RawAscending());
                    } else {
                        Collections.sort(level3LGA, new sortOutcome6ProportionalAscending());
                    }
                    break;
                }
                // Descending branch
                if (model.get("radio").equals("r")) {
                    Collections.sort(level3LGA, new sortOutcome6RawDescending());
                } else {
                    Collections.sort(level3LGA, new sortOutcome6ProportionalDescending());
                }
                break;
            case "8":
                // Ascending/Descending branching
                if (outcomeSortOrder.equals("ascending")) {
                    // Raw/Proportional branching
                    if (model.get("radio").equals("r")) {
                        Collections.sort(level3LGA, new sortOutcome8RawAscending());
                    } else {
                        Collections.sort(level3LGA, new sortOutcome8ProportionalAscending());
                    }
                    break;
                }
                // Descending branch
                if (model.get("radio").equals("r")) {
                    Collections.sort(level3LGA, new sortOutcome8RawDescending());
                } else {
                    Collections.sort(level3LGA, new sortOutcome8ProportionalDescending());
                }
                break;
            }
        }
    }
}
