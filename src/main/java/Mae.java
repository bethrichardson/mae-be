import edu.maebe.handlers.Advice.AdviceIndexHandler;
import edu.maebe.handlers.Friend.FriendCreateHandler;
import edu.maebe.handlers.Friend.FriendIndexHandler;
import edu.maebe.handlers.Journal.JournalCreateHandler;
import edu.maebe.handlers.Journal.JournalDeleteHandler;
import edu.maebe.handlers.Journal.JournalIndexHandler;
import edu.maebe.handlers.Journal.JournalListHandler;
import edu.maebe.handlers.Journal.JournalUpdateHandler;
import edu.maebe.handlers.Reminders.SendReminderHandler;
import edu.maebe.handlers.HealthReport.ReportIndexHandler;
import edu.maebe.handlers.UserSettings.UserSettingsCreateHandler;
import edu.maebe.handlers.UserSettings.UserSettingsIndexHandler;
import edu.maebe.sql2omodel.Sql2oModel;
import edu.maebe.model.Model;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.log4j.Logger;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.SparkBase.port;

public class Mae
{
    private static final Logger logger = Logger.getLogger("edu.maebe");

    public static void main( String[] args) {
        port(Integer.valueOf(System.getenv("PORT")));
        Sql2o sql2o = getSqlDataAccess();
        initializeDatabase(sql2o);

        Model model = new Sql2oModel(sql2o);
        initializeHandlers(model);
    }

    private static void initializeHandlers(Model model) {
        // insert a journal
        post("/journals", new JournalCreateHandler(model));

        // update a journal
        put("/journals/:uuid", new JournalUpdateHandler(model));

        // get all journals
        get("/journals", new JournalListHandler(model));

        // get one journal
        get("/journals/:uuid", new JournalIndexHandler(model));

        // delete one journal
        delete("/journals/:uuid", new JournalDeleteHandler(model));

        // insert a journal
        post("/reminder/:uuid", new SendReminderHandler(model));

        // get all mood ratings
        get("/report", new ReportIndexHandler(model));

        // insert a friend
        post("/friends", new FriendCreateHandler(model));

        // get all friends
        get("/friends", new FriendIndexHandler(model));

        // create or update user settings
        // TODO: might want to split this into different behavior for put/post
        post("/settings", new UserSettingsCreateHandler(model));

        // get user settings
        get("/settings", new UserSettingsIndexHandler(model));

        // get random advice
        get("/advice", new AdviceIndexHandler(model));
    }

    private static Sql2o getSqlDataAccess() {
        return new Sql2o(System.getenv("JDBC_DATABASE_URL"),
                         System.getenv("JDBC_DATABASE_USERNAME"),
                         System.getenv("JDBC_DATABASE_PASSWORD"),
                         new PostgresQuirks() {
                {
                    converters.put(UUID.class, new UUIDConverter());
                }
            });
    }

    private static void initializeDatabase(Sql2o sql2o) {
        try (Connection conn = sql2o.beginTransaction()) {

            String databaseSetup = readFile("src/main/resources/db/schema.sql", StandardCharsets.UTF_8);
            conn.createQuery(databaseSetup).executeUpdate();
            conn.commit();
        }

        catch (Exception e) {
            logger.warn("There was an error creating table: " + e);
        }
    }

    private static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}