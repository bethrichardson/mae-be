package edu.maebe.handlers.Journal;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.handlers.EmptyPayload;
import edu.maebe.model.Model;
import spark.QueryParamsMap;

import java.util.Map;
import java.util.UUID;

public class JournalIndexHandler extends AbstractRequestHandler<EmptyPayload> {

    public JournalIndexHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String, String> urlParams, QueryParamsMap queryParams) {
        UUID journalId = UUID.fromString(urlParams.get(":uuid"));
        String json;

        if (model.existJournal(journalId)) {
            json = dataToJson(model.getJournal(journalId));

        } else {
            return new Answer(400);
        }

        return new Answer(200, json);
    }
}