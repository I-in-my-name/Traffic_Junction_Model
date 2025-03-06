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
     * 
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
     * 
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

    // Don't need test for
    // Mark when vehicle stops, store the timestamp
    public void stopMoving(float timestamp) {
        if (isMoving) { // Prevent consecutive stopMoving calls
            isMoving = false;
            lastStopTime = timestamp;
        }
    }

    // Test written for
    // calculates total wait time once vehicle removed from simulation, ensures all
    // waiting periods are summed prior to deletion
    public void calculateTotalWaitTime(float exitTimestamp) {
        totalWaitTime = 0.f;
        // System.out.print("Wait times: ");
        // System.out.println(waitTimes);
        for (float time : waitTimes) {
            totalWaitTime += time;
        }
        // System.out.println("Calc wait time");
        // System.out.println(totalWaitTime);
        // System.out.print("Total: ");
        // System.out.println(totalWaitTime);
        if (!isMoving && !waitTimes.isEmpty()) {
            // If not moving, then the last stop time is the final stop time
            // add it to the total wait time as it has previously been subtracted from the
            // wait time
            // Essentially the time spent waiting doesn't care about the last stop time
            // as it doesn't start moving again, so that time isn't relevant to waiting time
            if (!isMoving) {
                totalWaitTime += exitTimestamp - lastStopTime;
            }

            // totalWaitTime += (exitTimestamp - stopTimes.get(stopTimes.size() - 1));
        }
        // System.out.println(totalWaitTime);
        // System.out.println();
    }

    // Don't need test for
    public float getTotalWaitTime() {
        return totalWaitTime;
    }

    // Don't need test for
    public List<Float> getWaitTimes() {
        return waitTimes;
    }
}