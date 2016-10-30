package edu.maebe.handlers;

import edu.maebe.Validable;

public class EmptyPayload implements Validable {
    @Override
    public boolean isValid() {
        return true;
    }
}