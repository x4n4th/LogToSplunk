package com.splunk.sharedmc.loggable_events;

import com.splunk.sharedmc.utilities.Instrument;
import com.splunk.sharedmc.utilities.LivingLoggerEntity;
import com.splunk.sharedmc.utilities.LoggerEntity;

public class LoggableDamageEvent extends AbstractLoggableEvent {


    private LoggerEntity assailant;
    private LoggerEntity victim;
    private double damageRaw;
    private double damageFinal;
    private Instrument tool;
    private String cause;

    public LoggableDamageEvent(long gameTime, String minecraft_server, String world, String category, EntityDamageEventAction action) {
        super(gameTime, minecraft_server, world, category, action.asString());
    }

    public void setAssailant(LoggerEntity assailant) {
        this.assailant = assailant;
    }

    public void setVictim(LoggerEntity victim) {
        this.victim = victim;
    }

    public void setDamageRaw(double damageRaw) {
        this.damageRaw = damageRaw;
    }

    public void setDamageFinal(double damageFinal) {
        this.damageFinal = damageFinal;
    }

    public void setTool(Instrument tool) {
        this.tool = tool;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

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
}
