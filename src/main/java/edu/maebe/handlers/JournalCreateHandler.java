package edu.maebe.handlers;

import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Journal;
import edu.maebe.model.Model;
import edu.maebe.model.MoodRating;
import edu.maebe.watson.PersonalityScore;
import edu.maebe.watson.TextAnalyzer;
import edu.maebe.watson.WatsonCredentials;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

public class JournalCreateHandler extends AbstractRequestHandler<NewJournalPayload> {

    private Model model;

    public JournalCreateHandler(Model model) {
        super(NewJournalPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(NewJournalPayload value, Map<String, String> urlParams) {
        UUID id = model.createJournal(value.getType(), value.getValue());
        String textForAnalysis = value.getValue();

        TextAnalyzer textAnalyzer = new TextAnalyzer(model);
        PersonalityScore personalityScore = textAnalyzer.getPersonalityScore(textForAnalysis);

        return new Answer(200, personalityScore.toString());

    }


}