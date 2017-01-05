package com.splunk.sharedmc.logger.events;

import com.splunk.sharedmc.logger.entities.LoggableInstrument;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;

public class LoggableDeathEvent extends AbstractLoggableEvent {

    private LoggableInstrument weapon;
    private String cause;
    private LoggableLivingEntity killer;
    private LoggableLivingEntity victim;

    public LoggableDeathEvent(long gameTime, String minecraft_server, String world, DeathEventAction action) {
        super(gameTime, minecraft_server, world, "death", action.asString());
    }

    public LoggableLivingEntity getKiller() {
        return killer;
    }

    public void setKiller(LoggableLivingEntity killer) {
        this.killer = killer;
    }

    public LoggableLivingEntity getVictim() {
        return victim;
    }

    public void setVictim(LoggableLivingEntity victim) {
        this.victim = victim;
    }

    public LoggableInstrument getWeapon() {
        return this.weapon;
    }

    public void setWeapon(LoggableInstrument weapon) {
        this.weapon = weapon;
    }

    public String getCause() {
        return this.cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }


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
}
