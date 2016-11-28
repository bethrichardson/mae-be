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
        Child child = new Child(this.getUserSettings(user).getChildBirthDate());

        try (Connection conn = sql2o.beginTransaction()) {
            UUID journalUuid = uuidGenerator.generate();
            conn.createQuery("insert into journals(id, type, value, alexa, source, date) VALUES (:id, :type, :value, :alexa, :source, :date)")
                    .addParameter("id", journalUuid)
                    .addParameter("type", type)
                    .addParameter("value", value)
                    .addParameter("alexa", user)
                    .addParameter("date", new Date())
                    .addParameter("age", child.getAge())
                    .addParameter("source", source)
                    .executeUpdate();
            conn.commit();
            return journalUuid;
        }
    }

    @Override
    public UUID editJournal(String type, String value, UUID journal, String source) {
        try (Connection conn = sql2o.beginTransaction()) {

                conn.createQuery("update journals set type = :type, " +
                                         "value=:value, source=:source where id = :journalId")
                        .addParameter("journalId", journal)
                        .addParameter("type", type)
                        .addParameter("value", value)
                        .addParameter("source", source)
                        .executeUpdate();

            conn.commit();
            return journal;
        }
    }

    @Override
    public UUID editJournal(String type, String value, UUID journal, String source, int age) {
        try (Connection conn = sql2o.beginTransaction()) {

            conn.createQuery("update journals set type = :type, " +
                                     "value=:value, source=:source, age=:age where id = :journalId")
                    .addParameter("journalId", journal)
                    .addParameter("type", type)
                    .addParameter("value", value)
                    .addParameter("source", source)
                    .addParameter("age", age)
                    .executeUpdate();

            conn.commit();
            return journal;
        }
    }

    @Override
    public List<Journal> getAllJournals(String user) {
        String query;
        if (user == null) {
            query = "select * from journals where alexa is null";
        } else {
            query = "select * from journals where alexa = '" + user + "' order by date desc";
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
            query = "select * from journals where alexa = '" + user + "'and type = '" + type + "' order by date desc";
        }

        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery(query)
                    .addColumnMapping("alexa", "user")
                    .executeAndFetch(Journal.class);
            return journals;
        }
    }

    @Override
    public Journal getJournal(UUID journal) {
        String query = "select * from journals where id =:journal";

        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery(query)
                    .addParameter("journal", journal)
                    .addColumnMapping("alexa", "user")
                    .executeAndFetch(Journal.class);
            return journals.get(0);
        }
    }

    @Override
    public void deleteJournal(UUID journal) {
        String query = "delete from journals where id =:journal";

        try (Connection conn = sql2o.open()) {
            conn.createQuery(query)
                    .addParameter("journal", journal)
                    .executeUpdate();
        }
    }

    @Override
    public Journal getLastJournalForUser(String userid) {
        String query = "select * from journals where alexa =:userid order by date DESC";

        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery(query)
                    .addParameter("userid", userid)
                    .addColumnMapping("alexa", "user")
                    .executeAndFetch(Journal.class);
            return journals.get(0);
        }
    }

    @Override
    public boolean existJournal(UUID journal) {
        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery("select * from journals where id=:journal")
                    .addParameter("journal", journal)
                    .addColumnMapping("alexa", "user")
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
    public UUID createUserSettings(String user, Boolean immediateFeedback, String email, String phone,
                                   int numberOfChildren, String provider, String first, String last,
                                   Date birth, String gender, Date lastUpdate){
        UUID userSettingsId;

        try (Connection conn = sql2o.beginTransaction()) {
            if (this.existsUserSettings(user)) {
                userSettingsId = getUserSettings(user).getId();

                conn.createQuery("update user_settings set immediate_feedback = :immediateFeedback, " +
                                         "email=:email, phone=:phone, num_children=:numberOfChildren, " +
                                         "provider=:provider, first=:first, last=:last, birth=:birth," +
                                         "gender=:gender, last_update=:lastUpdate where userid = :userId")
                        .addParameter("userId", user)
                        .addParameter("immediateFeedback", immediateFeedback)
                        .addParameter("email", email)
                        .addParameter("phone", phone)
                        .addParameter("numberOfChildren", numberOfChildren)
                        .addParameter("provider", provider)
                        .addParameter("first", first)
                        .addParameter("last", last)
                        .addParameter("birth", birth)
                        .addParameter("gender", gender)
                        .addParameter("lastUpdate", lastUpdate)
                        .executeUpdate();
            }
            else {
                userSettingsId = uuidGenerator.generate();
                conn.createQuery("insert into user_settings (id, userid, immediate_feedback, email," +
                                         " phone, num_children, provider, birth, gender, last_update) values (:id, :userId, :immediateFeedback," +
                                         " :email, :phone, :numberOfChildren, :provider, :birth, :gender, :lastUpdate)")
                        .addParameter("id", userSettingsId)
                        .addParameter("userId", user)
                        .addParameter("immediateFeedback", immediateFeedback)
                        .addParameter("email", email)
                        .addParameter("phone", phone)
                        .addParameter("numberOfChildren", numberOfChildren)
                        .addParameter("provider", provider)
                        .addParameter("birth", birth)
                        .addParameter("gender", gender)
                        .addParameter("lastUpdate", new Date())
                        .executeUpdate();
            }

            conn.commit();
            return userSettingsId;
        }
    }

    @Override
    public UserSettings getUserSettings(String user){
        try (Connection conn = sql2o.beginTransaction()) {
            List<UserSettings> settings = conn.createQuery("select * from user_settings where userid = :user")
                    .addParameter("user", user)
                    .addColumnMapping("userid", "userId")
                    .addColumnMapping("immediate_feedback", "immediateFeedback")
                    .addColumnMapping("num_children", "numberOfChildren")
                    .addColumnMapping("birth", "childBirthDate")
                    .addColumnMapping("last_update", "lastUpdate")
                    .executeAndFetch(UserSettings.class);

            if(settings.size() > 0){
                return  settings.get(0);
            }            
            return null;
        }
    }

    @Override
    public boolean existsUserSettings(String userId){
        try (Connection conn = sql2o.open()) {
            List<UserSettings> settings = conn.createQuery("select * from user_settings where userid=:userId")
                    .addParameter("userId", userId)
                    .addColumnMapping("userid", "userId")
                    .addColumnMapping("immediate_feedback", "immediateFeedback")
                    .addColumnMapping("num_children", "numberOfChildren")
                    .addColumnMapping("birth", "childBirthDate")
                    .addColumnMapping("last_update", "lastUpdate")
                    .executeAndFetch(UserSettings.class);
            return settings.size() > 0;
        }
    }

    @Override
    public UUID createFriend(Friend friend, String user) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID friendUuid = uuidGenerator.generate();
            conn.createQuery("insert into friends(id, name, nickname, phone, email, userid, type, date) VALUES " +
                                     "(:id, :name, :nickname, :phone, :email, :userid, :type, :date)")
                    .addParameter("id", friendUuid)
                    .addParameter("name", friend.getName())
                    .addParameter("nickname", friend.getNickname())
                    .addParameter("phone", friend.getPhone())
                    .addParameter("email", friend.getEmail())
                    .addParameter("userid", friend.getUserId())
                    .addParameter("type", friend.getType())
                    .addParameter("date", new Date())
                    .executeUpdate();
            conn.commit();
            return friendUuid;
        }
    }

    @Override
    public List<Friend> getAllFriends(String user) {
        try (Connection conn = sql2o.open()) {
            List<Friend> friends = conn.createQuery("select * from friends where userid = " + "'" + user + "'")
                    .addColumnMapping("userid", "userId")
                    .executeAndFetch(Friend.class);
            return friends;
        }
    }

    @Override
    public List<Friend> getAllFriends(String type, String user) {
        String query;
        if (user == null) {
            query = "select * from friends where userid is null and type = '" + type + "'";
        } else {
            query = "select * from friends where userid = '" + user + "'and type = '" + type + "'";
        }

        try (Connection conn = sql2o.open()) {
            List<Friend> friends = conn.createQuery(query)
                    .addColumnMapping("userid", "userId")
                    .executeAndFetch(Friend.class);
            return friends;
        }
    }

    @Override
    public boolean existFriend(UUID friend) {
        try (Connection conn = sql2o.open()) {
            List<Friend> friends = conn.createQuery("select * from friends where id=:friend")
                    .addParameter("friend", friend)
                    .executeAndFetch(Friend.class);
            return friends.size() > 0;
        }
    }
}