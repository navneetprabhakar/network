package com.navneet.train.service;


import com.navneet.train.models.Network;
import com.navneet.train.models.Orders;
import com.navneet.train.models.Train;

import java.util.List;
import java.util.Map;

/**
 * @author navneetprabhakar
 * This class handles all Order related methods and services
 */
public class OrderService {

    /**
     * This method checks for available trains in network who can take the order
     * if not found returns false
     * @param order: @{@link Orders}
     * @param network: @{@link Network}
     * @return @{@link Boolean}
     */
    public Boolean placeOrder(Orders order, Network network) throws Exception{
        for(Train train: network.getTrains()){
            if(!train.isMoving() && train.getCapacity()-train.getOccupied()>=order.getWeight()){
                List<String> stations=network.getShortestPaths().get(train.getStation()).get(order.getCurrentStation());
                if(null!=stations && !stations.isEmpty()){
                    train.setMoving(true);// Train starts moving to pick the order
                    pickOrder(train, order, network);
                    return true;
                }else{
                    System.out.println("No route available for train:"+train.getName()+" from station:"+train.getStation()+" to station:"+order.getCurrentStation());
                }
            }
        }
        System.out.println("No Available train to handle the order"+order.getName()+". Reasons: no capacity/no route to order/all trains are moving.");
        return false;
    }

    /**
     * This method moves train from current location to Order pickup location and prints the path and train status
     * @param train:@{@link Train}
     * @param order:@{@link Orders}
     * @param network:@{@link Network}
     */
    public void pickOrder(Train train,Orders order, Network network) throws Exception{
        List<String> stations=network.getShortestPaths().get(train.getStation()).get(order.getCurrentStation());
        for(String station: stations){
            if(!station.equalsIgnoreCase(train.getStation())){
                moveTrainToStation(train, station, network.getMinimumTimeTaken().get(train.getStation()));
            }
        }
        train.getPackages().offer(order);// add to the package queue
        train.setOccupied(train.getOccupied()+order.getWeight());
        System.out.println("Order :"+order.getName()+" picked by Train:"+train.getName()+" at Station:"+order.getCurrentStation());
    }

    /**
     * This method moves train to target station
     * @param train: @{@link Train}
     * @param station: Destination Station
     * @param timeTaken: Shortest time taken for route map
     */
    public void moveTrainToStation(Train train, String station, Map<String, Integer> timeTaken) throws Exception{
        Thread.sleep(500); // To simulate train movement, can be removed
        StringBuilder packages=new StringBuilder();
        for(Orders order: train.getPackages()){
            packages.append(order.getName()).append(" ");
        }
        System.out.println("Train :"+train.getName()+" moving from:"+train.getStation()+" to:"+station+ " Packages:"+packages.toString()
                +" Total Weight:"+ train.getOccupied()+" Total Capacity:"+train.getCapacity()+ " Time Taken:"+timeTaken.get(station)+ " units");
        train.setStation(station);
    }

    /**
     * This method delivers order
     * @param train: @{@link Train}
     * @param network: @{@link Network}
     */
    public void deliverOrder(Train train, Network network) throws Exception{
        if(train.getPackages().isEmpty()){
            System.out.println("No packages in train:"+train.getName());
            return;
        }
        Orders order=train.getPackages().peek(); // Pick the first order in queue for execution
        List<String> stations=network.getShortestPaths().get(train.getStation()).get(order.getDestination());
        for(String station:stations){
            if(!station.equalsIgnoreCase(train.getStation())){
                moveTrainToStation(train, station, network.getMinimumTimeTaken().get(train.getStation()));
            }
        }
        train.getPackages().poll();
        train.setOccupied(train.getOccupied()-order.getWeight());
        System.out.println("Order :"+order.getName()+" delivered by Train:"+train.getName()+" at Station:"+train.getStation());
        if(train.getPackages().isEmpty()){
            System.out.println("All Orders delivered by Train:"+train.getName()+" , Setting train to idle at Station:"+train.getStation());
            train.setMoving(false);
        }
    }
}
