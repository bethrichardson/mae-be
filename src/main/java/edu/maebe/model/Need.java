package edu.maebe.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@ToString(includeFieldNames=true)
@Data
public class Need {
    UUID id;
    Date date;
    int challenge;
    int closeness;
    int curiosity;
    int excitement;
    int harmony;
    int ideal;
    int liberty;
    int love;
    int practicality;
    int selfExpression;
    int stability;
    int structure;
}
