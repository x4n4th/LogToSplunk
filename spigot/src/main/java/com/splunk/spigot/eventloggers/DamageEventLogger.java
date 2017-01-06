package com.splunk.spigot.eventloggers;

import com.splunk.sharedmc.logger.actions.EntityDamageEventAction;
import com.splunk.sharedmc.logger.entities.*;
import com.splunk.sharedmc.logger.AbstractEventLogger;
import com.splunk.sharedmc.logger.events.LoggableDamageEvent;
import com.splunk.sharedmc.logger.utilities.Point3d;
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

        victim = getLivingEntity(entity);

        loggableEvent.setVictim(victim);

        if(event instanceof EntityDamageByEntityEvent){

            LoggableEntity assailant;

            Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();

            if(attacker instanceof Projectile){
                attacker = (Entity)((Projectile) attacker).getShooter();
            }

            if(attacker instanceof Player){
                tool = getInstrument((Player)attacker);

                loggableEvent.setTool(tool);
            }

            assailant = getLivingEntity(attacker);

            loggableEvent.setAttacker(assailant);

        }

        loggableEvent.setDamageRaw(damageRaw);
        loggableEvent.setDamageFinal(damageFinal);
        loggableEvent.setCause(cause);

        return loggableEvent;
    }

    private LoggableEntity getLivingEntity(Entity entity){

        LoggableEntity livingEntity;

        Point3d location = new Point3d(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());

        if (entity instanceof Player) {

            livingEntity = new LoggableLivingEntity("player", entity.getName(), location);

            for (PotionEffect potion : ((Player) entity).getActivePotionEffects()) {
                ((LoggableLivingEntity)livingEntity).addPotions(potion.getType().getName() + ":" + potion.getAmplifier() );
            }

            ((LoggableLivingEntity)livingEntity).setCurrentHealth(((Player) entity).getHealth());

            ((LoggableLivingEntity)livingEntity).setMaxHealth(((Player)entity).getMaxHealth());
            ((LoggableLivingEntity)livingEntity).setYaw(entity.getLocation().getYaw());
            ((LoggableLivingEntity)livingEntity).setPitch(entity.getLocation().getPitch());

        }else if (entity instanceof Item) {

            livingEntity = new LoggableItemEntity("item", entity.getName(), location);


        } else {
            if (entity.getType() == EntityType.SKELETON) {
                Skeleton skeleton = (org.bukkit.entity.Skeleton) entity;

                livingEntity = new LoggableLivingEntity("creature", skeleton.getSkeletonType() + "_SKELETON", location);


            } else {
                livingEntity = new LoggableLivingEntity("creature", entity.getName(), location);

            }

            ((LoggableLivingEntity)livingEntity).setYaw(entity.getLocation().getYaw());
            ((LoggableLivingEntity)livingEntity).setPitch(entity.getLocation().getPitch());
        }




        return livingEntity;
    }

    private LoggableInstrument getInstrument(Player p){
        ItemStack instrument = p.getInventory().getItemInMainHand();

        // Some items have "_ITEM" appended to the end.
        String instrumentName = instrument.getType().toString().replaceAll("_ITEM$","");

        LoggableInstrument tool = new LoggableInstrument(instrumentName);
        for (Enchantment key : instrument.getEnchantments().keySet()) {

            tool.addEnchantment(key.getName().toString() + ":" + instrument.getEnchantments().get(key));
        }

        if(instrument.getItemMeta() != null){
            tool.setName(instrument.getItemMeta().getDisplayName());
        }

        return tool;
    }
}
