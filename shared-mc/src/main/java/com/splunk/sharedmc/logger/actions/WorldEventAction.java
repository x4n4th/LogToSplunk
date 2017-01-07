package com.splunk.sharedmc.logger.actions;

public enum WorldEventAction {
    CHUNK_POPULATE("chunk_populate");

    private final String action;

    WorldEventAction(String action) {
        this.action = action;
    }

    public String asString() {
        return action;
    }
}