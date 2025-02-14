package com.trafficjunction.Junction_Classes;

import java.util.List;

class Pair<K, V> { // amount of time the taffic light will be in a given state
// the state (e.g. red =)
    K left;
    V right;
    
    public Pair(K k, V v) {
        left = k;
        right = v;
    }

    public K getLeft() {
        return left;
    }

    public V getRight() {
        return right;
    }
}

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
    public List<TrafficLightState> getState(float time) {
        return configs;
    }
    
}

class TrafficLightState {


    private int ID;
	private float time;
	private ArrayList<Pair<Float,Integer>> states;
    
	
	public TrafficLightState(float time, int states, int ID) {
        this.time = time;
        this.states = states;
        this.ID = ID;
	}

    public int getID() {
        return ID;
    }

    public int TrafficLightState

    public int getStates() {
        while (time < trafficLightState) {
            i = i + 1;
        }
    }




}