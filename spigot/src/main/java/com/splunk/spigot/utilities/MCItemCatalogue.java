package com.splunk.spigot.utilities;

import com.google.gson.Gson;
import com.splunk.spigot.LogToSplunkPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.*;

import static org.bukkit.Bukkit.*;

public class MCItemCatalogue implements Iterable<MCItem> {

    private static MCItemCatalogue instance = null;
    private Map<String, MCItem> mcItems = new HashMap<>();

    private final Logger logger = LogManager.getLogger(LogToSplunkPlugin.class.getName());

    public static MCItemCatalogue getInstance() {
        if (instance == null) {
            instance = new MCItemCatalogue();
        }

        return instance;
    }

    public Map<String, MCItem>  getMCItems() {
        return mcItems;
    }

    public void setMCItems(Map<String, MCItem>  MCItems) {
        this.mcItems = MCItems;
    }

    public void parseItemsFile(String contents) {

        Gson gson = new Gson();

        // Using an array to populate the items because we have to do some modifications to the values.
        MCItem mcItems[] = gson.fromJson(contents, MCItem[].class);


        logger.info("[LogToSplunk] Loaded " + mcItems.length + " items");

        for (MCItem item : mcItems) {
            String key = item.getMaterial().toLowerCase() + item.getMeta();
            this.mcItems.put(key, item);
        }

    }

    public Iterator<MCItem> iterator() {
        return this.mcItems.values().iterator();
    }

    public String getBlockName(Block block) throws Exception{
        BlockState state =  block.getState();

        String material = state.getType().toString().toLowerCase();
        int meta = state.getData().toItemStack().getDurability();


        return getBlockName(material, meta);
    }

    public String getBlockName(String material, int meta) throws Exception{
        MCItem item = this.mcItems.get(material + meta);

        if(item == null){
            item = this.mcItems.get(material + 0);
        }

        if(item != null){
            return item.getName();
        }

        logger.warn("Could not find LoggableBlock in items.json " +
                "\nLoggableBlock: " + material.toString().toLowerCase() +
                "\nMeta: " + meta);

        throw new Exception("LoggableBlock not found");
    }
}
