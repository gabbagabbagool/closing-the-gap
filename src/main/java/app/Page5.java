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
public class Page5 implements Handler {

    // URL of this page relative to http://localhost:7000/
    public static final String URL = "/page5.html";

    private static final String TEMPLATE = ("page5.html");

    @Override
    public void handle(Context context) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
    
        JDBCConnection jdbc = new JDBCConnection();

        ArrayList<thymeleafOutcomes> page5 = new ArrayList<thymeleafOutcomes>();
        // Outcome 1 The query below returns a count for the total indigenous population that has completed year 12 for each LGA
        String inputQuery = "SELECT s.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(s.count) AS value " +
        "FROM SchoolStatistics AS s JOIN LGAs ON s.lga_code16 = LGAs.lga_code16 " +
        "WHERE s.School = 'y12_equiv' AND s.indigenous_status = 'indig' " +
        "GROUP BY s.lga_code16, s.indigenous_status, s.School;";
        String outcomeNumAndType = "1r";
        jdbc.thymeleafHookUp(page5, inputQuery, outcomeNumAndType);

        // Outcome 1 %
        inputQuery = "SELECT Indig_Y12.Code AS areaCode, LGAs.lga_name16 AS areaName, Indig_Y12.Total, pop_above_15.pValue, round(CAST (Indig_Y12.Total AS FLOAT)/pop_above_15.pValue * 100 , 1) AS 'value' " +
        "FROM Indig_Y12 JOIN pop_above_15 ON Indig_Y12.Code = pop_above_15.lgaCode JOIN LGAs ON Indig_Y12.Code = LGAs.lga_code16;";
        outcomeNumAndType = "1p";
        jdbc.thymeleafHookUp(page5, inputQuery, outcomeNumAndType);

        // Outcome 5 raw
        inputQuery = "SELECT s.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(s.count) AS value " +
        "FROM SchoolStatistics AS s JOIN LGAs ON s.lga_code16 = LGAs.lga_code16 " +
        "WHERE s.School = 'y12_equiv' AND s.indigenous_status = 'indig' " +
        "GROUP BY s.lga_code16, s.indigenous_status, s.School;";
        outcomeNumAndType = "5r";
        jdbc.thymeleafHookUp(page5, inputQuery, outcomeNumAndType);

        // Outcome 5 %
        inputQuery = "SELECT Indig_Y12.Code AS areaCode, LGAs.lga_name16 AS areaName, Indig_Y12.Total, pop_above_15.pValue, round(CAST (Indig_Y12.Total AS FLOAT)/pop_above_15.pValue * 100 , 1) AS 'value' " +
        "FROM Indig_Y12 JOIN pop_above_15 ON Indig_Y12.Code = pop_above_15.lgaCode JOIN LGAs ON Indig_Y12.Code = LGAs.lga_code16;";
        outcomeNumAndType = "5p";
        jdbc.thymeleafHookUp(page5, inputQuery, outcomeNumAndType);

        // Outcome 6 raw
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS value " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "WHERE q.indigenous_status = 'indig' " +
        "GROUP BY q.lga_code16;";
        outcomeNumAndType = "6r";
        jdbc.thymeleafHookUp(page5, inputQuery, outcomeNumAndType);

        // Outcome 6 % of qualificaions compared to indig population above 15 years.
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS qValue, pop.pValue AS pValue, round(CAST (SUM(q.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'value' " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "JOIN pop_above_15 AS pop ON q.lga_code16 = pop.lgaCode " +
        "WHERE q.indigenous_status = 'indig' " +
        "GROUP BY q.lga_code16;";
        outcomeNumAndType = "6p";
        jdbc.thymeleafHookUp(page5, inputQuery, outcomeNumAndType);

        // Outcome 8 raw count of in labour force but unemployed
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS value " +
        "FROM EmploymentStatistics AS e JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'indig' AND e.Labour_force = 'in_lf_unemp' " +
        "GROUP BY e.lga_code16;";
        outcomeNumAndType = "8r";
        jdbc.thymeleafHookUp(page5, inputQuery, outcomeNumAndType);

        // Outcome 8 % of in labour force but unemployed compared to indig population abouve 15 years.
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS eValue, round(CAST (SUM(e.count) AS FLOAT)/pop_above_15.pValue * 100 , 1) AS 'value' " +
        "FROM EmploymentStatistics AS e JOIN pop_above_15 ON e.lga_code16 = pop_above_15.lgaCode JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'indig' AND e.Labour_force = 'in_lf_unemp' " +
        "GROUP BY e.lga_code16;";
        outcomeNumAndType = "8p";
        jdbc.thymeleafHookUp(page5, inputQuery, outcomeNumAndType);


        model.put("tableData", page5);

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.render(TEMPLATE, model);
    }

}
