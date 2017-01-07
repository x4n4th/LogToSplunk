package com.splunk.sharedmc.logger.entities;


import com.splunk.sharedmc.logger.utilities.Point3d;

public class LoggableBlock {

    private String material;
    private String name;
    private Point3d location;

    private LoggableBiome biome;

    public LoggableBlock(String base_type, String item, Point3d location) {
        this.material = base_type;
        this.name = item;
        this.location = location;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point3d getLocation() {
        return location;
    }

    public void setLocation(Point3d location) {
        this.location = location;
    }

    public void setBiome(LoggableBiome biome) {
        this.biome = biome;
    }
}
