package com.splunk.sharedmc.logger.events;

import com.splunk.sharedmc.logger.actions.CreatureEventAction;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;

public class LoggableCreatureEvent extends AbstractLoggableEvent {

    private LoggableLivingEntity entity;

    public LoggableCreatureEvent(long gameTime, String minecraft_server, String world, CreatureEventAction action) {
        super(gameTime, minecraft_server, world, "creature", action.asString());
    }

    public LoggableLivingEntity getEntity() {
        return entity;
    }

    public void setEntity(LoggableLivingEntity entity) {
        this.entity = entity;
    }

}
