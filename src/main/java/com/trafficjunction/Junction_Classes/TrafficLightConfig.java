package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;


public class TrafficLightConfig {
    /**
     * Example:
     * List{
     * Pair(4.0seconds, List{Red, Green, Red}),
     * Pair(3.0seconds, List{Green, Red, Red}),
     * Pair(4.0seconds, List{Red, Red, Green})
     * }
     */
    private List<Pair<Float, List<Integer>>> record;
    private float cycle_duration;   // stores the total time it takes for the cycle

    public TrafficLightConfig() {
        record = new ArrayList<>();
        cycle_duration = 0;
    }

    public boolean addState() {
        return false;
    }

    public boolean removeState(int index) {
        
        return false;
    }

    public boolean setState(float time, int state, int record_index) {
        //float currentTime = 0;
        //int i = 0;
        //if (record_index < 0 || record_index >= record.size() || state < 0 || state >= record.size()) {
        //    return false;
        //} else {
        //    while (currentTime < time && found == false) {
        //        currentTime = currentTime + record.get(i).getLeft();
        //        i ++;
        //    }
        //    record.get(i).getRight().set(record_index, state);
        //    return true;
        //}
        return false;
    }

    public boolean insertState(int index, float time, List<Integer> state) {

        return false;
    }
    
    public List<Integer> getStates(float time) {
        if (record.isEmpty()) {
            return null;  // Early return if no configurations are defined
        }

        float cycleTime = 0;
        // Calculate the total cycle duration
        for (Pair<Float, List<Integer>> state : record) {
            cycleTime += state.getLeft();
        }

        // Make sure time is within one full cycle (modulo operation)
        time = time % cycleTime;  // Wrap the time around the cycle duration

        float currentTime = 0;
        for (Pair<Float, List<Integer>> config : record) {
            currentTime += config.getLeft();
            if (time < currentTime) {
                return config.getRight();  // Return the state that matches the given time
            }
        }

        return null;  // In case no match was found (shouldn't happen with the modulo adjustment)
    }
    
}