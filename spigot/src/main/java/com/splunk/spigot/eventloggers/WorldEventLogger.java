package com.splunk.spigot.eventloggers;


import com.splunk.sharedmc.event_loggers.AbstractEventLogger;
import com.splunk.sharedmc.loggable_events.LoggableWorldEvent;
import com.splunk.sharedmc.utilities.Biome;
import com.splunk.sharedmc.utilities.Block;

import com.splunk.sharedmc.utilities.Point3d;
import com.splunk.spigot.utilities.MCItemCatalogue;
import org.bukkit.Bukkit;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.WorldEvent;

import java.util.Properties;
import java.util.logging.Logger;

public class WorldEventLogger extends AbstractEventLogger implements Listener {

    MCItemCatalogue MCItems = MCItemCatalogue.getInstance();

    Logger log;

    public WorldEventLogger(Properties properties) {
        super(properties);

        log = Bukkit.getLogger();
    }


    @EventHandler
    public void capturePopulateChunkEvent(ChunkPopulateEvent event) {

        ChunkSnapshot snapshot = event.getChunk().getChunkSnapshot();

        for(int x = 0; x < 16; x++){
            for(int z = 0; z < 16; z++){

                int y = snapshot.getHighestBlockYAt(x, z) - 1;

                org.bukkit.block.Block mcBlock = event.getChunk().getBlock(x, y, z);

                String biomeName = mcBlock.getBiome().name();

                Point3d boxLocation = new Point3d(mcBlock.getX(), mcBlock.getY(), mcBlock.getZ());

                try{
                    Block b = new Block(mcBlock.getType().toString(), MCItems.getBlockName(mcBlock), boxLocation);
                    b.setBiome(new Biome(biomeName));

                    LoggableWorldEvent loggableEvent = getLoggablePlayerEvent(LoggableWorldEvent.WorldEventAction.CHUNK_POPULATE, event);

                    loggableEvent.setBlock(b);

                    logAndSend(loggableEvent);
                } catch (Exception ex){
                    log.warning(ex.toString());
                }
            }
        }

    }

    private LoggableWorldEvent getLoggablePlayerEvent(LoggableWorldEvent.WorldEventAction action, WorldEvent event) {

        final World world = event.getWorld();

        LoggableWorldEvent playerEvent = new LoggableWorldEvent(world.getFullTime(), minecraft_server, world.getName(), action);

        return playerEvent;

    }
}