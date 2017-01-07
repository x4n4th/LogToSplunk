package com.splunk.spigot.eventloggers;

import com.splunk.sharedmc.logger.actions.EntityDamageEventAction;
import com.splunk.sharedmc.logger.entities.*;
import com.splunk.sharedmc.logger.AbstractEventLogger;
import com.splunk.sharedmc.logger.events.LoggableDamageEvent;
import com.splunk.sharedmc.logger.utilities.Point3d;
import com.splunk.spigot.utilities.EntityUtil;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Properties;


public class DamageEventLogger extends AbstractEventLogger implements Listener {

    public DamageEventLogger(Properties properties) {
        super(properties);
    }

    @EventHandler
    public void captureDamageEvent(EntityDamageEvent event) {
        if (!event.isCancelled())
            logAndSend(getLoggableEntityEvent(EntityDamageEventAction.DAMAGE, event));
    }

    private LoggableDamageEvent getLoggableEntityEvent(EntityDamageEventAction action, EntityDamageEvent event) {

        LoggableEntity victim;
        LoggableInstrument tool;

        Entity entity = event.getEntity();

        String category;

        if(entity instanceof Player) {
            category = "player";
        } else if (entity instanceof Item) {
            category = "item";
        } else {
            category = "creature";
        }

        World world = entity.getWorld();
        LoggableDamageEvent loggableEvent = new LoggableDamageEvent(world.getFullTime(), minecraft_server, world.getName(), category, action);

        double damageRaw = event.getDamage();
        double damageFinal = event.getFinalDamage();
        String cause = event.getCause().name();

        victim = EntityUtil.getLoggableEntity(entity);

        loggableEvent.setVictim(victim);

        if(event instanceof EntityDamageByEntityEvent){

            LoggableEntity assailant;

            Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();

            if(attacker instanceof Projectile){
                attacker = (Entity)((Projectile) attacker).getShooter();
            }

            if(attacker instanceof Player){
                tool = EntityUtil.getInstrument((Player)attacker);

                loggableEvent.setTool(tool);
            }

            assailant = EntityUtil.getLoggableEntity(attacker);

            loggableEvent.setAttacker(assailant);

        }

        loggableEvent.setDamageRaw(damageRaw);
        loggableEvent.setDamageFinal(damageFinal);
        loggableEvent.setCause(cause);

        return loggableEvent;
    }
}
