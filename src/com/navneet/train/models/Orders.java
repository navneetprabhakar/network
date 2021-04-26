package com.navneet.train.models;

/**
 * Order details or Package details
 * @author navneetprabhakar
 */
public class Orders {
    private String name;
    private String currentStation;
    private String destination;
    private Integer weight;

    /**
     * All Arguments Constructor with Order name, pick up station, drop station and order weight
     */
    public Orders(String name, String currentStation, String destination, Integer weight){
        this.name=name;
        this.currentStation=currentStation;
        this.destination=destination;
        this.weight=weight;
    }

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
