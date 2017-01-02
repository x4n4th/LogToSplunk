package com.splunk.spigot.eventloggers;

import com.splunk.sharedmc.event_loggers.AbstractEventLogger;
import com.splunk.sharedmc.loggable_events.LoggableDamageEvent;
import com.splunk.sharedmc.utilities.*;
import com.splunk.sharedmc.utilities.LivingLoggerEntity;
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
            logAndSend(getLoggableEntityEvent(LoggableDamageEvent.EntityDamageEventAction.DAMAGE, event));
    }

    private LoggableDamageEvent getLoggableEntityEvent(LoggableDamageEvent.EntityDamageEventAction action, EntityDamageEvent event) {

        LoggerEntity victim;
        Instrument tool;

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

        victim = getLivingEntity(entity);

        loggableEvent.setVictim(victim);

        if(event instanceof EntityDamageByEntityEvent){

            LoggerEntity assailant;

            Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();

            if(attacker instanceof Projectile){
                attacker = (Entity)((Arrow) attacker).getShooter();
            }

            if(attacker instanceof Player){
                tool = getInstrument((Player)attacker);

                loggableEvent.setTool(tool);
            }

            assailant = getLivingEntity(attacker);

            loggableEvent.setAssailant(assailant);

        }

        loggableEvent.setDamageRaw(damageRaw);
        loggableEvent.setDamageFinal(damageFinal);

        return loggableEvent;
    }

    private LoggerEntity getLivingEntity(Entity entity){

        LoggerEntity livingEntity;

        Point3d location = new Point3d(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());

        if (entity instanceof Player) {

            livingEntity = new LivingLoggerEntity("player", entity.getName(), location);

            for (PotionEffect potion : ((Player) entity).getActivePotionEffects()) {
                ((LivingLoggerEntity)livingEntity).addPotions(potion.getType().getName() + ":" + potion.getAmplifier() );
            }

            ((LivingLoggerEntity)livingEntity).setCurrentHealth(((Player) entity).getHealth());

            ((LivingLoggerEntity)livingEntity).setMaxHealth(((Player)entity).getMaxHealth());
            ((LivingLoggerEntity)livingEntity).setYaw(entity.getLocation().getYaw());
            ((LivingLoggerEntity)livingEntity).setPitch(entity.getLocation().getPitch());

        }else if (entity instanceof Item) {

            livingEntity = new ItemLoggerEntity("item", entity.getName(), location);


        } else {
            if (entity.getType() == EntityType.SKELETON) {
                Skeleton skeleton = (org.bukkit.entity.Skeleton) entity;

                livingEntity = new LivingLoggerEntity("creature", skeleton.getSkeletonType() + "_SKELETON", location);


            } else {
                livingEntity = new LivingLoggerEntity("creature", entity.getName(), location);

            }

            ((LivingLoggerEntity)livingEntity).setYaw(entity.getLocation().getYaw());
            ((LivingLoggerEntity)livingEntity).setPitch(entity.getLocation().getPitch());
        }




        return livingEntity;
    }

    private Instrument getInstrument(Player p){
        ItemStack instrument = p.getInventory().getItemInMainHand();

        // Some items have "_ITEM" appended to the end.
        String instrumentName = instrument.getType().toString().replaceAll("_ITEM$","");

        Instrument tool = new Instrument(instrumentName);
        for (Enchantment key : instrument.getEnchantments().keySet()) {

            tool.addEnchantment(key.getName().toString() + ":" + instrument.getEnchantments().get(key));
        }

        if(instrument.getItemMeta() != null){
            tool.setName(instrument.getItemMeta().getDisplayName());
        }

        return tool;
    }
}
