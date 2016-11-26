package edu.maebe.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;
import java.util.Date;

@ToString(includeFieldNames=true)
@Data
public class UserSettings {
    UUID id;
    String user;

    //user settings
    Boolean readFeedbackImmediately;
    String emailAddress;
    String phoneNumber;
    int numberOfChildren;
    String cellProvider;
    Date lastUpdate;

    public static final String CELL_PROVIDER_VERIZON = "vtext.com";
    public static final String CELL_PROVIDER_VIRGIN = "vmobl.com ";
    public static final String CELL_PROVIDER_SPRINT = "messaging.sprintpcs.com";
    public static final String CELL_PROVIDER_CINGULAR = "cingularme.com";
    public static final String CELL_PROVIDER_NEXTEL = "messaging.nextel.com";
    public static final String CELL_PROVIDER_TMOBILE = "tmomail.net";
}
