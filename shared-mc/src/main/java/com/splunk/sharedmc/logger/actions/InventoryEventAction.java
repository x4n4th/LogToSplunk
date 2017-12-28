package com.splunk.sharedmc.logger.actions;

/**
 * Different types of actions that can occur as part of a Event.
 */
public enum InventoryEventAction {
    CRAFT("craft");

    /**
     * The name of the action.
     */
    private final String action;

    InventoryEventAction(String action) {
        this.action = action;
    }

    /**
     * String representation of the action.
     *
     * @return The action in friendly String format.
     */
    public String asString() {
        return action;
    }
}