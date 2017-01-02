package com.splunk.sharedmc.loggable_events;

import com.splunk.sharedmc.utilities.Instrument;
import com.splunk.sharedmc.utilities.LivingLoggerEntity;

/**
 * Created by powerschill on 7/25/16.
 */
public class LoggableDeathEvent extends AbstractLoggableEvent {

    private Instrument weapon;
    private String cause;
    private LivingLoggerEntity killer;
    private LivingLoggerEntity victim;

    public LoggableDeathEvent(long gameTime, String minecraft_server, String world, DeathEventAction action) {
        super(gameTime, minecraft_server, world, "death", action.asString());
    }

    public LivingLoggerEntity getKiller() {
        return killer;
    }

    public void setKiller(LivingLoggerEntity killer) {
        this.killer = killer;
    }

    public LivingLoggerEntity getVictim() {
        return victim;
    }

    public void setVictim(LivingLoggerEntity victim) {
        this.victim = victim;
    }

    public Instrument getWeapon() {
        return this.weapon;
    }

    public void setWeapon(Instrument weapon) {
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
