package app;

import java.util.ArrayList;
import java.util.Collections;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
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
public class level2State implements Handler {

    // URL of this page relative to http://localhost:7000/
    public static final String URL = "/level2State.html";

    private static final String TEMPLATE = ("level2State.html");

    @Override
    public void handle(Context context) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
    
        JDBCConnection jdbc = new JDBCConnection();

        ArrayList<thymeleafOutcomes> level2State = new ArrayList<thymeleafOutcomes>();

        // If we visit this page from a GET request, e.g. a navigation from navbar
        if(context.method().equalsIgnoreCase("get")){
            // Populate default selections for checkbox & radio
            model.put("outcome1", "true");
            model.put("outcome5", "true");
            model.put("outcome6", "true");
            model.put("outcome8", "true");
            model.put("radio", "p");
        }
        // If we visit this page from a POST request
        else{
            // Populate chosen selections for checkbox & radio
            model.put("outcome1", context.formParam("checkboxOutcome1"));
            model.put("outcome5", context.formParam("checkboxOutcome5"));
            model.put("outcome6", context.formParam("checkboxOutcome6"));
            model.put("outcome8", context.formParam("checkboxOutcome8"));
            model.put("radio", context.formParam("radio"));
        }
        String inputQuery;
        // If outcome 1 has been selected
        if (model.get("outcome1") != null){
            
            // If the raw radio is selected
            if (model.get("radio").equals("r")){
                // We ask the database to return a raw count
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, State.stateName AS areaName, SUM(p.count) AS value FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN State ON substr(LGAs.lga_code16, 1, 1) = stateCode WHERE p.indigenous_status = 'indig' AND p.age = '_65_yrs_ov' GROUP BY substr(LGAs.lga_code16, 1, 1);";
            }
            else{
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, State.stateName AS areaName, SUM(p.count) AS value FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN State ON substr(LGAs.lga_code16, 1, 1) = stateCode WHERE p.indigenous_status = 'indig' AND p.age = '_65_yrs_ov' GROUP BY substr(LGAs.lga_code16, 1, 1);";
            }
            String outcomeNumAndType = "1";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level2State, inputQuery, outcomeNumAndType);
        }
        // if outcome 5 has been selected
        if (model.get("outcome5") != null){
            // If the raw radio is selected
            if (model.get("radio").equals("r")){
                // We ask the database to return a raw count
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, SUM(Indig_Y12.total) as value, State.stateName as areaName FROM Indig_Y12 JOIN LGAs on Indig_Y12.Code = LGAs.lga_code16 JOIN State on substr(LGAs.lga_code16, 1, 1) = stateCode GROUP BY substr(LGAs.lga_code16, 1, 1)";
            }
            else{
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, State.stateName AS areaName, SUM(p.count) AS value FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN State ON substr(LGAs.lga_code16, 1, 1) = stateCode WHERE p.indigenous_status = 'indig' AND p.age = '_65_yrs_ov' GROUP BY substr(LGAs.lga_code16, 1, 1);";
            }
            String outcomeNumAndType = "5";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level2State, inputQuery, outcomeNumAndType);
        }
        if (model.get("outcome6") != null){
            // If the raw radio is selected
            if (model.get("radio").equals("r")){
                // We ask the database to return a raw count
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, SUM(Indig_Y12.total) as value, State.stateName as areaName FROM Indig_Y12 JOIN LGAs on Indig_Y12.Code = LGAs.lga_code16 JOIN State on substr(LGAs.lga_code16, 1, 1) = stateCode GROUP BY substr(LGAs.lga_code16, 1, 1)";
            }
            else{
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, State.stateName AS areaName, SUM(p.count) AS value FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN State ON substr(LGAs.lga_code16, 1, 1) = stateCode WHERE p.indigenous_status = 'indig' AND p.age = '_65_yrs_ov' GROUP BY substr(LGAs.lga_code16, 1, 1);";
            }
            String outcomeNumAndType = "6";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level2State, inputQuery, outcomeNumAndType);
        }
        if (model.get("outcome8") != null){
            // If the raw radio is selected
            if (model.get("radio").equals("r")){
                // We ask the database to return a raw count
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, SUM(Indig_Y12.total) as value, State.stateName as areaName FROM Indig_Y12 JOIN LGAs on Indig_Y12.Code = LGAs.lga_code16 JOIN State on substr(LGAs.lga_code16, 1, 1) = stateCode GROUP BY substr(LGAs.lga_code16, 1, 1)";
            }
            else{
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, State.stateName AS areaName, SUM(p.count) AS value FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN State ON substr(LGAs.lga_code16, 1, 1) = stateCode WHERE p.indigenous_status = 'indig' AND p.age = '_65_yrs_ov' GROUP BY substr(LGAs.lga_code16, 1, 1);";
            }
            String outcomeNumAndType = "8";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level2State, inputQuery, outcomeNumAndType);
        }
        // Store the form options that were selected for sorting
        String sortSelect = context.formParam("outcomeSortSelect");
        String outcomeSortOrder = context.formParam("outcomeSortOrder");

        // If there was a selection
        if ((sortSelect != null)&&(!sortSelect.equals("null"))){

            // Switch case to find the outcome to sort
            switch(sortSelect){
                // For outcome 8
                case "8":
                    // Ascending/Descending branching
                    if (outcomeSortOrder.equals("ascending")){
                        // Raw/Proportional branching
                        if(model.get("radio").equals("r")){
                            Collections.sort(level2State, new sortOutcome8RawAscending());
                        }
                        else{
                            Collections.sort(level2State, new sortOutcome8ProportionalAscending());
                        }
                        break;
                    }
                        // Descending branch
                    if(model.get("radio").equals("r")){
                        Collections.sort(level2State, new sortOutcome8RawDescending());
                    }
                    else{
                        Collections.sort(level2State, new sortOutcome8ProportionalDescending());
                    }
                    break;                 
            }
        }
        // If outcome 5 sort has been selected
            // See if ascending or descending has been selected
                // Sort data according to selection

        model.put("sortSelect", sortSelect);
        model.put("outcomeSortOrder", outcomeSortOrder);
        model.put("tableData", level2State);
        model.put("currentPage", "level2State");

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.render(TEMPLATE, model);
    }

}
