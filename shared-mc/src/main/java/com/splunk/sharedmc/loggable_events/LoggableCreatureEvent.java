package com.splunk.sharedmc.loggable_events;

import com.splunk.sharedmc.utilities.LivingLoggerEntity;

public class LoggableCreatureEvent extends AbstractLoggableEvent {


    private LivingLoggerEntity entity;

    public LoggableCreatureEvent(long gameTime, String minecraft_server, String world, EntityEventAction action) {
        super(gameTime, minecraft_server, world, "creature", action.asString());
    }

    public LivingLoggerEntity getEntity() {
        return entity;
    }

    public void setEntity(LivingLoggerEntity entity) {
        this.entity = entity;
    }

    public enum EntityEventAction {
        SPAWN("spawn");

        private final String action;

        EntityEventAction(String action) {
            this.action = action;
        }

        public String asString() {
            return action;
        }
    }
}
