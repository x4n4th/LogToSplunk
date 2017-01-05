package com.splunk.spigot.utilities;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.*;

public class MCItemCatalogue implements Iterable<MCItem> {

    private static MCItemCatalogue instance = null;
    private List<MCItem> MCItems = new ArrayList<>();

    public Logger log;

    public static MCItemCatalogue getInstance() {
        if (instance == null) {
            instance = new MCItemCatalogue();
            instance.log = Bukkit.getLogger();
        }

        return instance;
    }

    public List<MCItem> getMCItems() {
        return MCItems;
    }

    public void setMCItems(List<MCItem> MCItems) {
        this.MCItems = MCItems;
    }

    public void parseItemsFile(String contents) {

        Gson gson = new Gson();

        // Using an array to populate the items because we have to do some modifications to the values.
        MCItem mcItems[] = gson.fromJson(contents, MCItem[].class);


        log.info("Loaded " + mcItems.length + " items");

        for (MCItem item : mcItems) {
            log.info("Material: " + item.getMaterial());

            MCItems.add(item);
        }

    }

    public Iterator<MCItem> iterator() {
        return MCItems.iterator();
    }

    public String getBlockName(Block block) throws Exception{
        BlockState state =  block.getState();

        Material material = state.getType();
        int meta = state.getData().toItemStack().getDurability();

        for (MCItem item : this.getMCItems()) {

            // First match type <--> MATERIAL
            if (item.getMaterial().equalsIgnoreCase(material.toString())) {

                // Next match on meta number
                if (item.getMeta() == meta || item.getMeta() == -1) {
                    return item.getName();
                }
            }
        }

        log.warning("Could not find Block in items.json " +
                "\nBlock: " + material.toString().toLowerCase() +
                "\nMeta: " + meta);

        throw new Exception("Block not found");
    }
}
