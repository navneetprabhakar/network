package com.navneet.train.service;

import com.navneet.train.models.Network;
import com.navneet.train.models.Tracks;
import javafx.util.Pair;

import java.util.*;

/**
 * @author navneetprabhakar
 */
public class RouteService {

    /**
     * This method generates all paths based on the inputs
     * @param parent: Map of stations with parent values to generate shortest path
     * @param timeTaken: Map of timeTaken for each station
     * @param sourceStation: source station for which shortest path is being calculated
     * @return : path sequence for each station from source station
     */
    public static Map<String, List<String>> generateAllPaths(Map<String, String> parent, Map<String, Integer> timeTaken, String sourceStation){
        Map<String, List<String>> paths=new HashMap<>();
        for(Map.Entry<String, Integer> entry:timeTaken.entrySet()){
            List<String> path= generatePathForDestination(parent, entry.getKey(), new ArrayList<>(), sourceStation);
            paths.put(entry.getKey(), path);
        }
        return paths;
    }

    /**
     * Generate shortest path to all nodes in network (Dijkstra Algorithm)
     * @param sourceVertex: Source station for which shortest path has to be generated for
     * @param vertices: Total number of stations
     * @param network: @{@link Network}
     */
    public static void generateShortestPaths(String sourceVertex, int vertices, Network network){
        Map<String, Boolean> mst=new HashMap<>(); // minimum spanning tree
        //distance used to store the distance of vertex from a source
        Map<String, Integer> timeTaken=new HashMap<>();
        Map<String, String> parent=new HashMap<>();
        parent.put(sourceVertex,"-1");

        //Initialize priority queue and override the comparator to do the sorting based keys
        PriorityQueue<Pair<Integer, String>> pq = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, String>>() {
            @Override
            public int compare(Pair<Integer, String> p1, Pair<Integer, String> p2) {
                //sort using timeTaken values
                return p1.getKey()-p2.getKey();
            }
        });
        //create the pair for for the source, timeTaken =0
        timeTaken.put(sourceVertex, 0);
        pq.offer(new Pair<>(timeTaken.get(sourceVertex),sourceVertex));

        //while priority queue is not empty
        while(!pq.isEmpty()){
            //extract the min
            Pair<Integer, String> extractedPair = pq.poll();

            //if extracted vertex is not visited
            if(!mst.containsKey(extractedPair.getValue()) || !mst.get(extractedPair.getValue())) {
                mst.put(extractedPair.getValue(), true);

                //iterate through all the adjacent vertices and update the keys
                LinkedList<Tracks> list = network.getTracks().get(extractedPair.getValue());
                for (int i = 0; i < list.size(); i++) {
                    Tracks edge = list.get(i);
                    String destination = edge.getDestination();
                    //only if edge destination is not present in mst
                    if (!mst.containsKey(destination) || !mst.get(destination)) {
                        ///check if timeTaken needs an update or not
                        // check total timeTaken from source to station is less than
                        //the current timeTaken value, if yes then update the timeTaken
                        Integer existingTime=timeTaken.containsKey(extractedPair.getValue())?timeTaken.get(extractedPair.getValue()):Integer.MAX_VALUE;
                        int newTimeTaken =  existingTime + edge.getTimeTaken() ;
                        int currentTimeTaken = timeTaken.containsKey(destination)?timeTaken.get(destination):Integer.MAX_VALUE;
                        if(currentTimeTaken>newTimeTaken){
                            Pair<Integer, String> p = new Pair<>(newTimeTaken, destination);
                            pq.offer(p);
                            timeTaken.put(destination, newTimeTaken);
                            parent.put(destination, extractedPair.getValue());
                        }
                    }
                }
            }
        }
        Map<String, List<String>> paths= generateAllPaths(parent, timeTaken, sourceVertex);
        network.getShortestPaths().put(sourceVertex, paths);
        network.getMinimumTimeTaken().put(sourceVertex, timeTaken);
    }

    /**
     * This method generates path for destination station from source station based on inputs recursively
     * @param parent: Map of stations with parent values to generate shortest path
     * @param destination: destination station to which shortest path is being calculated
     * @param path: path sequence for original destination till now
     * @param source: source station for which shortest path is being calculated
     * @return : path sequence for original destination station
     */
    private static List<String> generatePathForDestination(Map<String, String> parent, String destination, List<String> path, String source){
        //if vertex is source then stop recursion
        if(parent.get(destination).equals("-1")){
            path.add(source);
            return path;
        }
        generatePathForDestination(parent, parent.get(destination), path, source);
        path.add(destination);
        return path;
    }

    /**
     * This method generates shortest paths from each station to rest of stations in the network
     * @param network: @{@link Network}
     */
    public static void generateAllPaths(Network network){
        for(String station: network.getStations()){
            generateShortestPaths(station, network.getNumberOfStations(), network);
        }
    }

}
