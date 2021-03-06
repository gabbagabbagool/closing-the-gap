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

        // add filters for gender, population and gap
        String genderFilter = "";
        String populationFilterIndig = "";
        String populationFilterNon = "";
        String populationValueIndig;
        String populationValueNon;
        String gapFilter = "";
        String gapValue;

        // add the strings that will add to the SQL queries
        String filterSex1r = " ";
        String filterSex5 = "";
        String filterSex6 = "";
        String filterSex8 = "";
        String populationQueryIndig = "";
        String populationQueryNon = "";
        String gapFilterQuery = "";
        String gapFilterQuery1 = "";
        String gapFilterQuery5 = "";
        String gapFilterQuery6 = "";
        String gapFilterQuery8 = "";


        genderFilter = context.formParam("genderFilterValue");
        populationFilterIndig = context.formParam("populationFilterIndig");
        populationFilterNon = context.formParam("populationFilterNon");
        populationValueIndig = context.formParam("populationFilterValueIndig");
        populationValueNon = context.formParam("populationFilterValueNon");
        gapFilter = context.formParam("gapFilter");
        gapValue = context.formParam("gapFilterValue"); 
        
        // defualt values to 0 if nothing selected
        if ((populationFilterIndig != null) && (populationValueIndig == "")) {
            populationValueIndig = "0";
        }
        if ((populationFilterNon != null) && (populationValueNon == "")) {
            populationValueNon = "0";
        }
        if ((gapFilter != null) && (gapValue == "")) {
            gapValue = "0";
        }

        // if clear button hit - clear this filter
        if (context.formParam("buttonClearFilter") != null) {
            genderFilter = null;
            populationFilterIndig = null;
            populationFilterNon = null;
            gapFilter = null;
            populationValueIndig = "0";
            populationValueNon = "0";
            gapValue = "0";
        }
        
        model.put("genderFilterSelection", genderFilter);
        model.put("populationFilterSelectionIndig", populationFilterIndig);
        model.put("populationFilterSelectionNon", populationFilterNon);
        model.put("populationInputIndig", populationValueIndig);
        model.put("populationInputNon", populationValueNon);
        model.put("gapFilter", gapFilter);
        model.put("gapFilterValue", gapValue);
        
        // set SQL for gender
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

        // set the population filter value to update SQL query
        if (populationFilterIndig != null) {
            if ("greater".equals(populationFilterIndig)) {
                populationQueryIndig = " and pop.pValue > " + populationValueIndig + " ";
                
            } else if ("less".equals(populationFilterIndig)) {
                populationQueryIndig = " and pop.pValue < " + populationValueIndig + " ";
                
            } else {
                populationQueryIndig = " ";
            }
        }
        if (populationFilterNon != null) {
            if ("greater".equals(populationFilterNon)) {
                populationQueryNon = " and pop.pValue > " + populationValueNon + " ";
                
            } else if ("less".equals(populationFilterNon)) {
                populationQueryNon = " and pop.pValue < " + populationValueNon + " ";
                
            } else {
                populationQueryNon = " ";

            }
        }
        
        // set the gap filter value to update SQL query
        if (gapFilter != null) {
            gapFilterQuery = " HAVING proportion > " + gapValue + " ";
            if ("Outcome 1".equals(gapFilter)) {
                gapFilterQuery1 = gapFilterQuery; 

            } else if ("Outcome 5".equals(gapFilter)) {
                gapFilterQuery5 = gapFilterQuery;
            
            } else if ("Outcome 6".equals(gapFilter)) {
                gapFilterQuery6 = gapFilterQuery;
            
            } else if ("Outcome 8".equals(gapFilter)) {
                gapFilterQuery8 = gapFilterQuery;
            
            } else if ("All".equals(gapFilter)) {
                gapFilterQuery1 = gapFilterQuery;
                gapFilterQuery5 = gapFilterQuery;
                gapFilterQuery6 = gapFilterQuery;
                gapFilterQuery8 = gapFilterQuery;
            }
        }

        // add SQL queries
        String inputQuery = "";
        String outcomeNumAndType = "";

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
        "WHERE p.indigenous_status = 'indig' and p.age = '_65_yrs_ov' " + filterSex1r + populationQueryIndig +
        "GROUP BY p.lga_code16 " + gapFilterQuery1 + " ;";
        outcomeNumAndType = "1Indig";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);
        
        // Outcome 1 % select Non-indig proportion of population over 65 years comparied to all Non-indig above 15 years per LGA
        inputQuery = "SELECT p.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(p.count) AS value, pop.pValue AS pValue, round(CAST (SUM(p.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM PopulationStatistics AS p JOIN LGAs ON p.lga_code16 = LGAs.lga_code16 JOIN " +
        "(SELECT p.lga_code16 AS lgaCode, SUM(p.count) AS pValue FROM PopulationStatistics AS p WHERE p.indigenous_status = 'non_indig' and p.age <> '_0_4' AND p.age <> '_5_9' AND p.age <> '_10_14' AND p.age <> '_65_yrs_ov' " + filterSex1r +
        "GROUP BY p.lga_code16) AS pop ON p.lga_code16 = pop.lgaCode " +
        "WHERE p.indigenous_status = 'non_indig' and p.age = '_65_yrs_ov' " + filterSex1r + populationQueryNon +
        "GROUP BY p.lga_code16 " + gapFilterQuery1 + " ;";
        outcomeNumAndType = "1Non";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 5 % select indig proportion that have completed year 12 compaired to all indig above 15 years per LGA
        inputQuery = "SELECT s.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(s.count) AS value, pop.pValue AS pValue, round(CAST (SUM(s.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM SchoolStatistics AS s JOIN LGAs ON s.lga_code16 = LGAs.lga_code16 JOIN " +
        "(SELECT p.lga_code16 AS lgaCode, SUM(p.count) AS pValue FROM PopulationStatistics AS p  " +
        "WHERE p.indigenous_status = 'indig' and p.age <> '_0_4' AND p.age <> '_5_9' AND p.age <> '_10_14' AND p.age <> '_65_yrs_ov' " +  filterSex1r +
        "GROUP BY p.lga_code16) AS pop ON s.lga_code16 = pop.lgaCode " +
        "WHERE s.School = 'y12_equiv' AND s.indigenous_status = 'indig' " +  filterSex5 + populationQueryIndig +
        "GROUP BY s.lga_code16 " + gapFilterQuery5 + " ;";
        outcomeNumAndType = "5Indig";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);
        
        // Outcome 5 % select Non-indig proportion that have completed year 12 compaired to all Non-indig above 15 years per LGA
        inputQuery = "SELECT s.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(s.count) AS value, pop.pValue AS pValue, round(CAST (SUM(s.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM SchoolStatistics AS s JOIN LGAs ON s.lga_code16 = LGAs.lga_code16 JOIN " +
        "(SELECT p.lga_code16 AS lgaCode, SUM(p.count) AS pValue FROM PopulationStatistics AS p  " +
        "WHERE p.indigenous_status = 'non_indig' and p.age <> '_0_4' AND p.age <> '_5_9' AND p.age <> '_10_14' AND p.age <> '_65_yrs_ov' " +  filterSex1r +
        "GROUP BY p.lga_code16) AS pop ON s.lga_code16 = pop.lgaCode " +
        "WHERE s.School = 'y12_equiv' AND s.indigenous_status = 'non_indig' " +  filterSex5 + populationQueryNon +
        "GROUP BY s.lga_code16 " + gapFilterQuery5 + " ;";
        outcomeNumAndType = "5Non";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);
        
        // Outcome 6 % select indig proportion that have any qualification compared to indig population above 15 years per LGA.
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS value, pop.pValue AS pValue, round(CAST (SUM(q.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "JOIN pop_above_15 AS pop ON q.lga_code16 = pop.lgaCode " +
        "WHERE q.indigenous_status = 'indig' " + filterSex6 + populationQueryIndig +
        "GROUP BY q.lga_code16 " + gapFilterQuery6 + " ;";
        outcomeNumAndType = "6Indig";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 6 % select Non-indig proportion that have any qualification compared to Non-indig population above 15 years per LGA.
        inputQuery = "SELECT q.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, SUM(q.count) AS value, pop.pValue AS pValue, round(CAST (SUM(q.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM QualificationStatistics AS q JOIN LGAs ON q.lga_code16 = LGAs.lga_code16 " +
        "JOIN Non_indig_pop_above_15 AS pop ON q.lga_code16 = pop.areaCode " +
        "WHERE q.indigenous_status = 'non_indig' " + filterSex6 + populationQueryNon +
        "GROUP BY q.lga_code16 " + gapFilterQuery6 + " ;";
        outcomeNumAndType = "6Non";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 8 % select indig proportion in labour force but unemployed compared to indig population above 15 years per LGA.
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS value, pop.pValue AS pValue, round(CAST (SUM(e.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM EmploymentStatistics AS e JOIN indig_pop_above_15 AS pop ON e.lga_code16 = pop.areaCode JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'indig' AND e.Labour_force = 'in_lf_emp' " +  filterSex8 + populationQueryIndig +
        "GROUP BY e.lga_code16 " + gapFilterQuery8 + " ;";
        outcomeNumAndType = "8Indig";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // Outcome 8 % select Non-indig proportion in labour force but unemployed compared to Non-indig population above 15 years per LGA.
        inputQuery = "SELECT e.lga_code16 AS areaCode, LGAs.lga_name16 AS areaName, e.Labour_force, SUM(e.count) AS value, pop.pValue AS pValue, round(CAST (SUM(e.count) AS FLOAT)/pop.pValue * 100 , 1) AS 'proportion' " +
        "FROM EmploymentStatistics AS e JOIN Non_indig_pop_above_15 AS pop ON e.lga_code16 = pop.areaCode JOIN LGAs ON e.lga_code16 = LGAs.lga_code16 " +
        "WHERE e.indigenous_status = 'non_indig' AND e.Labour_force = 'in_lf_emp' " +  filterSex8 + populationQueryNon +
        "GROUP BY e.lga_code16 " + gapFilterQuery8 + " ;";
        outcomeNumAndType = "8Non";
        jdbc.filterHookUp(page6Indig, inputQuery, outcomeNumAndType);

        // filter results to remove null values
        int removedCounter = 0;
        int listSize = page6Indig.size();
        
        for (int i = 0; i < listSize - removedCounter; i++) {
            
            if ((page6Indig.get(i).outcome1IndigRaw == null) || 
            (page6Indig.get(i).outcome5IndigRaw == null) || 
            (page6Indig.get(i).outcome6IndigRaw == null) || 
            (page6Indig.get(i).outcome8IndigRaw == null)) {
                page6Indig.remove(i);
                removedCounter ++;
                i --;
            } else if (page6Indig.get(i).outcome1NonRaw == null) {
                
                page6Indig.remove(i);
                removedCounter ++;
                i --;
            }
        } 

        for (int i = 0; i < page6Indig.size(); i++) {
            page6Indig.get(i).setRanking(checkboxOutcome1, checkboxOutcome5, checkboxOutcome6, checkboxOutcome8);
        }
        
        // Store the form options that were selected for sorting
        String sortSelectOutcome = context.formParam("outcomeSortSelect");
        String sortSelectGroup = context.formParam("outcomeSelectGroup");
        String outcomeSortOrder = context.formParam("outcomeSortOrder");
        model.put("sortSelect", sortSelectOutcome);
        model.put("sortSelectGroup", sortSelectGroup);
        model.put("outcomeSortOrder", outcomeSortOrder);
        
        // If there was a selection
        if ((sortSelectOutcome != null)&&(!sortSelectOutcome.equals("null"))){
          
            // Switch case to find the outcome to sort
            switch(sortSelectOutcome){
                case "Rank":
                    // Ascending/Descending branching
                    if (outcomeSortOrder.equals("ascending")){
                        // Ascending 
                        Collections.sort(page6Indig, new sortOutcomeRankAscending());
                        break;
                    }
                     // Descending branch
                     Collections.sort(page6Indig, new sortOutcomeRankDescending());
                    break;

                // For outcome 1
                case "1":
                    // Ascending/Descending branching
                    if (outcomeSortOrder.equals("ascending")){
                        // Raw/Proportional branching
                        if(model.get("dataType").equals("rawSelected")){
                            switch(sortSelectGroup) {
                                case "Indig":
                                Collections.sort(page6Indig, new sortOutcome1IndigRawAscending());
                                    break;
                                case "Non":
                                Collections.sort(page6Indig, new sortOutcome1NonRawAscending());
                                    break;
                                case "Gap":
                                Collections.sort(page6Indig, new sortOutcome1GapAscending());
                                    break;
                              }
                        } else {
                            // Ascending proportional
                            switch(sortSelectGroup) {
                                case "Indig":
                                Collections.sort(page6Indig, new sortOutcome1IndigProportionalAscending());
                                    break;
                                case "Non":
                                Collections.sort(page6Indig, new sortOutcome1NonProportionalAscending());
                                    break;
                                case "Gap":
                                Collections.sort(page6Indig, new sortOutcome1GapAscending());
                                    break;
                              }
                        }
                        break;
                    }
                    // Descending branch
                    if(model.get("dataType").equals("rawSelected")){
                        switch(sortSelectGroup) {
                            case "Indig":
                            Collections.sort(page6Indig, new sortOutcome1IndigRawDescending());
                                break;
                            case "Non":
                            Collections.sort(page6Indig, new sortOutcome1NonRawDescending());
                                break;
                            case "Gap":
                            Collections.sort(page6Indig, new sortOutcome1GapDescending());
                                break;
                        }
                        
                    } else {
                        // Descending proportional
                        switch(sortSelectGroup) {
                            case "Indig":
                            Collections.sort(page6Indig, new sortOutcome1IndigProportionalDescending());
                                break;
                            case "Non":
                            Collections.sort(page6Indig, new sortOutcome1NonProportionalDescending());
                                break;
                            case "Gap":
                            Collections.sort(page6Indig, new sortOutcome1GapDescending());
                                break;
                          }
                    }
                    break;

                // For outcome 5
                case "5":
                // Ascending/Descending branching
                if (outcomeSortOrder.equals("ascending")){
                    // Raw/Proportional branching
                    if(model.get("dataType").equals("rawSelected")){
                        switch(sortSelectGroup) {
                            case "Indig":
                            Collections.sort(page6Indig, new sortOutcome5IndigRawAscending());
                                break;
                            case "Non":
                            Collections.sort(page6Indig, new sortOutcome5NonRawAscending());
                                break;
                            case "Gap":
                            Collections.sort(page6Indig, new sortOutcome5GapAscending());
                                break;
                          }
                    } else {
                        // Ascending proportional
                        switch(sortSelectGroup) {
                            case "Indig":
                            Collections.sort(page6Indig, new sortOutcome5IndigProportionalAscending());
                                break;
                            case "Non":
                            Collections.sort(page6Indig, new sortOutcome5NonProportionalAscending());
                                break;
                            case "Gap":
                            Collections.sort(page6Indig, new sortOutcome5GapAscending());
                                break;
                          }
                    }
                    break;
                }
                // Descending branch
                if(model.get("dataType").equals("rawSelected")){
                    switch(sortSelectGroup) {
                        case "Indig":
                        Collections.sort(page6Indig, new sortOutcome5IndigRawDescending());
                            break;
                        case "Non":
                        Collections.sort(page6Indig, new sortOutcome5NonRawDescending());
                            break;
                        case "Gap":
                        Collections.sort(page6Indig, new sortOutcome5GapDescending());
                            break;
                    }
                    
                } else {
                    // Descending proportional
                    switch(sortSelectGroup) {
                        case "Indig":
                        Collections.sort(page6Indig, new sortOutcome5IndigProportionalDescending());
                            break;
                        case "Non":
                        Collections.sort(page6Indig, new sortOutcome5NonProportionalDescending());
                            break;
                        case "Gap":
                        Collections.sort(page6Indig, new sortOutcome5GapDescending());
                            break;
                      }
                }
                break;

                // For outcome 6
                case "6":
                 // Ascending/Descending branching
                 if (outcomeSortOrder.equals("ascending")){
                    // Raw/Proportional branching
                    if(model.get("dataType").equals("rawSelected")){
                        switch(sortSelectGroup) {
                            case "Indig":
                            Collections.sort(page6Indig, new sortOutcome6IndigRawAscending());
                                break;
                            case "Non":
                            Collections.sort(page6Indig, new sortOutcome6NonRawAscending());
                                break;
                            case "Gap":
                            Collections.sort(page6Indig, new sortOutcome6GapAscending());
                                break;
                          }
                    } else {
                        // Ascending proportional
                        switch(sortSelectGroup) {
                            case "Indig":
                            Collections.sort(page6Indig, new sortOutcome6IndigProportionalAscending());
                                break;
                            case "Non":
                            Collections.sort(page6Indig, new sortOutcome6NonProportionalAscending());
                                break;
                            case "Gap":
                            Collections.sort(page6Indig, new sortOutcome6GapAscending());
                                break;
                          }
                    }
                    break;
                }
                // Descending branch
                if(model.get("dataType").equals("rawSelected")){
                    switch(sortSelectGroup) {
                        case "Indig":
                        Collections.sort(page6Indig, new sortOutcome6IndigRawDescending());
                            break;
                        case "Non":
                        Collections.sort(page6Indig, new sortOutcome6NonRawDescending());
                            break;
                        case "Gap":
                        Collections.sort(page6Indig, new sortOutcome6GapDescending());
                            break;
                    }
                    
                } else {
                    // Descending proportional
                    switch(sortSelectGroup) {
                        case "Indig":
                        Collections.sort(page6Indig, new sortOutcome6IndigProportionalDescending());
                            break;
                        case "Non":
                        Collections.sort(page6Indig, new sortOutcome6NonProportionalDescending());
                            break;
                        case "Gap":
                        Collections.sort(page6Indig, new sortOutcome6GapDescending());
                            break;
                      }
                }
                break;

                // For outcome 8
                case "8":
                 // Ascending/Descending branching
                 if (outcomeSortOrder.equals("ascending")){
                    // Raw/Proportional branching
                    if(model.get("dataType").equals("rawSelected")){
                        switch(sortSelectGroup) {
                            case "Indig":
                            Collections.sort(page6Indig, new sortOutcome8IndigRawAscending());
                                break;
                            case "Non":
                            Collections.sort(page6Indig, new sortOutcome8NonRawAscending());
                                break;
                            case "Gap":
                            Collections.sort(page6Indig, new sortOutcome8GapAscending());
                                break;
                          }
                    } else {
                        // Ascending proportional
                        switch(sortSelectGroup) {
                            case "Indig":
                            Collections.sort(page6Indig, new sortOutcome8IndigProportionalAscending());
                                break;
                            case "Non":
                            Collections.sort(page6Indig, new sortOutcome8NonProportionalAscending());
                                break;
                            case "Gap":
                            Collections.sort(page6Indig, new sortOutcome8GapAscending());
                                break;
                          }
                    }
                    break;
                }
                // Descending branch
                if(model.get("dataType").equals("rawSelected")){
                    switch(sortSelectGroup) {
                        case "Indig":
                        Collections.sort(page6Indig, new sortOutcome8IndigRawDescending());
                            break;
                        case "Non":
                        Collections.sort(page6Indig, new sortOutcome8NonRawDescending());
                            break;
                        case "Gap":
                        Collections.sort(page6Indig, new sortOutcome8GapDescending());
                            break;
                    }
                    
                } else {
                    // Descending proportional
                    switch(sortSelectGroup) {
                        case "Indig":
                        Collections.sort(page6Indig, new sortOutcome8IndigProportionalDescending());
                            break;
                        case "Non":
                        Collections.sort(page6Indig, new sortOutcome8NonProportionalDescending());
                            break;
                        case "Gap":
                        Collections.sort(page6Indig, new sortOutcome8GapDescending());
                            break;
                      }
                }
                break;
            }
        }


        model.put("tableDataIndig", page6Indig); 
        model.put("currentPage", "level3Filter");
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.render(TEMPLATE, model);
    }

}
