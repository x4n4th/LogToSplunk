package com.splunk.sharedmc.logger.actions;

public enum CreatureEventAction {
    SPAWN("spawn");

    private final String action;

    CreatureEventAction(String action) {
        this.action = action;
    }

    public String asString() {
        return action;
    }
}