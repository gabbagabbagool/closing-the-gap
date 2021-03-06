package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;


/**
 * Main Application Class.
 * <p>
 * Running this class as regular java application will start the 
 * Javalin HTTP Server and our web application.
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class App {

    public static final int         JAVALIN_PORT    = 7000;
    public static final String      CSS_DIR         = "css/";
    public static final String      IMAGES_DIR      = "images/";

    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7000
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));
            
            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);


        // Configure Web Routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // All webpages are listed here as GET pages
        app.get(Index.URL, new Index());
        app.get(missionStatement.URL, new missionStatement());
        app.get(level2LGA.URL, new level2LGA());
        app.get(level2State.URL, new level2State());
        app.get(level3Filter.URL, new level3Filter());
        app.get(level3LGA.URL, new level3LGA());

        // Add / uncomment POST commands for any pages that need web form POSTS
        // app.post(Index.URL, new Index());
        // app.post(Page1.URL, new Page1());
        app.post(level2LGA.URL, new level2LGA());
        app.post(level2State.URL, new level2State());
        app.post(level3Filter.URL, new level3Filter());
        app.post(level3LGA.URL, new level3LGA());
    }

}
