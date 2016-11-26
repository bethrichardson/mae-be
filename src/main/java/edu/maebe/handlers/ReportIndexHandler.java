package edu.maebe.handlers;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Model;
import spark.QueryParamsMap;

import java.util.Map;

public class ReportIndexHandler extends AbstractRequestHandler<EmptyPayload> {

    public ReportIndexHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map urlParams, QueryParamsMap queryParams) {
        String userId = queryParams.value("userId");

        String json;

        json = dataToJson(model.getAllMoodRatings(userId));

        return Answer.ok(json);
    }
}