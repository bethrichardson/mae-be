package edu.maebe.handlers;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Model;
import spark.QueryParamsMap;

import java.util.Map;

public class FriendIndexHandler extends AbstractRequestHandler<EmptyPayload> {

    public FriendIndexHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map urlParams, QueryParamsMap queryParams) {
        String userId = queryParams.value("userId");
        String type = queryParams.value("type");
        String json;

        if (type != null) {
            json = dataToJson(model.getAllFriends(type, userId));
        } else {
            json = dataToJson(model.getAllFriends(userId));
        }

        return Answer.ok(json);
    }
}