package edu.maebe.model;

import com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;


@ToString(includeFieldNames=true, exclude = {"id", "date", "userId"})
@Data
@Getter
@Setter
public class MoodRating {
    private UUID id;
    private Date date;
    private String userId;

    public MoodRating(String userId) {
        this.userId = userId;
    }

    private double big5_agreeableness;
    private double big5_conscientiousness;
    private double big5_extraversion;
    private double big5_openness;

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

    @Data
    @Getter
    @Setter
    private class EmotionalRangeElevated {
        @Getter
        private boolean facet_anger;
        @Getter
        private boolean facet_anxiety;
        @Getter
        private boolean facet_depression;
        @Getter
        private boolean facet_immoderation;
        @Getter
        private boolean facet_self_consciousness;
        @Getter
        private boolean facet_vulnerability;
    }

    private enum EmotionalText {
        facet_anger ("angry"),
        facet_anxiety ("anxious"),
        facet_depression ("sad"),
        facet_immoderation ("over indulgent"),
        facet_self_consciousness ("self conscious"),
        facet_vulnerability ("vulnerable"),
        happy ("happy");

        private final String value;

        EmotionalText(String value) {
            this.value = value;
        }
        private String value() { return value; }
    }

    private EmotionalRangeElevated getValuesOutsideBaseline(User user) {
        EmotionalRangeElevated emotionalRangeElevated = new EmotionalRangeElevated();
        emotionalRangeElevated.facet_anger = user.getFacet_anger().outsideNormalValue(this.getFacet_anger());
        emotionalRangeElevated.facet_anxiety = user.getFacet_anxiety().outsideNormalValue(this.getFacet_anxiety()) ;
        emotionalRangeElevated.facet_depression = user.getFacet_depression().outsideNormalValue(this.getFacet_depression());
        emotionalRangeElevated.facet_immoderation = user.getFacet_immoderation().outsideNormalValue(this.getFacet_immoderation());
        emotionalRangeElevated.facet_self_consciousness = user.getFacet_self_consciousness().outsideNormalValue(this.getFacet_self_consciousness());
        emotionalRangeElevated.facet_vulnerability = user.getFacet_vulnerability().outsideNormalValue(this.getFacet_vulnerability());

        return emotionalRangeElevated;
    }

    public String getBiggestEmotionOutsideNormal(User user) {
        EmotionalRangeElevated emotionalRangeElevated = this.getValuesOutsideBaseline(user);
        double biggestEmotionValue = 0.0;
        String biggestEmotionOutsideNorm = EmotionalText.happy.value();

        if (this.facet_anger >= biggestEmotionValue && emotionalRangeElevated.facet_anger){
            biggestEmotionValue = this.facet_anger;
            biggestEmotionOutsideNorm = EmotionalText.facet_anger.value();
        }

        if (this.facet_anxiety >= biggestEmotionValue && emotionalRangeElevated.facet_anxiety){
            biggestEmotionValue = this.facet_anxiety;
            biggestEmotionOutsideNorm = EmotionalText.facet_anxiety.value();
        }

        if (this.facet_depression >= biggestEmotionValue && emotionalRangeElevated.facet_depression){
            biggestEmotionValue = this.facet_depression;
            biggestEmotionOutsideNorm = EmotionalText.facet_depression.value();
        }

        if (this.facet_immoderation >= biggestEmotionValue && emotionalRangeElevated.facet_immoderation){
            biggestEmotionValue = this.facet_immoderation;
            biggestEmotionOutsideNorm = EmotionalText.facet_immoderation.value();
        }

        if (this.facet_self_consciousness >= biggestEmotionValue && emotionalRangeElevated.facet_self_consciousness){
            biggestEmotionValue = this.facet_self_consciousness;
            biggestEmotionOutsideNorm = EmotionalText.facet_self_consciousness.value();
        }

        if (this.facet_vulnerability >= biggestEmotionValue && emotionalRangeElevated.facet_vulnerability){
            biggestEmotionOutsideNorm = EmotionalText.facet_vulnerability.value();
        }

        return biggestEmotionOutsideNorm;
    }

    public boolean getRequiresMedicalAdvice(User user) {
        return user.getFacet_anger().stronglyOutsideNormalValue(this.getFacet_anger()) ||
                user.getFacet_anxiety().stronglyOutsideNormalValue(this.getFacet_anxiety()) ||
                user.getFacet_depression().stronglyOutsideNormalValue(this.getFacet_depression()) ||
                user.getFacet_immoderation().stronglyOutsideNormalValue(this.getFacet_immoderation()) ||
                user.getFacet_self_consciousness().stronglyOutsideNormalValue(this.getFacet_self_consciousness()) ||
                user.getFacet_vulnerability().stronglyOutsideNormalValue(this.getFacet_vulnerability());
    }

    public void setField(Trait trait, double value) throws IllegalAccessException, NoSuchFieldException {
        Field field;
        String fieldName = trait.getTraitId();

        if (fieldName.equals("big5_neuroticism")) {
            String childFieldName;
            for (Trait facet : trait.getChildren()) {
                childFieldName = facet.getTraitId();
                field = MoodRating.class.getDeclaredField(childFieldName);
                field.setAccessible(true);
                field.set(this, facet.getRawScore());
            }

        } else {
            field = MoodRating.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this, value);
        }
    }
}
