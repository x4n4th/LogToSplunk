package com.splunk.sharedmc.logger.events;


import com.splunk.sharedmc.logger.actions.PlayerEventAction;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;
import com.splunk.sharedmc.logger.utilities.Point3d;

public class LoggablePlayerEvent extends AbstractLoggableEvent {


    private LoggableLivingEntity player;
    private Point3d src;
    private Point3d dest;

    public LoggablePlayerEvent(long gameTime, String minecraft_server, String world, PlayerEventAction action) {
        super(gameTime, minecraft_server, world, "player", action.asString());
    }

    public Point3d getDest() {
        return dest;
    }

    public void setDest(Point3d dest) {
        this.dest = dest;
    }

    public LoggableLivingEntity getPlayer() {
        return this.player;
    }

    public void setPlayer(LoggableLivingEntity player) {
        this.player = player;
    }

    public Point3d getSrc() {
        return this.src;
    }

    public void setSrc(Point3d src) {
        this.src = src;
    }

}
