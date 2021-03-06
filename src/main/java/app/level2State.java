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
        // Store the form options that were selected for sorting
        String sortSelect = context.formParam("outcomeSortSelect");
        String outcomeSortOrder = context.formParam("outcomeSortOrder");

        populateData(model, jdbc, level2State, sortSelect);
        sorting(model, level2State, sortSelect, outcomeSortOrder);

        model.put("sortSelect", sortSelect);
        model.put("outcomeSortOrder", outcomeSortOrder);
        model.put("tableData", level2State);
        model.put("currentPage", "level2State");

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.render(TEMPLATE, model);
    }

    private void sorting(Map<String, Object> model, ArrayList<thymeleafOutcomes> level2State, String sortSelect,
            String outcomeSortOrder) {
        // If there was a selection
        if ((sortSelect != null)&&(!sortSelect.equals("null"))){

            // Switch case to find the outcome to sort
            switch(sortSelect){
                case "1":
                    // Ascending/Descending branching
                    if (outcomeSortOrder.equals("ascending")){
                        // Raw/Proportional branching
                        if(model.get("radio").equals("r")){
                            Collections.sort(level2State, new sortOutcome1RawAscending());
                        }
                        else{
                            Collections.sort(level2State, new sortOutcome1ProportionalAscending());
                        }
                        break;
                    }
                        // Descending branch
                    if(model.get("radio").equals("r")){
                        Collections.sort(level2State, new sortOutcome1RawDescending());
                    }
                    else{
                        Collections.sort(level2State, new sortOutcome1ProportionalDescending());
                    }
                    break;
                case "5":
                    // Ascending/Descending branching
                    if (outcomeSortOrder.equals("ascending")){
                        // Raw/Proportional branching
                        if(model.get("radio").equals("r")){
                            Collections.sort(level2State, new sortOutcome5RawAscending());
                        }
                        else{
                            Collections.sort(level2State, new sortOutcome5ProportionalAscending());
                        }
                        break;
                    }
                        // Descending branch
                    if(model.get("radio").equals("r")){
                        Collections.sort(level2State, new sortOutcome5RawDescending());
                    }
                    else{
                        Collections.sort(level2State, new sortOutcome5ProportionalDescending());
                    }
                    break;
                case "6":
                    // Ascending/Descending branching
                    if (outcomeSortOrder.equals("ascending")){
                        // Raw/Proportional branching
                        if(model.get("radio").equals("r")){
                            Collections.sort(level2State, new sortOutcome6RawAscending());
                        }
                        else{
                            Collections.sort(level2State, new sortOutcome6ProportionalAscending());
                        }
                        break;
                    }
                        // Descending branch
                    if(model.get("radio").equals("r")){
                        Collections.sort(level2State, new sortOutcome6RawDescending());
                    }
                    else{
                        Collections.sort(level2State, new sortOutcome6ProportionalDescending());
                    }
                    break;
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
    }

    private void populateData(Map<String, Object> model, JDBCConnection jdbc, ArrayList<thymeleafOutcomes> level2State,
            String sortSelect) {
        String inputQuery;
        // If outcome 1 has been selected
        if ((model.get("outcome1") != null)||(sortSelect.equals("1"))){
            if(model.get("outcome1") == null){
                model.put("outcome1", "true");
                model.put("error", "Outcome 1 was chosen to sort, whilst not selected to display. The site has included outcome 1 in the results");
            }

            // If the raw radio is selected
            if (model.get("radio").equals("r")){
                // We ask the database to return a raw count
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, State.stateName AS areaName, SUM(p.count) AS value FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN State ON substr(LGAs.lga_code16, 1, 1) = stateCode WHERE p.indigenous_status = 'indig' AND p.age = '_65_yrs_ov' GROUP BY substr(LGAs.lga_code16, 1, 1);";
            }
            else{
                inputQuery = "SELECT State.stateCode AS areaCode, State.stateName AS areaName, ROUND(SUM(indig.over_65) * 1.0 / SUM(every.over_65) * 100.0, 2) AS value FROM all_over_65 AS every JOIN indig_over_65 AS indig ON  every.lga_code16 = indig.lga_code16 JOIN State ON substr(indig.lga_code16, 1, 1) = stateCode GROUP BY State.stateCode  ORDER BY State.stateCode;";
            }
            String outcomeNumAndType = "1";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level2State, inputQuery, outcomeNumAndType);
        }
        // if outcome 5 has been selected
        if ((model.get("outcome5") != null)||(sortSelect.equals("5"))){
            if(model.get("outcome5") == null){
                model.put("outcome5", "true");
                model.put("error", "Outcome 5 was chosen to sort, whilst not selected to display. The site has included outcome 5 in the results");
            }
            // If the raw radio is selected
            if (model.get("radio").equals("r")){
                // We ask the database to return a raw count
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, SUM(Indig_Y12.total) as value, State.stateName as areaName FROM Indig_Y12 JOIN LGAs on Indig_Y12.Code = LGAs.lga_code16 JOIN State on substr(LGAs.lga_code16, 1, 1) = stateCode GROUP BY substr(LGAs.lga_code16, 1, 1)";
            }
            else{
                inputQuery = "SELECT State.stateCode AS areaCode, State.stateName AS areaName, ROUND(SUM(Indig_Y12.Total) * 1.0 / (SUM(indig_finish_hs.finish_hs)) * 100, 2) AS value FROM Indig_Y12 JOIN indig_finish_hs ON Code = lga_code16 JOIN State ON substr(indig_finish_hs.lga_code16, 1, 1) = stateCode GROUP BY substr(Indig_Y12.Code, 1, 1)  ORDER BY Indig_Y12.Code;";
            }
            String outcomeNumAndType = "5";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level2State, inputQuery, outcomeNumAndType);
        }
        if ((model.get("outcome6") != null)||(sortSelect.equals("6"))){
            if(model.get("outcome6") == null){
                model.put("outcome6", "true");
                model.put("error", "Outcome 6 was chosen to sort, whilst not selected to display. The site has included outcome 6 in the results");
            }
            // If the raw radio is selected
            if (model.get("radio").equals("r")){
                // We ask the database to return a raw count
                inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, SUM(certIII.certIII) AS value, State.stateName AS areaName FROM indig_certIII AS certIII JOIN LGAs ON certIII.lga_code16 = LGAs.lga_code16 JOIN State ON substr(LGAs.lga_code16, 1, 1) = stateCode GROUP BY substr(LGAs.lga_code16, 1, 1)";
            }
            else{
                inputQuery = "SELECT State.stateCode AS areaCode, State.stateName AS areaName, ROUND(SUM(certIII.certIII) * 1.0 / SUM(all_qual.all_qual) * 100.0, 2) AS value FROM indig_all_qual AS all_qual JOIN indig_certIII AS certIII ON  all_qual.lga_code16 = certIII.lga_code16 JOIN State ON substr(certIII.lga_code16, 1, 1) = stateCode GROUP BY State.stateCode  ORDER BY State.stateCode;";
            }
            String outcomeNumAndType = "6";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level2State, inputQuery, outcomeNumAndType);
        }
        if ((model.get("outcome8")) != null||(sortSelect.equals("8"))){
            if(model.get("outcome8") == null){
                model.put("outcome8", "true");
                model.put("error", "Outcome 8 was chosen to sort, whilst not selected to display. The site has included outcome 8 in the results");
            }
            // If the raw radio is selected
            if (model.get("radio").equals("r")){
                // We ask the database to return a raw count
                inputQuery = "SELECT sum(employed_indig) as value, substr(lga_code16, 1, 1) as areaCode, State.stateName as areaName FROM labour_force_indig JOIN State on substr(lga_code16, 1, 1) = stateCode GROUP BY stateName;";
            }
            else{
                inputQuery = "SELECT ROUND(100 - (100.0 * unemployed_indig/employed_indig),1) AS value, substr(lga_code16, 1, 1) as areaCode, State.stateName as areaName FROM unemployed_indig NATURAL JOIN labour_force_indig JOIN State on substr(lga_code16, 1, 1) = stateCode GROUP BY areaName ORDER BY areaCode;";
            }
            String outcomeNumAndType = "8";
            outcomeNumAndType += model.get("radio");
            jdbc.thymeleafHookUp(level2State, inputQuery, outcomeNumAndType);
        }
    }

}
