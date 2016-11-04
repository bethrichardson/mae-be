package edu.maebe.model;

import com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;


@ToString(includeFieldNames=true)
@Data
@Getter
@Setter
public class MoodRating {
    private UUID id;
    private Date date;
    private Personality personality;
    private EmotionalRange emotionalRange;

    public MoodRating() {
        this.personality = new Personality();
        this.emotionalRange = new EmotionalRange();
    }

    @Data
    @Getter
    @Setter
    public class Personality {
        private double big5_agreeableness;
        private double big5_conscientiousness;
        private double big5_extraversion;
        private double big5_openness;
    }

    @Data
    @Getter
    @Setter
    public class EmotionalRange {
        @Getter
        private double facet_anger;
        @Getter
        private double facet_anxiety;
        @Getter
        private double facet_depression;
        @Getter
        private double facet_immoderation;
        @Getter
        private double facet_self_consciousness;
        @Getter
        private double facet_vulnerability;
    }

    public String getEmotionalText(String emotionalState) {
        String returnText;
        switch (emotionalState) {
            case("facet_anger"):
                returnText = "angry";
                break;
            case("facet_anxiety"):
                returnText = "anxious";
                break;
            case("facet_depression"):
                returnText = "sad";
                break;
            case("facet_immoderation"):
                returnText = "over indulgent";
                break;
            case("facet_self_consciousness"):
                returnText = "self conscious";
                break;
            case("facet_vulnerability"):
                returnText = "vulnerable";
                break;
            default:
                returnText = "happy";
                break;
        }

        return returnText;
    }

    public String getBiggestEmotion() {
        double biggestEmotionValue = 0.0;
        double baselineEmotionalValue = 0.001;
        String biggestEmotion = getEmotionalText("");
        Field[] fields = MoodRating.EmotionalRange.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getGenericType() != MoodRating.class) {
                double currentFieldValue = 0;
                field.setAccessible(true);
                try {
                    currentFieldValue = (double) field.get(this.getEmotionalRange());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (currentFieldValue > biggestEmotionValue && currentFieldValue > baselineEmotionalValue) {
                    biggestEmotionValue = currentFieldValue;
                    biggestEmotion = getEmotionalText(field.getName());
                } else if (currentFieldValue == biggestEmotionValue && currentFieldValue > baselineEmotionalValue) {
                    biggestEmotion = getEmotionalText(field.getName());
                }
            }
        }

        return biggestEmotion;
    }

    public void setField(Trait trait, double value) throws IllegalAccessException, NoSuchFieldException {
        Field field;
        String fieldName = trait.getTraitId();

        if (fieldName.equals("big5_neuroticism")) {
            String childFieldName;
            for (Trait facet : trait.getChildren()) {
                childFieldName = facet.getTraitId();
                field = MoodRating.EmotionalRange.class.getDeclaredField(childFieldName);
                field.setAccessible(true);
                field.set(this.emotionalRange, value);

            }

        } else {
            field = MoodRating.Personality.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this.personality, value);
        }
    }
}
