package com.splunk.spigot;

import com.splunk.spigot.eventloggers.*;
import com.splunk.spigot.utilities.MCItemCatalogue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Properties;
import java.util.stream.Collectors;

public class LogToSplunkPlugin extends JavaPlugin implements Listener {
    private static final Logger logger = LogManager.getLogger(LogToSplunkPlugin.class.getName());
    private String itemsFileContents = "";
    private Properties properties;

    private File itemsConfig;
    private File propertiesConfig;

    /**
     * Called when the mod is initialized.
     */
    @Override
    public void onEnable() {
        // Could probably move this to the AbstractEventLogger in shared
        this.saveDefaultItemsConfig("items.json");
        this.saveDefaultPropertiesConfig("LogToSplunk.properties");


        properties = new Properties();

        try (final FileReader reader = new FileReader(propertiesConfig)) {
            properties.load(reader);
        } catch (final Exception e) {
            logger.warn("Unable to load properties for LogToSplunk! Default values will be used.");
        }

        // Read items file
        try {

            FileInputStream inputStream;

            inputStream = new FileInputStream(itemsConfig);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            itemsFileContents = reader.lines().collect(Collectors.joining("\n"));

            reader.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        if(Boolean.valueOf(properties.getProperty("splunk.craft.plugin.configured", "false"))){
            MCItemCatalogue MCItems = MCItemCatalogue.getInstance();
            MCItems.parseItemsFile(itemsFileContents);

            if (Boolean.valueOf(properties.getProperty("splunk.craft.logging.block.enable", "true")))
                getServer().getPluginManager().registerEvents(new BlockEventLogger(properties), this);

            if (Boolean.valueOf(properties.getProperty("splunk.craft.logging.player.enable", "true")))
                getServer().getPluginManager().registerEvents(new PlayerEventLogger(properties), this);

            if (Boolean.valueOf(properties.getProperty("splunk.craft.logging.creature.enable", "true")))
                getServer().getPluginManager().registerEvents(new CreatureEventLogger(properties), this);

            if (Boolean.valueOf(properties.getProperty("splunk.craft.logging.death.enable", "true")))
                getServer().getPluginManager().registerEvents(new DeathEventLogger(properties), this);

            if (Boolean.valueOf(properties.getProperty("splunk.craft.logging.world.enable", "false")))
                getServer().getPluginManager().registerEvents(new WorldEventLogger(properties), this);

            if (Boolean.valueOf(properties.getProperty("splunk.craft.logging.damage.enable", "true")))
                getServer().getPluginManager().registerEvents(new DamageEventLogger(properties), this);
        } else {
            logger.error("Please configure LogToSplunk.properties and set 'splunk.craft.logging.configured' to 'true'");
        }




    }

    /**
     * Logs and sends messages to be prepared for Splunk.
     *
     * @param message The message to log.
     */
    private void logAndSend(String message) {
        logger.info(message);
    }

    public void saveDefaultItemsConfig(String file) {
        if (itemsConfig == null) {
            itemsConfig = new File(getDataFolder(), file);
        }
        if (!itemsConfig.exists()) {
            this.saveResource(file, false);
        }
    }

    public void saveDefaultPropertiesConfig(String file) {
        if (propertiesConfig == null) {
            propertiesConfig = new File(getDataFolder(), file);
        }
        if (!propertiesConfig.exists()) {
            this.saveResource(file, false);
        }
    }
}