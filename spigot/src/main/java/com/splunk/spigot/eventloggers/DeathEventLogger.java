package com.splunk.spigot.eventloggers;

import com.splunk.sharedmc.logger.AbstractEventLogger;
import com.splunk.sharedmc.logger.actions.DeathEventAction;
import com.splunk.sharedmc.logger.events.LoggableDeathEvent;
import com.splunk.sharedmc.logger.entities.LoggableInstrument;
import com.splunk.sharedmc.logger.entities.LoggableLivingEntity;
import com.splunk.sharedmc.logger.utilities.Point3d;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
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

        // Default to creature death. Will override if actual player death.
        logAndSend(getLoggableDeathEvent(DeathEventAction.CREATURE, event));
    }

    private LoggableDeathEvent getLoggableDeathEvent(DeathEventAction action, EntityDeathEvent event) {

        final org.bukkit.entity.LivingEntity victim = event.getEntity();
        final Location location = victim.getLocation();
        final World world = victim.getWorld();

        LoggableLivingEntity spVictim;

        Point3d victimLocation = new Point3d(location.getX(), location.getY(), location.getZ());

        LoggableDeathEvent deathEvent = new LoggableDeathEvent(world.getFullTime(), minecraft_server, world.getName(), action);

        if (event instanceof PlayerDeathEvent) {
            // TODO switch this to use the enum.
            deathEvent.setAction("player_death");

            spVictim = new LoggableLivingEntity("player", victim.getName(), victimLocation);

        } else {
            if (event.getEntityType() == EntityType.SKELETON) {
                Skeleton skeleton = (org.bukkit.entity.Skeleton) event.getEntity();

                spVictim = new LoggableLivingEntity("creature", skeleton.getSkeletonType() + "_SKELETON", victimLocation);


            } else {
                spVictim = new LoggableLivingEntity("creature", victim.getName(), victimLocation);

            }
        }

        spVictim.setYaw(location.getYaw());
        spVictim.setPitch(location.getPitch());

        for (PotionEffect potion : event.getEntity().getActivePotionEffects()) {
            spVictim.addPotions(potion.getType().getName() + ":" + potion.getAmplifier() );
        }

        spVictim.setCurrentHealth(victim.getHealth());
        spVictim.setMaxHealth(victim.getMaxHealth());

        deathEvent.setVictim(spVictim);

        if (event.getEntity().getKiller() != null) {

            Location loc = event.getEntity().getKiller().getLocation();

            Point3d killerLocation = new Point3d(loc.getX(), loc.getY(), loc.getZ());

            org.bukkit.entity.LivingEntity killer = event.getEntity().getKiller();
            LoggableLivingEntity spKiller = new LoggableLivingEntity("player", killer.getName(), killerLocation);

            spKiller.setYaw(event.getEntity().getKiller().getLocation().getYaw());
            spKiller.setPitch(event.getEntity().getKiller().getLocation().getPitch());

            for (PotionEffect potion : killer.getActivePotionEffects()) {
                spKiller.addPotions(potion.getType().getName() + ":" + potion.getAmplifier());
            }

            spKiller.setCurrentHealth(killer.getHealth());
            spKiller.setMaxHealth(killer.getMaxHealth());
            deathEvent.setKiller(spKiller);

            ItemStack instrument = event.getEntity().getKiller().getInventory().getItemInMainHand();

            // Some items have "_ITEM" appended to the end.
            String instrumentName = instrument.getType().toString().replaceAll("_ITEM$","");

            LoggableInstrument tool = new LoggableInstrument(instrumentName);
            for (Enchantment key : instrument.getEnchantments().keySet()) {

                tool.addEnchantment(key.getName().toString() + ":" + instrument.getEnchantments().get(key));
            }

            tool.setName(instrument.getItemMeta().getDisplayName());

            deathEvent.setWeapon(tool);

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
