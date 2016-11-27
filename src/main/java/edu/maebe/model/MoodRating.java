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

    public double big5_agreeableness;
    public double big5_conscientiousness;
    public double big5_extraversion;
    public double big5_openness;

    @Getter
    public double facet_anger;
    @Getter
    public double facet_anxiety;
    @Getter
    public double facet_depression;
    @Getter
    public double facet_immoderation;
    @Getter
    public double facet_self_consciousness;
    @Getter
    public double facet_vulnerability;

    @Data
    @Getter
    @Setter
    public class EmotionalRangeElevated {
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

    private EmotionalRangeElevated getValuesOutsideBaseline(User user) {
        EmotionalRangeElevated emotionalRangeElevated = new EmotionalRangeElevated();
        System.out.println("_________________VALUES_________________");
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
        String biggestEmotionOutsideNorm = getEmotionalText("");

        if (this.facet_anger >= biggestEmotionValue && emotionalRangeElevated.facet_anger){
            biggestEmotionValue = this.facet_anger;
            biggestEmotionOutsideNorm = getEmotionalText("facet_anger");
        }

        if (this.facet_anxiety >= biggestEmotionValue && emotionalRangeElevated.facet_anxiety){
            biggestEmotionValue = this.facet_anxiety;
            biggestEmotionOutsideNorm = getEmotionalText("facet_anxiety");
        }

        if (this.facet_depression >= biggestEmotionValue && emotionalRangeElevated.facet_depression){
            biggestEmotionValue = this.facet_depression;
            biggestEmotionOutsideNorm = getEmotionalText("facet_depression");
        }

        if (this.facet_immoderation >= biggestEmotionValue && emotionalRangeElevated.facet_immoderation){
            biggestEmotionValue = this.facet_immoderation;
            biggestEmotionOutsideNorm = getEmotionalText("facet_immoderation");
        }

        if (this.facet_self_consciousness >= biggestEmotionValue && emotionalRangeElevated.facet_self_consciousness){
            biggestEmotionValue = this.facet_self_consciousness;
            biggestEmotionOutsideNorm = getEmotionalText("facet_self_consciousness");
        }

        if (this.facet_vulnerability >= biggestEmotionValue && emotionalRangeElevated.facet_vulnerability){
            biggestEmotionOutsideNorm = getEmotionalText("facet_vulnerability");
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
                childFieldName = facet.getTraitId(); // TODO: Remove reflection and just list out all fields
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
