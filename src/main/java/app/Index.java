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

        // Add HTML for the logo.png image
        html += "<img src='logo.png' height='100px'/>";

        html += "<div class='topnav'>";
        html +=   "<a href='/'>Homepage</a>";
        html +=   "<a href='page1.html'>Mission Statement</a>";
        html +=   "<a href='page2.html'>LGA Data</a>";
        html +=   "<a href='page3.html'>State Data</a>";
        html +=   "<a href='page4.html'>The Gap</a>";
        html +=   "<a href='page5.html'>Deep Dive</a>";
        html += "</div>";

        // Add HTML for the list of pages
        html += "<h1>Homepage</h1>" +
               "<p>Links to sub-pages</p>" +
               "<ul>";

        // Link for each page
        html += "<li> <a href='page1.html'>Page 1</a> </li>";
        html += "<li> <a href='page2.html'>Page 2</a> </li>";
        html += "<li> <a href='page3.html'>Page 3</a> </li>";
        html += "<li> <a href='page4.html'>Page 4</a> </li>";
        html += "<li> <a href='page5.html'>Page 5</a> </li>";
        html += "<li> <a href='page6.html'>Page 6</a> </li>";

        // Finish the List HTML
        html += "</ul>";

        // Finish the HTML webpage, import Bootstraps scripts
        html += "</body>";
        html += "<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js' integrity='sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p' crossorigin='anonymous'></script>";
        html += "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
