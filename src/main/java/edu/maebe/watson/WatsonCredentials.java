package edu.maebe.watson;

public class WatsonCredentials {
    public static final String password = System.getenv("WATSON_PASSWORD");
    public static final String username = System.getenv("WATSON_USERNAME");
}
