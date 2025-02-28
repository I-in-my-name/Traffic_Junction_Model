package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;

public class LaneMetrics {

    private List<VehicleMetrics> vehicleMetrics;

    private int maxQueueLength;
    private float averageWaitTime;
    private float maxWaitTime;

    // Float is timestamp, integer is size of queue
    // Storing time with sie could be used for more
    // complex data analytics later
    private List<Pair<Float, Integer>> queueLengths;

    private List<Float> AverageWaitTimes;
    private List<Float> maxWaitTimes;

    private float junctionEmissions;
    private float pedestrianFriendliness;

    public LaneMetrics() {
        vehicleMetrics = new ArrayList<>();
        queueLengths = new ArrayList<>();
    }

    public void addVehicleMetric(VehicleMetrics vehicleMetric) {
        vehicleMetrics.add(vehicleMetric);
    }

    public void setFriendliness(float value) {

    }

    // assumes all vehicles have exited and have had calculateTotalWaitTime called
    public void calculateMetrics() {
        averageWaitTime = 0.f;
        maxWaitTime = 0.f;
        for (VehicleMetrics metric : vehicleMetrics) {
            averageWaitTime += metric.getTotalWaitTime() / vehicleMetrics.size();

            if (metric.getTotalWaitTime() > maxWaitTime)
                maxWaitTime = metric.getTotalWaitTime();
        }
    }

    /*
     * Lane must call this method every update method to maintain
     * accurate recording of queue lengths
     */
    public void updateQueueSize(float timestamp, int currentQueueLength) {
        queueLengths.add(new Pair<Float, Integer>(timestamp, currentQueueLength));
    }

    public int getMaxQueueLength() {
        maxQueueLength = -1;
        for (Pair<Float, Integer> queueLengthAtTime : queueLengths) {
            int queueLength = queueLengthAtTime.getRight();
            if(queueLength > maxQueueLength)
                maxQueueLength = queueLength;
        }
        return maxQueueLength;
    }

    public float getAverageWaitTime() {
        return averageWaitTime;
    }

    public float getMaxWaitTime() {
        return maxWaitTime;
    }

    public String getMetrics() {
        return null;
    }
    
}
