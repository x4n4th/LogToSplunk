package com.splunk.sharedmc.utilities;

import java.util.ArrayList;
import java.util.List;

public class Instrument {

    private String item;
    private String name;
    private List enchantments;

    public Instrument(String item) {
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
