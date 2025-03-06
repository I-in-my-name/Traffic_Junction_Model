package com.trafficjunction.Junction_Classes;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

import com.trafficjunction.observer.*;

public class Vehicle {

    // stores the speed of the vehicle
    private float speed; 

    // stores the maximum speed the vehicle can travel at
    private float maxSpeed;

    // stores the length of the vehicle
    private float length;

    // A queue storing the route the route the given 
    // vehicle will take
    private Queue<Lane> desiredRoute;

    // An object storing the metrics for the given vehicle
    private VehicleMetrics metrics;

    // A float storing the time at which the vehicle was
    // first creeated
    private float creationTime;
    private float currentTime;

    // stores the direction the vehicle is moving in (eg "nte")
    private String direction;


    /**
     * Overload method in the case that vehicle route/direction is not specified
     */
    public Vehicle(float time, float maxSpeed, float length) {
        // making empty object instead of null
        this(time, maxSpeed, length, new ArrayList<Lane>());
    }

    public Vehicle(float time, float maxSpeed, float length, List<Lane> route) {
        this(time, maxSpeed, length, route, "");
    }

    /**
     * Main constucture for the Vehicle class
     * <p>
     * Initializes all necessary attributes, 
     * these include speed route and direction
     * <p>
     * @param time Time at which vehicle was created
     * @param maxSpeed The maximum speed at which the vehicle can travel
     * @param Length The length of the vehicle
     * @param route the route that the vehicle will take
     */
    public Vehicle(float time, float maxSpeed, float length, List<Lane> route, String direction) {
        
        // Inittaly sets the speed to the max speed
        this.speed = maxSpeed;
        this.maxSpeed = maxSpeed;
        this.length = length;
        this.metrics = new VehicleMetrics(time);
        
        // Sets the creation time and current time to 0
        this.creationTime = time;
        this.currentTime = time;

        // Sets the direction the vehicle is traveling in
        // and the route it will take
        this.desiredRoute = new LinkedList<>(route);
        this.direction = direction;
    }

    /**
     * Gets the metric storing the total wait time for the vehicle
     */
    public float getWaitTime() {
        // returns the wait time
        return metrics.getTotalWaitTime();
    }

    /**
     * Gets the all the metrics for the vehicle
     */
    public VehicleMetrics getMetrics() {
        // returns the metrics
        return metrics;
    }

    /**
     * Pops a lane from the route once it has been visited
     */
    public Lane popRoute() {
        // returns the part of the route that has been complete or null
        return desiredRoute.poll();   
    }
    
    /**
     * Gets the length of the vehicle
     */
    public float getLength() {
        // Returns the length of the vehicle
        return length;
    }

    /**
     * Sets the ArrayList storing the route the vehicle will take
     */
    public void setRoute(List<Lane> route) {
        // Sets the arrayLis tto store the correct route
        desiredRoute = new LinkedList<>(route);
    }

