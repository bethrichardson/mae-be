package edu.maebe.handlers.Advice;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.handlers.EmptyPayload;
import edu.maebe.model.Model;
import edu.maebe.model.Need;
import spark.QueryParamsMap;

import java.util.Map;

public class AdviceIndexHandler extends AbstractRequestHandler<EmptyPayload> {

    public AdviceIndexHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map urlParams, QueryParamsMap queryParams) {

        String json;

        json = dataToJson(Need.getRandomAdvice());

        return Answer.ok(json);
    }
}