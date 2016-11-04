package edu.maebe.handlers;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Journal;
import edu.maebe.model.Model;
import edu.maebe.watson.MetricAnalyzer;
import edu.maebe.watson.PersonalityScore;
import edu.maebe.watson.TextAnalyzer;
import java.util.Map;

public class JournalCreateHandler extends AbstractRequestHandler<NewJournalPayload> {

    private Model model;

    public JournalCreateHandler(Model model) {
        super(NewJournalPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(NewJournalPayload value, Map<String, String> urlParams) {

        if (value.getType().equals(Journal.JOURNAL_TYPE_TEXT)) {
            String textForAnalysis = value.getValue();
            model.createJournal(value.getType(), value.getValue());
            TextAnalyzer textAnalyzer = new TextAnalyzer(model);
            PersonalityScore personalityScore = textAnalyzer.getPersonalityScore(textForAnalysis);

            String baseText  = "You seem like you have been feeling ";
            String endText = " lately.";
            String textResponse =  baseText + personalityScore.getMoodRating().getBiggestEmotion() + endText;

            return new Answer(200, textResponse);
        } else {

            MetricAnalyzer metricAnalyzer = new MetricAnalyzer(value.getType(), value.getValue());
            String textResponse = metricAnalyzer.getResponseFromMetric();
            String metricValue;

            if (value.getType().equals(Journal.JOURNAL_TYPE_DIAPER)) {
                metricValue = metricAnalyzer.getValue();
            } else {
                metricValue = Integer.toString(metricAnalyzer.getValueInDigits());
            }

            model.createJournal(value.getType(), metricValue);
            System.out.println("Result : "+ metricValue);

            return new Answer(200, textResponse);
        }

    }
}