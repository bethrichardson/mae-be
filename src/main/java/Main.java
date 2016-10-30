import com.beust.jcommander.JCommander;
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
import spark.template.freemarker.FreeMarkerEngine;


import java.util.UUID;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

public class Main
{

    private static final Logger logger = Logger.getLogger(Main.class.getCanonicalName());

    public static void main( String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);

        logger.finest("Options.debug = " + options.debug);
        logger.finest("Options.database = " + options.database);
        logger.finest("Options.dbHost = " + options.dbHost);
        logger.finest("Options.dbUsername = " + options.dbUsername);
        logger.finest("Options.dbPort = " + System.getenv("PORT"));

        port(Integer.valueOf(System.getenv("PORT")));

        Sql2o sql2o = new Sql2o(System.getenv("JDBC_DATABASE_URL"),
                                System.getenv("JDBC_DATABASE_USERNAME"), System.getenv("JDBC_DATABASE_PASSWORD"), new PostgresQuirks() {
            {
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
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);

        // insert a post (using HTTP post method)
        post("/journals", new JournalCreateHandler(model));

        // get all post (using HTTP get method)
        get("/journals", new JournalIndexHandler(model));
    }
}