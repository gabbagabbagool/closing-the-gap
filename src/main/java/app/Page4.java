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
public class Page4 implements Handler {

    // URL of this page relative to http://localhost:7000/
    public static final String URL = "/page4.html";

    private static final String TEMPLATE = ("page4.html");

    @Override
    public void handle(Context context) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
    
        JDBCConnection jdbc = new JDBCConnection();

        ArrayList<thymeleafOutcomes> page4 = new ArrayList<thymeleafOutcomes>();
        // The query above returns a count for the total indigenous population that has completed year 12 for each LGA
        String inputQuery = "SELECT substr(LGAs.lga_code16, 1, 1) AS areaCode, SUM(Indig_Y12.total) as value, State.stateName as areaName FROM Indig_Y12 JOIN LGAs on Indig_Y12.Code = LGAs.lga_code16 JOIN State on substr(LGAs.lga_code16, 1, 1) = stateCode GROUP BY substr(LGAs.lga_code16, 1, 1)";
        String outcomeNumAndType = "5r";
        jdbc.thymeleafHookUp(page4, inputQuery, outcomeNumAndType);

        model.put("tableData", page4);

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.render(TEMPLATE, model);
    }

}
