package edu.maebe.model;

import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test {
    private HashMap<String, Profile> tests;

    @Getter
    private Profile profile;

    public Test (String testName) {
        buildTestSet();

        this.profile = tests.get(getLastWordInTestName(testName));
        System.out.println("Testing with " + getLastWordInTestName(testName));
        if (profile == null) {
            this.profile = tests.get("normal");
        }
    }

    private String getLastWordInTestName(String testName) {
        return testName.substring(testName.lastIndexOf(" ")+1);
    }

    private void buildTestSet() {
        tests = new HashMap<>();
        tests.put("anger", createProfile(angryPersonality, challengeNeed));
        tests.put("anxiety", createProfile(anxiousPersonality, closenessNeed));
        tests.put("depression", createProfile(depressedPersonality, curiosityNeed));
        tests.put("immoderate", createProfile(immoderatePersonality, excitementNeed));
        tests.put("self-conscious", createProfile(selfconsciousPersonality, harmonyNeed));
        tests.put("vulnerable", createProfile(vulnerablePersonality, idealNeed));
        tests.put("normal", createProfile(normalPersonality, libertyNeed));
        tests.put("help", createProfile(needsHelpPersonality, libertyNeed));

    }

    private final List<Trait> normalPersonality = createPersonality(0.2, 0.2, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> angryPersonality = createPersonality(0.55, 0.2, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> anxiousPersonality = createPersonality(0.2, 0.65, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> depressedPersonality = createPersonality(0.2, 0.2, 0.57, 0.2, 0.2, 0.2);
    private final List<Trait> immoderatePersonality = createPersonality(0.2, 0.2, 0.2, 0.5, 0.2, 0.2);
    private final List<Trait> selfconsciousPersonality = createPersonality(0.2, 0.2, 0.2, 0.2, 0.65, 0.2);
    private final List<Trait> vulnerablePersonality = createPersonality(0.2, 0.2, 0.2, 0.2, 0.2, 0.55);
    private final List<Trait> needsHelpPersonality = createPersonality(0.2, 0.2, 0.2, 0.2, 0.2, 0.99);

    private final List<Trait> challengeNeed = createNeeds( 0.99, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> closenessNeed = createNeeds( 0.2, 0.99, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> curiosityNeed = createNeeds( 0.2, 0.2, 0.99, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> excitementNeed = createNeeds( 0.2, 0.2, 0.2, 0.99, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> harmonyNeed = createNeeds( 0.2, 0.2, 0.2, 0.2, 0.99, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> idealNeed = createNeeds( 0.2, 0.2, 0.2, 0.2, 0.2, 0.99, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> libertyNeed = createNeeds( 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.99, 0.2, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> loveNeed = createNeeds( 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.99, 0.2, 0.2, 0.2, 0.2);
    private final List<Trait> practicalityNeed = createNeeds( 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.99, 0.2, 0.2, 0.2);
    private final List<Trait> self_expressionNeed = createNeeds( 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.99, 0.2, 0.2);
    private final List<Trait> stabilityNeed = createNeeds( 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.99, 0.2);
    private final List<Trait> structureNeed = createNeeds( 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.99);


    private Profile createProfile(List<Trait> personality, List<Trait> needs) {
        Profile profile = new Profile();
        profile.setPersonality(personality);
        profile.setNeeds(needs);

        return profile;
    }

    private List<Trait> createPersonality(double facet_anger_value, double facet_anxious_value, double facet_depression_value,  double facet_immoderation_value,
                                         double facet_self_consciousness_value, double facet_vulnerability_value) {
        Trait facet_anger = createTrait("facet_anger", facet_anger_value);
        Trait facet_anxious = createTrait("facet_anxiety", facet_anxious_value);
        Trait facet_depression = createTrait("facet_depression", facet_depression_value);
        Trait facet_immoderation = createTrait("facet_immoderation", facet_immoderation_value);
        Trait facet_self_consciousness = createTrait("facet_self_consciousness", facet_self_consciousness_value);
        Trait facet_vulnerability = createTrait("facet_vulnerability", facet_vulnerability_value);
        Trait big5_neuroticism = createTrait("big5_neuroticism", 0.5);
        big5_neuroticism.setChildren(Arrays.asList(facet_anger, facet_anxious, facet_depression, facet_immoderation, facet_self_consciousness,
                                                   facet_vulnerability));
        Trait big5_agreeableness = createTrait("big5_agreeableness", 0.5);
        Trait big5_conscientiousness = createTrait("big5_conscientiousness", 0.5);
        Trait big5_extraversion = createTrait("big5_extraversion", 0.5);
        Trait big5_openness = createTrait("big5_openness", 0.5);
        return Arrays.asList(big5_agreeableness, big5_conscientiousness, big5_extraversion, big5_neuroticism, big5_openness);
    }

    private List<Trait> createNeeds(double need_challenge_value, double need_closeness_value,  double need_curiosity_value,
                                         double need_excitement_value, double need_harmony_value, double need_ideal_value,
                                   double need_liberty_value, double need_love_value, double need_practicality_value, double need_self_expression_value,
                                   double need_stability_value, double need_structure_value) {
        Trait need_challenge = createTrait("need_challenge", need_challenge_value);
        Trait need_closeness = createTrait("need_closeness", need_closeness_value);
        Trait need_curiosity = createTrait("need_curiosity", need_curiosity_value);
        Trait need_excitement = createTrait("need_excitement", need_excitement_value);
        Trait need_harmony = createTrait("need_harmony", need_harmony_value);
        Trait need_ideal = createTrait("need_ideal", need_ideal_value);
        Trait need_liberty = createTrait("need_liberty", need_liberty_value);
        Trait need_love = createTrait("need_love", need_love_value);
        Trait need_practivality = createTrait("need_practivality", need_practicality_value);
        Trait need_self_expression = createTrait("need_self_expression", need_self_expression_value);
        Trait need_stability = createTrait("need_stability", need_stability_value);
        Trait need_structure = createTrait("need_structure", need_structure_value);
        return Arrays.asList(need_challenge, need_closeness, need_curiosity, need_excitement,
                             need_harmony, need_ideal, need_liberty, need_love, need_practivality,
                             need_self_expression, need_stability, need_structure);
    }

    private Trait createTrait(String name, double value) {
        Trait trait = new Trait();
        trait.setTraitId(name);
        trait.setRawScore(value);

        return trait;
    }
}


