package edu.maebe.model;

import com.google.common.collect.ImmutableMap;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@ToString(includeFieldNames=true)
@Data
@Getter
@Setter
public class Need {
    UUID id;
    Date date;
    @Getter
    String userId;
    @Getter
    public double need_challenge;
    @Getter
    public double need_closeness;
    @Getter
    public double need_curiosity;
    @Getter
    public double need_excitement;
    @Getter
    public double need_harmony;
    @Getter
    public double need_ideal;
    @Getter
    public double need_liberty;
    @Getter
    public double need_love;
    @Getter
    public double need_practicality;
    @Getter
    public double need_self_expression;
    @Getter
    public double need_stability;
    @Getter
    public double need_structure;

    private String[] needs = { "need_challenge", "need_closeness", "need_curiosity", "need_excitement",
          "need_harmony", "need_ideal", "need_liberty", "need_love", "need_practicality", "need_self_expression",
            "need_stability", "need_structure"

    };

//    private static String need_challenge = "You seem like you could use a new challenge.";


    public Need(String userId) {
        this.userId = userId;
    }

    public void setField(String fieldName, double value) throws IllegalAccessException, NoSuchFieldException {
        Field field = Need.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this, value);
    }

    public String getAdviceForNeed(String needName) {
        String[] advice = Advice.adviceMap.get(needName);
        int random = (int)(Math.random() * advice.length);
        String needDescription = needMap.get(needName);
        return needDescription + advice[random];
    }

    public static Map<String, String> needMap = ImmutableMap.<String, String>builder()
            .put("need_challenge", "You seem like you could use a new challenge. ")
            .put("need_closeness", "You seem like you need to feel more connected to your family and getting your home in order. ")
            .put("need_curiosity", "Do you feel like you have a desire to discover new things? ")
            .put("need_excitement", "Have you been wanting to get out there and live life and just have some fun? ")
            .put("need_harmony", "You seem like you have a need to appreciate other people, their viewpoints, and their feelings. ")
            .put("need_ideal", "Have you been feeling a desire for perfection and a sense of community? ")
            .put("need_liberty", "You seem like you could use an escape. ")
            .put("need_love", "You might need a little extra love right now. ")
            .put("need_practicality", "You seem like you could benefit from getting a job done right now. ")
            .put("need_self_expression", "You seem like you would enjoy discovering and asserting your identity right now. ")
            .put("need_stability", "Right now it seems like you could use some stability. ")
            .put("need_structure", "You could use some structure in your life. ")
            .build();

    public String getAdviceForBiggestNeed() {
        double biggestNeedValue = 0.0;
        String biggestNeed = "";
        String[] fields = this.needs;
        for (String field : fields) {
            double currentFieldValue = 0;
            try {
                Field fieldProperty = Need.class.getField(field);
                fieldProperty.setAccessible(true);
                currentFieldValue = (double) fieldProperty.get(this);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (currentFieldValue > biggestNeedValue) {
                biggestNeedValue = currentFieldValue;
                biggestNeed = getAdviceForNeed(field);
            }
        }

        return biggestNeed;
    }
}
