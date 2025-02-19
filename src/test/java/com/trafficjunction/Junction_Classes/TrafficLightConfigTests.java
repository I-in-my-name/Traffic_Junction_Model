package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        List<Integer> states1 = new ArrayList<>();
        states1.add(0);
        states1.add(1);
        states1.add(1);
        states1.add(0);
        trafficLightConfig.addState(10.f, states1);

        List<Integer> states2 = new ArrayList<>();
        states2.add(1);
        states2.add(0);
        states2.add(0);
        states2.add(1);
        trafficLightConfig.addState(10.f, states2);

        List<Integer> states3 = new ArrayList<>();
        states3.add(0);
        states3.add(0);
        states3.add(0);
        states3.add(0);
        trafficLightConfig.addState(10.f, states3);

        List<Integer> returnedStateAtZero = trafficLightConfig.getStates(0.f);
        List<Integer> returnedStateAtTen = trafficLightConfig.getStates(10.f);
        List<Integer> returnedStateAtFifteen = trafficLightConfig.getStates(15.f);
        List<Integer> returnedStateAtTwentyfive = trafficLightConfig.getStates(25.f);
        List<Integer> returnedStateAtThirty = trafficLightConfig.getStates(30.f);

        assertEquals(states1, returnedStateAtZero);
        assertEquals(states2);
        
    }

}