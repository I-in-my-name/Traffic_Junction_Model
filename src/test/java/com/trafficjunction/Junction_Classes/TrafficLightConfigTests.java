package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TrafficLightConfigTests {

    // Test functionality of get state function
    // Depends on insertState
    @Test
    void testGetStatePositive() {
        /*
        * Returns list of integers representing the state of each traffic light
        * Lists are given on construction
        * Time each state should be active also given in construction
        *
        * @param float  time    Absolute time of simulation
        */
        TrafficLightConfig trafficLightConfig = new TrafficLightConfig();

        List<Integer> states = new ArrayList<>();
        states.add(0);
        states.add(1);
        states.add(1);
        states.add(0);
        trafficLightConfig.insertState(0, 10.f, states);

        states = new ArrayList<>();
        states.add(1);
        states.add(0);
        states.add(0);
        states.add(1);
        trafficLightConfig.insertState(1, 10.f, states);

        states = new ArrayList<>();
        states.add(0);
        states.add(0);
        states.add(0);
        states.add(0);
        trafficLightConfig.insertState(2, 10.f, states);

        List<Integer> returnedStateAtZero = trafficLightConfig.getStates(0.f);
        List<Integer> returnedStateAtTen = trafficLightConfig.getStates(0.f);
        List<Integer> returnedStateAtFifteen = trafficLightConfig.getStates(0.f);
        List<Integer> returnedStateAtTwentyfive = trafficLightConfig.getStates(0.f);
        List<Integer> returnedStateAtThirty = trafficLightConfig.getStates(0.f);

        assertEquals();
        
    }

}