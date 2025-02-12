package com.trafficjunction;

import java.util.List;

public class Vehicle {

    private float speed;
    private float max_speed;
    private float length;
    // May change to queue
    private List<Lane> desired_route;

    private VehicleMetrics metrics;

    public void update(float time, Lane lane) {

    }

    public Lane popRoute() {
        return null;
    }

    public VehicleMetrics getMetrics() {
        return null;
    }

    public float getLength() {
        return length;
    }
    
}
