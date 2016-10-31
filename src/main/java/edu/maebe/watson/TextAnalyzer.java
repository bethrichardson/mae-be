package edu.maebe.watson;

import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import edu.maebe.model.Journal;
import edu.maebe.model.Model;

import java.util.List;
import java.util.ListIterator;

public class TextAnalyzer {
    private Model model;
    private boolean enoughTextForAnalysis;
    private static final int MINIMUM_TEXT_CHARACTER_COUNT = 600;

    public TextAnalyzer(Model model){
        this.model = model;
    }

    public PersonalityScore getPersonalityScore(String baseText) {
        String textForAnalysis = getEnoughJournalTextForWatson(baseText);
        PersonalityScore personalityScore;

        if (enoughTextForAnalysis) {
            Profile profile = getPersonalityProfileFromWatson(textForAnalysis);
            personalityScore = buildPersonalityScore(profile);
        } else {
            Profile profile = new Profile();
            personalityScore = new PersonalityScore(profile);
        }

        return personalityScore;
    }

    private PersonalityScore buildPersonalityScore(Profile profile) {
        PersonalityScore personalityScore = new PersonalityScore(profile);
        model.createMoodRating(personalityScore.getMoodRating());
        model.createNeed(personalityScore.getNeed());
        System.out.print("PERSONALITY SCORE: " + personalityScore.toString());

        return personalityScore;
    }

    private Profile getPersonalityProfileFromWatson(String text) {
        PersonalityInsights service = new PersonalityInsights("2016-10-19");
        service.setUsernameAndPassword(WatsonCredentials.username, WatsonCredentials.password);

        Profile profile = service.getProfile(text).execute();
        System.out.println(profile);

        return profile;
    }

    private String getEnoughJournalTextForWatson(String baseText) {
        String textForAnalysis = baseText;
        List<Journal> allJournals = model.getAllJournals();
        enoughTextForAnalysis = true;

        ListIterator<Journal> journalIterator = allJournals.listIterator(allJournals.size());
        journalIterator.previous();

        while (textForAnalysis.length() < MINIMUM_TEXT_CHARACTER_COUNT) {
            if (journalIterator.hasPrevious()) {
                textForAnalysis += " " + journalIterator.previous().getValue();
            } else {
                enoughTextForAnalysis = false;
            }
        }

        return textForAnalysis;
    }
}
