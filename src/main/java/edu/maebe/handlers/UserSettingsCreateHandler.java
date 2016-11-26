package edu.maebe.handlers;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Model;
import edu.maebe.model.UserSettings;
import spark.QueryParamsMap;

import java.util.Map;
import java.util.UUID;

public class UserSettingsCreateHandler extends AbstractRequestHandler<NewUserSettingsPayload> {

    private Model model;

    public UserSettingsCreateHandler(Model model) {
        super(NewUserSettingsPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(NewUserSettingsPayload value, Map<String, String> urlParams, QueryParamsMap queryParams) {
        String userId = value.getUserId();

        UserSettings settings = new UserSettings(value.getUserId(), value.getImmediateFeedback(), value.getPhone(),
                                         value.getEmail(), value.getNumberOfChildren(), value.getProvider());
        UUID settingsID = model.createUserSettings(userId, settings.getImmediateFeedback(), settings.getEmail(),
                                                   settings.getPhone(), settings.getNumberOfChildren(),
                                                   settings.getProvider(), settings.getLastUpdate());
        settings.setId(settingsID);

        return new Answer(200, settings.toString());
    }
}