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
    @Setter(AccessLevel.PACKAGE) String value;
    Date date;
}
