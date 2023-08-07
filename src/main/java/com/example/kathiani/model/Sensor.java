package com.example.kathiani.model;

public class Sensor {
    private String name;
    private double value;

    public Sensor(String name, double value) {
        this.name = name;
        this.value = value;
    }

    // Getters and setters (optional)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
