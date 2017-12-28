package com.splunk.sharedmc.logger.events;


import com.splunk.sharedmc.logger.actions.BlockEventAction;
import com.splunk.sharedmc.logger.actions.InventoryEventAction;
import com.splunk.sharedmc.logger.entities.LoggableBlock;
import com.splunk.sharedmc.logger.entities.LoggableInstrument;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;
import jdk.nashorn.internal.runtime.logging.Loggable;

/**
 * Almost pojo with fields for information that might be associated with a block event.
 */
public class LoggableInventoryEvent extends AbstractLoggableEvent {

    private Object item;
    private LoggableLivingEntity player;

    public LoggableInventoryEvent(long gameTime, String minecraft_server, String world, InventoryEventAction action) {
        super(gameTime, minecraft_server, world, "inventory", action.asString());
    }

    public LoggableLivingEntity getPlayer() {
        return player;
    }

    public void setPlayer(LoggableLivingEntity player) {
        this.player = player;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}
