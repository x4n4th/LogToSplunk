package com.splunk.spigot.eventloggers;

import com.splunk.sharedmc.logger.AbstractEventLogger;
import com.splunk.sharedmc.logger.actions.DeathEventAction;
import com.splunk.sharedmc.logger.events.LoggableDeathEvent;
import com.splunk.sharedmc.logger.entities.LoggableInstrument;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;
import com.splunk.sharedmc.logger.utilities.Point3d;

import com.splunk.spigot.utilities.EntityUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeathEventLogger extends AbstractEventLogger implements Listener {

    public DeathEventLogger(Properties properties) {
        super(properties);
    }

    @EventHandler
    public void captureDeathEvent(EntityDeathEvent event) {

        LoggableDeathEvent deathEvent = getLoggableDeathEvent(DeathEventAction.DEATH, event);

        if(event instanceof PlayerDeathEvent){
            deathEvent.setCategory("player");
        } else {
            deathEvent.setCategory("creature");
        }

        logAndSend(deathEvent);
    }

    private LoggableDeathEvent getLoggableDeathEvent(DeathEventAction action, EntityDeathEvent event) {

        final org.bukkit.entity.LivingEntity victim = event.getEntity();
        final Location location = victim.getLocation();
        final World world = victim.getWorld();

        LoggableDeathEvent deathEvent = new LoggableDeathEvent(world.getFullTime(), minecraft_server, world.getName(), action);

        LoggableLivingEntity spVictim = (LoggableLivingEntity)EntityUtil.getLoggableEntity(victim);

        deathEvent.setVictim(spVictim);

        Entity killer = event.getEntity().getKiller();

        if (killer != null) {

            LoggableLivingEntity spKiller = (LoggableLivingEntity)EntityUtil.getLoggableEntity(killer);
            deathEvent.setKiller(spKiller);


            LoggableInstrument instrument = EntityUtil.getInstrument((Player)killer);


            deathEvent.setWeapon(instrument);

        } else {
            // Creature did killing and we have to get killer from death message
            if (event instanceof PlayerDeathEvent) {
                // Only works if a player dies.

                Pattern regex = Pattern.compile("\\S* (was slain by|was shot by a|was blown up by) (?<killer>\\S*)");
                Matcher matcher = regex.matcher(((PlayerDeathEvent) event).getDeathMessage());

                if (matcher.matches()) {

                    deathEvent.setKiller(new LoggableLivingEntity("creature", matcher.group("killer")));
                }
            }
        }
        return deathEvent;
    }

}
