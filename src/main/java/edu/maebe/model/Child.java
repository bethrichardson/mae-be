package edu.maebe.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Months;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ToString(includeFieldNames=true)
@Data
public class Child {
    @Getter
    private int age;
    private Date birthDate;

    public Child(Date birthDate){
        Date today = new Date();
        this.birthDate = birthDate;
        this.age = calculateAgeInMonths(today);
    }

    public int calculateAgeInMonths(Date todayDate) {
        DateTime today = new DateTime(todayDate.getTime());
        DateTime birthDatetime = new DateTime(birthDate.getTime());

        Months d = Months.monthsBetween(today, birthDatetime);
        return Math.abs(d.getMonths());
    }
}
