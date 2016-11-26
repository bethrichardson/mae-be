package edu.maebe.model;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;
import java.util.Date;

@ToString(includeFieldNames=true)
@Data
public class UserSettings {
    UUID id;
    String userId;

    //user settings
    Boolean immediateFeedback;
    String email;
    String phone;
    int numberOfChildren;
    String provider;
    String first;
    String last;
    Date lastUpdate;

    public UserSettings(String userId, Boolean immediateFeedback, String phone, String email,
                        int numberOfChildren, String provider, String first, String last) {
        this.userId = userId;
        this.immediateFeedback = immediateFeedback;
        this.phone = phone;
        this.email = email;
        this.numberOfChildren = numberOfChildren;
        this.provider = provider;
        this.first = first;
        this.last = last;
        this.lastUpdate = new Date();
    }

    public static final String CELL_PROVIDER_VERIZON = "vtext.com";
    public static final String CELL_PROVIDER_VIRGIN = "vmobl.com ";
    public static final String CELL_PROVIDER_SPRINT = "messaging.sprintpcs.com";
    public static final String CELL_PROVIDER_CINGULAR = "cingularme.com";
    public static final String CELL_PROVIDER_NEXTEL = "messaging.nextel.com";
    public static final String CELL_PROVIDER_TMOBILE = "tmomail.net";
}
