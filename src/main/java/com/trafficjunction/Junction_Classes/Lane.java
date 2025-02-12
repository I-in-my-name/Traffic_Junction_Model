package com.trafficjunction;
import java.util.*;
//import java.util.PriorityQueue;
//import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

class Pair<K,V> {
    K left;
    V right;
    
    public Pair(K k, V v) {
        left = k;
        right = v;
    }

    public K getLeft() {
        return left;
    }

    public V getRight() {
        return right;
    }
}

public class Lane {
    private List<Lane> goesTo; // lanes that current lane leads to
    private List<Lane> comesFrom; // previous lanes connected to current lane
    private float length; // length of lane
    
    //private PriorityQueue<Float, Vehicle> vehicles; // store vehicles & positions - this doesn't work
    // Need some kind of queue pair data structure
    private List<Pair<Float, Vehicle>> vehicles;

    private TrafficLight trafficLight; 
    private String direction; // N, S, E, W --> can formalise later
    private boolean busLane; // if bus lane

    public void Lane (float length, TrafficLight trafficLight, String direction) {
		this.goesTo = new ArrayList<>();
        this.comesFrom = new ArrayList<>();
        
        this.length = length;
        this.trafficLight = trafficLight;
        this.direction = direction;
        this.busLane = false;

        vehicles = new ArrayList<>();
        
    }

    // Returns direction as a String
    public String getDirection() {
        return this.direction;
    }

    // Updates direction
    public void setDirection(String newDirection) {
        // if (newDirection != "F" || newDirection != "L") - potentially go 2 directions? so more than 1 char?
        this.direction = newDirection; // need to restrict param to N, S etc
    }

    // Updates if the lane is a bus lane
    public void setBusLane(boolean bool) {
        // if (newDirection != "F" || newDirection != "L") - potentially go 2 directions? so more than 1 char?
        this.busLane = bool; // need to restrict param to N, S etc
    }

    // Get lane lenght
    public float getLength() {
        return this.length;
    }

    // Update lane length
    public boolean setLength(float newLength) {
        if (newLength <= 0) {
            return false;
        }
        this.length = newLength;
        return true;
    }

    // Add a lane that connects to this lane
    public void addComingLane(Lane lane) {
        if (lane != null) {
            comesFrom.add(lane);
        }
    }

    // Removes a lane from the comesFrom list
    public void removeComingLane(Lane lane) {
        comesFrom.remove(lane);
    }

    // Return list of connected lanes
    public List<Lane> getComesFrom() {
        return new ArrayList<>(comesFrom); // returns copy to prevent modification
    }

    /* Need? :
    - method that returns directions of all incoming lanes
    */ 

   public void addGoingLane(Lane lane) {
        goesTo.remove(lane);
   }

   public void removeGoinglane(Lane lane) {
        goesTo.add(lane);
   }

   public List<Lane> getGoingTo() {
        return new ArrayList<>(goesTo); // return a copy to avoid modification
   }

   public int getVehicleNum(int num) {
        return vehicles.size();
   }
   
    // add vehicle needs to check if lane full

    public boolean isFull() {
        // check if vehicle at back of queue --> indicates it is full

        int index = vehicles.size(); // get the number of vehicles in the lane
        if (index == 0) {
            return false; // If no vehicles, lane is not full
        }
        index--;    // Index of backmost vehicle is #vehicles - 1

        float vehicle_position  = vehicles.get(index).getLeft();                // Gets the backmost vehicle's position
        float vehicle_length    = vehicles.get(index).getRight().getLength();   // Gets the backmost vehicle's length

        // Check if the backmost vehicle is not blocking the back of the lane
        return this.length >= (vehicle_position + vehicle_length);
    }
}