package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;

/**
 * Temporary HTML as an example page.
 * 
 * Based on the Project Workshop code examples.
 * This page currently:
 *  - Provides a link back to the index page
 *  - Displays the list of movies from the Movies Database using the JDBCConnection
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class level3Filter implements Handler {

    // URL of this page relative to http://localhost:7000/
    public static final String URL = "/level3Filter.html";

    private static final String TEMPLATE = ("level3Filter.html");

    @Override
    public void handle(Context context) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
    
        JDBCConnection jdbc = new JDBCConnection();

        ArrayList<filterOutcomes> page6Indig = new ArrayList<filterOutcomes>();
        // ArrayList<filterOutcomes> page6Non = new ArrayList<filterOutcomes>();

        // radio buttons form link
        String countRadio1 = context.formParam("customRadio");
        if (countRadio1 == null ) {
            // If NULL, choose raw by default
            countRadio1 = "raw";
            model.put("dataType", new String("rawSelected"));
        } else if (countRadio1.equalsIgnoreCase("raw")) {
            model.put("dataType", new String("rawSelected"));
        } else if (countRadio1.equalsIgnoreCase("proportional")) {
            model.put("dataType", new String("fracSelected"));
        }

        // outcome checkbox button form link
        String checkboxOutcome1;
        String checkboxOutcome5;
        String checkboxOutcome6;
        String checkboxOutcome8;
        String contextMethod = context.method();
        
        if (contextMethod.equalsIgnoreCase("GET")) {
            checkboxOutcome1 = "checked";
            checkboxOutcome5 = "checked";
            checkboxOutcome6 = "checked";
            checkboxOutcome8 = "checked";
        } else {
            checkboxOutcome1 = context.formParam("checkboxOutcome1");
            checkboxOutcome5 = context.formParam("checkboxOutcome5");
            checkboxOutcome6 = context.formParam("checkboxOutcome6");
            checkboxOutcome8 = context.formParam("checkboxOutcome8");
        }
        
        model.put("checkboxOutcome1", checkboxOutcome1);
        model.put("checkboxOutcome5", checkboxOutcome5);
        model.put("checkboxOutcome6", checkboxOutcome6);
        model.put("checkboxOutcome8", checkboxOutcome8);

        // add filters for gender and population
        
        String genderFilter = "";
        String populationFilter = "";

        String filterSex1r = " ";
        String filterSex5 = "";
        String filterSex6 = "";
        String filterSex8 = "";
        String populationQuery = "";
        String populationViewQuery = "";
        

        String inputQuery = "";
        String outcomeNumAndType = "";
        String populationValueIndig;

        genderFilter = context.formParam("genderFilterValue");
        populationFilter = context.formParam("populationFilterIndig");
        populationValueIndig = context.formParam("populationFilterValueIndig");
        // if clear button hit - clear this filter
        if (context.formParam("buttonClearFilter") != null) {
            genderFilter = null;
            populationFilter = null;
        }
        System.out.println(populationFilter);
        model.put("genderFilterSelection", genderFilter);
        model.put("populationFilterSelection", populationFilter);
        
        if (genderFilter != null) {
            if (!"all".equals(genderFilter)) {
                filterSex1r = "and p.sex = '" + genderFilter + "' ";
                filterSex5 = "and s.sex = '" + genderFilter + "' ";
                filterSex6 = "and q.sex = '" + genderFilter + "' ";
                filterSex8 = "and e.sex = '" + genderFilter + "' ";
            } else {
                filterSex1r = "";
            }
        }

        if (populationFilter != null) {
            if ("greater".equals(populationFilter)) {
                populationQuery = "and pop.pValue > " + populationValueIndig + " ";
                populationViewQuery = "WHERE pop.pValue > " + populationValueIndig + " ";
            } else if ("less".equals(populationFilter)) {
                populationQuery = "and pop.pValue < " + populationValueIndig + " ";
                populationViewQuery = "WHERE pop.pValue < " + populationValueIndig + " ";
            } else {
                populationQuery = "";

            }
        }
        // TODO populationQuery set to  none for now
        populationQuery = "";

        inputQuery = "DROP VIEW IF EXISTS indig_pop_above_15;";
        jdbc.createSqlView(inputQuery);

        inputQuery = "CREATE VIEW indig_pop_above_15 AS " +
        "SELECT p.lga_code16 AS areaCode, SUM(p.count) AS pValue FROM PopulationStatistics AS p " +
        "WHERE p.indigenous_status = 'indig' and p.age <> '_0_4' AND p.age <> '_5_9' AND p.age <> '_10_14' AND p.age <> '_65_yrs_ov' " + filterSex1r +
        "GROUP BY p.lga_code16;";
        jdbc.createSqlView(inputQuery);

        inputQuery = "DROP VIEW IF EXISTS Non_indig_pop_above_15;";
        jdbc.createSqlView(inputQuery);

        inputQuery = "CREATE VIEW Non_indig_pop_above_15 AS " +
        "SELECT p.lga_code16 AS areaCode, SUM(p.count) AS pValue FROM PopulationStatistics AS p " +
        "WHERE p.indigenous_status = 'non_indig' and p.age <> '_0_4' AND p.age <> '_5_9' AND p.age <> '_10_14' AND p.age <> '_65_yrs_ov' " + filterSex1r +
        "GROUP BY p.lga_code16;";
        jdbc.createSqlView(inputQuery);

        inputQuery = "DROP VIEW IF EXISTS Non_indig_Y12;";
        jdbc.createSqlView(inputQuery);

        inputQuery = "CREATE VIEW Non_indig_Y12 AS " +
        "SELECT s.lga_code16 AS areaCode, SUM(s.count) AS sValue FROM SchoolStatistics AS s " +
        "WHERE s.indigenous_status = 'non_indig' and s.School = 'y12_equiv' " + filterSex5 +
        "GROUP BY s.lga_code16;";
        jdbc.createSqlView(inputQuery);
        

        // Outcome 1 % select indig proportion of population over 65 years comparied to all indig above 15 years per LGA
        inputQuery = "SELECT p.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(p.count) AS value, pop.pValue AS pValue, round(CAST (SUM(p.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN " +
        "(SELECT p.lga_code16 AS lgaCode, SUM(p.count) AS pValue FROM PopulationStatistics AS p WHERE p.indigenous_status = 'indig' and p.age <> '_0_4' AND p.age <> '_5_9' AND p.age <> '_10_14' AND p.age <> '_65_yrs_ov' " + filterSex1r + 
        "GROUP BY p.lga_code16) AS pop ON p.lga_code16 = pop.lgaCode " +
        "WHERE p.indigenous_status = 'indig' and p.age = '_65_yrs_ov' " + filterSex1r + populationQuery +
        "GROUP BY p.lga_code16;";
        outcomeNumAndType = "1Indig";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);
        
        // Outcome 1 % select Non-indig proportion of population over 65 years comparied to all Non-indig above 15 years per LGA
        inputQuery = "SELECT p.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(p.count) AS value, pop.pValue AS pValue, round(CAST (SUM(p.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN " +
        "(SELECT p.lga_code16 AS lgaCode, SUM(p.count) AS pValue FROM PopulationStatistics AS p WHERE p.indigenous_status = 'non_indig' and p.age <> '_0_4' AND p.age <> '_5_9' AND p.age <> '_10_14' AND p.age <> '_65_yrs_ov' " + filterSex1r +
        "GROUP BY p.lga_code16) AS pop ON p.lga_code16 = pop.lgaCode " +
        "WHERE p.indigenous_status = 'non_indig' and p.age = '_65_yrs_ov' " + filterSex1r + populationQuery +
        "GROUP BY p.lga_code16;";
        outcomeNumAndType = "1Non";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 5 % select indig proportion that have completed year 12 compaired to all indig above 15 years per LGA
        inputQuery = "SELECT Indig_Y12.Code AS areaCode, LGAs.lga_name16 AS areaName, Indig_Y12.Total AS value, pop.pValue AS pValue, round(CAST (Indig_Y12.Total AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM Indig_Y12 JOIN indig_pop_above_15 AS pop ON Indig_Y12.Code = pop.areaCode JOIN LGAs ON Indig_Y12.Code = LGAs.lga_code16;";
        outcomeNumAndType = "5Indig";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 5 % select Non-indig proportion that have completed year 12 compaired to all Non-indig above 15 years per LGA
        inputQuery = "SELECT Non_indig_Y12.areaCode AS areaCode, LGAs.lga_name16 AS areaName, Non_indig_Y12.sValue AS value, pop.pValue AS pValue, round(CAST (Non_indig_Y12.sValue AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM Non_indig_Y12 JOIN Non_indig_pop_above_15 AS pop ON Non_indig_Y12.areaCode = pop.areaCode JOIN LGAs ON Non_indig_Y12.areaCode = LGAs.lga_code16;";
        outcomeNumAndType = "5Non";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 6 % select indig proportion that have any qualification compared to indig population above 15 years per LGA.
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS value, pop.pValue AS pValue, round(CAST (SUM(q.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "JOIN pop_above_15 AS pop ON q.lga_code16 = pop.lgaCode " +
        "WHERE q.indigenous_status = 'indig' " + filterSex6 + populationQuery +
        "GROUP BY q.lga_code16;";
        outcomeNumAndType = "6Indig";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 6 % select Non-indig proportion that have any qualification compared to Non-indig population above 15 years per LGA.
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS value, pop.pValue AS pValue, round(CAST (SUM(q.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "JOIN Non_indig_pop_above_15 AS pop ON q.lga_code16 = pop.areaCode " +
        "WHERE q.indigenous_status = 'non_indig' " + filterSex6 + populationQuery +
        "GROUP BY q.lga_code16;";
        outcomeNumAndType = "6Non";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 8 % select indig proportion in labour force but unemployed compared to indig population above 15 years per LGA.
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS value, pop.pValue AS pValue, round(CAST (SUM(e.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM EmploymentStatistics AS e JOIN indig_pop_above_15 AS pop ON e.lga_code16 = pop.areaCode JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'indig' AND e.Labour_force = 'in_lf_unemp' " +  filterSex8 + populationQuery +
        "GROUP BY e.lga_code16;";
        outcomeNumAndType = "8Indig";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 8 % select Non-indig proportion in labour force but unemployed compared to Non-indig population above 15 years per LGA.
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS value, pop.pValue AS pValue, round(CAST (SUM(e.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM EmploymentStatistics AS e JOIN Non_indig_pop_above_15 AS pop ON e.lga_code16 = pop.areaCode JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'non_indig' AND e.Labour_force = 'in_lf_unemp' " +  filterSex8 + populationQuery +
        "GROUP BY e.lga_code16;";
        outcomeNumAndType = "8Non";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        

        model.put("tableDataIndig", page6Indig); 
    

        model.put("currentPage", "level3Filter");
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.render(TEMPLATE, model);
    }

}
