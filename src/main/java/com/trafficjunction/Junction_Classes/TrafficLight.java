package com.trafficjunction.Junction_Classes;

public class TrafficLight {

    public int state;

    public TrafficLight() {
        state = 0;  // 0 - Red
                    // 1 - Green
    }

    public void setState(int s) {
        state = s;
    }
    
    public int getState() {
        return state;
    }
}
