package com.trafficjunction;

public class TrafficLight {

    public int state;

    public TrafficLight() {
        state = 0;
    }

    public void setState(int s) {
        state = s;
    }
    
    public int getState() {
        return state;
    }
}
