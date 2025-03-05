package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;

public class LaneMetrics {

    private List<VehicleMetrics> vehicleMetrics;

    private int maxQueueLength;
    private float averageWaitTime;
    private float maxWaitTime;

    private int maximumQueueLength;
    private int queueLengthRunningTotal;
    private int queueTotalCount; // # Of times the queue size is recorded


    private List<Float> AverageWaitTimes;
    private List<Float> maxWaitTimes;

    private float junctionEmissions;
    private float pedestrianFriendliness;

    public LaneMetrics() {
        vehicleMetrics = new ArrayList<>();
        maximumQueueLength = -1;
        queueLengthRunningTotal = 0;
        queueTotalCount = 0;
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
        if (currentQueueLength > maximumQueueLength) {
            maximumQueueLength = currentQueueLength;
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
        return null;
    }
    
}
