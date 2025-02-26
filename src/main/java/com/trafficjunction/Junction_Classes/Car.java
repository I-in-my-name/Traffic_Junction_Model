package com.trafficjunction.Junction_Classes;
import java.util.List;

public class Car extends Vehicle {

    public Car (float time, List<Lane> route) {
        super(time, 60.f, 2.f, route);
    }
}
