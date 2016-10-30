package edu.maebe;

import com.beust.jcommander.JCommander;
import com.heroku.sdk.jdbc.DatabaseUrl;
import edu.maebe.handlers.JournalCreateHandler;
import edu.maebe.handlers.JournalIndexHandler;
import edu.maebe.sql2omodel.Sql2oModel;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import edu.maebe.model.Model;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

public class MaebeService
{

    private static final Logger logger = Logger.getLogger(MaebeService.class.getCanonicalName());

    public static void main( String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);

        logger.finest("Options.debug = " + options.debug);
        logger.finest("Options.database = " + options.database);
        logger.finest("Options.dbHost = " + options.dbHost);
        logger.finest("Options.dbUsername = " + options.dbUsername);
        logger.finest("Options.dbPort = " + options.dbPort);
        logger.finest("Options.servicePort = " + options.servicePort);

        port(options.servicePort);

        Sql2o sql2o = new Sql2o(System.getenv("JDBC_DATABASE_URL"),
                                System.getenv("JDBC_DATABASE_USERNAME"), System.getenv("JDBC_DATABASE_PASSWORD"), new PostgresQuirks() {
            {
                // make sure we use default UUID converter.
                converters.put(UUID.class, new UUIDConverter());
            }
        });


        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery("CREATE TABLE IF NOT EXISTS journals (\n" +
                                     "    id uuid primary key,\n" +
                                     "    type text not null,\n" +
                                     "    value text,\n" +
                                     "    date date\n" +
                                     ");")
                    .executeUpdate();
            conn.commit();
        }

        catch (Exception e) {
            logger.warning("There was an error creating table: " + e);
        }

        Model model = new Sql2oModel(sql2o);
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(MaebeService.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);

        // insert a post (using HTTP post method)
        post("/journals", new JournalCreateHandler(model));

        // get all post (using HTTP get method)
        get("/journals", new JournalIndexHandler(model));
    }
}