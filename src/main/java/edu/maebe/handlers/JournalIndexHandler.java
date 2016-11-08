package edu.maebe.handlers;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Model;
import spark.QueryParamsMap;

import java.util.Map;
import java.util.Set;

public class JournalIndexHandler extends AbstractRequestHandler<EmptyPayload> {

    public JournalIndexHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map urlParams, QueryParamsMap queryParams) {
        String userId = queryParams.value("userId");

        System.out.println("USER ID: " + userId);
        System.out.println("PARAMS: " + queryParams);
        System.out.println("PARAMS: " + urlParams);
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