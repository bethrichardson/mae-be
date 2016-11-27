package edu.maebe.watson;

import com.ibm.watson.developer_cloud.personality_insights.v3.model.*;
import edu.maebe.model.MoodRating;
import edu.maebe.model.Need;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(includeFieldNames=true, exclude = {"userId"})
public class PersonalityScore {
    MoodRating moodRating;
    Need need;
    String userId;

    PersonalityScore(String userId) {
        this.userId = userId;
        this.moodRating = new MoodRating(userId);
        this.need = new Need(userId);
    }

    PersonalityScore(String userId, Profile profile) {
        this.userId = userId;
        List<Trait> traitsList = profile.getPersonality();
        List<Trait> needsList = profile.getNeeds();
        moodRating = new MoodRating(userId);
        need = new Need(userId);
        for (Trait trait:traitsList) {
            try {
                moodRating.setField(trait, trait.getRawScore());
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (Trait trait:needsList) {
            try {
                need.setField(trait.getTraitId(), trait.getRawScore());
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
