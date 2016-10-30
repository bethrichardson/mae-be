package edu.maebe.model;

import java.util.List;
import java.util.UUID;

public interface Model {
    //DiaperEntry
    UUID createDiaperEntry(boolean hasPee, boolean hasPoop);
    List<DiaperEntry> getAllDiaperEntries();
    boolean existDiaperEntry(UUID entry);

    //Journal
    UUID createJournal(String type, String value);
    List<Journal> getAllJournals();
    boolean existJournal(UUID journal);

    //MoodRating
    UUID createMoodRating(int agreeableness, int conscientiousness, int extraversion, int openness, int anger,
                          int anxiety, int depression, int immoderation, int selfConsciousness, int vulnerability,
                          int challenge, int closeness, int curiosity, int excitement, int harmony, int ideal,
                          int liberty, int love, int practicality, int selfExpression, int stability, int structure);
    List<MoodRating> getAllMoodRatings();
    boolean existMoodRating(UUID rating);

    //StatsEntry
    UUID createStatsEntry(Double heightInCM, Double weightInKg);
    List<StatsEntry> getAllStatsEntries();
    boolean existStatsEntry(UUID entry);
}