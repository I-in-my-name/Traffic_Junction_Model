package com.trafficjunction.Junction_Classes;
import java.util.List;

public class Car extends Vehicle {

    public Car (float time, List<Lane> route) {
        super(time, 40.f, 2.f, route);
    }
    public Car (float time, List<Lane> route, String direction) {
        super(time, 40.f, 2.f, route, direction);
    }
}
