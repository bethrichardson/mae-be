package edu.maebe.handlers.UserSettings;
import edu.maebe.Validable;
import lombok.Data;

@Data
class NewUserSettingsPayload implements Validable {
    private String userId;
    private Boolean immediateFeedback;
    private String email;
    private String phone;
    private String first;
    private String last;
    private int numberOfChildren;
    private String provider;

    public boolean isValid() {
        return userId != null && !userId.isEmpty();
    }
}