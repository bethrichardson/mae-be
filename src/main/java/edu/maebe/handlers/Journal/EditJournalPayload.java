package edu.maebe.handlers.Journal;
import edu.maebe.Validable;
import lombok.Data;

import java.util.UUID;

@Data
class EditJournalPayload implements Validable {
    private UUID id;
    private String type;
    private String value;
    private String userId;
    private String source;
    private boolean advice;

    public boolean isValid() {
        return id != null && type != null && !type.isEmpty() && value != null && !value.isEmpty()
                && userId != null && !userId.isEmpty();
    }
}