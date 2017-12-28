package com.splunk.spigot.eventloggers;

import com.splunk.sharedmc.logger.AbstractEventLogger;
import com.splunk.sharedmc.logger.actions.InventoryEventAction;
import com.splunk.sharedmc.logger.entities.LoggableBlock;
import com.splunk.sharedmc.logger.entities.LoggableInstrument;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;
import com.splunk.sharedmc.logger.events.LoggableInventoryEvent;
import com.splunk.sharedmc.logger.utilities.Point3d;
import com.splunk.spigot.utilities.EntityUtil;
import com.splunk.spigot.utilities.MCItemCatalogue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Properties;
import java.util.logging.Logger;

public class InventoryEventLogger extends AbstractEventLogger implements Listener {

    Logger log;

    MCItemCatalogue MCItems = MCItemCatalogue.getInstance();

    public InventoryEventLogger(Properties properties) {
        super(properties);
        log = Bukkit.getLogger();
    }

    @EventHandler
    public void captureBreakEvent(CraftItemEvent event) {
        // Only log event if it is successful
        if (!event.isCancelled()){
            try{
                final Location location = event.getWhoClicked().getLocation();
                final World world = event.getWhoClicked().getWorld();


                LoggableInventoryEvent inventoryEvent = new LoggableInventoryEvent(world.getFullTime(), minecraft_server, world.getName(), InventoryEventAction.CRAFT);

                inventoryEvent.setPlayer((LoggableLivingEntity) EntityUtil.getLoggableEntity(event.getWhoClicked()));

                ItemStack itemStack = event.getRecipe().getResult();

                Material material = itemStack.getType();

                if(material.isBlock())
                {
                    Point3d boxLocation = new Point3d(location.getX(), location.getY(), location.getZ());

                    inventoryEvent.setItem(new LoggableBlock(itemStack.getType().toString(), MCItems.getBlockName(itemStack.getType().toString().toLowerCase(), itemStack.getDurability()), boxLocation));
                } else {

                    LoggableInstrument instrument = EntityUtil.getInstrument(itemStack);
                    inventoryEvent.setItem(instrument);
                }


                logAndSend(inventoryEvent);


            } catch (Exception ex){
                log.warning(ex.toString());
            }
        }
    }

}
