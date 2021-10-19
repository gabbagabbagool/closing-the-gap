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


        boolean outcome1 = true;
        boolean outcome5 = true;
        boolean outcome6 = true;
        boolean outcome8 = true;
        if(context.method() == "POST"){
            // Add checkboxes
            html += "<div class='container'>";
            html += "<form action='/page2.html' method='post'>";
            html += "<input type='checkbox' name='outcome1' value='1Select' ";
            if (context.formParam("outcome1") != null){
                html += "checked";
                outcome1 = true;
            } else {
                outcome1 = false;
            }
            html += ">";
            html += "<label for='Outcome1'> Outcome 1</label><br>";
            html += "<input type='checkbox' name='outcome5' value='5Select' ";
            if (context.formParam("outcome5") != null){
                html += "checked";
                outcome5 = true;
            }else {
                outcome5 = false;
            }
            html += ">";
            html += "<label for='outcome5'> Outcome 5</label><br>";
            html += "<input type='checkbox' name='outcome6' value='6Select' ";
            if (context.formParam("outcome6") != null){
                html += "checked";
                outcome6 = true;
            }else {
                outcome6 = false;
            }
            html += ">";
            html += "<label for='outcome6'> Outcome 6</label><br>";
            html += "<input type='checkbox' name='outcome8' value='8Select' ";
            if (context.formParam("outcome8") != null){
                html += "checked";
                outcome8 = true;
            }else {
                outcome8 = false;
            }
            html += ">";
            html += "<label for='outcome8'> Outcome 8</label><br>";
            html += "<input type='submit' value='Update Data'>";
            html += "</form>";
            html += "</div>";
        }
        else if(context.method() == "GET"){
            // Add checkboxes
            html += "<div class='container'>";
            html += "<form action='/page2.html' method='post'>";
            html += "<input type='checkbox' id='outcome1' name='outcome1' value='outcome1' checked>";
            html += "<label for='outcome1'> Outcome 1</label><br>";
            html += "<input type='checkbox' id='outcome5' name='outcome5' value='Outcome5' checked>";
            html += "<label for='outcome5'> Outcome 5</label><br>";
            html += "<input type='checkbox' id='outcome6' name='outcome6' value='outcome6' checked>";
            html += "<label for='outcome6'> Outcome 6</label><br>";
            html += "<input type='checkbox' id='outcome8' name='outcome8' value='outcome8' checked>";
            html += "<label for='outcome8'> Outcome 8</label><br>";
            html += "<input type='submit' value='Update Data'>";
            html += "</form>";
            html += "</div>";
        }

        // set visabliity of table columns for each outcome
        String visColumn1 = "";
        String visColumn5 = "";
        String visColumn6 = "";
        String visColumn8 = "";
        if (outcome1 != true){
            visColumn1 = "style='display:none'";
        }
        if (outcome5 != true){
            visColumn5 = "style='display:none'";
        }
        if (outcome6 != true){
            visColumn6 = "style='display:none'";
        }
        if (outcome8 != true){
            visColumn8 = "style='display:none'";
        }
        // Add HTML for the table
        html += "<div class='container'>";
        html += "<div class='table-responsive'>";
        html += "<table class='table table-bordered table-striped table-sm'>";
        html +=   "<thead>";
        html +=     "<tr>";
        html +=       "<th scope='col'>#</th>";
        html +=       "<th scope='col'>LGA</th>"; 
        html +=       "<th " + visColumn1 + " scope='col'>Outcome 1</th>"; // Long & healthy lives
        html +=       "<th " + visColumn5 + " scope='col'>Outcome 5</th>"; // Full learning potential
        html +=       "<th " + visColumn6 + " scope='col'>Outcome 6</th>"; // Further education completion
        html +=       "<th " + visColumn8 + " scope='col'>Outcome 8</th>"; // Employment participation
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
            if (outcome1 == true){
                html +=         "<td>" + entry.getOutcomeMetric("raw", 5) + "</td>";
            }
            if (outcome5 == true){
                html +=         "<td>" + entry.getOutcomeMetric("raw", 5) + "</td>";
            }
            if (outcome6 == true){
                html +=         "<td>" + entry.getOutcomeMetric("raw", 5) + "</td>";
            }
            if (outcome8 == true){
                html +=         "<td>" + entry.getOutcomeMetric("raw", 5) + "</td>";
            }
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
