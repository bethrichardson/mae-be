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
    Date timestamp;
    CurrentMood currentMood;

    private class CurrentMood {

        int agreeableness;
        int conscientiousness;
        int extraversion;
        int openness;
        EmotionalRange emotionalRange;
        Needs needs;

        private class EmotionalRange {
            int anger;
            int anxiety;
            int depression;
            int immoderation;
            int selfConsciousness;
            int vulnerability;
        }

        private class Needs {
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
    }
}
