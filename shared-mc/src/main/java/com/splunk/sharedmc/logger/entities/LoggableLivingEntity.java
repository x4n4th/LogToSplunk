package com.splunk.sharedmc.logger.entities;

import com.splunk.sharedmc.logger.utilities.Point3d;

import java.util.ArrayList;
import java.util.List;

public class LoggableLivingEntity extends LoggableEntity {

    private List potions;
    private double currentHealth;
    private double maxHealth;

    private double yaw;
    private double pitch;

    public LoggableLivingEntity(String type, String name, Point3d location, List potions, double currentHealth, double maxHealth) {
        super(type, name, location);
        this.potions = potions;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

    public LoggableLivingEntity(String type, String name) {
        super(type, name.replaceAll("§\\S", "")); // Remove the formatting codes;
    }

    public LoggableLivingEntity(String type, String name, Point3d location) {
        super(type, name.replaceAll("§\\S", ""), location);
    }

    public LoggableLivingEntity() {
    }

    public List getPotions() {
        return potions;
    }

    public void setPotions(List potions) {
        this.potions = potions;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(double currentHealth) {
        this.currentHealth = Math.round(currentHealth * 100.00) / 100.00;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = Math.round(maxHealth * 100.00) / 100.00;
    }

    public void addPotions(String item) {
        if(potions == null){
            this.potions = new ArrayList();
        }

        potions.add(item);
    }

    public void setYaw(float yaw) {
        this.yaw = Math.round(yaw * 100.00) / 100.00;;
    }

    public void setPitch(float pitch) {
        this.pitch = Math.round(pitch * 100.00) / 100.00;
    }

}
