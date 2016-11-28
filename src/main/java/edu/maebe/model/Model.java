package edu.maebe.model;

import java.util.List;
import java.util.UUID;
import java.util.Date;

public interface Model {
    //Journal
    UUID createJournal(String type, String value, String user, String source);
    UUID editJournal(String type, String value, UUID journal, String source);
    UUID editJournal(String type, String value, UUID journal, String source, int age);
    List<Journal> getAllJournals(String user);
    List<Journal> getAllJournals(String type, String user);
    Journal getJournal(UUID journal);
    void deleteJournal(UUID journal);
    Journal getLastJournalForUser(String userId);
    boolean existJournal(UUID journal);

    //MoodRating
    UUID createMoodRating(MoodRating moodRating);
    List<MoodRating> getAllMoodRatings(String user);
    boolean existMoodRating(UUID rating);

    //Need
    UUID createNeed(Need need);
    List<Need> getAllNeeds(String user);
    boolean existNeed(UUID need);

    //Need
    UUID createFriend(Friend friend, String user);
    List<Friend> getAllFriends(String user);
    List<Friend> getAllFriends(String type, String user);
    boolean existFriend(UUID friend);
    
    //UserSetting
    UUID createUserSettings(String userId, Boolean immediateFeedback, String email, String phone,
            int numberOfChildren, String provider, String first, String last, Date birth, Date lastUpdate);
    UserSettings getUserSettings(String user);
    boolean existsUserSettings(String userSettings);
}