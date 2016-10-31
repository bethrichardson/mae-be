package edu.maebe.model;

import com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        private double facet_anger;
        private double facet_anxiety;
        private double facet_depression;
        private double facet_immoderation;
        private double facet_self_consciousness;
        private double facet_vulnerability;
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
