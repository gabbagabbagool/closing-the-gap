package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

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
public class Page1 implements Handler {

    // URL of this page relative to http://localhost:7000/
    public static final String URL = "/page1.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Movies</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";

        // Add the body
        html = html + "<body>";

        // Add HTML for link back to the homepage
        html = html + "<h1>Mission Statement</h1>";
        html = html + "<p>Return to Homepage: ";
        html = html + "<a href='/'>Link to Homepage</a>";
        html = html + "</p>";

        html = html + "<h2>Social Challenge</h2>";
        html = html + "<p>To address the social challenge, we will provide a platform to data which indicates the progress of the government's initiative to close the gap.";
        html = html + "This will be done by providing easy access to simple and clear representations of the data for users of all skill levels. With this access to information, users will be able to make their own informed decisions on the current progress."; 
        html = html + "By empowering casual access to information regarding the Closing the Gap national agreement we seek to provide greater accountability to the Australian Federal Government.";
        html = html + "</p>";

        html = html + "<h2>Target Demographic</h2>";
        html = html + "<p>In our research we have identified personas which are leading our design choices. Their commonality is an interest in information on the issues surrounding First Nations Peoples in Australia. However, the personas do have differing interests in how the information is provided to them, this data will need to be presented in infographics, deep-dive analysis, and in a way that provides conversational talking points.";
        html = html + "</p>";

        html = html + "<h2>Site Guide</h2>";
        html = html + "<p>A user will browse through our landing page, presented with an option to scroll down and view the mission statement and other information provided in this subtask. The pages linked to in the navbar will be providing data on the Closing the Gap outcomes by Local Government Area in a table, which is able to be manipulated by the user. These options for manipulation will change from page to page, as a consideration of the user’s needs. For users who require a shallower understanding of this information, we will provide tables which are sortable by outcome, and will also provide options to hide or display these four outcomes. For users who require more detailed analysis we will provide advanced filters.";
        html = html + "</p>";

        html = html + "<h2>Contributors</h2>";
        html = html + "<p>Thomas Monaghan - s3541313</p>";
        html = html + "<p>Jacob Hill - s3870682";
        html = html + "</p>";

        // Add HTML for link to the data
        html = html + "<p>";
        html = html + "<a href='page2.html'>Search Data</a>";
        html = html + "</p>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
