package com.trafficjunction.Junction_Classes;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

public class Vehicle {

    private float speed;
    private float max_speed;
    private float length;
    // May change to queue
    private Queue<Lane> desired_route;

    private VehicleMetrics metrics;
    private float creation_time;
    private float current_time;

    // Should vehicle be abstract class only defined by Car and Bus classes?
    public Vehicle(float time, float max_speed, float length) { // Overload method in the case that vehicle route is not specified
        // making empty object instead of null
        this(time, max_speed, length, new ArrayList<Lane>());
    }
    public Vehicle(float time, float max_speed, float length, List<Lane> route) {
        
        this.speed = max_speed;
        this.max_speed = max_speed;
        this.length = length;
        this.metrics = new VehicleMetrics(time);
        
        this.creation_time = time;
        this.current_time = time;

        this.desired_route = new LinkedList<>(route);
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
        return this.desired_route.poll();   // pop method
    }
    
    // Don't need test for
    public float getLength() {
        return length;
    }

    // method to set the vehicles route
    public void setRoute(List<Lane> route) {
        this.desired_route = new LinkedList<>(route);
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
    public String update(float new_time, Lane lane, int index) {
        // Get needed variables:
        float time_difference = new_time - this.current_time;			// Gets the time increment increase
        if (time_difference <= 0.f || index >= lane.getVehicles().size() || index < 0) { // If index is out of bounds, or no update is needed
            return "no update";
        }
        Pair<Float,Vehicle> this_pair = lane.getVehicles().get(index);	// Gets the pair which stores this vehicle
        float position = this_pair.getLeft();	// Gets the vehicle's current position

        // If index == 0, traversable position = 0
        // TODO / QUESTION: Is traversable position the length possible ot move
        // or the new position that it could get to in theory if it kept moving?
        // If the latter, then 0 makes sense
        // otherwise it doesn't
        // Code looks like it means the first though
        float traversable_position;		// Get the possible distance our vehicle can/should move forward
        if (index == 0) {
            traversable_position = 0;
        } else {
            Pair<Float,Vehicle> pos_vehicle = lane.getVehicles().get(index-1);  // Gets the entry of the next vehicle
            float next_vehicle_position = pos_vehicle.getLeft();                // Gets the next vehicle position on the lane
            float vehicle_length = pos_vehicle.getRight().getLength();          // Gets the next vehicle's length
            traversable_position = next_vehicle_position + vehicle_length + 1.f;
        }

        // position > traversable_position means can move
        // e.g. position = 0 means start. position = 50 means 50 away from start. 
        // traversable position = 20 means should be able to move 30 forward
        // TODO: this is right, right? Changed it when fixing bugs to run simulation
        if (position > traversable_position) { // If the vehicle can move
            if (this.speed != 0) {
                this.speed = max_speed;
                metrics.startMoving(this.current_time); // Saves time to vehicle metrics
            }
            // Travel possible distance:
            float max_traversable_distance = calculateDistanceFromTime(time_difference);
            //float traversable_distance = traversable_position - position;
            // changed this from above to below as position > traversable position,
            // part of change described above
            float traversable_distance = position - traversable_position;
            float distance = Math.min(traversable_distance, max_traversable_distance);
            // position - distance is new position
            // closer to 0 is closer to end of lane so position gets smaller as distance travelled grows
            this_pair.setLeft(position - distance);
            // Calculate time taken:
            float time_taken = calculateTimeFromDistance(distance);
            this.current_time += time_taken;
            
            // Run function again in case reaching the end of the lane; (current_time) is updated, so it won't run infinitely:
            if (new_time > current_time) {
                return "another update, "+ this.update(new_time, lane, index);
            }

        } else { // The vehicle can not move OR is at 0 and waiting for TrafficLights
            boolean can_proceed = lane.canPass();
            if (index == 0 && position == 0.f && can_proceed) { // If we are able to go to the next lane
                // TODO: Remember to shift list up if needed
                Lane next_lane = this.popRoute();
                if (next_lane != null && lane.getGoingTo().contains(next_lane)) {
                    lane.removeVehicle();                           // Remove vehicle from current lane
                    next_lane.addVehicle(this);
                    this.update(new_time, next_lane, next_lane.getVehicleNum()-1);
                } else if (!lane.getGoingTo().isEmpty()) {  // If there is one or more possible lanes for the vehicle to go in
                    List<Lane> next_lanes = lane.getGoingTo();
                    // Go to the lane with the least number of vehicles:
                    int check_index = 1;
                    int lowest_index = 0;
                    int least_v = next_lanes.get(0).getVehicleNum();    // Initially gets the number of vehicles in the first possible lane
                    boolean available = !next_lanes.get(0).isFull();
                    while (check_index != next_lanes.size()) {
                        boolean canGoTo = !next_lanes.get(check_index).isFull();
                        int new_v = next_lanes.get(check_index).getVehicleNum();    // Get number of vehicles in the first lane
                        if (least_v > new_v && canGoTo) {       // Compares num of vehicles between current lowest and another lane's
                            lowest_index = check_index;
                            least_v = new_v;
                            available = true;
                        }
                        check_index++;
                    }
                    Lane desiredLane = next_lanes.get(lowest_index);    // Gets the lane
                    if (available) {  // If a lane is available
                        lane.removeVehicle();                           // Remove vehicle from current lane
                        desiredLane.addVehicle(this);                   // Adds vehicle
                        return "add to another lane, " + this.update(new_time, desiredLane, desiredLane.getVehicles().size()-1);
                    } else {
                        return "unsuccesful in adding to lane";
                    }
                } else {
                    lane.removeVehicle();   // Remove vehicle from current lane
                    // Vehicle has reached the end of the route
                    // Give vehicle metrics to junction somehow (TODO: Discuss Start and End 'nodes'?)
                }
            } else if (this.speed != 0) {   // If the vehicle has speed meaning it has stopped when the method executed this time
                this.speed = 0;
                metrics.stopMoving(this.current_time); // Saves time to vehicle metrics
            }
        }
        return "?";
    }

    // Methods to go from time (s) to distance (m) and vice versa, dependant on speed/max speed of vehicle
    public float calculateTimeFromDistance(float distance_to_travel) {
        float time_taken = distance_to_travel / (max_speed / 3.6f);
        return time_taken;
    }
    public float calculateDistanceFromTime(float time_to_travel) {
        if (time_to_travel==0) {
            return 0;
        }
        float distance = (max_speed / 3.6f) * time_to_travel;
        return distance;
    }

    public float getSpeed() {
        return speed;
    }

    // method: get vehicle position: used in lane class for deleting vehicles. 
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Vehicle [");
        builder.append(Float.toString(speed));
        builder.append(", ");
        builder.append(Float.toString(length));
        builder.append("]");
        return builder.toString();
    }
}
