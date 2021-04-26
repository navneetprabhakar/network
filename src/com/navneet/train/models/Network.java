package com.navneet.train.models;

import java.util.*;

/**
 * Station and Tracks Network
 * @author navneetprabhakar
 */
public class Network {
    private Integer numberOfStations;
    private List<String> stations;
    private Map<String, LinkedList<Tracks>> tracks;
    private List<Train> trains;
    private Map<String, Map<String, List<String>>> shortestPaths;
    private Map<String, Map<String, Integer>> minimumTimeTaken;

    /**
     * Network Constructor, based on number of stations in network. Initialises other variables
     */
    public Network(Integer numberOfStations){
        this.numberOfStations=numberOfStations;
        this.stations=new ArrayList<>();
        this.tracks =new HashMap<>();
        this.trains=new ArrayList<>();
        this.minimumTimeTaken =new HashMap<>();
        this.shortestPaths=new HashMap<>();
    }

    /**
     * Add Station in Network
     * @param station: Station name
     */
    public void addStation(String station){
        this.stations.add(station);
    }

    /**
     * Add Path in Network
     * @param source: Station name
     * @param destination: Station name
     * @param timeTaken to travel from source to destination
     */
    public void addPath(String name, String source, String destination, Integer timeTaken){
        // if destination is null means the path is looped at source itself
        if(destination.equals("null")){
            destination=source;
        }
        Tracks tracks =new Tracks(name, source,destination,timeTaken);
        LinkedList<Tracks> linkedList=new LinkedList<>();
        if(this.tracks.containsKey(source)){
            linkedList=this.tracks.get(source);
        }
        linkedList.addFirst(tracks);
        this.tracks.put(source, linkedList);

        // For Undirected Network path needs to be added in both direction
        linkedList=new LinkedList<>();
        tracks =new Tracks(name, destination, source, timeTaken);
        if(this.tracks.containsKey(destination)){
            linkedList=this.tracks.get(destination);
        }
        linkedList.addFirst(tracks);
        this.tracks.put(destination, linkedList);
    }

    /**
     * Add Train in network
     * @param name : Name of the train
     * @param station : Current Station
     * @param capacity : Total weight capacity
     */
    public void addTrain(String name, String station, Integer capacity){
        this.trains.add(new Train(name, station, capacity));
    }

    // Getters & Setters

    public Integer getNumberOfStations() {
        return numberOfStations;
    }

    public void setNumberOfStations(Integer numberOfStations) {
        this.numberOfStations = numberOfStations;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public Map<String, LinkedList<Tracks>> getTracks() {
        return tracks;
    }

    public void setTracks(Map<String, LinkedList<Tracks>> tracks) {
        this.tracks = tracks;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trains) {
        this.trains = trains;
    }

    public Map<String, Map<String, List<String>>> getShortestPaths() {
        return shortestPaths;
    }

    public void setShortestPaths(Map<String, Map<String, List<String>>> shortestPaths) {
        this.shortestPaths = shortestPaths;
    }

    public Map<String, Map<String, Integer>> getMinimumTimeTaken() {
        return minimumTimeTaken;
    }

    public void setMinimumTimeTaken(Map<String, Map<String, Integer>> minimumTimeTaken) {
        this.minimumTimeTaken = minimumTimeTaken;
    }
}
