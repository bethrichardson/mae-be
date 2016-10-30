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

    //diapers
    @Override
    public UUID createDiaperEntry(boolean hasPee, boolean hasPoop) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID DiaperEntryUuid = uuidGenerator.generate();
            conn.createQuery("insert into diaperEntries(id, hasPee, hasPoop, date) VALUES (:id, :hasPee, :hasPoop, :date)")
                    .addParameter("id", DiaperEntryUuid)
                    .addParameter("hasPee", hasPee)
                    .addParameter("hasPoop", hasPoop)
                    .addParameter("date", new Date())
                    .executeUpdate();
            conn.commit();
            return DiaperEntryUuid;
        }
    }

    @Override
    public List<DiaperEntry> getAllDiaperEntries() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select * from DiaperEntries").executeAndFetch(DiaperEntry.class);
        }
    }

    @Override
    public boolean existDiaperEntry(UUID entry) {
        try (Connection conn = sql2o.open()) {
            List<DiaperEntry> DiaperEntries = conn.createQuery("select * from DiaperEntries where id=:entry")
                    .addParameter("entry", entry)
                    .executeAndFetch(DiaperEntry.class);
            return DiaperEntries.size() > 0;
        }
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
                                 int anxiety, int depression, int immoderation, int selfConsciousness, int vulnerability,
                                 int challenge, int closeness, int curiosity, int excitement, int harmony, int ideal,
                                 int liberty, int love, int practicality, int selfExpression, int stability, int structure)
    {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID MoodRatingUuid = uuidGenerator.generate();
            conn.createQuery("insert into moodRatings(id, type, value, date) VALUES (:id, :type, :value, :date)")
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
            return MoodRatingUuid;
        }
    }

    @Override
    public List<MoodRating> getAllMoodRatings() {
        try (Connection conn = sql2o.open()) {
            List<MoodRating> MoodRatings = conn.createQuery("select * from moodRatings")
                    .addColumnMapping("anger", "emotionalRange.anger")
                    .addColumnMapping("anxiety", "emotionalRange.anxiety")
                    .addColumnMapping("depression", "emotionalRange.depression")
                    .addColumnMapping("immoderation", "emotionalRange.immoderation")
                    .addColumnMapping("selfConsciousness", "emotionalRange.selfConsciousness")
                    .addColumnMapping("vulnerability", "emotionalRange.vulnerability")
                    .addColumnMapping("challenge", "needs.challenge")
                    .addColumnMapping("closeness", "needs.closeness")
                    .addColumnMapping("curiosity", "needs.curiosity")
                    .addColumnMapping("excitement", "needs.excitement")
                    .addColumnMapping("harmony", "needs.harmony")
                    .addColumnMapping("ideal", "needs.ideal")
                    .addColumnMapping("liberty", "needs.liberty")
                    .addColumnMapping("love", "needs.love")
                    .addColumnMapping("practicality", "needs.practicality")
                    .addColumnMapping("selfExpression", "needs.selfExpression")
                    .addColumnMapping("stability", "needs.stability")
                    .addColumnMapping("structure", "needs.structure")
                    .executeAndFetch(MoodRating.class);
            return MoodRatings;
        }
    }

    @Override
    public boolean existMoodRating(UUID rating) {
        try (Connection conn = sql2o.open()) {
            List<MoodRating> MoodRatings = conn.createQuery("select * from MoodRatings where id=:rating")
                    .addParameter("rating", rating)
                    .executeAndFetch(MoodRating.class);
            return MoodRatings.size() > 0;
        }
    }

    //stats
    @Override
    public UUID createStatsEntry(Double heightInCm, Double weightInKg) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID StatsEntryUuid = uuidGenerator.generate();
            conn.createQuery("insert into statsEntries(id, type, heightInCm, weightInKg) VALUES (:id, :heightInCm, :weightInKg, :date)")
                    .addParameter("id", StatsEntryUuid)
                    .addParameter("heightInCm", heightInCm)
                    .addParameter("weightInKg", weightInKg)
                    .addParameter("date", new Date())
                    .executeUpdate();
            conn.commit();
            return StatsEntryUuid;
        }
    }

    @Override
    public List<StatsEntry> getAllStatsEntries() {
        try (Connection conn = sql2o.open()) {
            List<StatsEntry> StatsEntries = conn.createQuery("select * from StatsEntries").executeAndFetch(StatsEntry.class);
            return StatsEntries;
        }
    }

    @Override
    public boolean existStatsEntry(UUID entry) {
        try (Connection conn = sql2o.open()) {
            List<StatsEntry> StatsEntries = conn.createQuery("select * from StatsEntries where id=:entry")
                    .addParameter("entry", entry)
                    .executeAndFetch(StatsEntry.class);
            return StatsEntries.size() > 0;
        }
    }
}