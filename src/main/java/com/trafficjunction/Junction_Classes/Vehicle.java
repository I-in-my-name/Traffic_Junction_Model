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
     * 
     * @param time     Time at which vehicle was created
     * @param maxSpeed The maximum speed at which the vehicle can travel
     * @param Length   The length of the vehicle
     * @param route    the route that the vehicle will take
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
        // returns the direction the vehicle is traveling in
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
    // public void updateMovement(float time, Lane lane) {
    // TrafficLight trafficLight = lane.getTrafficLight();

    // if (trafficLight.getState() == 0 || lane.isFull()) { // 0 = Red
    // if (this.speed != 0) {
    // metrics.stopMoving(time); // Track wait time
    // }
    // } else if (trafficLight.getState() == 1) { // 1 = Green
    // if (this.speed == 0) {
    // metrics.startMoving(time); // Resume movement
    // }
    // lane.removeVehicle();
    // List<Lane> lanes = lane.getGoingTo();
    // if (lanes == null) {
    // // TODO: delete self, pass metrics to JunctionMetrics somehow.
    // } else {
    // lanes.get(0).addVehicle(this);
    // }
    // }
    // }

    // TODO: finish test for
    /**
     * Method to update a vehicle's position
     * <p>
     * Updates the vehicles position in its lane based on the time and traffic
     * Determines if the vehcicle can move, needs to wait or can transfer to another
     * lane
     * <p>
     * 
     * @param newTime updated simulation time
     * @param lane    the lane which the vehicle is currently in
     * @param index   The position of the vehicle in the lane's vehicle list.
     * @return A status message indicating the outcome of the update.
     */
    public String update(float newTime, Lane lane, int index) {
        // get the time difference since the last update
        float timeDifference = newTime - currentTime;

        // If no update is needed (time hasn't progressed or index is out of bounds),
        // return early
        if (timeDifference <= 0.f || index >= lane.getVehicles().size() || index < 0) { // If index is out of bounds, or
                                                                                        // no update is needed
            return "no update";
        }

        // Retrieve the vehicle's position from the lane
        Pair<Float, Vehicle> thisPair = lane.getVehicles().get(index); // Gets the pair which stores this vehicle
        float position = thisPair.getLeft(); // Gets the vehicle's current position

        // Get the possible distance our vehicle can/should move forward
        float traversablePosition;
        if (index == 0) {
            traversablePosition = 0;
        } else {
            Pair<Float, Vehicle> posVehicle = lane.getVehicles().get(index - 1); // Gets the entry of the next vehicle
            float nextVehiclePosition = posVehicle.getLeft(); // Gets the next vehicle position on the lane
            float vehicleLength = posVehicle.getRight().getLength(); // Gets the next vehicle's length
            traversablePosition = nextVehiclePosition + vehicleLength + 1.f;
        }

        // Checks to see if the vehicle can move forward
        // If position > traversable_position means can move
        // e.g. position = 0 means start. position = 50 means 50 away from start.
        // traversable position = 20 means should be able to move 30 forward
        if (position > traversablePosition) { // If the vehicle can move
            if (speed == 0) {
                speed = maxSpeed;
                metrics.startMoving(currentTime); // track movement start time
            }

            // calculate possibe travel distance based on speed and time difference
            float maxTraversableDistance = calculateDistanceFromTime(timeDifference);
            float traversable_distance = position - traversablePosition;
            float distance = Math.min(traversable_distance, maxTraversableDistance);

            // Update the vehicle's position
            thisPair.setLeft(position - distance);

            // Calculate actual time taken for this movement
            float timeTaken = calculateTimeFromDistance(distance);
            currentTime += timeTaken;

            // If more time remains, attempt another update
            if (newTime > currentTime) {
                return "another update, " + this.update(newTime, lane, index);
            }

        } else { // The vehicle can not move OR is at 0 and waiting for TrafficLights
            boolean canProceed = lane.canPass();

            // If at the start of the lane and allowed to move to the next lane
            if (index == 0 && position == 0.f && canProceed) {
                Lane nextLane = popRoute();
                if (nextLane != null && lane.getGoingTo().contains(nextLane)) {
                    lane.removeVehicle(); // Remove vehicle from current lane
                    nextLane.addVehicle(this);
                    update(newTime, nextLane, nextLane.getVehicleNum() - 1);
                } else if (!lane.getGoingTo().isEmpty()) { // If there is one or more possible lanes for the vehicle to
                                                           // go in
                    List<Lane> nextLanes = lane.getGoingTo();
                    // Go to the lane with the least number of vehicles:
                    int checkIndex = 1;
                    int lowestIndex = 0;
                    int leastV = nextLanes.get(0).getVehicleNum(); // Initially gets the number of vehicles in the first
                                                                   // possible lane
                    boolean available = !nextLanes.get(0).isFull();
                    while (checkIndex != nextLanes.size()) {
                        boolean canGoTo = !nextLanes.get(checkIndex).isFull();
                        int newV = nextLanes.get(checkIndex).getVehicleNum(); // Get number of vehicles in the first
                                                                              // lane
                        if (leastV > newV && canGoTo) { // Compares num of vehicles between current lowest and another
                                                        // lane's
                            lowestIndex = checkIndex;
                            leastV = newV;
                            available = true;
                        }
                        checkIndex++;
                    }
                    Lane desiredLane = nextLanes.get(lowestIndex); // Gets the lane
                    if (available) { // If a lane is available
                        lane.removeVehicle(); // Remove vehicle from current lane

                        // send event
                        ArrayList<Float> eventData = new ArrayList<>();
                        // eventData.add();
                        // TODO: What is the index of lane leaving and lane joining?
                        Subject.notifyObservers(new Event(EventType.VEHICLE_LEFT_LANE, eventData));

                        desiredLane.addVehicle(this); // Adds vehicle
                        return "add to another lane, "
                                + this.update(newTime, desiredLane, desiredLane.getVehicles().size() - 1);
                    } else {
                        return "unsuccesful in adding to lane";
                    }
                } else {
                    this.currentTime = newTime;
                    lane.removeVehicle(); // Remove vehicle from current lane
                    // Vehicle has reached the end of the route
                    // Give vehicle metrics to junction somehow (TODO: Discuss Start and End
                    // 'nodes'?)
                }
            } else if (speed != 0) { // If the vehicle has speed meaning it has stopped when the method executed this
                                     // time
                speed = 0;
                metrics.stopMoving(this.currentTime); // Saves time to vehicle metrics
            }
            this.currentTime = newTime;
        }
        return "?";
    }

    /**
     * Calculates the time required to travel a given distance based on the
     * vehicle's maximum speed.
     * <p>
     * The speed is converted from km/h to m/s by dividing by 3.6.
     * <p>
     * 
     * @param distanceToTravel The distance to be traveled in meters.
     * @return The time taken to travel the given distance in seconds.
     */
    public float calculateTimeFromDistance(float distanceToTravel) {
        // Convert maxSpeed from km/h to m/s and calculate time using time = distance /
        // speed formula
        float time_taken = distanceToTravel / (maxSpeed / 3.6f);
        return time_taken;
    }

    /**
     * Calculates the distance a vehicle can travel in a given time based on its
     * maximum speed.
     * <p>
     * 
     * @param timeToTravel The time duration (in seconds) for which the vehicle
     *                     moves.
     * @return The distance (in meters) that the vehicle can travel in the given
     *         time.
     */
    public float calculateDistanceFromTime(float timeToTravel) {
        // If no time has passed, no distance is traveled
        if (timeToTravel == 0) {
            return 0;
        }

        // Convert speed from km/h to m/s by dividing by 3.6, then multiply by time to
        // get distance
        float distance = (maxSpeed / 3.6f) * timeToTravel;

        return distance;
    }

    /**
     * Retrieves the current speed of the vehicle.
     * <p>
     * 
     * @return The current speed of the vehicle in meters per second.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Returns a string representation of the Vehicle object.
     * <p>
     * This method provides a simple way to display vehicle information,
     * including its speed and length.
     * <p>
     * Used in the Lane class for vehicle management (e.g., deleting vehicles).
     * <p>
     * 
     * @return A string containing the vehicle's speed and length.
     */
    @Override
    public String toString() {
        // Using StringBuilder for efficient string concatenation
        StringBuilder builder = new StringBuilder();

        builder.append("Vehicle [");
        builder.append(Float.toString(speed)); // Append vehicle speed
        builder.append(", ");
        builder.append(Float.toString(length)); // Append vehicle length
        builder.append("]");

        return builder.toString();
    }
}
