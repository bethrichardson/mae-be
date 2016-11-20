package edu.maebe.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString(includeFieldNames=true)
@Data
public class User {
    @Getter
    private Baseline facet_anger;
    @Getter
    private Baseline facet_anxiety;
    @Getter
    private Baseline facet_depression;
    @Getter
    private Baseline facet_immoderation;
    @Getter
    private Baseline facet_self_consciousness;
    @Getter
    private Baseline facet_vulnerability;
    @Getter
    @Setter
    private String userId;
    private Model model;
    private List<MoodRating> moodRatingsForUser;

    public User(List<MoodRating> moodRatingsForUser, String userId){
        this.moodRatingsForUser = moodRatingsForUser;
        this.userId = userId;
        calculateBaselines();
    }

    private void calculateBaselines() {
        ArrayList<Double> angerValues = new ArrayList<>();
        ArrayList<Double> anxietyValues = new ArrayList<>();
        ArrayList<Double> depressionValues = new ArrayList<>();
        ArrayList<Double> immoderationValues = new ArrayList<>();
        ArrayList<Double> selfConsciousnessValues = new ArrayList<>();
        ArrayList<Double> vulnerabilityValues = new ArrayList<>();


        if (moodRatingsForUser.size() >= 14 ) {
            List<MoodRating> moodRatingsForBaseline = getMoodRatingsForBaseline(moodRatingsForUser);

            int index = 0;
            for (MoodRating moodRating : moodRatingsForBaseline) {
                angerValues.add(index, moodRating.getFacet_anger());
                anxietyValues.add(index, moodRating.getFacet_anxiety());
                depressionValues.add(index, moodRating.getFacet_depression());
                immoderationValues.add(index, moodRating.getFacet_immoderation());
                selfConsciousnessValues.add(index, moodRating.getFacet_self_consciousness());
                vulnerabilityValues.add(index, moodRating.getFacet_vulnerability());
                index++;
            }

            this.facet_anger = new Baseline(angerValues);
            this.facet_anxiety = new Baseline(anxietyValues);
            this.facet_depression = new Baseline(depressionValues);
            this.facet_immoderation = new Baseline(immoderationValues);
            this.facet_self_consciousness = new Baseline(selfConsciousnessValues);
            this.facet_vulnerability = new Baseline(vulnerabilityValues);

        }
    }

    private List<MoodRating> getMoodRatingsForBaseline(List<MoodRating> moodRatingsForUser) {
        List<MoodRating> moodRatingsForBaseline = new ArrayList<>();
        int index = 0;

        while(moodRatingsForBaseline.size() < 14){
            moodRatingsForBaseline.add(index, moodRatingsForUser.get(index));
            index++;
        }
        return moodRatingsForBaseline;
    }


    @Getter
    public class Baseline {
        @Getter
        private double value;
        @Getter
        private double standardDeviation;

        private Baseline (ArrayList<Double> values) {
            this.value = mean(values);
            this.standardDeviation = getStandardDeviation(values);
        }

        public boolean outsideNormalValue (double valueToCompare) {
            return valueToCompare > this.getValue() + this.getStandardDeviation();
        }

        private double getStandardDeviation (ArrayList<Double> values)
        {
            // Step 1:
            double mean = mean(values);
            double temp = 0;

            for (int i = 0; i < values.size(); i++)
            {
                double val = values.get(i);

                // Step 2:
                double squrDiffToMean = Math.pow(val - mean, 2);

                // Step 3:
                temp += squrDiffToMean;
            }

            // Step 4:
            double meanOfDiffs = temp / (double) (values.size());

            // Step 5:
            return Math.sqrt(meanOfDiffs);
        }

        private double mean (ArrayList<Double> values){
            double total = 0;

            for ( int i= 0;i < values.size(); i++)
            {
                double currentNum = values.get(i);
                total+= currentNum;
            }
            return total / (double) values.size();
        }
    }
}
