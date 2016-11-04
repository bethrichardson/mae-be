package edu.maebe.watson;

import edu.maebe.model.Journal;
import lombok.Getter;

public class MetricAnalyzer {
    @Getter
    String value;
    @Getter
    String type;

    public MetricAnalyzer(String type, String value) {
        this.value = getRidOfUnits(value);
        this.type = type;
    }

    public int getValueInDigits(){
        return Integer.parseInt(this.value);
    }

    private String getRidOfUnits(String value) {
        String valueWithNoUnits = value;
        valueWithNoUnits = valueWithNoUnits.replace(" hours","");
        valueWithNoUnits = valueWithNoUnits.replace(" pounds","");
        valueWithNoUnits = valueWithNoUnits.replace(" inches","");
        valueWithNoUnits = valueWithNoUnits.replace(" lbs","");
        valueWithNoUnits = valueWithNoUnits.replace("\"","");
        return valueWithNoUnits;
    }

    public String getResponseFromMetric(){
        String textResponse = "";
        switch (type){
            case Journal.JOURNAL_TYPE_HEIGHT:
                textResponse = "Your baby is getting so big! I recorded a new height of " + value + " inches.";
                break;
            case Journal.JOURNAL_TYPE_WEIGHT:
                textResponse = "Your baby is getting so big! I recorded a new weight of " + value + " pounds.";
                break;
            case Journal.JOURNAL_TYPE_SLEEP:
                textResponse = "Rock a bye baby. I recorded a new sleep time of " + value + " hours.";
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
