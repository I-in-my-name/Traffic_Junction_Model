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
    /*
    public float move_timestamp;
    public float stop_timestamp;

    public int vehicle_direction;

    private float total_wait_time;
    private List<Float> start_stop_times;
    */
    private float startExistingTime; // time the vehicle starts existing
    // car could be moving immediately, in which case start_stop_times[0] = startExistingTime
    // may not thoguh
    // total_wait = start_stop_times[i] - start_stop_times[i - 1] for every odd i + (start_stop_times[0] - startExistingTime) 
    private float totalWaitTime = 0; // total time spent waiting
    private List<Float> stopTimes; // stores timestamps for when vehicle stops
    private boolean isMoving; // if vehicle is currently moving

    // @param startExistingTime is when the vehicle first appears in the simulation
    public VehicleMetrics(float startExistingTime) {
        this.startExistingTime = startExistingTime;
        this.stopTimes = new ArrayList<>();
        this.isMoving = true; // assuming vehicles starts moving
    }

    // TODO: Maintain valid state
    // Right now if startmoving is called twice in a row vehicle metrics 
    // would be in invalid state and wait times would be wrong
    // same is stopmoving is called consecutively
    // store flag of moving or not moving?

    // Don't need test for
    public void startMoving(float timestamp) {
        if (!isMoving) { // prevent consecutive startMoving calls
            isMoving = true;
            float lastStopTime = stopTimes.get(stopTimes.size() - 1);
            totalWaitTime += (timestamp - lastStopTime);
        }
    }

    // Don't need test for
    // Mark when vehicle stops, store the timestamp
    public void stopMoving(float timestamp) {
        if (isMoving) { // Prevent consecutive stopMoving calls
            isMoving = false;
            stopTimes.add(timestamp);
        }
    }

    // Test written for
    // calculates total wait time once vehicle removed from simulation, ensures all waiting periods are summed prior to deletion
    public void calculateMetrics(float exitTimestamp) {
        if (!isMoving && !stopTimes.isEmpty()) {
            totalWaitTime += (exitTimestamp - stopTimes.get(stopTimes.size() - 1));
        }
    }
/*
    // Don't need test for
    public List<Float> getWaitTimes() {
        return null;
    }
*/
    // Don't need test for
    public float getTotalWaitTime() {
        return totalWaitTime;
    }

}