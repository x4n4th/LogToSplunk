package com.splunk.spigot.utilities;

public class MCItem {

    private int meta;
    private String name;
    private String material;
    private int type;

    public MCItem(int meta, String name, String material, int type) {
        this.meta = meta;
        this.name = name;
        this.material = material;
        this.type = type;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
