package com.splunk.spigot.utilities;

import com.splunk.sharedmc.logger.entities.LoggableEntity;
import com.splunk.sharedmc.logger.entities.LoggableInstrument;
import com.splunk.sharedmc.logger.entities.LoggableItemEntity;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;
import com.splunk.sharedmc.logger.utilities.Point3d;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public enum EntityUtil {;

    public static LoggableEntity getLoggableEntity(Entity entity){
        Point3d location = new Point3d(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());

        if(entity instanceof Player){
            Player player = (Player)entity;

            LoggableLivingEntity spEntity = new LoggableLivingEntity("player", player.getDisplayName(), location);

            spEntity.setCurrentHealth(player.getHealth());
            spEntity.setMaxHealth(player.getMaxHealth());
            spEntity.setPitch(player.getLocation().getPitch());
            spEntity.setYaw(player.getLocation().getPitch());

            for (PotionEffect potion : player.getActivePotionEffects()) {
                spEntity.addPotions(potion.getType().getName() + ":" + potion.getAmplifier());
            }

            return spEntity;
        } else if(entity instanceof Creature){

            LoggableLivingEntity spCreature = new LoggableLivingEntity();

            spCreature.setType("creature");
            spCreature.setCurrentHealth(((org.bukkit.entity.LivingEntity)entity).getHealth());
            spCreature.setMaxHealth(((org.bukkit.entity.LivingEntity)entity).getMaxHealth());
            spCreature.setLocation(location);
            spCreature.setPitch(entity.getLocation().getPitch());
            spCreature.setYaw(entity.getLocation().getPitch());

            for (PotionEffect potion : (((org.bukkit.entity.LivingEntity)entity).getActivePotionEffects())) {
                spCreature.addPotions(potion.getType().getName() + ":" + potion.getAmplifier());
            }

            if (entity.getType() == EntityType.SKELETON) {
                Skeleton skeleton = (org.bukkit.entity.Skeleton) entity;
                spCreature.setName(skeleton.getSkeletonType().toString().toLowerCase() + "_skeleton");
            } else {
                spCreature.setName(entity.getType().name());
            }
        } else if(entity instanceof Item){
            return new LoggableItemEntity("item", entity.getName(), location);
        }
        return null;
    }

    public static LoggableInstrument getInstrument(Player p){
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
