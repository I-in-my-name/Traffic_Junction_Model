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

    // TODO: finish est for
    public void update(float time, Lane lane) {

    }

    // Don't need test for
    public Lane popRoute() {
        return null;
    }

    // Don't need test for
    public VehicleMetrics getMetrics() {
        return null;
    }

    // Don't need test for
    public float getLength() {
        return length;
    }

    // method: get vehicle position: used in lane class for deleting vehicles. 
    
}
