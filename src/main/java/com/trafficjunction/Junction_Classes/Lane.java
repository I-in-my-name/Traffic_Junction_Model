package com.trafficjunction.Junction_Classes;
import java.util.ArrayList;
import java.util.List;

public class Lane {
    private List<Lane> goes_to; // lanes that current lane leads to
    private List<Lane> comes_from; // previous lanes connected to current lane
    private float length; // length of lane
    
    private List<Pair<Float, Vehicle>> vehicles;

    private TrafficLight traffic_light; 
    private String direction; // N, S, E, W --> can formalise later
    private boolean bus_lane; // if bus lane

    private boolean removedVehicle = false;

    private LaneMetrics metrics;

    private int vehicleTotalNum = 0;

    public Lane(float length, TrafficLight traffic_light, String direction) {
		this.goes_to = new ArrayList<>();
        this.comes_from = new ArrayList<>();
        
        this.length = length;
        this.traffic_light = traffic_light;
        this.direction = direction;
        this.bus_lane = false;

        vehicles = new ArrayList<>();

        metrics = new LaneMetrics();
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
        if (newDirection.length() == 0 || newDirection.length() > 3) return false;

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
    public void setBusLane(boolean isbus_lane) {
        this.bus_lane = isbus_lane; // need to restrict param to N, S etc
    }

    public boolean isBusLane() {
        return bus_lane;
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
            comes_from.add(lane);
        }
    }

    // Doesn't need test
    // Removes a lane from the comes_from list
    public void removeComingLane(Lane lane) {
        comes_from.remove(lane);
    }

    // Doesn't need test
    // Return list of connected lanes
    public List<Lane> getComesFrom() {
        return comes_from; 
    }
   
    // Doesn't need test
    public TrafficLight getTrafficLight() {
        // return the traffic light for a given lane (not the traffic light's state)
        return this.traffic_light;
    }

    /**
     * Return if the lane can be passed to the next one
     */
    public boolean canPass() {
        return (this.traffic_light == null || this.traffic_light.getState() == 1);
    }

    // Doesn't need test
    public void setTrafficLight(TrafficLight tl) {
        this.traffic_light = tl;
    }

    // Doesn't need test
    public void addGoingLane(Lane lane) {
        goes_to.add(lane);
    }

    // Doesn't need test
    public void removeGoinglane(Lane lane) {
        goes_to.remove(lane);
    }

    // Doesn't need test
    public List<Lane> getGoingTo() {
        return goes_to; 
    }

    // Written test
    // TODO: need to connect this to the Simulation and incorporate some global time manager and have a finalising total wait time method here
    public boolean removeVehicle() {
        if (vehicles.isEmpty()) {
            return false; // Lane is empty, nothing to remove
        }
        removedVehicle = true;
        vehicles.remove(0); // Remove the first vehicle in the list (front of the lane)
        return true; // Successfully removed
    }

    // Written test
    public boolean addVehicle(Vehicle vehicle) {
        if (isFull()) {
            return false;
        }

        vehicleTotalNum++;
        final float gap = 1.f; // fixed spacing between vehicles
        float newPosition;
        if (vehicles.isEmpty()) {
            // For an empty lane, place the vehicle at the back of the lane.
            newPosition = this.length;
        } else {
            // Get the last (back-most) vehicle and compute the new vehicle's starting position.
            Pair<Float, Vehicle> lastPair = vehicles.get(vehicles.size() - 1);
            float lastPosition = lastPair.getLeft();
            float lastVehicleLength = lastPair.getRight().getLength();
            newPosition = lastPosition + lastVehicleLength + gap;
        }
        // Safety check: newPosition should not exceed lane length.
        if (newPosition > this.length) {
            return false;
        }
        
        vehicles.add(new Pair<>(newPosition, vehicle));
        metrics.addVehicleMetric(vehicle.getMetrics());
        return true;

        /*
        vehicleTotalNum += 1;
        // Instead of adding vehicles at position length,
        // add them to position of last vehicle + constant 
        //vehicles.add(new Pair<>(length, vehicle)); // Add to the back of the lane
        if (vehicles.isEmpty()) {
            vehicles.add(new Pair<>(0.f, vehicle));
            metrics.addVehicleMetric(vehicle.getMetrics());
            return true;
        }
        float maxPosition = 0.f;
        for (Pair<Float, Vehicle> vehiclePairs : vehicles) {
            float position = vehiclePairs.getLeft();
            if (position > maxPosition)
                maxPosition = position;
        }
        float constantSpacing = 1.f; // TODO: What is distance between cars?
        float newPosition = maxPosition + constantSpacing;
        vehicles.add(new Pair<>(newPosition, vehicle)); // Add to back of lane's list of cars
        metrics.addVehicleMetric(vehicle.getMetrics());
        return true;
        */
    }

