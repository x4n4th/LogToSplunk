package com.splunk.sharedmc.logger.events;


import com.splunk.sharedmc.logger.entities.LoggableBlock;
import com.splunk.sharedmc.logger.entities.LoggableInstrument;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;

/**
 * Almost pojo with fields for information that might be associated with a block event.
 */
public class LoggableBlockEvent extends AbstractLoggableEvent {


    private LoggableBlock block;
    private LoggableLivingEntity player;
    private LoggableInstrument tool;
    private String cause;

    public LoggableBlockEvent(long gameTime, String minecraft_server, String world, BlockEventAction action) {
        super(gameTime, minecraft_server, world, "block", action.asString());
    }

    public LoggableLivingEntity getPlayer() {
        return player;
    }

    public void setPlayer(LoggableLivingEntity player) {
        this.player = player;
    }

    public LoggableBlock getBlock() {
        return this.block;
    }

    public void setBlock(LoggableBlock block) {
        this.block = block;
    }


    public LoggableInstrument getTool() {
        return this.tool;
    }

    public void setTool(LoggableInstrument tool) {
        this.tool = tool;
    }

    public String getCause() {
        return this.cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }


    /**
     * Different types of actions that can occur as part of a BlockEvent.
     */
    public enum BlockEventAction {
        BREAK("break"),
        PLACE("place"),
        IGNITE("ignite");

        /**
         * The name of the action.
         */
        private final String action;

        BlockEventAction(String action) {
            this.action = action;
        }

        /**
         * String representation of the action.
         *
         * @return The action in friendly String format.
         */
        public String asString() {
            return action;
        }
    }
}
