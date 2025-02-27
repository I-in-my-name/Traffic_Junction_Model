package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;

public class JunctionMetrics {

    private List<VehicleMetrics> vehicle_metrics;

    private int max_queue_length;
    private float average_wait_time;
    private float max_wait_time;

    private List<Integer> max_queue_lengths;
    private List<Float> average_wait_times;
    private List<Float> max_wait_times;

    private float junction_emissions;
    private float pedestrian_friendliness;

    public JunctionMetrics() {
        vehicle_metrics = new ArrayList<>();
    }

    public void addVehicleMetric(VehicleMetrics vehicle_metric) {
        vehicle_metrics.add(vehicle_metric);
    }

    public void setFriendliness(float value) {

    }

    // assumes all vehicles have exited and have had calculateTotalWaitTime called
    public void calculateMetrics() {
        average_wait_time = 0.f;
        max_wait_time = 0.f;
        for (VehicleMetrics metric : vehicle_metrics) {
            average_wait_time += metric.getTotalWaitTime() / vehicle_metrics.size();

            if (metric.getTotalWaitTime() > max_wait_time)
                max_wait_time = metric.getTotalWaitTime();
        }
    }

    // How will this work?
    // Has list of vehicle metrics for the whole junction
    // Does it check what lane each vehicle belongs to?
    // Can't be done as the vehicle does not know
    // Each vehicle only tells vehicle metrics when it stops and starts
    public float getMaxQueueLength() {
        return max_queue_length;
    }

    public float getAverageWaitTime() {
        return average_wait_time;
    }

    public float getMaxWaitTime() {
        return max_wait_time;
    }

    public String getMetrics() {
        return null;
    }
    
}
