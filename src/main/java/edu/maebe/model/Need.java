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
    private double need_challenge;
    private double need_closeness;
    private double need_curiosity;
    private double need_excitement;
    private double need_harmony;
    private double need_ideal;
    private double need_liberty;
    private double need_love;
    private double need_practicality;
    private double need_self_expression;
    private double need_stability;
    private double need_structure;

    public Need(String userId) {
        this.userId = userId;
    }

    public void setField(String fieldName, double value) throws IllegalAccessException, NoSuchFieldException {
        Field field = Need.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this, value);
    }
}
