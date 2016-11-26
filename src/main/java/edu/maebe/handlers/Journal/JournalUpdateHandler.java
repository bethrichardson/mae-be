package edu.maebe.handlers.Journal;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Model;
import spark.QueryParamsMap;

import java.util.Map;
import java.util.UUID;

public class JournalUpdateHandler extends AbstractRequestHandler<EditJournalPayload> {

    private Model model;

    public JournalUpdateHandler(Model model) {
        super(EditJournalPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(EditJournalPayload value, Map<String, String> urlParams, QueryParamsMap queryParams) {
        UUID journalId = UUID.fromString(urlParams.get(":uuid"));
        String json;

        if (model.existJournal(journalId)) {
            model.editJournal(value.getType(), value.getValue(), journalId, value.getSource());

            json = dataToJson(model.getJournal(journalId).toString());

        } else {
            return new Answer(400);
        }

        return new Answer(200, json);
    }
}