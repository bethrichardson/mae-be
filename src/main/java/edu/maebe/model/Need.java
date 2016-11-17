package edu.maebe.model;

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
        return advice[random];
    }

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
