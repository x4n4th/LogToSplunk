package com.splunk.sharedmc.utilities;


import java.util.List;

public abstract class LoggerEntity {
    private String type;
    private String name;
    private Point3d location;

    public LoggerEntity(String type, String name, Point3d location){
        this.type = type;
        this.name = name;
        this.location = location;
    }

    public LoggerEntity(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public LoggerEntity() {
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
