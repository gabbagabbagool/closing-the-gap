package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;

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
public class Page2 implements Handler {

    // URL of this page relative to http://localhost:7000/
    public static final String URL = "/page2.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html += "<head>" + 
               "<title>Outcomes by LGA</title>";

        // Add Bootsrap's CSS
        html += "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>";

        // Add some CSS (external file)
        html += "<link rel='stylesheet' type='text/css' href='common.css' />";

        // Add the body
        html += "<body>";

        // Bootstraps Navbar
        html += "<nav class='navbar navbar-expand-lg navbar-light bg-light'>";
        html +=     "<div class='container-fluid'>";
        html +=         "<a class='navbar-brand' href='/'>";
        html +=             "<img src='logo.png' alt='' height='24' class='d-inline-block align-text-top mx-2'>";
        html +=         "</a>";
        html +=         "<button class='navbar-toggler' type='button' data-bs-toggle='collapse' data-bs-target='#navbarNav' aria-controls='navbarNav' aria-expanded='false' aria-label='Toggle navigation'>";
        html +=             "<span class='navbar-toggler-icon'></span>";
        html +=         "</button>";
        html +=         "<div class='collapse navbar-collapse' id='navbarNav'>";
        html +=             "<ul class='navbar-nav'>";
        html +=              "<li class='nav-item'>";
        html +=                "<a class='nav-link' aria-current='page' href='/'>Home</a>";
        html +=              "</li>";
        html +=              "<li class='nav-item'>";
        html +=                "<a class='nav-link' href='/page1.html'>Mission Statement</a>";
        html +=              "</li>";
        html +=              "<li class='nav-item'>";
        html +=                "<a class='nav-link active' href='/page2.html'>By LGA</a>";
        html +=              "</li>";
        html +=              "<li class='nav-item'>";
        html +=                "<a class='nav-link' href='/page3.html'>By State</a>";
        html +=              "</li>";
        html +=              "<li class='nav-item'>";
        html +=                "<a class='nav-link' href='/page4.html'>Page 4</a>";
        html +=              "</li>";
        html +=              "<li class='nav-item'>";
        html +=                "<a class='nav-link' href='/page5.html'>Page 5</a>";
        html +=              "</li>";
        html +=            "</ul>";
        html +=         "</div>";
        html +=    "</div>";
        html += "</nav>";

        // Add HTML Mission statement heading
        html += "<div class='container'>";
        html +=     "<h1 class=\"display-4\">Outcomes by LGA</h1>";
        html += "</div>";

        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the ingig_Y12
        ArrayList<lgaOutcomeTracker> indigY12 = jdbc.outcomeBuilder();


        boolean outcome1 = false;
        boolean outcome5 = false;
        boolean outcome6 = false;
        boolean outcome8 = false;
        if(context.method() == "POST"){
            // Add checkboxes
            html = html + "<form action='/page2.html' method='post'>";
            html = html + "<input type='checkbox' name='outcome1' value='1Select' ";
            if (context.formParam("outcome1") != null){
                html = html + "checked";
                outcome1 = true;
            }
            html = html + ">";
            html = html + "<label for='Outcome1'> Outcome 1</label><br>";
            html = html + "<input type='checkbox' name='outcome5' value='5Select'";
            if (context.formParam("outcome5") != null){
                html = html + "checked";
                outcome5 = true;
            }
            html = html + ">";
            html = html + "<label for='outcome5'> Outcome 5</label><br>";
            html = html + "<input type='checkbox' name='outcome6' value='5Select'";
            if (context.formParam("outcome6") != null){
                html = html + "checked";
                outcome6 = true;
            }
            html = html + ">";
            html = html + "<label for='outcome6'> Outcome 6</label>";
            html = html + "<input type='submit' value='Submit'>";
            html = html + "</form>";
        }
        else if(context.method() == "GET"){
            // Add checkboxes
            html = html + "<form action='/page2.html' method='post'>";
            html = html + "<input type='checkbox' id='outcome1' name='outcome1' value='outcome1' >";
            html = html + "<label for='outcome1'> Outcome 1</label><br>";
            html = html + "<input type='checkbox' id='outcome5' name='outcome5' value='Outcome5' >";
            html = html + "<label for='outcome5'> Outcome 5</label><br>";
            html = html + "<input type='checkbox' id='outcome6' name='outcome6' value='outcome6' >";
            html = html + "<label for='outcome6'> Outcome 6</label>";
            html = html + "<input type='submit' value='Submit'>";
            html = html + "</form>";
        }


        // Add HTML for the table
        html += "<div class='container'>";
        html += "<div class='table-responsive'>";
        html += "<table class='table table-bordered table-striped table-sm'>";
        html +=   "<thead>";
        html +=     "<tr>";
        html +=       "<th scope='col'>#</th>";
        html +=       "<th scope='col'>LGA</th>"; // data-sortable="true" 
        html +=       "<th style='display:none' scope='col'>Long & healthy lives</th>"; // Here is an example of a hidden column
        html +=       "<th scope='col'>Full learning potential</th>";
        html +=       "<th style='display:none' scope='col'>Further education completion</th>";
        html +=       "<th scope='col'>Employment participation</th>";
        html +=     "</tr>";
        html +=   "</thead>";
        html +=   "<tbody>";

        /* TODO Before this iterator, we could save the values for raw/proportional 
           and hand it in to the loop to keep it dynamic */
        int rowIndex = 1;
        for (lgaOutcomeTracker entry : indigY12){
            html +=     "<tr>";
            html +=       "<th scope='row'>" + rowIndex + "</th>";
            html +=         "<td>" + entry.getLgaName() + "</td>";
            html +=         "<td>" + entry.getOutcomeMetric("raw", 5) + "</td>";
            html +=     "</tr>";
            rowIndex++;
        }

        html +=   "</tbody>";
        html += "</table>";
        html += "</div>";
        html += "</div>";

        // Finish the HTML webpage
        html += "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
