package edu.maebe.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@ToString(includeFieldNames=true)
@Data
public class StatsEntry {
    UUID id;
    Date date;
    Double heightInCm;
    Double weightInKg;
}
