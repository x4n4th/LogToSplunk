package com.splunk.sharedmc.logger.actions;

/**
 * Different types of actions that can occur as part of a PlayerEvent.
 */
public enum PlayerEventAction {
    PLAYER_LOGIN("login"),
    PLAYER_LOGOUT("logout"),
    MOVE("move"),
    TELEPORT("teleport");

    /**
     * The name of the action.
     */
    private final String action;

    PlayerEventAction(String action) {
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