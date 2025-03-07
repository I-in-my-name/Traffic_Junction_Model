package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;

// need to track how long vehicle is in junction + store total wait time --> vehicle and lane class
/*
When a vehicle enters a lane, startExistingTime should be set
When a vehicle is removed, stopMoving(timestamp) should be triggered
Vehicles should record when they stop (e.g., due to a red light or another car ahead)
Vehicles should track their total wait time based on when they were stopped
*/

class VehicleMetrics {

    // Timestamp when the vehicle first appears in the simulation
    private float startExistingTime;

    // Duration of the most recent waiting period
    private float lastWaitTime;

    // Timestamp when the vehicle last stopped moving
    private float lastStopTime;

    // Cumulative time the vehicle has spent waiting
    private float totalWaitTime;

    // List of individual waiting periods
    private List<Float> waitTimes;

    // Indicates whether the vehicle is currently moving
    private boolean isMoving;

    /**
     * Constructs a VehicleMetrics object to track waiting times for a
     * vehicle in a junction.
     * <p>
     * Initializes the vehicle's state as moving and sets the start time
     * of existence.
     * <p>
     * @param startExistingTime The timestamp when the vehicle first
     *                          appears in the simulation.
     */
    public VehicleMetrics(float startExistingTime) {
        this.lastWaitTime = 0.f;
        this.lastStopTime = 0.f;
        this.totalWaitTime = 0.f;

        // Will store the wait times of the vehicle in an ArrayList
        this.waitTimes = new ArrayList<>();

        // Sets the start time to the current time when object is created
        this.startExistingTime = startExistingTime;

        // assuming vehicle starts moving
        this.isMoving = true;
    }

    /**
     * Martks the vehicle as moving and records the last wait time
     * <p>
     * Ensures consecutive calls do not affect the recorded wat times
     * <p>
     * @param timesamp the current simulation time when the vehicle starts
     *                 moving
     */
    public void startMoving(float timestamp) {

        // prevents consecutive startMoving calls
        if (!isMoving) {
            isMoving = true;

            // Calculates the duration that the vehicle was stopped for
            lastWaitTime = timestamp - lastStopTime;

            // Add the calculated wait time to the list of recorded wait
            waitTimes.add(lastWaitTime);

        }
    }

    /**
     * Marks the vehicle as stopped and records the timestamp of when it stopped
     * <p>
     * Ensures that consecutive stop events are not recorded to prevent duplicate entries
     * <p>
     * @param timestamp The time at which the vehicle stops moving.
     */
    public void stopMoving(float timestamp) {
        
        // Only updates if the vehicle was previously moving
        if (isMoving) {

            // Mark the vehicle as stopped
            isMoving = false;

            // Store the time the vehicle stopped
            lastStopTime = timestamp;
        }
    }

    /**
     * Calculates the total wait time for the vehicle once it is removed from the simulation.
     * It ensures that all waiting periods are summed prior to deletion.
     * 
     * @param exitTimestamp The timestamp when the vehicle exits the simulation.
     */
    public void calculateTotalWaitTime(float exitTimestamp) {
        totalWaitTime = 0.f;

        // Sum all recorded wait times
        for (float time : waitTimes) {
            totalWaitTime += time;
        }
       

        // If the vehicle is not moving at the time of exit, account for the last waiting period
        if (!isMoving) {
            totalWaitTime += exitTimestamp - lastStopTime;
            
        }
        
    }

    // Getter for the total wait time for all of the vehicles
    public float getTotalWaitTime() {
        return totalWaitTime;
    }

    // Getter for the wait times of the vehicles
    public List<Float> getWaitTimes() {
        return waitTimes;
    }

    // Coverts the wait time to a string to be processed
    @Override
    public String toString() {
        return waitTimes.toString();
    }
}