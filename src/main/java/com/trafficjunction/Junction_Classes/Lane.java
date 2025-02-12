package com.trafficjunction;
import java.util.*;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import Vehicle;
import TrafficLight;


class Lane {
    private List<Lane> goesTo; // lanes that current lane leads to
    private List<Lane> comesFrom; // previous lanes connected to current lane
    private float length; // length of lane
    private PriorityQueue<Float, Vehicle> vehicles; // store vehicles & positions
    private TrafficLight trafficLight; 
    private String direction; // N, S, E, W --> can formalise later
    private boolean busLane; // if bus lane

    public void Lane (float length, TrafficLight trafficLight, String direction, boolean busLane) {
		this.length = length;
        this.trafficLight = trafficLight;
        this.direction - direction;
        this.busLane = busLane;
        this.vehicles = new PriorityQueue<>();
        this.goesTo = new ArrayList<>();
        this.comesFrom = new ArrayList<>();
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

    // Get lane lenght
    public float getLength() {
        return this.length;
    }

    // Update lane length // VLAD 
    public void setLength(float newLength) {
        if (newLength <= 0) {
            throw new IllegalArqumentException("Lane length must be greater than zero.");
        }
        this.length = newLength;
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
        /// 
   }
   
    // add vehicle needs to check if lane full

    public boolean isFull() {
        // check if vehicle at back of queue --> indicates it is full 
        if (vehicles.isEmpty()) {
            return false; // If no vehicles, lane is not full
        }
        float laneCapacityThreshold = this.length - 2.0f; // Backmost vehicle should not be beyond this point

        // Get the vehicle with the highest position (furthest back in queue)
        float backmostVehiclePos = Collections.max(vehicles);

        // Check if the backmost vehicle is within 2m of the front (0m)
        return backmostVehiclePos >= laneCapacityThreshold;
    }
}