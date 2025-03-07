package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;

public class LaneMetrics {

    private List<VehicleMetrics> vehicleMetrics;

    private float averageWaitTime;
    private float maxWaitTime;

    private int maxQueueLength;
    private int queueLengthRunningTotal;
    private int queueTotalCount; // # Of times the queue size is recorded
    // private List<Integer> queueLengths;
    // private int lastQueueLength;

    // private List<Float> AverageWaitTimes;
    // private List<Float> maxWaitTimes;

    private float junctionEmissions;
    private float pedestrianFriendliness;

    public LaneMetrics() {
        vehicleMetrics = new ArrayList<>();
        maxQueueLength = -1;
        queueLengthRunningTotal = 0;
        queueTotalCount = 0;
        // queueLengths = new ArrayList<>();
        // lastQueueLength = 0;
    }

    public void addVehicleMetric(VehicleMetrics vehicleMetric) {
        vehicleMetrics.add(vehicleMetric);
    }

    public void setFriendliness(float value) {

    }

    // assumes all vehicles have exited and have had calculateTotalWaitTime called
    public void calculateMetrics(float timestamp) {
        averageWaitTime = 0.f;
        maxWaitTime = 0.f;
        for (VehicleMetrics metric : vehicleMetrics) {
            metric.calculateTotalWaitTime(timestamp);
            averageWaitTime += metric.getTotalWaitTime() / vehicleMetrics.size();

            if (metric.getTotalWaitTime() > maxWaitTime)
                maxWaitTime = metric.getTotalWaitTime();
        }
    }

    /*
     * Lane must call this method every update method to maintain
     * accurate recording of queue lengths
     */
    public void updateQueueSize(int currentQueueLength) {
        // Update last queue length and queue lengths list:
        // if (lastQueueLength < currentQueueLength) { // If the queue length is
        // increasing
        // lastQueueLength = currentQueueLength; // Update queue length
        // } else if (lastQueueLength > currentQueueLength) { // If the queue length is
        // decreasing
        // queueLengths.add(lastQueueLength); // Add the queue length to the list
        // lastQueueLength = currentQueueLength;
        // }
        // Check if there is a new max queue length:
        if (currentQueueLength > maxQueueLength) {
            maxQueueLength = currentQueueLength;
        }
        queueLengthRunningTotal += currentQueueLength;
        queueTotalCount += 1;
    }

    public float getAverageQueueLength() {
        return queueLengthRunningTotal / queueTotalCount;
    }

    public int getQueueLengthRunningTotal() {
        return queueLengthRunningTotal;
    }

    public int getQueueTotalCount() {
        return queueTotalCount;
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }

    public float getAverageWaitTime() {
        return averageWaitTime;
    }

    public float getMaxWaitTime() {
        return maxWaitTime;
    }

    public int getSize() {
        return vehicleMetrics.size();
    }

    public String getMetrics() {
        return Integer.toString(vehicleMetrics.size());
    }

    @Override
    public String toString() {
        return vehicleMetrics.toString();
    }
}
