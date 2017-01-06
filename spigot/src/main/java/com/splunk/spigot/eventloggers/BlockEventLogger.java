package com.splunk.spigot.eventloggers;

import com.splunk.sharedmc.logger.AbstractEventLogger;
import com.splunk.sharedmc.logger.actions.BlockEventAction;
import com.splunk.sharedmc.logger.entities.LoggableBlock;
import com.splunk.sharedmc.logger.events.LoggableBlockEvent;
import com.splunk.sharedmc.logger.entities.LoggableInstrument;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;
import com.splunk.sharedmc.logger.utilities.Point3d;
import com.splunk.spigot.utilities.MCItemCatalogue;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Properties;
import java.util.logging.Logger;


/**
 * Handles the logging of block events.
 */
public class BlockEventLogger extends AbstractEventLogger implements Listener {


    MCItemCatalogue MCItems = MCItemCatalogue.getInstance();

    Logger log;

    public BlockEventLogger(Properties properties) {
        super(properties);
        log = Bukkit.getLogger();
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public void captureBreakEvent(BlockBreakEvent event) {
        // Only log event if it is successful
        if (!event.isCancelled()){
            try{
                logAndSend(getLoggableBlockEvent(BlockEventAction.BREAK, event));
            } catch (Exception ex){
                log.warning(ex.toString());
            }
        }
    }

    @EventHandler
    public void capturePlaceEvent(BlockPlaceEvent event) {
        if (!event.isCancelled()){
            try{
                logAndSend(getLoggableBlockEvent(BlockEventAction.PLACE, event));
            } catch (Exception ex){
                log.warning(ex.toString());
            }
        }
    }


    @EventHandler
    public void captureIgniteEvent(BlockIgniteEvent event) {
        if (!event.isCancelled()){
            try{
                logAndSend(getLoggableBlockEvent(BlockEventAction.IGNITE, event));
            } catch (Exception ex){
                log.warning(ex.toString());
            }
        }
    }


    private LoggableBlockEvent getLoggableBlockEvent(BlockEventAction action, BlockEvent event) throws Exception{


        // Pull a couple of objects from the event.
        final Block block = event.getBlock();
        final Location location = block.getLocation();
        final World world = block.getWorld();


        LoggableBlockEvent blockEvent = new LoggableBlockEvent(world.getFullTime(), minecraft_server, world.getName(), action);

        Point3d boxLocation = new Point3d(location.getX(), location.getY(), location.getZ());
        blockEvent.setBlock(new LoggableBlock(block.getType().toString(), MCItems.getBlockName(block), boxLocation));


        if (event instanceof BlockBreakEvent) {

            Player player = ((BlockBreakEvent) event).getPlayer();

            LoggableLivingEntity spEntity = new LoggableLivingEntity("player", player.getDisplayName(), new Point3d(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
            spEntity.setCurrentHealth(player.getHealth());
            spEntity.setMaxHealth(player.getMaxHealth());
            spEntity.setPitch(player.getLocation().getPitch());
            spEntity.setYaw(player.getLocation().getPitch());

            blockEvent.setPlayer(spEntity);

            ItemStack instrument = ((BlockBreakEvent) event).getPlayer().getInventory().getItemInMainHand();

            // Some items have "_ITEM" appended to the end.
            String instrumentName = instrument.getType().toString().replaceAll("_ITEM$","");

            LoggableInstrument tool = new LoggableInstrument(instrumentName);
            for (Enchantment key : instrument.getEnchantments().keySet()) {

                tool.addEnchantment(key.getName().toString() + ":" + instrument.getEnchantments().get(key));
            }

            if(instrument.getItemMeta() != null){
                tool.setName(instrument.getItemMeta().getDisplayName());
            }

            blockEvent.setTool(tool);


        } else if (event instanceof BlockPlaceEvent) {
            Player player = ((BlockPlaceEvent) event).getPlayer();

            LoggableLivingEntity spEntity = new LoggableLivingEntity("player", player.getDisplayName(), new Point3d(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
            spEntity.setCurrentHealth(player.getHealth());
            spEntity.setMaxHealth(player.getMaxHealth());
            spEntity.setPitch(location.getPitch());
            spEntity.setYaw(location.getYaw());
            blockEvent.setPlayer(spEntity);
        } else if (event instanceof BlockIgniteEvent) {


            switch (((BlockIgniteEvent) event).getCause()) {
                case ENDER_CRYSTAL:
                    blockEvent.setCause("ENDER_CRYSTAL");
                    break;
                case EXPLOSION:
                    blockEvent.setCause("EXPLOSION");
                    break;
                case FIREBALL:
                    blockEvent.setCause("FIREBALL");
                    break;
                case FLINT_AND_STEEL:
                    blockEvent.setCause("FLINT_AND_STEEL");
                    break;
                case LAVA:
                    blockEvent.setCause("LAVA");
                    break;
                case LIGHTNING:
                    blockEvent.setCause("LIGHTNING");
                    break;
                case SPREAD:
                    blockEvent.setCause("SPREAD");
                    break;
            }
        }


        return blockEvent;
    }

}