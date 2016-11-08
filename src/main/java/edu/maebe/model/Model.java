package edu.maebe.model;

import java.util.List;
import java.util.UUID;

public interface Model {
    //Journal
    UUID createJournal(String type, String value, String user);
    List<Journal> getAllJournals(String user);
    List<Journal> getAllJournals(String type, String user);
    boolean existJournal(UUID journal);

    //MoodRating
    UUID createMoodRating(MoodRating moodRating);
    List<MoodRating> getAllMoodRatings(String user);
    boolean existMoodRating(UUID rating);

    //Need
    UUID createNeed(Need need);
    List<Need> getAllNeeds(String user);
    boolean existNeed(UUID need);
}