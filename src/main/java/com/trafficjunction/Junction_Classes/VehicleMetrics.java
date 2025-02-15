package com.trafficjunction.Junction_Classes;

import java.util.List;

class VehicleMetrics {

    public float move_timestamp;
    public float stop_timestamp;

    public int vehicle_direction;

    private float total_wait_time;
    private List<Float> start_stop_times;

    private float startExistingTime; // time the vehicle starts existing
    // car could be moving immediately, in which case start_stop_times[0] = startExistingTime
    // may not thoguh
    // total_wait = start_stop_times[i] - start_stop_times[i - 1] for every odd i + (start_stop_times[0] - startExistingTime) 

    public VehicleMetrics(float startExistingTime) {
        this.startExistingTime = startExistingTime;
    }

    // TODO: Maintain valid state
    // Right now if startmoving is called twice in a row vehicle metrics 
    // would be in invalid state and wait times would be wrong
    // same is stopmoving is called consecutively
    // store flag of moving or not moving?

    // Don't need test for
    public void startMoving(float timestamp) {

    }

    // Don't need test for
    public void stopMoving(float timestamp) {

    }

    // Test written for
    public void calculateMetrics() {

    }

    // Don't need test for
    public List<Float> getWaitTimes() {
        return null;
    }

    // Don't need test for
    public float getTotalWaitTime() {
        return total_wait_time;
    }

}