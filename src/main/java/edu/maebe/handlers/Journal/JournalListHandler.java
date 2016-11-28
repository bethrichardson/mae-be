package edu.maebe.handlers.Journal;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.handlers.EmptyPayload;
import edu.maebe.model.Child;
import edu.maebe.model.Journal;
import edu.maebe.model.Model;
import spark.QueryParamsMap;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

//        fillAllNullAges(userId);

        if (type != null) {
            json = dataToJson(model.getAllJournals(userId, type));
        } else {
            json = dataToJson(model.getAllJournals(userId));
        }

        return Answer.ok(json);
    }

    private void fillAllNullAges(String userId) {
        Date childBirthDate = model.getUserSettings(userId).getChildBirthDate();
        Child child = new Child(childBirthDate);

        List<Journal> journals = model.getAllJournals(userId);
        journals.stream().filter(journal -> journal.getAge() == null || journal.getAge() < 0)
                .forEach(j -> model.editJournal(
                        j.getType(), j.getValue(), j.getId(), j.getSource(),
                        child.calculateAgeInMonths(j.getDate())));
    }
}