package com.navneet.train;

import com.navneet.train.models.Network;
import com.navneet.train.models.Orders;
import com.navneet.train.models.Train;
import com.navneet.train.service.OrderService;
import com.navneet.train.service.RouteService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Driver class
 * @author navneetprabhakar
 */
public class Main {

    public static void main(String[] args) throws Exception{

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the number of Stations in the network:");
        int numberOfStations=Integer.parseInt(reader.readLine());
        Network network=new Network(numberOfStations);
        while(numberOfStations>0){
            System.out.println("Enter station name:");
            String station=reader.readLine();
            if(network.getStations().contains(station)){
                System.out.println("Station name already taken. Please enter a different name.");
            }else{
                network.getStations().add(station);
                numberOfStations--;
            }
        }
        System.out.println("Enter the total number of routes:");
        int routes=Integer.parseInt(reader.readLine());
        for(int i=0;i<routes;i++){
            System.out.println("Enter route details separated by comma, e.g. E1,A,B,3: ");
            String[] details= reader.readLine().split(",");
            network.addPath(details[0], details[1],details[2],Integer.valueOf(details[3]));
        }
        System.out.println("Total number of packages:");
        int packages=Integer.parseInt(reader.readLine());
        List<Orders> orders=new ArrayList<>();
        for(int i=0;i<packages;i++){
            System.out.println("Enter package details separated by comma, e.g. P1,A,C,5: ");
            String[] details=reader.readLine().split(",");
            orders.add(new Orders(details[0], details[1], details[2], Integer.valueOf(details[3])));
        }
        System.out.println("Enter the total number of trains:");
        int trains=Integer.parseInt(reader.readLine());
        for(int i=0;i<trains;i++){
            System.out.println("Enter train details separated by comma, e.g. Q1,B,6: ");
            String[] details=reader.readLine().split(",");
            network.addTrain(details[0],details[1], Integer.valueOf(details[2]));
        }
        RouteService.generateAllPaths(network);
        List<Orders> pending=new ArrayList<>();
        OrderService orderService=new OrderService();
        try {
            while (!orders.isEmpty()) {
                System.out.println("Orders present in queue.");
                for (Orders order : orders) {
                    if(!orderService.placeOrder(order, network)){
                        pending.add(order);
                    }
                }
                orders=new ArrayList<>(pending);
                pending=new ArrayList<>();
                for (Train train:network.getTrains()){
                    orderService.deliverOrder(train, network);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
