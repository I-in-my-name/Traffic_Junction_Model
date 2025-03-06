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
    public float moveTimestamp;
    public float stopTimestamp;

    public int vehicleDirection;

    private float totalTime;
    private List<Float> startTimes;
    */
    private float startExistingTime; // time the vehicle starts existing
    // car could be moving immediately, in which case startTimes[0] = startExistingTime
    // may not thoguh
    // totalWait = startTimes[i] - startTimes[i - 1] for every odd i + (startTimes[0] - startExistingTime) 
    private float lastWaitTime;    // total time spent waiting
    private float lastStopTime;   // last timestamp the vehicle has stopped
    private float totalWaitTime;
    private List<Float> waitTimes;
    //private List<Float> stopTimes;     // stores timestamps for when vehicle stops // REMOVE
    private boolean isMoving;   // if vehicle is currently moving

    // @param startExistingTime is when the vehicle first appears in the simulation
    public VehicleMetrics(float startExistingTime) {
        this.lastWaitTime = 0.f;
        this.lastStopTime = 0.f;
        this.totalWaitTime = 0.f;
        this.waitTimes = new ArrayList<>();
        this.startExistingTime = startExistingTime;
        //this.stopTimes = new ArrayList<>(); // REMOVE
        this.isMoving = true; // assuming vehicles starts moving
    }

    /*
     * Changes made to how the metrics are calculated 
     * Now adds the star ttimestamps as they come in and
     * subtracts the stop timestamps as they come in
     * Add the last stop timestamp when exiting 
     */

    // Don't need test for
    public void startMoving(float timestamp) {
        if (!isMoving) { // prevent consecutive startMoving calls
            //System.out.println("Add to wait times");
            isMoving = true;
            lastWaitTime = timestamp - lastStopTime;    // Calculate new last wait time
            waitTimes.add(lastWaitTime);                // Add last wait time to list
            //System.out.print("Last wait time: ");
            //System.out.println(lastWaitTime);
            //System.out.print("Wait times: ");
            //System.out.println(waitTimes);

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
    // calculates total wait time once vehicle removed from simulation, ensures all waiting periods are summed prior to deletion
    public void calculateTotalWaitTime(float exitTimestamp) {
        totalWaitTime = 0.f;
        //System.out.print("Wait times: ");
        //System.out.println(waitTimes);
        for (float time : waitTimes) {
            totalWaitTime += time;
        }
        //System.out.println("Calc wait time");
        //System.out.println(totalWaitTime);
        //System.out.print("Total: ");
        //System.out.println(totalWaitTime);
        if (!isMoving && !waitTimes.isEmpty()) {
            // If not moving, then the last stop time is the final stop time
            // add it to the total wait time as it has previously been subtracted from the wait time
            // Essentially the time spent waiting doesn't care about the last stop time
            // as it doesn't start moving again, so that time isn't relevant to waiting time
            if (!isMoving) {
                totalWaitTime += exitTimestamp - lastStopTime;
            }
            
            //totalWaitTime += (exitTimestamp - stopTimes.get(stopTimes.size() - 1));
        }
        //System.out.println(totalWaitTime);
        //System.out.println();
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