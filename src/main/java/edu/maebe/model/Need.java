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
import java.util.concurrent.ThreadLocalRandom;

@ToString(includeFieldNames=true, exclude = {"id", "date", "userId"})
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
    private static String[] needs = { "need_challenge", "need_closeness", "need_curiosity", "need_excitement",
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

    private static String getAdviceForNeed(String needName) {
        String[] advice = Advice.adviceMap.get(needName);
        int random = (int)(Math.random() * advice.length);
        String needDescription = needMap.get(needName);
        return needDescription + advice[random];
    }

    private static Map<String, String> needMap = ImmutableMap.<String, String>builder()
            .put("need_challenge", "Could you use a new challenge? ")
            .put("need_closeness", "Would you like to feel more connected to your family? Or maybe you would like to get your home in order. ")
            .put("need_curiosity", "Do you feel like you have a desire to discover new things? ")
            .put("need_excitement", "Have you been wanting to get out there and live life and just have some fun? ")
            .put("need_harmony", "Do you have a desire to appreciate other people, their viewpoints, and their feelings? ")
            .put("need_ideal", "Have you been feeling a desire for perfection and a sense of community? ")
            .put("need_liberty", "It's always good to take some time to just get away. ")
            .put("need_love", "Everyone could use a little extra love in their life. ")
            .put("need_practicality", "Do you feel like just getting something done? ")
            .put("need_self_expression", "Would you enjoy discovering and asserting your personal identity right now? ")
            .put("need_stability", "Do you feel like you could benefit from some stability in your life? ")
            .put("need_structure", "Would some structure in your life help you feel better? ")
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

    public static String getRandomAdvice() {
        int min = 0;
        int max = needs.length;

        int randomNum = ThreadLocalRandom.current().nextInt(min, max);
        String randomNeed = needs[randomNum];
        return getAdviceForNeed(randomNeed);
    }
}
