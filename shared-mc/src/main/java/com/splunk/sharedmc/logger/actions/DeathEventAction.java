package com.splunk.sharedmc.logger.actions;

public enum DeathEventAction {
    DEATH("death");

    private final String action;

    DeathEventAction(String action) {
        this.action = action;
    }

    public String asString() {
        return action;
    }
}