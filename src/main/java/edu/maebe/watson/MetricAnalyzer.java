package edu.maebe.watson;

import edu.maebe.model.Journal;
import lombok.Getter;

public class MetricAnalyzer {
    @Getter
    String value;
    @Getter
    String type;

    public MetricAnalyzer(String type, String value) {
        this.value = type.equals(Journal.JOURNAL_TYPE_DIAPER) ? value : getRidOfUnits(value);
        this.type = type;
    }

    public double getValueInDigits(){
        return Double.valueOf(this.value);
    }

    private String getRidOfUnits(String value) {
        String valueWithNoUnits = value;
        valueWithNoUnits = valueWithNoUnits.replaceAll("[^\\d.]", "");
        return valueWithNoUnits;
    }

    public String getResponseFromMetric(){
        String textResponse = "";
        switch (type){
            case Journal.JOURNAL_TYPE_HEIGHT:
                textResponse = "I recorded a new height of " + value + " inches.";
                break;
            case Journal.JOURNAL_TYPE_WEIGHT:
                textResponse = "I recorded a new weight of " + value + " pounds.";
                break;
            case Journal.JOURNAL_TYPE_SLEEP:
                textResponse = "I recorded a new sleep time of " + value + " hours.";
                break;
            case Journal.JOURNAL_TYPE_DIAPER:
                textResponse = "I recorded a new diaper containing " + value + ".";
                break;
            default:
                textResponse = "I hope you are having a great day.";
                break;
        }

        return textResponse;
    }
}
