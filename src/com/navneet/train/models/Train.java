package com.navneet.train.models;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Trains to pick up and deliver orders
 * @author navneetprabhakar
 */
public class Train {
    private String name;
    private String station;
    private Integer capacity;
    private Integer occupied;
    private boolean isMoving;
    private Queue<Orders> packages;

    /**
     * Train Constructor with train name, origin station and weight capacity
     */
    public Train(String name, String station, Integer capacity){
        this.name=name;
        this.station=station;
        this.capacity=capacity;
        this.occupied=0;
        this.packages=new LinkedList<>();
    }

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getOccupied() {
        return occupied;
    }

    public void setOccupied(Integer occupied) {
        this.occupied = occupied;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Queue<Orders> getPackages() {
        return packages;
    }

    public void setPackages(Queue<Orders> packages) {
        this.packages = packages;
    }
}
