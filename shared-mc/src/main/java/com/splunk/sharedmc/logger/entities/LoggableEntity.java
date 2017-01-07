package com.splunk.sharedmc.logger.entities;


import com.splunk.sharedmc.logger.utilities.Point3d;

public abstract class LoggableEntity {
    private String type;
    private String name;
    private Point3d location;

    public LoggableEntity(String type, String name, Point3d location){
        this.type = type;
        this.name = name;
        this.location = location;
    }

    public LoggableEntity(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public LoggableEntity() {
    }

    public Point3d getLocation() {
        return location;
    }

    public void setLocation(Point3d location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