    /**
     * Sets the direction that the vehicle will take
     */
    public String getDirection() {
        //returns the direction the vehicle is traveling in
        return this.direction;
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

    // TODO: finish test for
    /**
     * Method to update a vehicle's position
     * <p>
     * 
     * Parameters - (time) the time since simulation started, (lane) the lane that the vehicle is currently in
     */
    
    // Overload method so we can get the index without searching for the vehicle in the lane
    public String update(float newTime, Lane lane, int index) {
        //TODO: Figure out where metrics.startMoving and metrics.stopMoving calls go
        // I think that is the bug causing weird metrics rn 

        // Get needed variables:
        float timeDifference = newTime - currentTime;			// Gets the time increment increase
        if (timeDifference <= 0.f || index >= lane.getVehicles().size() || index < 0) { // If index is out of bounds, or no update is needed
            return "no update";
        }
        Pair<Float,Vehicle> thisPair = lane.getVehicles().get(index);	// Gets the pair which stores this vehicle
        float position = thisPair.getLeft();	// Gets the vehicle's current position

        // If index == 0, traversable position = 0
        // TODO / QUESTION: Is traversable position the length possible ot move
        // or the new position that it could get to in theory if it kept moving?
        // If the latter, then 0 makes sense
        // otherwise it doesn't
        // Code looks like it means the first though
        float traversablePosition;		// Get the possible distance our vehicle can/should move forward
        if (index == 0) {
            traversablePosition = 0;
        } else {
            Pair<Float,Vehicle> posVehicle = lane.getVehicles().get(index-1);  // Gets the entry of the next vehicle
            float nextVehiclePosition = posVehicle.getLeft();                // Gets the next vehicle position on the lane
            float vehicleLength = posVehicle.getRight().getLength();          // Gets the next vehicle's length
            traversablePosition = nextVehiclePosition + vehicleLength + 1.f;
        }

        // position > traversablePosition means can move
        // e.g. position = 0 means start. position = 50 means 50 away from start. 
        // traversable position = 20 means should be able to move 30 forward
        // TODO: this is right, right? Changed it when fixing bugs to run simulation
        if (position > traversablePosition) { // If the vehicle can move
            if (speed == 0) {
                speed = maxSpeed;
                metrics.startMoving(currentTime);
            }
            // Travel possible distance:
            float maxTraversableDistance = calculateDistanceFromTime(timeDifference);
            //float traversableDistance = traversablePosition - position;
            // changed this from above to below as position > traversable position,
            // part of change described above
            float traversableDistance = position - traversablePosition;
            float distance = Math.min(traversableDistance, maxTraversableDistance);
            // position - distance is new position
            // closer to 0 is closer to end of lane so position gets smaller as distance travelled grows
            thisPair.setLeft(position - distance);
            // Calculate time taken:
            float timeTaken = calculateTimeFromDistance(distance);
            currentTime += timeTaken;
            
            // Run function again in case reaching the end of the lane; (currentTime) is updated, so it won't run infinitely:
            if (newTime > currentTime) {
                return "another update, "+ this.update(newTime, lane, index);
            }

        } else { // The vehicle can not move OR is at 0 and waiting for TrafficLights
            boolean canProceed = lane.canPass();
            if (index == 0 && position == 0.f && canProceed) { // If we are able to go to the next lane
                // TODO: Remember to shift list up if needed
                Lane nextLane = popRoute();
                if (nextLane != null && lane.getGoingTo().contains(nextLane)) {
                    lane.removeVehicle();                           // Remove vehicle from current lane
                    nextLane.addVehicle(this);
                    update(newTime, nextLane, nextLane.getVehicleNum()-1);
                } else if (!lane.getGoingTo().isEmpty()) {  // If there is one or more possible lanes for the vehicle to go in
                    List<Lane> nextLanes = lane.getGoingTo();
                    // Go to the lane with the least number of vehicles:
                    int checkIndex = 1;
                    int lowestIndex = 0;
                    int leastV = nextLanes.get(0).getVehicleNum();    // Initially gets the number of vehicles in the first possible lane
                    boolean available = !nextLanes.get(0).isFull();
                    while (checkIndex != nextLanes.size()) {
                        boolean canGoTo = !nextLanes.get(checkIndex).isFull();
                        int newV = nextLanes.get(checkIndex).getVehicleNum();    // Get number of vehicles in the first lane
                        if (leastV > newV && canGoTo) {       // Compares num of vehicles between current lowest and another lane's
                            lowestIndex = checkIndex;
                            leastV = newV;
                            available = true;
                        }
                        checkIndex++;
                    }
                    Lane desiredLane = nextLanes.get(lowestIndex);    // Gets the lane
                    if (available) {  // If a lane is available
                        lane.removeVehicle();                           // Remove vehicle from current lane
                        
                        // send event
                        ArrayList<Float> eventData = new ArrayList<>();
                        //eventData.add();
                        // TODO: What is the index of lane leaving and lane joining?
                        Subject.notifyObservers(new Event(EventType.VEHICLE_LEFT_LANE, eventData));

                        desiredLane.addVehicle(this);                   // Adds vehicle
                        return "add to another lane, " + this.update(newTime, desiredLane, desiredLane.getVehicles().size()-1);
                    } else {
                        return "unsuccesful in adding to lane";
                    }
                } else {
                    this.currentTime = newTime;
                    lane.removeVehicle();   // Remove vehicle from current lane
                    // Vehicle has reached the end of the route
                    // Give vehicle metrics to junction somehow (TODO: Discuss Start and End 'nodes'?)
                }
            } else if (speed != 0) {   // If the vehicle has speed meaning it has stopped when the method executed this time
                speed = 0;
                metrics.stopMoving(this.currentTime); // Saves time to vehicle metrics
            }
            this.currentTime = newTime;
        }
        return "?";
    }

    // Methods to go from time (s) to distance (m) and vice versa, dependant on speed/max speed of vehicle
    public float calculateTimeFromDistance(float distanceToTravel) {
        float timeTaken = distanceToTravel / (maxSpeed / 3.6f);
        return timeTaken;
    }
    public float calculateDistanceFromTime(float timeToTravel) {
        if (timeToTravel==0) {
            return 0;
        }
        float distance = (maxSpeed / 3.6f) * timeToTravel;
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
