package com.splunk.sharedmc.logger.actions;

public enum EntityDamageEventAction {
    DAMAGE("damage");

    private final String action;

    EntityDamageEventAction(String action) {
        this.action = action;
    }

    public String asString() {
        return action;
    }
}