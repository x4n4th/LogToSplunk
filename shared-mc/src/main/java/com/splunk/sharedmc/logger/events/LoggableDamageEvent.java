package com.splunk.sharedmc.logger.events;

import com.splunk.sharedmc.logger.entities.LoggableInstrument;
import com.splunk.sharedmc.logger.entities.LoggableEntity;

public class LoggableDamageEvent extends AbstractLoggableEvent {


    private LoggableEntity attacker;
    private LoggableEntity victim;
    private double damageRaw;
    private double damageFinal;
    private LoggableInstrument tool;
    private String cause;

    public LoggableDamageEvent(long gameTime, String minecraft_server, String world, String category, EntityDamageEventAction action) {
        super(gameTime, minecraft_server, world, category, action.asString());
    }

    public void setAttacker(LoggableEntity attacker) {
        this.attacker = attacker;
    }

    public void setVictim(LoggableEntity victim) {
        this.victim = victim;
    }

    public void setDamageRaw(double damageRaw) {
        this.damageRaw = Math.round(damageRaw * 100.00) / 100.00;
    }

    public void setDamageFinal(double damageFinal) {
        this.damageFinal = Math.round(damageFinal * 100.00) / 100.00;
    }

    public void setTool(LoggableInstrument tool) {
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
