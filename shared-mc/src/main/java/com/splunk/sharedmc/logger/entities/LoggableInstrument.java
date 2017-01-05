package com.splunk.sharedmc.logger.entities;

import java.util.ArrayList;
import java.util.List;

public class LoggableInstrument {

    private String item;
    private String name;
    private List enchantments;

    public LoggableInstrument(String item) {
        this.enchantments = new ArrayList();
        this.item = item;
    }

    public void addEnchantment(String item) {
        this.enchantments.add(item);
    }

    public void setName(String name){
        this.name = name;
    }
}
