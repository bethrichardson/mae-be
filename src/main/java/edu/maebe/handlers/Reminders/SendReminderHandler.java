package edu.maebe.handlers.Reminders;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.handlers.EmptyPayload;
import edu.maebe.model.Model;
import edu.maebe.model.UserSettings;
import edu.maebe.smtp.SmtpMailSender;
import spark.QueryParamsMap;

import java.util.Date;
import java.util.Map;

public class SendReminderHandler extends AbstractRequestHandler<EmptyPayload> {

    private Model model;

    public SendReminderHandler(Model model) {
        super(EmptyPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String, String> urlParams, QueryParamsMap queryParams) {
        String userId = urlParams.get(":uuid");

        if (model.existsUserSettings(userId)) {
            UserSettings user = model.getUserSettings(userId);
            Date lastUpdateDate = model.getLastJournalForUser(userId).getDate();
            SmtpMailSender.sendText(user.getProvider(), user.getPhone(), user.getFirst(), lastUpdateDate);

            return new Answer(200);
        }
        else {
            return new Answer(400);
        }
    }
}