package edu.maebe.handlers;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Model;

import java.util.Map;

public class JournalIndexHandler extends AbstractRequestHandler<EmptyPayload> {

    public JournalIndexHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map urlParams) {
            String json = dataToJson(model.getAllJournals());
            return Answer.ok(json);
    }

}