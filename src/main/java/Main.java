import com.beust.jcommander.JCommander;
import edu.maebe.handlers.FriendCreateHandler;
import edu.maebe.handlers.FriendIndexHandler;
import edu.maebe.handlers.JournalCreateHandler;
import edu.maebe.handlers.JournalIndexHandler;
import edu.maebe.handlers.ReportIndexHandler;
import edu.maebe.sql2omodel.Sql2oModel;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import edu.maebe.model.Model;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.template.freemarker.FreeMarkerEngine;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

public class Main
{

    private static final Logger logger = Logger.getLogger(Main.class.getCanonicalName());

    public static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

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

        System.out.println("Working Directory = " +
                                   System.getProperty("user.dir"));

        try (Connection conn = sql2o.beginTransaction()) {

            String databaseSetup = readFile("src/main/resources/db/schema.sql", StandardCharsets.UTF_8);
            conn.createQuery(databaseSetup).executeUpdate();
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

        // insert a journal (using HTTP post method)
        post("/journals", new JournalCreateHandler(model));

        // get all journals (using HTTP get method)
        get("/journals", new JournalIndexHandler(model));

        // get all mood ratings (using HTTP get method)
        get("/report", new ReportIndexHandler(model));

        // insert a friend (using HTTP post method)
        post("/friends", new FriendCreateHandler(model));

        // get all friends (using HTTP get method)
        get("/friends", new FriendIndexHandler(model));
    }
}