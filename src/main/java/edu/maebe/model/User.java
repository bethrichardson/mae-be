package edu.maebe.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

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
    private static final Logger logger = Logger.getLogger("edu.maebe");

    public User(List<MoodRating> moodRatingsForUser, String userId){
        this.moodRatingsForUser = moodRatingsForUser;
        this.userId = userId;
        calculateBaselines();
    }

    private void calculateBaselines() {
        if (moodRatingsForUser.size() >= 14 ) {
            MoodRating[] moodRatingsForBaseline = getMoodRatingsForBaseline(moodRatingsForUser);
            int size = moodRatingsForBaseline.length;
            double[] angerValues = new double[size];
            double[] anxietyValues = new double[size];
            double[] depressionValues = new double[size];
            double[] immoderationValues = new double[size];
            double[] selfConsciousnessValues = new double[size];
            double[] vulnerabilityValues = new double[size];

            int index = 0;
            for (MoodRating moodRating : moodRatingsForBaseline) {
                angerValues[index] = moodRating.getFacet_anger();
                anxietyValues[index] = moodRating.getFacet_anxiety();
                depressionValues[index] = moodRating.getFacet_depression();
                immoderationValues[index] = moodRating.getFacet_immoderation();
                selfConsciousnessValues[index] = moodRating.getFacet_self_consciousness();
                vulnerabilityValues[index] = moodRating.getFacet_vulnerability();
                index++;
            }

            this.facet_anger = new Baseline(angerValues, "anger");
            this.facet_anxiety = new Baseline(anxietyValues, "anxiety");
            this.facet_depression = new Baseline(depressionValues, "depression");
            this.facet_immoderation = new Baseline(immoderationValues, "immoderation");
            this.facet_self_consciousness = new Baseline(selfConsciousnessValues, "self-consciousness");
            this.facet_vulnerability = new Baseline(vulnerabilityValues, "vulnerability");

        }
    }

    private MoodRating[] getMoodRatingsForBaseline(List<MoodRating> moodRatingsForUser) {
        MoodRating[] moodRatingsForBaseline = new MoodRating[14];

        for (int i = 0; i < 14; i++) {
            moodRatingsForBaseline[i] = moodRatingsForUser.get(i);
        }

        return moodRatingsForBaseline;
    }

    @Getter
    class Baseline {
        @Getter
        private String valueType;
        @Getter
        private double value;
        @Getter
        private double standardDeviation;

        private double standardDeviationBoundary() {
            return this.getValue() + this.getStandardDeviation();
        }

        private double doubleStandardDeviationBoundary() {
            return this.getValue() + ( 2 * this.getStandardDeviation() );
        }

        void logString(double valueToCompare, boolean nonNormal, boolean stronglyNonNormal) {
            String valueAnalysisLog = String.format("Analyzing value for %s=%s; stdv=%s; " +
                                  "boundary=%s; extreme=%s. " +
                                  "|| current=%s; non-normal=%s; strongly-non-normal=%s", valueType, value, standardDeviation,
                          Double.toString(standardDeviationBoundary()), Double.toString(doubleStandardDeviationBoundary()),
                          valueToCompare,
                          Boolean.toString(nonNormal), Boolean.toString(stronglyNonNormal)
            );

            logger.info(valueAnalysisLog);
        }

        private Baseline (double[] values, String name) {
            this.value = mean(values);
            this.standardDeviation = getStandardDeviation(values);
            this.valueType = name;
        }

        boolean outsideNormalValue (double valueToCompare) {
            boolean outsideNormal = valueToCompare > this.standardDeviationBoundary();
            boolean stronglyOutsideNormal = stronglyOutsideNormalValue(valueToCompare);
            this.logString(valueToCompare, outsideNormal, stronglyOutsideNormal);

            return outsideNormal;
        }

        boolean stronglyOutsideNormalValue (double valueToCompare) {
            return valueToCompare > this.doubleStandardDeviationBoundary();
        }

        private double getStandardDeviation (double[] values) {
            double mean = mean(values);
            double temp = Arrays.stream(values).map(v -> Math.pow(v - mean, 2)).sum();

            double meanOfDiffs = temp / (double) (values.length);
            return Math.sqrt(meanOfDiffs);
        }

        private double mean (double[] values) {
            return Arrays.stream(values).sum() / values.length;
        }
    }
}
