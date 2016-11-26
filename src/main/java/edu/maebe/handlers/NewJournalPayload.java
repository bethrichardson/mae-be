package edu.maebe.handlers;
import edu.maebe.Validable;
import lombok.Data;

@Data
class NewJournalPayload implements Validable {
    private String type;
    private String value;
    private String userId;
    private String source;
    private boolean advice;

    public boolean isValid() {
        return type != null && !type.isEmpty() && value != null && !value.isEmpty()
                && userId != null && !userId.isEmpty();
    }
}