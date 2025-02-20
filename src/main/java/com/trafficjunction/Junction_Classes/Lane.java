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
    public boolean setDirection(String newDirection) {
        //edge case guard
        if (newDirection.length() == 0) return false;

        // Check content/ Check for duplicates
        for (int i = 0; i < newDirection.length(); i++) {
            char character_i = newDirection.charAt(i); // Gets the i'th characthter in direction
            String character = Character.toString(character_i); // Converts the character to a string so the (.contains()) method can be used
            String allowed = "lfr";     // Allowed characters
            if (!allowed.contains(character)) {
                return false;
            } else {
                // Check if there is a duplicate character
                for (int j = i + 1; j < newDirection.length(); j++) {
                    char character_j = newDirection.charAt(j);
                    if (character_i == character_j) {
                        return false;
                    }
                }
            }
        }
        this.direction = newDirection;
        return true;
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
        //utterly arbitrary number for length
        if (newLength <= 0 || newLength >= 5000) {
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

    /**
     * Return if the lane can be passed to the next one
     */
    public boolean canPass() {
        return (this.trafficLight == null || this.trafficLight.getState() == 1);
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
    // TODO: need to connect this to the Simulation and incorporate some global time manager and have a finalising total wait time method here
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
        // check the lane length isn't violated
        if (newPosition + vehicle.getLength() > this.getLength()){
            return false;
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

        return backmostVehiclePos >= (length - lastVehicle.getRight().getLength());
    }

    /**
     * (This function is to be removed unless decided to be necessary)
     * 
     * Updates all vehicles in the lane based on the traffic light's state.
     * - If the traffic light is red (state = 0) or the lane is blocked, vehicles stop.
     * - If the traffic light is green (state = 1), vehicles resume movement.
     * - Vehicles interact with their `VehicleMetrics` to track wait time.
     *
     * @param time The current simulation time (in seconds).
     */
    //public void updateTraffic(float time) {
    //    TrafficLight trafficLight = this.getTrafficLight(); // Retrieve the lane's traffic light

    //    for (Pair<Float, Vehicle> pos_vehicle : vehicles) {  // Loop through all vehicles in the lane
    //        Vehicle vehicle = pos_vehicle.getRight(); // Extract the Vehicle object from the Pair

    //        // If the light is red (0) or the lane is blocked, stop the vehicle
    //        if (trafficLight.getState() == 0 || this.isFull()) {  
    //            vehicle.updateMovement(time, this);  // Stop the vehicle (tracked in VehicleMetrics)

    //        // If the light is green (1), allow movement
    //        } else if (trafficLight.getState() == 1) {  
    //            vehicle.updateMovement(time, this);  // Allow vehicle to move
    //        }
    //    }
    //}


    /**
     * Method to update the lane
     * It updates each vehicle starting from the end of the lane.
     * 
     * Parameters - (time) which is the time (sec) since the simulation started
     * @return 
     */
    public void update(float time) {
        // TODO: shift stored values in index? Will this cause problems? If so then does the vehicles list need to be cloned?
        // List has a clone method but not for when it stores custom objects.
        int index = 0;
        for (Pair<Float,Vehicle> pos_vehicle : vehicles) {
            Vehicle vehicle = pos_vehicle.getRight();
            vehicle.update(time, this, index);  // Give an index
            index++;
        }
    }

}