    // Number of total unique vehicles that have gone through this lane
    public int getTotalVehicleNum() {
        return vehicleTotalNum;
    }

    // Doesn't need test
    public int getVehicleNum() {
        return vehicles.size();
    }

    // Doesn't need test
    public List<Pair<Float, Vehicle>> getVehicles() {
        return this.vehicles;
    }

    // Written test TODO: Fix tests

    // Rewrite of isFull(): 04/03/25
    public boolean isFull() {
        if (vehicles.isEmpty()) {
            return false;
        }
        // Define the fixed gap between vehicles.
        final float gap = 1.f;
        Pair<Float, Vehicle> lastVehicle = vehicles.get(vehicles.size() - 1);
        float lastVehiclePos = lastVehicle.getLeft();
        float vehicleLength = lastVehicle.getRight().getLength();
        // There is enough space if the gap from the last vehicle to the end of the lane
        // is at least the length of a vehicle plus the fixed gap.
        return (length - lastVehiclePos) < (vehicleLength + gap); // check if the space left in the lane is less than the space required for adding a new vehicle
        /*
        With this change, if the lane is empty the check returns false. 
        If there’s at least one vehicle, a new vehicle can only be added if there is enough space 
        (i.e. the remaining space from the last vehicle’s current position to the end of the lane is at least one vehicle length plus the gap).
        */
        // Lane's should never be full?
        //Pair<Float, Vehicle> lastVehicle = vehicles.get(vehicles.size() - 1);
        //float backmostVehiclePos = lastVehicle.getLeft(); // get pos

        //return backmostVehiclePos >= (length - lastVehicle.getRight().getLength());
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
    //    TrafficLight traffic_light = this.getTrafficLight(); // Retrieve the lane's traffic light

    //    for (Pair<Float, Vehicle> pos_vehicle : vehicles) {  // Loop through all vehicles in the lane
    //        Vehicle vehicle = pos_vehicle.getRight(); // Extract the Vehicle object from the Pair

    //        // If the light is red (0) or the lane is blocked, stop the vehicle
    //        if (traffic_light.getState() == 0 || this.isFull()) {  
    //            vehicle.updateMovement(time, this);  // Stop the vehicle (tracked in VehicleMetrics)

    //        // If the light is green (1), allow movement
    //        } else if (traffic_light.getState() == 1) {  
    //            vehicle.updateMovement(time, this);  // Allow vehicle to move
    //        }
    //    }
    //}

    public float getMaxWaitTime() {
        return metrics.getMaxWaitTime();
    }

    public float getAverageWaitTime() {
        return metrics.getAverageWaitTime();
    }

    public int getMaxQueueLength() {
        return metrics.getMaxQueueLength();
    }

    public void calculateMetrics(float timestamp) {
        System.out.println("Metric size");
        System.out.println(metrics.getSize());
        System.out.println();
        metrics.calculateMetrics(timestamp);
    }


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
        int currentQueueLength = 0;

        int index = 0;
        for (Pair<Float,Vehicle> pos_vehicle : vehicles) {
            Vehicle vehicle = pos_vehicle.getRight();
            vehicle.update(time, this, index);  // Give an index
            index++;
            
            // vehicle is considered to be waiting, i.e. in a queue,
            // if its speed = 0. TODO: Does this make sense?
            if (vehicle.getSpeed() == 0) {
                currentQueueLength++;
            }

            // Prevents concurrent modification exception
            // As vehicles removed themselves during this loop,
            // the lane would continue to loop through all the vehicles
            // Causing concurrent modification exception
            // Now if a vehicle removes itself, the loop breaks early
            // TODO: Could restrucure code to prevent this?
            // e.g. have lane construct new list of vehicles that are still
            // in its lane after this timestep?
            if (removedVehicle) {
                removedVehicle = false;
                break;
            }
        }

        metrics.updateQueueSize(time, currentQueueLength);
    }

    // Useful for testing
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Lane (");
        if (this.traffic_light == null) {   // Lane has no traffic light
            builder.append("-");
        } else if (this.traffic_light.getState() == 0) {    // traffic light is red
            builder.append("R");
        } else {                            // Traffic light is green
            builder.append("G");
        }
        builder.append(") [");
        for (int i = 0; i < vehicles.size(); i++) {
            builder.append(vehicles.get(i).toString());
            if (i != vehicles.size() -1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}