package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Index implements Handler {

    // URL of this page relative to http://localhost:7000/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html += "<head>" + 
               "<title>Homepage</title>";

        // Bootstrap's CSS
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
        html +=                "<a class='nav-link active' aria-current='page' href='/'>Home</a>";
        html +=              "</li>";
        html +=              "<li class='nav-item'>";
        html +=                "<a class='nav-link' href='/page1.html'>Mission Statement</a>";
        html +=              "</li>";
        html +=              "<li class='nav-item'>";
        html +=                "<a class='nav-link' href='/page2.html'>By LGA</a>";
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

        // Add HTML for the important facts
        html += "<div class=\"jumbotron jumbotron-fluid\">";
        html +=     "<div class='container'>";
        html +=         "<h1 class='display-4'>Important facts</h1>";
        html +=     "</div>";
        html += "</div>";

        // Carousel of important facts
        html +=     "<div class='container'>";
        html +=  "<div id='carouselExampleIndicators' class='carousel slide' data-bs-ride='carousel'>";
        html +=  "<div class='carousel-indicators'>";
        html +=    "<button type='button' data-bs-target='#carouselExampleIndicators' data-bs-slide-to='0' class='active' aria-current='true' aria-label='Slide 1'></button>";
        html +=    "<button type='button' data-bs-target='#carouselExampleIndicators' data-bs-slide-to='1' aria-label='Slide 2'></button>";
        html +=    "<button type='button' data-bs-target='#carouselExampleIndicators' data-bs-slide-to='2' aria-label='Slide 3'></button>";
        html +=  "</div>";
        html +=  "<div class='carousel-inner'>";
        html +=    "<div class='carousel-item active'>";
        html +=      "<img src='Important_fact_1.jpg' class='d-block w-100' alt='Important fact 1'>";
        html +=    "</div>";
        html +=    "<div class='carousel-item'>";
        html +=      "<img src='Important_fact_2.jpg' class='d-block w-100' alt='Important fact 2'>";
        html +=    "</div>";
        html +=    "<div class='carousel-item'>";
        html +=      "<img src='Important_fact_3.jpg' class='d-block w-100' alt='Important fact 3'>";
        html +=    "</div>";
        html +=  "</div>";
        html +=  "<button class='carousel-control-prev' type='button' data-bs-target='#carouselExampleIndicators' data-bs-slide='prev'>";
        html +=    "<span class='carousel-control-prev-icon' aria-hidden='true'></span>";
        html +=    "<span class='visually-hidden'>Previous</span>";
        html +=  "</button>";
        html +=  "<button class='carousel-control-next' type='button' data-bs-target='#carouselExampleIndicators' data-bs-slide='next'>";
        html +=    "<span class='carousel-control-next-icon' aria-hidden='true'></span>";
        html +=    "<span class='visually-hidden'>Next</span>";
        html +=  "</button>";
        html += "</div>";
        html += "</div>";

        // Add HTML for link for button to the Mission statement
        html +=  "<div class='container'>";
        html +=     "<p>";
        html +=     "<a href='page1.html' class='btn btn-outline-primary' role='button'>Mission Statement</a>";
        html +=     "</p>";
        html +=  "</div>";
        

        // Finish the HTML webpage, import Bootstraps scripts
        html += "</body>";
        html += "<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js' integrity='sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p' crossorigin='anonymous'></script>";
        html += "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
