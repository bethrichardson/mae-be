package edu.maebe.handlers.UserSettings;
import edu.maebe.Validable;
import lombok.Data;

import java.util.Date;

@Data
class NewUserSettingsPayload implements Validable {
    private String userId;
    private Boolean immediateFeedback;
    private String email;
    private String phone;
    private String first;
    private String last;
    private int numberOfChildren;
    private Date childBirthDate; // currently support one baby for simplicity
    private String gender; // child's gender
    private String provider;

    public boolean isValid() {
        return userId != null && !userId.isEmpty();
    }
}