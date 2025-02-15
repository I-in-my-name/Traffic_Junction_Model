package com.trafficjunction.Junction_Classes;
import java.util.ArrayList;
import java.util.List;

public class Lane {
    private List<Lane> goesTo; // lanes that current lane leads to
    private List<Lane> comesFrom; // previous lanes connected to current lane
    private float length; // length of lane
    
    private List<Pair<Float, Vehicle>> vehicles;

    private TrafficLight trafficLight; 
    private String direction; // N, S, E, W --> can formalise later
    private boolean busLane; // if bus lane

    public Lane(float length, TrafficLight trafficLight, String direction) {
		this.goesTo = new ArrayList<>();
        this.comesFrom = new ArrayList<>();
        
        this.length = length;
        this.trafficLight = trafficLight;
        this.direction = direction;
        this.busLane = false;

        vehicles = new ArrayList<>();
    }

    // Doesn't need test
    // Returns direction as a String
    public String getDirection() {
        return this.direction;
    }

    // Written test
    // Updates direction
    public void setDirection(String newDirection) {
        // if (newDirection != "f" || newDirection != "l") - potentially go 2 directions? so more than 1 char?
        this.direction = newDirection; // need to restrict param to N, S etc
    }

    // Doesn't need test
    // Updates if the lane is a bus lane
    public void setBusLane(boolean isBusLane) {
        this.busLane = isBusLane; // need to restrict param to N, S etc
    }

    // Doesn't need test
    // Get lane lenght
    public float getLength() {
        return this.length;
    }

    // Written test
    // Update lane length
    public boolean setLength(float newLength) {
        if (newLength <= 0) {
            return false;
        }
        this.length = newLength;
        return true;
    }

    // Doesn't need test
    // Add a lane that connects to this lane
    public void addComingLane(Lane lane) {
        if (lane != null) {
            comesFrom.add(lane);
        }
    }

    // Doesn't need test
    // Removes a lane from the comesFrom list
    public void removeComingLane(Lane lane) {
        comesFrom.remove(lane);
    }

    // Doesn't need test
    // Return list of connected lanes
    public List<Lane> getComesFrom() {
        return comesFrom; 
    }
   
    // Doesn't need test
    public TrafficLight getTrafficLight() {
        // return the traffic light for a given lane (not the traffic light's state)
        return this.trafficLight;
    }

    // Doesn't need test
    public void setTrafficLight(TrafficLight tl) {
        this.trafficLight = tl;
    }

    // Doesn't need test
    public void addGoingLane(Lane lane) {
        goesTo.add(lane);
    }

    // Doesn't need test
    public void removeGoinglane(Lane lane) {
        goesTo.remove(lane);
    }

    // Doesn't need test
    public List<Lane> getGoingTo() {
        return goesTo; 
    }

    // Written test
    public boolean removeVehicle() {
        if (vehicles.isEmpty()) {
            return false; // Lane is empty, nothing to remove
        }

        vehicles.remove(0); // Remove the first vehicle in the list (front of the lane)
        return true; // Successfully removed
    }

    // Written test
    public boolean addVehicle(Vehicle vehicle) {
        if (isFull()) {
            return false;
        }

        float newPosition;

        if (vehicles.isEmpty()) {
            newPosition = 0; // First vehicle starts at position 0
        } else {
            Pair<Float, Vehicle> lastVehicle = vehicles.get(vehicles.size() - 1); // Get last vehicle
            float lastPosition = lastVehicle.getLeft();
            float lastVehicleLength = lastVehicle.getRight().getLength(); // uses getLength() from vehicle class 

            newPosition = lastPosition + lastVehicleLength; // Position it right after the last one
        }

        vehicles.add(new Pair<>(newPosition, vehicle)); // Add to the back of the list
        return true;
    }

    // Doesn't need test
    public int getVehicleNum() {
        return vehicles.size();
    }

    // Doesn't need test
    public List<Pair<Float, Vehicle>> getVehicles() {
        return this.vehicles;
    }

    // Written test
    public boolean isFull() {
        if (vehicles.isEmpty()) {
            return false;
        }

        Pair<Float, Vehicle> lastVehicle = vehicles.get(vehicles.size() - 1);
        float backmostVehiclePos = lastVehicle.getLeft(); // get pos

        return backmostVehiclePos >= (length - 2);
    }

}