package edu.maebe.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@ToString(includeFieldNames=true)
@Data
public class MoodRating {
    UUID id;
    Date date;
    CurrentMood currentMood;
    EmotionalRange emotionalRange;

    private class CurrentMood {
        int agreeableness;
        int conscientiousness;
        int extraversion;
        int openness;
    }

    private class EmotionalRange {
        int anger;
        int anxiety;
        int depression;
        int immoderation;
        int selfConsciousness;
        int vulnerability;
    }
}
