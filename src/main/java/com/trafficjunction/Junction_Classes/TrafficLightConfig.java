package com.trafficjunction;

import java.util.List;

public class TrafficLightConfig {

    public List<TrafficLightState> configs;
    private float cycle_duration;

    public TrafficLightConfig() {

    }

    public void addState(float time, int state) {

    }

    public void removeState(int index) {

    }

    public void setState(int index, float time, int state) {

    }

    public void insertState(int index, float time, int state) {

    }

    // Should return a list of states
    public int getState(float time) {
        return 0;
    }
    
}

class TrafficLightState {

	public float time;
	public int[] states;
	
	public TrafficLightState(float time, int[] states) {
        this.time = time;
        this.states = states;
	}
}