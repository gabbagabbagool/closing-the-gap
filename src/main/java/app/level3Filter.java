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

        ArrayList<thymeleafOutcomes> page6Indig = new ArrayList<thymeleafOutcomes>();
        ArrayList<thymeleafOutcomes> page6Non = new ArrayList<thymeleafOutcomes>();

        // radio buttons form link
        String countRadio1 = context.formParam("customRadio");
        if (countRadio1 == null ) {
            System.out.println("null radio selection");
            // If NULL, nothing to show, therefore we make some "no results"
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
        System.out.println(contextMethod);
        if (contextMethod.equalsIgnoreCase("GET")) {
            checkboxOutcome1 = "startChecked";
            checkboxOutcome5 = "startChecked";
            checkboxOutcome6 = "startChecked";
            checkboxOutcome8 = "startChecked";
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

        // add filter for sex
        
        String filterSelect = "";
        String filterValue = "";

        String filterSex1r = "";
        String filterSex1p = "";
        String filterSex5 = "";
        String filterSex6 = "";
        String filterSex8 = "";
        String filterAge = "";

        filterSelect = context.formParam("filterType");
        System.out.println(filterSelect);
        filterValue = context.formParam("filterValue");
        System.out.println(filterValue);

        // TODO receiving an error here "Cannot invoke "String.equalsIgnoreCase(String)" because "filterSelect" is null"
        // Will not load page
        if (filterSelect.equalsIgnoreCase("sex") && (!"Choose...".equals(filterValue))) {
            filterSex1r = "and p.sex = '" + filterValue + "'";
            filterSex1p = "and pop.sex = '" + filterValue + "'";
        } else {
            filterSex1r = "";
            filterSex1p = "";
        }

        // Outcome 1 raw select indig count of population over 65 years per LGA
        String inputQuery = "SELECT p.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(p.count) AS value " +
        "FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 " +
        "WHERE p.indigenous_status = 'indig' and p.age = '_65_yrs_ov' " + filterSex1r +
        "GROUP BY p.lga_code16;";
        String outcomeNumAndType = "1r";
        jdbc.thymeleafHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 1 raw select NON-indig count of population over 65 years per LGA
        inputQuery = "SELECT p.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(p.count) AS value " +
        "FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 " +
        "WHERE p.indigenous_status = 'non_indig' and p.age = '_65_yrs_ov' " + filterSex1r + 
        "GROUP BY p.lga_code16;";
        outcomeNumAndType = "1r";
        jdbc.thymeleafHookUp(page6Non, inputQuery, outcomeNumAndType);

        // Outcome 1 % select indig proportion of population over 65 years comparied to all indig above 15 years per LGA
        inputQuery = "SELECT p.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(p.count) AS above65, pop.pValue, round(CAST (SUM(p.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'value' " +
        "FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN pop_above_15 AS pop ON p.lga_code16 = pop.lgaCode " +
        "WHERE p.indigenous_status = 'indig' and p.age = '_65_yrs_ov' " + filterSex1r + filterSex1p +
        "GROUP BY p.lga_code16;";
        outcomeNumAndType = "1p";
        jdbc.thymeleafHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 1 % select Non-indig proportion of population over 65 years comparied to all Non-indig above 15 years per LGA
        inputQuery = "SELECT p.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(p.count) AS above65, pop.pValue, round(CAST (SUM(p.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'value' " +
        "FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN Non_indig_pop_above_15 AS pop ON p.lga_code16 = pop.lgaCode " +
        "WHERE p.indigenous_status = 'non_indig' and p.age = '_65_yrs_ov' " + filterSex1r + filterSex1p +
        "GROUP BY p.lga_code16;";
        outcomeNumAndType = "1p";
        jdbc.thymeleafHookUp(page6Non, inputQuery, outcomeNumAndType);

        // Outcome 5 raw select indig count that have completed year 12 per LGA
        inputQuery = "SELECT s.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(s.count) AS value " +
        "FROM SchoolStatistics AS s JOIN LGAs ON s.lga_code16 = LGAs.lga_code16 " +
        "WHERE s.School = 'y12_equiv' AND s.indigenous_status = 'indig' " +
        "GROUP BY s.lga_code16, s.indigenous_status, s.School;";
        outcomeNumAndType = "5r";
        jdbc.thymeleafHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 5 raw select Non-indig count that have completed year 12 per LGA
        inputQuery = "SELECT s.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(s.count) AS value " +
        "FROM SchoolStatistics AS s JOIN LGAs ON s.lga_code16 = LGAs.lga_code16 " +
        "WHERE s.School = 'y12_equiv' AND s.indigenous_status = 'non_indig' " +
        "GROUP BY s.lga_code16, s.indigenous_status, s.School;";
        outcomeNumAndType = "5r";
        jdbc.thymeleafHookUp(page6Non, inputQuery, outcomeNumAndType);

        // Outcome 5 % select indig proportion that have completed year 12 compaired to all indig above 15 years per LGA
        inputQuery = "SELECT Indig_Y12.Code AS areaCode, LGAs.lga_name16 AS areaName, Indig_Y12.Total, pop_above_15.pValue, round(CAST (Indig_Y12.Total AS FLOAT)/pop_above_15.pValue * 100 , 1) AS 'value' " +
        "FROM Indig_Y12 JOIN pop_above_15 ON Indig_Y12.Code = pop_above_15.lgaCode JOIN LGAs ON Indig_Y12.Code = LGAs.lga_code16;";
        outcomeNumAndType = "5p";
        jdbc.thymeleafHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 5 % select Non-indig proportion that have completed year 12 compaired to all Non-indig above 15 years per LGA
        inputQuery = "SELECT Indig_Y12.Code AS areaCode, LGAs.lga_name16 AS areaName, Indig_Y12.Total, pop.pValue, round(CAST (Indig_Y12.Total AS FLOAT)/pop.pValue * 100 , 1) AS 'value' " +
        "FROM Indig_Y12 JOIN Non_indig_pop_above_15 AS pop ON Indig_Y12.Code = pop.lgaCode JOIN LGAs ON Indig_Y12.Code = LGAs.lga_code16;";
        outcomeNumAndType = "5p";
        jdbc.thymeleafHookUp(page6Non, inputQuery, outcomeNumAndType);

        // Outcome 6 raw select indig count that have any qualification per LGA
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS value " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "WHERE q.indigenous_status = 'indig' " +
        "GROUP BY q.lga_code16;";
        outcomeNumAndType = "6r";
        jdbc.thymeleafHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 6 raw select Non-indig count that have any qualification per LGA
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS value " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "WHERE q.indigenous_status = 'non_indig' " +
        "GROUP BY q.lga_code16;";
        outcomeNumAndType = "6r";
        jdbc.thymeleafHookUp(page6Non, inputQuery, outcomeNumAndType);

        // Outcome 6 % select indig proportion that have any qualification compared to indig population above 15 years per LGA.
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS qValue, pop.pValue AS pValue, round(CAST (SUM(q.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'value' " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "JOIN pop_above_15 AS pop ON q.lga_code16 = pop.lgaCode " +
        "WHERE q.indigenous_status = 'indig' " +
        "GROUP BY q.lga_code16;";
        outcomeNumAndType = "6p";
        jdbc.thymeleafHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 6 % select Non-indig proportion that have any qualification compared to Non-indig population above 15 years per LGA.
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS qValue, pop.pValue AS pValue, round(CAST (SUM(q.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'value' " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "JOIN Non_indig_pop_above_15 AS pop ON q.lga_code16 = pop.lgaCode " +
        "WHERE q.indigenous_status = 'non_indig' " +
        "GROUP BY q.lga_code16;";
        outcomeNumAndType = "6p";
        jdbc.thymeleafHookUp(page6Non, inputQuery, outcomeNumAndType);

        // Outcome 8 raw select indig count in labour force but unemployed per LGA
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS value " +
        "FROM EmploymentStatistics AS e JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'indig' AND e.Labour_force = 'in_lf_unemp' " +
        "GROUP BY e.lga_code16;";
        outcomeNumAndType = "8r";
        jdbc.thymeleafHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 8 raw select Non-indig count in labour force but unemployed per LGA
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS value " +
        "FROM EmploymentStatistics AS e JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'non_indig' AND e.Labour_force = 'in_lf_unemp' " +
        "GROUP BY e.lga_code16;";
        outcomeNumAndType = "8r";
        jdbc.thymeleafHookUp(page6Non, inputQuery, outcomeNumAndType);

        // Outcome 8 % select indig proportion in labour force but unemployed compared to indig population above 15 years per LGA.
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS eValue, round(CAST (SUM(e.count) AS FLOAT)/pop_above_15.pValue * 100 , 1) AS 'value' " +
        "FROM EmploymentStatistics AS e JOIN pop_above_15 ON e.lga_code16 = pop_above_15.lgaCode JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'indig' AND e.Labour_force = 'in_lf_unemp' " +
        "GROUP BY e.lga_code16;";
        outcomeNumAndType = "8p";
        jdbc.thymeleafHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 8 % select Non-indig proportion in labour force but unemployed compared to Non-indig population above 15 years per LGA.
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS eValue, round(CAST (SUM(e.count) AS FLOAT)/Non_indig_pop_above_15.pValue * 100 , 1) AS 'value' " +
        "FROM EmploymentStatistics AS e JOIN Non_indig_pop_above_15 ON e.lga_code16 = Non_indig_pop_above_15.lgaCode JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'non_indig' AND e.Labour_force = 'in_lf_unemp' " +
        "GROUP BY e.lga_code16;";
        outcomeNumAndType = "8p";
        jdbc.thymeleafHookUp(page6Non, inputQuery, outcomeNumAndType);


        model.put("tableDataIndig", page6Indig); 
        model.put("tableDataNon", page6Non);

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.render(TEMPLATE, model);
    }

}
