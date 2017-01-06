package com.splunk.sharedmc.logger.actions;

public enum DeathEventAction {
    CREATURE("creature_death"),
    PLAYER("player_death");

    private final String action;

    DeathEventAction(String action) {
        this.action = action;
    }

    public String asString() {
        return action;
    }
}