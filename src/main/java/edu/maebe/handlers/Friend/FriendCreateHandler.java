package edu.maebe.handlers.Friend;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Friend;
import edu.maebe.model.Model;
import spark.QueryParamsMap;

import java.util.Map;
import java.util.UUID;

public class FriendCreateHandler extends AbstractRequestHandler<NewFriendPayload> {

    private Model model;

    public FriendCreateHandler(Model model) {
        super(NewFriendPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(NewFriendPayload value, Map<String, String> urlParams, QueryParamsMap queryParams) {
        String userId = value.getUserId();

        Friend friend = new Friend(value.getName(), value.getNickname(), value.getPhone(),
                                   value.getEmail(), value.getUserId(), value.getType());
        UUID friendID = model.createFriend(friend, userId);
        friend.setId(friendID);

        return new Answer(200, friend.toString());
    }
}