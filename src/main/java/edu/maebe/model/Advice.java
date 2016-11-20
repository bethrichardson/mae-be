package edu.maebe.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Advice {

    private static String[] need_challenge = {
            "Why don't you try a new puzzle?",
            "Have you thought about trying to learn how to cook something complicated?",
            "Is there something difficult that you have been putting off?",
            "Have you ever tried mountain biking? Maybe you should get a bike and try it.",
            "Would you be up for trying a new sport? Maybe there is an amateur soccer league nearby."
    };
    private static String challenge = "You seem like you could use a new challenge.";


    private static String[] need_closeness = {
            "You could use a hug from a close friend.",
            "Have you reached out to a loved one for a chat lately?",
            "Find some time to get close with someone you love today.",
            "Get a good snuggle from your baby later."
    };

    private static String[] need_curiosity = {
            "Why don't you try learning a new language?",
            "What's something you would like to learn more about? There are lots of free online courses that can teach you new things you haven;t tried before.",
            "Where is your family originally from? Maybe you should see if you can find anything out from a family member or online.",
            "What is your favorite work of art? Do you know who painted it and where it is? Go do some research and find out."
    };

    private static String[] need_excitement = {
            "You should go find an amusement park or other fun place to go play with your baby today.",
            "Go outside and try something new today. Maybe go fly a kite at a park nearby?",
            "Have you gone dancing lately? You should see if some friends would like to join you for dancing soon."
    };

    private static String[] need_harmony = {
            "Look for resolution in any conflicts you have been having lately.",
            "Meditation can be great to restore balance in a complicated time.",
            "Try to find balance in your daily schedule. Make sure to schedule time for your self to calm down.",
            "Set aside time to talk things out with your partner or other family members at the end of a long day.",
            "Make sure to spend some time having a good conversation with a friend or family member today."
    };

    private static String[] need_ideal = {
            "How can you perfect something you have recently been working on. Give yourself some time to work on that project today.",
            "Do you have a local community project you volunteer for? If not, go seek one out today. If so, schedule some time to go help again soon.",
            "How have you contributed to your family today? Give yourself credit for all that you do every day for those around you!"
    };

    private static String[] need_liberty = {
            "Do you have some money in your budget to go shopping for something new for yourself?",
            "Today I want you to treat yourself to something new!",
            "Can you find a way to just get away for a few minutes today? Maybe ask a friend or neighbor to watch the baby for a few hours and take off!",
            "Find a way to give yourself some time to just do whatever you want today, even if just for ten minutes."
    };

    private static String[] need_love = {
            "Reach out to a close friend or loved one for some special time today.",
            "Take some time with your partner to spend some time getting close today.",
            "Have you tried talking to your baby out loud? Look into those little eyes and tell that baby you are in love!"
    };

    private static String[] need_practicality = {
            "Try to set aside some time today to get something done that you have been putting off. Even if it's just fifteen minutes to work towards that goal.",
            "Give your baby a new toy and let yourself have some fun getting a job done today.",
            "What's the one project that sounds like the most fun to you? What can you do to let yourself make some progress on it today?"
    };

    private static String[] need_self_expression = {
            "It's great that you want to share so much with your journal. Can you also start a paper journal to include illustrations?",
            "Have you ever considered starting a photo journal online or just on your computer? You can organize and share your favorite images from your life.",
            "Is there something you have always wanted to try to wear or change about your appearance? Is it possible to experiment today?"
    };

    private static String[] need_stability = {
            "Do you have a defined routine that works well to weather the challenging times of your day? Try to write down what works and what doesn't and hash out a plan.",
            "Focus on what makes you feel secure and safe in your current home. Focus on the positive and think about how it comforts you.",
            "Try to just say no to interruptions and disruptions from your stable day today. Give yourself time to just take it easy and do what feels natural.",
            "How does a hot bath sound today?"
    };

    private static String[] need_structure = {
            "Take some time to work on your to do list today. Just get everything down so you can work on it when you have time.",
            "What does your schedule look like for this week? Are you prepared now for what is coming up tomorrow?",
            "Can you make some adjustments to your daily routine to make sure your chores get done today?",
            "Do you have a large project hanging over you? If so, create a plan for how you will tackle it today."
    };

    public static Map<String, String[]> adviceMap = ImmutableMap.<String, String[]>builder()
            .put("need_challenge", need_challenge)
            .put("need_closeness", need_closeness)
            .put("need_curiosity", need_curiosity)
            .put("need_excitement", need_excitement)
            .put("need_harmony", need_harmony)
            .put("need_ideal", need_ideal)
            .put("need_liberty", need_liberty)
            .put("need_love", need_love)
            .put("need_practicality", need_practicality)
            .put("need_self_expression", need_self_expression)
            .put("need_stability", need_stability)
            .put("need_structure", need_structure)
            .build();
}
