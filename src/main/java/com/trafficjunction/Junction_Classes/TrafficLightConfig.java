package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;

/*
Traffic Light Initial Config:
- user inputs the time interval traffic lights will be green form, this will be uniform across all roads
- traffic lights follow a set cycle starting from North road going clockwise. Since there are 4 roads, it takes 2 runs to complete a cycle (parallel roads are green simultaneously)
- After cycle the user can input the pedestrian crossing period.
*/


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

    public boolean addState(Float time, List<Integer> states) {
        if (time <= 0 || states == null || states.isEmpty()) {
            return false;
        }
        else {
            Pair<Float, List<Integer>> thisCycle = new Pair<>(time, states);
            record.add(thisCycle);
            cycle_duration += time; // Update total cycle duration
            return true;
        }
    }

    public boolean removeState(int index) {
        if (record.size() < index) {
            return false;
        } else {
            float time = record.get(index).getLeft();
            cycle_duration -= time;
            record.remove(index);
            return true;
        }
        
    }

    public boolean setState(float time, int state, int record_index) {
        float currentTime = 0;
        int i = 0;
        boolean found = false;
        if (record_index < 0 || record_index >= record.size() || state < 0 || state >= record.size()) {
            return false;
        } else {
            while (found == false) {
                currentTime = currentTime + record.get(i).getLeft();
                if (currentTime > time) {
                    found = true;
                } else {
                    i++;
                }
            }
            record.get(i).getRight().set(record_index, state);
            return true;
        }
    }

    public boolean insertState(int index, float time, List<Integer> states) {
        if (index < 0 || index > record.size()) {
            return false;
        } else {
            Pair <Float, List<Integer>> thisCycle = new Pair<>(time, states);
            record.remove(index);
            record.add(index, thisCycle);
            return true;
        }
       
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