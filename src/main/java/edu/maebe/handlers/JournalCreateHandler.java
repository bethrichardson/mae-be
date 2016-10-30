package edu.maebe.handlers;

import edu.maebe.AbstractRequestHandler;
import edu.maebe.Answer;
import edu.maebe.model.Model;

import java.util.Map;
import java.util.UUID;

public class JournalCreateHandler extends AbstractRequestHandler<NewJournalPayload> {

    private Model model;

    public JournalCreateHandler(Model model) {
        super(NewJournalPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(NewJournalPayload value, Map<String, String> urlParams) {
        UUID id = model.createJournal(value.getType(), value.getValue());
        return new Answer(200, id.toString());
    }
}