package edu.maebe.handlers.Journal;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Journal;
import edu.maebe.model.Model;
import edu.maebe.model.MoodRating;
import edu.maebe.model.Test;
import edu.maebe.model.User;
import edu.maebe.watson.MetricAnalyzer;
import edu.maebe.watson.PersonalityScore;
import edu.maebe.watson.TextAnalyzer;
import spark.QueryParamsMap;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class JournalCreateHandler extends AbstractRequestHandler<NewJournalPayload> {

    private Model model;
    private String baseText  = "You seem like you have been feeling ";
    private String emotionText = "happy";
    private String endText = " lately.";
    private String advice = "Take some time to enjoy the rest of your day!";
    private static final Logger logger = Logger.getLogger("edu.maebe");

    public JournalCreateHandler(Model model) {
        super(NewJournalPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(NewJournalPayload value, Map<String, String> urlParams, QueryParamsMap queryParams) {
        String userId = value.getUserId();
        String textForAnalysis = value.getValue();
        long startTime = System.nanoTime();

        boolean userIdIdentified = userId.equals(System.getenv("ALEXA_USERID"));

        switch (value.getType()) {
            case Journal.JOURNAL_TYPE_TEST: {
                String textResponse = getTextResponseForTestJournal(value, userId, textForAnalysis);

                return new Answer(200, textResponse);
            }
            case Journal.JOURNAL_TYPE_TEXT: {
                String textResponse = getTextResponseForJournal(value, userId, textForAnalysis, userIdIdentified);

                long endTime = System.nanoTime();

                long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                logger.info("Call complete. Total-duration=" + duration / 1000000 + "ms");

                return new Answer(200, textResponse);
            }
            default: {

                MetricAnalyzer metricAnalyzer = new MetricAnalyzer(value.getType(), textForAnalysis);
                String textResponse = metricAnalyzer.getResponseFromMetric();
                String metricValue;

                if (value.getType().equals(Journal.JOURNAL_TYPE_DIAPER)) {
                    metricValue = metricAnalyzer.getValue();
                } else {
                    metricValue = Double.toString(metricAnalyzer.getValueInDigits());
                }

                model.createJournal(value.getType(), metricValue, userId, value.getSource());

                return new Answer(200, textResponse);
            }
        }

    }

    private String getTextResponseForJournal(NewJournalPayload value, String userId, String textForAnalysis, boolean userIdIdentified) {
        model.createJournal(value.getType(), textForAnalysis, userId, value.getSource());
        boolean requiresMedicalAdvice = false;

        if (userIdIdentified) {
            TextAnalyzer textAnalyzer = new TextAnalyzer(model, userId);
            PersonalityScore personalityScore = textAnalyzer.getPersonalityScore(textForAnalysis);

            List<MoodRating> moodRatingsForUser = model.getAllMoodRatings(userId);
            User user = new User(moodRatingsForUser, userId);

            emotionText = personalityScore.getMoodRating().getBiggestEmotionOutsideNormal(user);
            advice = personalityScore.getNeed().getAdviceForBiggestNeed();

            requiresMedicalAdvice = personalityScore.getMoodRating().getRequiresMedicalAdvice(user);
        }

        return buildResponse(value, baseText, emotionText, endText, advice, requiresMedicalAdvice);
    }


    private String getTextResponseForTestJournal(NewJournalPayload value, String userId, String typeOfTest) {
        boolean requiresMedicalAdvice;

        Test test = new Test(typeOfTest);

        TextAnalyzer textAnalyzer = new TextAnalyzer(userId);
        PersonalityScore personalityScore = textAnalyzer.buildTestPersonalityScore(test.getProfile());

        List<MoodRating> moodRatingsForUser = model.getAllMoodRatings(userId);
        User user = new User(moodRatingsForUser, userId);

        emotionText = personalityScore.getMoodRating().getBiggestEmotionOutsideNormal(user);
        advice = personalityScore.getNeed().getAdviceForBiggestNeed();

        requiresMedicalAdvice = personalityScore.getMoodRating().getRequiresMedicalAdvice(user);

        return buildResponse(value, baseText, emotionText, endText, advice, requiresMedicalAdvice);
    }

    private String buildResponse(NewJournalPayload value, String baseText, String emotionText, String endText, String advice, boolean requiresMedicalAdvice) {
        if (requiresMedicalAdvice) {
            return "Recently, I have noticed some differences in you and wondered how you are doing. " +
                    "You might want to consider reaching out to a trusted medical practitioner for some advice. " +
                    "You can access a healthcare report on the Mae website to share " +
                    "your recent emotional states with your caregiver.";
        } else if (value.isAdvice()) {
            return baseText + emotionText + endText + " " + advice;
        } else {
            return "I have recorded a new journal. Thank you.";
        }
    }
}