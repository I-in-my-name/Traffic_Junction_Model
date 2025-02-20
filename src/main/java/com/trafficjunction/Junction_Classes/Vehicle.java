package com.trafficjunction.Junction_Classes;

import java.util.List;

public class Vehicle {

    private float speed;
    private float max_speed;
    private float length;
    // May change to queue
    private List<Lane> desired_route;

    private VehicleMetrics metrics;
    private float creation_time;
    private float current_time;

    // Should vehicle be abstract class only defined by Car and Bus classes?
    public Vehicle(float time, float max_speed, float length) {
        this.speed = max_speed;
        this.max_speed = max_speed;
        this.length = length;
        this.metrics = new VehicleMetrics(time);
        
        this.creation_time = time;
        this.current_time = time;
    }

/// Methods for VehicleMetrics:

    public float getWaitTime() {
        return metrics.getTotalWaitTime();
    }

    public VehicleMetrics getMetrics() {
        return metrics;
    }

///

    // Don't need test for
    public Lane popRoute() {
        return null;
    }
    
    // Don't need test for
    public float getLength() {
        return length;
    }

    /**
     * TODO: remove this if not needed
     * 
     * Updates the vehicle's movement based on lane conditions.
     * If the traffic light is red (state = 0), the vehicle stops.
     * If the light is green (state = 1), the vehicle moves.
     * Time here is an objective timestamp and is not relative.
     */
    //public void updateMovement(float time, Lane lane) {
    //    TrafficLight trafficLight = lane.getTrafficLight();
        
    //    if (trafficLight.getState() == 0 || lane.isFull()) {  // 0 = Red
    //        if (this.speed != 0) {
    //            metrics.stopMoving(time);  // Track wait time
    //        }
    //    } else if (trafficLight.getState() == 1) {  // 1 = Green
    //        if (this.speed == 0) {
    //            metrics.startMoving(time);  // Resume movement
    //        }
    //        lane.removeVehicle();
    //        List<Lane> lanes = lane.getGoingTo();
    //        if (lanes == null) {
    //            // TODO: delete self, pass metrics to JunctionMetrics somehow.
    //        } else {
    //            lanes.get(0).addVehicle(this);
    //        }
    //    }
    //}

///

    // TODO: finish test for
    /**
     * Method to update a vehicle's position
     * 
     * 
     * Parameters - (time) the time since simulation started, (lane) the lane that the vehicle is currently in
     */
    public void update(float time, Lane lane) {
        List<Pair<Float,Vehicle>> vehicles = lane.getVehicles();
        int index = 0;
        for (Pair<Float,Vehicle> pos_vehicle : vehicles) {
            if (pos_vehicle.getRight() == this) {
                break;
            }
            index++;
        }
        this.update(time, lane, index);
    }
    // Overload method so we can get the index without searching for the vehicle in the lane
    public void update(float new_time, Lane lane, int index) {
        // Get needed variables:
        float time_difference = new_time - this.current_time;			// Gets the time increment increase
        if (time_difference < 0.f || index >= lane.getVehicles().size() || index < 0) { // If index is out of bounds, or no update is needed
            return;
        }
        Pair<Float,Vehicle> this_pair = lane.getVehicles().get(index);	// Gets the pair which stores this vehicle
        float position = this_pair.getLeft();	// Gets the vehicle's current position

        float traversable_position;		// Get the possible distance our vehicle can/should move forward
        if (index == 0) {
            traversable_position = 0;
        } else {
            Pair<Float,Vehicle> pos_vehicle = lane.getVehicles().get(index-1);  // Gets the entry of the next vehicle
            float next_vehicle_position = pos_vehicle.getLeft();                // Gets the next vehicle position on the lane
            float vehicle_length = pos_vehicle.getRight().getLength();          // Gets the next vehicle's length
            traversable_position = next_vehicle_position + vehicle_length + 1.f;
        }

        if (position < traversable_position) { // If the vehicle can move
            if (this.speed != 0) {
                this.speed = max_speed;
                // TODO: save time to vehicle metrics
            }
            // Travel possible distance:
            float max_traversable_distance = calculateDistanceFromTime(time_difference);
            float traversable_distance = traversable_position - position;
            float distance = Math.min(traversable_distance, max_traversable_distance);
            this_pair.setLeft(distance);
            // Calculate time taken:
            float time_taken = calculateTimeFromDistance(distance);
            this.current_time += time_taken;
            
            // Run function again in case reaching the end of the lane; (current_time) is updated, so it won't run infinitely:
            this.update(new_time, lane, index);

        } else { // The vehicle can not move OR is at 0 and waiting for trafficlights
            boolean can_proceed = lane.canPass();
            if (index == 0 && position == 0.f && can_proceed) { // If we are able to go to the next lane
                // TODO: Remove vehicle from (lane)
                // TODO: Remember to shift list up if needed
                Lane next_lane = this.popRoute();
                if (next_lane != null && lane.getGoingTo().contains(next_lane)) {
                    next_lane.addVehicle(this);
                    this.update(new_time, next_lane, index);
                } else if (lane.getGoingTo().size() != 0) {
                    // Go to a random available lane
                } else {
                    // Vehicle has reached the end of the route
                    // Give vehicle metrics to junction somehow (TODO: Discuss Start and End 'nodes'?)
                }
            } else if (this.speed != 0) {
                this.speed = 0;
                // TODO: save time to vehicle metrics
            }
        }
    }

    // Methods to go from time (s) to distance (m) and vice versa, dependant on speed/max speed of vehicle
    public float calculateTimeFromDistance(float distance_to_travel) {
        float time_taken = distance_to_travel / this.max_speed;
        return time_taken;
    }
    public float calculateDistanceFromTime(float time_to_travel) {
        float distance = 0;
        return distance;
    }

    // method: get vehicle position: used in lane class for deleting vehicles. 
}
