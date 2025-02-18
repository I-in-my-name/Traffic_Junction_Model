package com.trafficjunction.Junction_Classes;

import java.util.List;

public class Vehicle {

    private float speed;
    private float max_speed;
    private float length;
    // May change to queue
    private List<Lane> desired_route;

    private VehicleMetrics metrics;
    private float start_time;
    private float current_time;

    // Should vehicle be abstract class only defined by Car and Bus classes?
    public Vehicle(float time, float max_speed, float length) {
        this.speed = max_speed;
        this.max_speed = max_speed;
        this.length = length;
        this.metrics = new VehicleMetrics(time);
        
        this.start_time = time;
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
     * Updates the vehicle's movement based on lane conditions.
     * If the traffic light is red (state = 0), the vehicle stops.
     * If the light is green (state = 1), the vehicle moves.
     */
    public void updateMovement(float time, Lane lane) {
        TrafficLight trafficLight = lane.getTrafficLight();
        
        if (trafficLight.getState() == 0 || lane.isFull()) {  // 0 = Red
            if (this.speed != 0) {
                metrics.stopMoving(time);  // Track wait time
            }
        } else if (trafficLight.getState() == 1) {  // 1 = Green
            if (this.speed == 0) {
                metrics.startMoving(time);  // Resume movement
            }
            lane.removeVehicle();
            List<Lane> lanes = lane.getGoingTo();
            if (lanes == null) {
                // TODO: delete self, pass metrics to JunctionMetrics somehow.
            } else {
                lanes.get(0).addVehicle(this);
            }
        }
    }

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
    public void update(float time, Lane lane, int index) {
        // Attributes (will remove these after having finished implementing):
        //float speed;
        //float max_speed;
        //float length;
        float position = lane.getVehicles().get(index).getLeft();
        if (index == 0) {
            if (position == 0) {
                // Check trafficlight
            } else {
                // check if car can move to trafficlight
                // if so then do so, then move to the above??
                // if not then just move
            }
        } else {
            Pair<Float,Vehicle> pos_vehicle = lane.getVehicles().get(index-1);  // Gets the entry of the next vehicle
            float next_vehicle_position = pos_vehicle.getLeft();                // Gets the next vehicle position on the lane
            float vehicle_length = pos_vehicle.getRight().getLength();          // Gets the next vehicle's length
            float difference = next_vehicle_position + vehicle_length - position;   // Calculates how far away the vehicles are
            if (difference < 3) {
                // don't move
            } else {
                // check if it is further than the reachable distance
                // if so then just move
                // otherwise just move until the car is 2m behind the next one
            }
        }
    }

    // method: get vehicle position: used in lane class for deleting vehicles. 
}
