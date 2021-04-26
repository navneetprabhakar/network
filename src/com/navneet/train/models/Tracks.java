package com.navneet.train.models;

/**
 * Tracks between Stations i.e. Edge between Vertices
 * @author navneetprabhakar
 */
public class Tracks {
    private String name;
    private String source;
    private String destination;
    private Integer timeTaken;

    /**
     * All Arguments Constructor for Tracks
     */
    public Tracks(String name, String source, String destination, Integer timeTaken){
        this.name=name;
        this.source=source;
        this.destination=destination;
        this.timeTaken=timeTaken;
    }

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Integer timeTaken) {
        this.timeTaken = timeTaken;
    }
}
