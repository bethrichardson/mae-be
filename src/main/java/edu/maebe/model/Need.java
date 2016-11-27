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

@ToString(includeFieldNames=true, exclude = {"id", "date", "needMap", "userId"})
@Data
@Getter
@Setter
public class Need {
    UUID id;
    Date date;
    @Getter
    String userId;
    @Getter
    private double need_challenge;
    @Getter
    private double need_closeness;
    @Getter
    private double need_curiosity;
    @Getter
    private double need_excitement;
    @Getter
    private double need_harmony;
    @Getter
    private double need_ideal;
    @Getter
    private double need_liberty;
    @Getter
    private double need_love;
    @Getter
    private double need_practicality;
    @Getter
    private double need_self_expression;
    @Getter
    private double need_stability;
    @Getter
    private double need_structure;

    public Need(String userId) {
        this.userId = userId;
    }

    public void setField(String fieldName, double value) throws IllegalAccessException, NoSuchFieldException {
        Field field = Need.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this, value);
    }

    private String getAdviceForNeed(String needName) {
        String[] advice = Advice.adviceMap.get(needName);
        int random = (int)(Math.random() * advice.length);
        String needDescription = needMap.get(needName);
        return needDescription + advice[random];
    }

    private Map<String, String> needMap = ImmutableMap.<String, String>builder()
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

        if (this.getNeed_challenge() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_challenge();
            biggestNeed = getAdviceForNeed("need_challenge");
        }

        if (this.getNeed_closeness() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_closeness();
            biggestNeed = getAdviceForNeed("need_closeness");
        }

        if (this.getNeed_curiosity() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_curiosity();
            biggestNeed = getAdviceForNeed("need_curiosity");
        }

        if (this.getNeed_excitement() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_excitement();
            biggestNeed = getAdviceForNeed("need_excitement");
        }

        if (this.getNeed_harmony() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_harmony();
            biggestNeed = getAdviceForNeed("need_harmony");
        }

        if (this.getNeed_ideal() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_ideal();
            biggestNeed = getAdviceForNeed("need_ideal");
        }

        if (this.getNeed_liberty() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_liberty();
            biggestNeed = getAdviceForNeed("need_liberty");
        }

        if (this.getNeed_love() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_love();
            biggestNeed = getAdviceForNeed("need_love");
        }

        if (this.getNeed_practicality() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_practicality();
            biggestNeed = getAdviceForNeed("need_practicality");
        }

        if (this.getNeed_self_expression() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_self_expression();
            biggestNeed = getAdviceForNeed("need_self_expression");
        }

        if (this.getNeed_stability() > biggestNeedValue) {
            biggestNeedValue = this.getNeed_stability();
            biggestNeed = getAdviceForNeed("need_stability");
        }

        if (this.getNeed_structure() > biggestNeedValue) {
            biggestNeed = getAdviceForNeed("need_structure");
        }

        return biggestNeed;
    }
}
