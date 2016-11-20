package edu.maebe.handlers;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Journal;
import edu.maebe.model.Model;
import edu.maebe.model.MoodRating;
import edu.maebe.model.User;
import edu.maebe.watson.MetricAnalyzer;
import edu.maebe.watson.PersonalityScore;
import edu.maebe.watson.TextAnalyzer;
import spark.QueryParamsMap;

import java.util.List;
import java.util.Map;

public class JournalCreateHandler extends AbstractRequestHandler<NewJournalPayload> {

    private Model model;

    public JournalCreateHandler(Model model) {
        super(NewJournalPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(NewJournalPayload value, Map<String, String> urlParams, QueryParamsMap queryParams) {
        String userId = value.getUserId();
        String textForAnalysis = value.getValue();
        boolean userIdIdentified = userId.equals(System.getenv("ALEXA_USERID"));

        if (value.getType().equals(Journal.JOURNAL_TYPE_TEXT)) {

            String textResponse = getTextResponseForJournal(value, userId, textForAnalysis, userIdIdentified);

            return new Answer(200, textResponse);
        } else {

            MetricAnalyzer metricAnalyzer = new MetricAnalyzer(value.getType(), textForAnalysis);
            String textResponse = metricAnalyzer.getResponseFromMetric();
            String metricValue;

            if (value.getType().equals(Journal.JOURNAL_TYPE_DIAPER)) {
                metricValue = metricAnalyzer.getValue();
            } else {
                metricValue = Double.toString(metricAnalyzer.getValueInDigits());
            }

            model.createJournal(value.getType(), metricValue, userId);
            System.out.println("Result : "+ metricValue);

            return new Answer(200, textResponse);
        }

    }

    private String getTextResponseForJournal(NewJournalPayload value, String userId, String textForAnalysis, boolean userIdIdentified) {
        model.createJournal(value.getType(), textForAnalysis, userId);
        String baseText  = "You seem like you have been feeling ";
        String emotionText = "happy";
        String endText = " lately.";
        String advice = "Take some time to enjoy the rest of your day!";

        if (userIdIdentified) {
            TextAnalyzer textAnalyzer = new TextAnalyzer(model, userId);
            PersonalityScore personalityScore = textAnalyzer.getPersonalityScore(textForAnalysis);

            List<MoodRating> moodRatingsForUser = model.getAllMoodRatings(userId);
            User user = new User(moodRatingsForUser, userId);

            emotionText = personalityScore.getMoodRating().getBiggestEmotionOutsideNormal(user);
            advice = personalityScore.getNeed().getAdviceForBiggestNeed();
        }


        return baseText + emotionText + endText + " " + advice;
    }
}