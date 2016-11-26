package edu.maebe.sql2omodel;

import edu.maebe.RandomUuidGenerator;
import edu.maebe.UuidGenerator;
import edu.maebe.model.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Sql2oModel implements Model {

    private Sql2o sql2o;
    private UuidGenerator uuidGenerator;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
        uuidGenerator = new RandomUuidGenerator();
    }

    //journals
    @Override
    public UUID createJournal(String type, String value, String user, String source) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID journalUuid = uuidGenerator.generate();
            conn.createQuery("insert into journals(id, type, value, alexa, date) VALUES (:id, :type, :value, :alexa, :date)")
                    .addParameter("id", journalUuid)
                    .addParameter("type", type)
                    .addParameter("value", value)
                    .addParameter("alexa", user)
                    .addParameter("date", new Date())
                    .addParameter("source", source)
                    .executeUpdate();
            conn.commit();
            return journalUuid;
        }
    }

    @Override
    public List<Journal> getAllJournals(String user) {
        String query;
        if (user == null) {
            query = "select * from journals where alexa is null";
        } else {
            query = "select * from journals where alexa = '" + user + "'";
        }

        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery(query)
                    .addColumnMapping("alexa", "user")
                    .executeAndFetch(Journal.class);
            return journals;
        }
    }

    @Override
    public List<Journal> getAllJournals(String user, String type) {
        String query;
        if (user == null) {
            query = "select * from journals where alexa is null and type = '" + type + "'";
        } else {
            query = "select * from journals where alexa = '" + user + "'and type = '" + type + "'";
        }

        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery(query)
                    .addColumnMapping("alexa", "user")
                    .executeAndFetch(Journal.class);
            return journals;
        }
    }

    @Override
    public boolean existJournal(UUID journal) {
        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery("select * from journals where id=:journal")
                    .addParameter("journal", journal)
                    .executeAndFetch(Journal.class);
            return journals.size() > 0;
        }
    }

    //mood rating
    @Override
    public UUID createMoodRating(MoodRating moodRating)
    {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID MoodRatingUuid = uuidGenerator.generate();
            conn.createQuery("insert into mood_ratings(id, big5_agreeableness, big5_conscientiousness, " +
                                     "big5_extraversion, big5_openness, facet_anger, " +
                                     "facet_anxiety, facet_depression, facet_immoderation, " +
                                     "facet_self_consciousness, facet_vulnerability, alexa, date) " +
                                     "VALUES (:id, :big5_agreeableness, :big5_conscientiousness, " +
                                     ":big5_extraversion, :big5_openness, :facet_anger, " +
                                     ":facet_anxiety, :facet_depression, :facet_immoderation, " +
                                     ":facet_self_consciousness, :facet_vulnerability, :alexa, :date)")
                    .addParameter("id", MoodRatingUuid)
                    .addParameter("big5_agreeableness", moodRating.getBig5_agreeableness())
                    .addParameter("big5_conscientiousness", moodRating.getBig5_conscientiousness())
                    .addParameter("big5_extraversion", moodRating.getBig5_extraversion())
                    .addParameter("big5_openness", moodRating.getBig5_openness())
                    .addParameter("facet_anger", moodRating.getFacet_anger())
                    .addParameter("facet_anxiety", moodRating.getFacet_anxiety())
                    .addParameter("facet_depression", moodRating.getFacet_depression())
                    .addParameter("facet_immoderation", moodRating.getFacet_immoderation())
                    .addParameter("facet_self_consciousness", moodRating.getFacet_self_consciousness())
                    .addParameter("facet_vulnerability", moodRating.getFacet_vulnerability())
                    .addParameter("alexa", moodRating.getUserId())
                    .addParameter("date", new Date())
                    .executeUpdate();
            conn.commit();
            return MoodRatingUuid;
        }
    }

    @Override
    public List<MoodRating> getAllMoodRatings(String user) {
        try (Connection conn = sql2o.open()) {
            List<MoodRating> MoodRatings = conn.createQuery("select * from mood_ratings where alexa = " + "'" + user + "'")
                    .addColumnMapping("alexa", "userId")
                    .executeAndFetch(MoodRating.class);
            return MoodRatings;
        }
    }

    @Override
    public boolean existMoodRating(UUID rating) {
        try (Connection conn = sql2o.open()) {
            List<MoodRating> MoodRatings = conn.createQuery("select * from mood_ratings where id=:rating")
                    .addParameter("rating", rating)
                    .executeAndFetch(MoodRating.class);
            return MoodRatings.size() > 0;
        }
    }

    //needs
    @Override
    public UUID createNeed(Need need)
    {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID NeedUuid = uuidGenerator.generate();
            conn.createQuery("insert into needs(id, need_challenge, need_closeness, " +
                                     "need_curiosity, need_excitement, need_harmony," +
                                     "need_ideal, need_liberty, need_love," +
                                     "need_practicality, need_self_expression, need_stability," +
                                     "need_structure, alexa, date) VALUES (:id, :need_challenge, :need_closeness, " +
                                     ":need_curiosity, :need_excitement, :need_harmony, " +
                                     ":need_ideal, :need_liberty, :need_love, " +
                                     ":need_practicality, :need_self_expression, :need_stability, " +
                                     ":need_structure, :alexa, :date)")
                    .addParameter("id", NeedUuid)
                    .addParameter("need_challenge", need.getNeed_challenge())
                    .addParameter("need_closeness", need.getNeed_closeness())
                    .addParameter("need_curiosity", need.getNeed_curiosity())
                    .addParameter("need_excitement", need.getNeed_excitement())
                    .addParameter("need_harmony", need.getNeed_harmony())
                    .addParameter("need_ideal", need.getNeed_ideal())
                    .addParameter("need_liberty", need.getNeed_liberty())
                    .addParameter("need_love", need.getNeed_love())
                    .addParameter("need_practicality", need.getNeed_practicality())
                    .addParameter("need_self_expression", need.getNeed_self_expression())
                    .addParameter("need_stability", need.getNeed_stability())
                    .addParameter("need_structure", need.getNeed_structure())
                    .addParameter("alexa", need.getUserId())
                    .addParameter("date", new Date())
                    .executeUpdate();
            conn.commit();
            return NeedUuid;
        }
    }

    @Override
    public List<Need> getAllNeeds(String user) {
        try (Connection conn = sql2o.open()) {
            List<Need> Needs = conn.createQuery("select * from needs where alexa = " + "'" + user + "'")
                    .executeAndFetch(Need.class);
            return Needs;
        }
    }

    @Override
    public boolean existNeed(UUID need) {
        try (Connection conn = sql2o.open()) {
            List<Need> Needs = conn.createQuery("select * from needs where id=:need")
                    .addParameter("need", need)
                    .executeAndFetch(Need.class);
            return Needs.size() > 0;
        }
    }

    //userSettings
    @Override
    public UUID createUserSettings(String user, Boolean readFeedbackImmediately, String emailAddress, String phoneNumber,
                                   int numberOfChildren, String cellProvider, Date lastUpdate){
        try (Connection conn = sql2o.beginTransaction()) {
            UUID userSettingsId = uuidGenerator.generate();
            conn.createQuery("insert into userSettings (id, user, readFeedbackImmediately, emailAddress," +
                    " phoneNumber, numberOfChildren, cellProvider, lastUpdate) values (:id, :user, :readFeedbackImmediately," +
                    " :emailAddress, :phoneNumber, :numberOfChildren, :cellProvider, :lastUpdate)")
                    .addParameter("id", userSettingsId)
                    .addParameter("user", user)
                    .addParameter("readFeedbackImmediately", readFeedbackImmediately)
                    .addParameter("emailAddress", emailAddress)
                    .addParameter("phoneNumber", phoneNumber)
                    .addParameter("numberOfChildren", numberOfChildren)
                    .addParameter("cellProvider", cellProvider)
                    .addParameter("lastUpdate", new Date())
                    .executeUpdate();
            conn.commit();
            return  userSettingsId;
        }

    }

    @Override
    public UserSettings getUserSettings(String user){
        try (Connection conn = sql2o.beginTransaction()) {
            List<UserSettings> settings = conn.createQuery("select * from userSettings where alex = :user")
                    .addParameter("user", user)
                    .executeAndFetch(UserSettings.class);

            if(settings.size() > 0){
                return  settings.get(0);
            }            
            return null;
        }
    }

    @Override
    public boolean existsUserSettings(UUID userSettings){
        try (Connection conn = sql2o.open()) {
            List<UserSettings> settings = conn.createQuery("select * from userSettings where id=:userSettings")
                    .addParameter("userSettings", userSettings)
                    .executeAndFetch(UserSettings.class);
            return settings.size() > 0;
        }
    }
}