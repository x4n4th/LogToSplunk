package com.splunk.sharedmc.logger.events;

import com.splunk.sharedmc.logger.actions.WorldEventAction;
import com.splunk.sharedmc.logger.entities.LoggableBlock;

public class LoggableWorldEvent extends AbstractLoggableEvent {

    private LoggableBlock block;

    public LoggableWorldEvent(long gameTime, String minecraft_server, String world, WorldEventAction action) {
        super(gameTime, minecraft_server, world, "world", action.asString());
    }

    public void setBlock(LoggableBlock block) {
        this.block = block;
    }

}
