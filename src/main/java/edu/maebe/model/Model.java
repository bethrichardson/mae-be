package edu.maebe.model;

import java.util.List;
import java.util.UUID;

public interface Model {
    //Journal
    UUID createJournal(String type, String value);
    List<Journal> getAllJournals();
    boolean existJournal(UUID journal);

    //MoodRating
    UUID createMoodRating(MoodRating moodRating);
    List<MoodRating> getAllMoodRatings();
    boolean existMoodRating(UUID rating);

    //Need
    UUID createNeed(Need need);
    List<Need> getAllNeeds();
    boolean existNeed(UUID need);
}