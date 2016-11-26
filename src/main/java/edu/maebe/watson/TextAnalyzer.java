package edu.maebe.watson;

import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import edu.maebe.model.Journal;
import edu.maebe.model.Model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class TextAnalyzer {
    private Model model;
    private String userId;
    private boolean enoughTextForAnalysis;
    private static final int MINIMUM_TEXT_CHARACTER_COUNT = 2000;

    public TextAnalyzer(Model model, String userId){
        this.model = model;
        this.userId = userId;
    }

    public TextAnalyzer(String userId){
        this.userId = userId;
    }

    public PersonalityScore getPersonalityScore(String baseText) {
        String textForAnalysis = getEnoughJournalTextForWatson(baseText);
        PersonalityScore personalityScore;

        if (enoughTextForAnalysis) {
            Profile profile = getPersonalityProfileFromWatson(textForAnalysis);
            personalityScore = buildPersonalityScore(profile);
        } else {
            personalityScore = new PersonalityScore(userId);
        }

        return personalityScore;
    }

    public PersonalityScore buildPersonalityScore(Profile profile) {
        PersonalityScore personalityScore = new PersonalityScore(userId, profile);
        model.createMoodRating(personalityScore.getMoodRating());
        model.createNeed(personalityScore.getNeed());
        System.out.print("PERSONALITY SCORE: " + personalityScore.toString());

        return personalityScore;
    }

    public PersonalityScore buildTestPersonalityScore(Profile profile) {
        PersonalityScore personalityScore = new PersonalityScore(userId, profile);
        System.out.print("PERSONALITY SCORE: " + personalityScore.toString());

        return personalityScore;
    }

    private Profile getPersonalityProfileFromWatson(String text) {
        PersonalityInsights service = new PersonalityInsights("2016-10-19");
        service.setUsernameAndPassword(WatsonCredentials.username, WatsonCredentials.password);
        ProfileOptions.Builder builder = new ProfileOptions.Builder();
        builder.rawScores(true)
                .text(text);

        Profile profile = service.getProfile(builder.build()).execute();
        System.out.println(profile);

        return profile;
    }

    private String getEnoughJournalTextForWatson(String baseText) {
        String textForAnalysis = baseText;
        List<Journal> allJournals = model.getAllJournals(userId);
        enoughTextForAnalysis = true;
        Journal currentJournal;
        Date currentJournalDate = new Date();
        Date thresholdDate = getDateForMinimumAmountOfAnalysis();

        ListIterator<Journal> journalIterator = allJournals.listIterator(allJournals.size());
        journalIterator.previous();

        // Continue to gather text until we have enough text and enough time to analyze
        while (textForAnalysis.length() < MINIMUM_TEXT_CHARACTER_COUNT || currentJournalDate.after(thresholdDate)) {
            if (journalIterator.hasPrevious()) {
                currentJournal = journalIterator.previous();
                currentJournalDate = currentJournal.getDate();
                if (currentJournal.getType().equals(Journal.JOURNAL_TYPE_TEXT)){
                    textForAnalysis += " " + currentJournal.getValue();
                }
            } else {
                enoughTextForAnalysis = false;
                break;
            }
        }

        String log = String.format("Text analyzed characters=%s; earliestJournal=%s", textForAnalysis.length(), currentJournalDate);
        System.out.println(log);

        return textForAnalysis;
    }

    private Date getDateForMinimumAmountOfAnalysis() {
        int minNumberOfDays = 14;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -minNumberOfDays);
        return cal.getTime();
    }
}
