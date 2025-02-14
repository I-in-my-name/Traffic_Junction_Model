package com.trafficjunction.Junction_Classes;

import java.util.List;

public class Vehicle {

    private float speed;
    private float max_speed;
    private float length;
    // May change to queue
    private List<Lane> desired_route;

    private VehicleMetrics metrics;

    // Should vehicle be abstract class only defined by Car and Bus classes?
    public Vehicle(float speed, float max_speed, float length) {
        this.speed = speed;
        this.max_speed = max_speed;
        this.length = length;
    }

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

    // method: get vehicle position: used in lane class for deleting vehicles. 
    
}
