package edu.maebe.handlers.Journal;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.handlers.EmptyPayload;
import edu.maebe.model.Model;
import spark.QueryParamsMap;

import java.util.Map;

public class JournalListHandler extends AbstractRequestHandler<EmptyPayload> {

    public JournalListHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map urlParams, QueryParamsMap queryParams) {
        String userId = queryParams.value("userId");
        String type = queryParams.value("type");
        String json;

        if (type != null) {
            json = dataToJson(model.getAllJournals(userId, type));
        } else {
            json = dataToJson(model.getAllJournals(userId));
        }

        return Answer.ok(json);
    }
}