package edu.maebe.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@ToString(includeFieldNames=true)
@Data
public class DiaperEntry {
    UUID id;
    Boolean hasPee;
    Boolean hasPoop;
    Date date;
}
