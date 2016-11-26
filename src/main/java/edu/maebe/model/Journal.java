package edu.maebe.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@ToString(includeFieldNames=true)
@Data
public class Journal {
    UUID id;
    String type;
    String user;
    @Setter(AccessLevel.PACKAGE) String value;
    Date date;
    String source;

    public static final String JOURNAL_TYPE_TEXT = "journal";
    public static final String JOURNAL_TYPE_HEIGHT = "height";
    public static final String JOURNAL_TYPE_WEIGHT = "weight";
    public static final String JOURNAL_TYPE_SLEEP = "sleep";
    public static final String JOURNAL_TYPE_DIAPER = "diaper";
    public static final String JOURNAL_TYPE_TEST = "test";
    
    public static final String JOURNAL_SOURCE_WEB = "web";
    public static final String JOURNAL_SOURCE_ALEXA = "alexa";
}
