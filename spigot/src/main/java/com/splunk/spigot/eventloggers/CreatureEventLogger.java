package com.splunk.spigot.eventloggers;

import com.splunk.sharedmc.logger.AbstractEventLogger;
import com.splunk.sharedmc.logger.actions.CreatureEventAction;
import com.splunk.sharedmc.logger.events.LoggableCreatureEvent;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;
import com.splunk.sharedmc.logger.utilities.Point3d;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Properties;


public class CreatureEventLogger extends AbstractEventLogger implements Listener {

    public CreatureEventLogger(Properties properties) {
        super(properties);
    }

    @EventHandler
    public void captureSpawnEvent(CreatureSpawnEvent event) {
        if (!event.isCancelled())
            logAndSend(getLoggableEntityEvent(CreatureEventAction.SPAWN, event));
    }

    private LoggableCreatureEvent getLoggableEntityEvent(CreatureEventAction action, EntityEvent event) {

        final Entity entity = event.getEntity();
        final Location location = entity.getLocation();
        final World world = entity.getWorld();

        Point3d coordinates = new Point3d(location.getX(), location.getY(), location.getZ());

        LoggableCreatureEvent entityEvent = new LoggableCreatureEvent(world.getFullTime(), minecraft_server, world.getName(), action);

        LoggableLivingEntity spCreature = new LoggableLivingEntity();
        spCreature.setType("creature");
        spCreature.setCurrentHealth(((org.bukkit.entity.LivingEntity)entity).getHealth());
        spCreature.setMaxHealth(((org.bukkit.entity.LivingEntity)entity).getMaxHealth());
        spCreature.setLocation(coordinates);

        for (PotionEffect potion : (((org.bukkit.entity.LivingEntity)entity).getActivePotionEffects())) {
            spCreature.addPotions(potion.getType().getName() + ":" + potion.getAmplifier());
        }

        if (event.getEntityType() == EntityType.SKELETON) {
            Skeleton skeleton = (org.bukkit.entity.Skeleton) event.getEntity();
            spCreature.setName(skeleton.getSkeletonType() + "_SKELETON");
        } else {
            spCreature.setName(event.getEntityType().name());
        }

        entityEvent.setEntity(spCreature);
        return entityEvent;
    }
}
