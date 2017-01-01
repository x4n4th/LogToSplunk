package com.splunk.sharedmc.loggable_events;

import com.splunk.sharedmc.utilities.Block;

public class LoggableWorldEvent extends AbstractLoggableEvent {

    private Block block;

    public LoggableWorldEvent(long gameTime, String minecraft_server, String world, WorldEventAction action) {
        super(gameTime, minecraft_server, world, "world", action.asString());
    }

    public void setBlock(Block block) {
        this.block = block;
    }

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
}
