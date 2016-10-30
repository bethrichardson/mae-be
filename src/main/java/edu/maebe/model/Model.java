package edu.maebe.model;

import java.util.List;
import java.util.UUID;

public interface Model {
    UUID createJournal(String type, String value);
    List<Journal> getAllJournals();
    boolean existJournal(UUID journal);
}