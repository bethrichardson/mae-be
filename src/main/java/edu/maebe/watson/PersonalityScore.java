package edu.maebe.watson;

import com.ibm.watson.developer_cloud.personality_insights.v3.model.*;
import edu.maebe.model.MoodRating;
import edu.maebe.model.Need;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(includeFieldNames=true)
public class PersonalityScore {
    MoodRating moodRating;
    Need need;

    public PersonalityScore(Profile profile) {
        List<Trait> traitsList = profile.getPersonality();
        List<Trait> needsList = profile.getNeeds();
        moodRating = new MoodRating();
        need = new Need();
        for (Trait trait:traitsList) {
            try {
                moodRating.setField(trait, trait.getPercentile());
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (Trait trait:needsList) {
            try {
                need.setField(trait.getTraitId(), trait.getPercentile());
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
