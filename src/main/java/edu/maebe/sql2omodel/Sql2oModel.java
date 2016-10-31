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
    public UUID createJournal(String type, String value) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID journalUuid = uuidGenerator.generate();
            conn.createQuery("insert into journals(id, type, value, date) VALUES (:id, :type, :value, :date)")
                    .addParameter("id", journalUuid)
                    .addParameter("type", type)
                    .addParameter("value", value)
                    .addParameter("date", new Date())
                    .executeUpdate();
            conn.commit();
            return journalUuid;
        }
    }

    @Override
    public List<Journal> getAllJournals() {
        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery("select * from journals").executeAndFetch(Journal.class);
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
    public UUID createMoodRating(int agreeableness, int conscientiousness, int extraversion, int openness, int anger,
                                 int anxiety, int depression, int immoderation, int selfConsciousness, int vulnerability)
    {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID MoodRatingUuid = uuidGenerator.generate();
            conn.createQuery("insert into mood_ratings(id, agreeableness, conscientiousness, " +
                                     "extraversion, openness, anger, " +
                                     "anxiety, depression, immoderation, " +
                                     "selfConsciousness, vulnerability, date) " +
                                     "VALUES (:id, :agreeableness, :conscientiousness, " +
                                     ":extraversion, :openness, :anger, " +
                                     ":anxiety, :depression, :immoderation, " +
                                     ":selfConsciousness, :vulnerability, :date)")
                    .addParameter("id", MoodRatingUuid)
                    .addParameter("agreeableness", agreeableness)
                    .addParameter("conscientiousness", conscientiousness)
                    .addParameter("extraversion", extraversion)
                    .addParameter("openness", openness)
                    .addParameter("anger", anger)
                    .addParameter("anxiety", anxiety)
                    .addParameter("depression", depression)
                    .addParameter("immoderation", immoderation)
                    .addParameter("selfConsciousness", selfConsciousness)
                    .addParameter("vulnerability", vulnerability)
                    .addParameter("date", new Date())
                    .executeUpdate();
            conn.commit();
            return MoodRatingUuid;
        }
    }

    @Override
    public List<MoodRating> getAllMoodRatings() {
        try (Connection conn = sql2o.open()) {
            List<MoodRating> MoodRatings = conn.createQuery("select * from mood_ratings")
                    .addColumnMapping("agreeableness", "currentMood.agreeableness")
                    .addColumnMapping("conscientiousness", "currentMood.conscientiousness")
                    .addColumnMapping("extraversion", "currentMood.extraversion")
                    .addColumnMapping("openness", "currentMood.openness")
                    .addColumnMapping("anger", "emotionalRange.anger")
                    .addColumnMapping("anxiety", "emotionalRange.anxiety")
                    .addColumnMapping("depression", "emotionalRange.depression")
                    .addColumnMapping("immoderation", "emotionalRange.immoderation")
                    .addColumnMapping("selfConsciousness", "emotionalRange.selfConsciousness")
                    .addColumnMapping("vulnerability", "emotionalRange.vulnerability")
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
    public UUID createNeed(int challenge, int closeness, int curiosity, int excitement, int harmony, int ideal,
                                 int liberty, int love, int practicality, int selfExpression, int stability, int structure)
    {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID NeedUuid = uuidGenerator.generate();
            conn.createQuery("insert into needs(id, challenge, closeness, " +
                                     "curiosity, excitement, harmony," +
                                     "ideal, liberty, love," +
                                     "practicality, selfExpression, stability," +
                                     "structure, date) VALUES (:id, :challenge, :closeness, " +
                                     ":curiosity, :excitement, :harmony, " +
                                     ":ideal, :liberty, :love, " +
                                     ":practicality, :selfExpression, :stability, " +
                                     ":structure, :date)")
                    .addParameter("id", NeedUuid)
                    .addParameter("challenge", challenge)
                    .addParameter("closeness", closeness)
                    .addParameter("curiosity", curiosity)
                    .addParameter("excitement", excitement)
                    .addParameter("harmony", harmony)
                    .addParameter("ideal", ideal)
                    .addParameter("liberty", liberty)
                    .addParameter("love", love)
                    .addParameter("practicality", practicality)
                    .addParameter("selfExpression", selfExpression)
                    .addParameter("stability", stability)
                    .addParameter("structure", structure)
                    .addParameter("date", new Date())
                    .executeUpdate();
            conn.commit();
            return NeedUuid;
        }
    }

    @Override
    public List<Need> getAllNeeds() {
        try (Connection conn = sql2o.open()) {
            List<Need> Needs = conn.createQuery("select * from needs")
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
}