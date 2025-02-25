package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TrafficLightConfigTests {

    // Test functionality and validation of set tate function
    // Dependent on functionality of addState method
    @Test void testInsertState() {
        TrafficLightConfig trafficLightConfig = new TrafficLightConfig();
        boolean expected;
        boolean result;

        // Test functionality
        expected = true;

        List<Integer> states1 = new ArrayList<>();
        states1.add(0);
        states1.add(1);
        states1.add(1);
        states1.add(0);
        result = trafficLightConfig.insertState(10.f, states1, 0);
        assertEquals(expected, result);
        assertEquals(10.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(0).getRight());

        List<Integer> states2 = new ArrayList<>();
        states2.add(1);
        states2.add(0);
        states2.add(0);
        states2.add(1);
        result = trafficLightConfig.insertState(5.f, states2, 0);
        assertEquals(expected, result);
        // testing correct order of insertion (at start)
        assertEquals(5.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states2, trafficLightConfig.getStateByIndex(0).getRight());
        assertEquals(10.f, trafficLightConfig.getStateByIndex(1).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(1).getRight());

        List<Integer> states3 = new ArrayList<>();
        states3.add(1);
        states3.add(1);
        states3.add(1);
        states3.add(1);
        result = trafficLightConfig.insertState(7.5f, states3, 1);
        assertEquals(expected, result);
        // testing correct order of insertion (inbetween)
        assertEquals(5.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states2, trafficLightConfig.getStateByIndex(0).getRight());
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(1).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(1).getRight());
        assertEquals(10.f, trafficLightConfig.getStateByIndex(2).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(2).getRight());

        List<Integer> states4 = new ArrayList<>();
        states4.add(1);
        states4.add(1);
        states4.add(1);
        states4.add(0);
        result = trafficLightConfig.insertState(12.5f, states4, 3);
        assertEquals(expected, result);
        // testing correct order of insertion (at end)
        assertEquals(5.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states2, trafficLightConfig.getStateByIndex(0).getRight());
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(1).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(1).getRight());
        assertEquals(10.f, trafficLightConfig.getStateByIndex(2).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(2).getRight());
        assertEquals(12.5f, trafficLightConfig.getStateByIndex(3).getLeft());
        assertEquals(states4, trafficLightConfig.getStateByIndex(3).getRight());

        // Test validation
        expected = false;

        // should not insert with negative index
        result = trafficLightConfig.insertState(12.5f, states4, -1);
        assertEquals(expected, result);
        // assert same values as before
        assertEquals(5.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states2, trafficLightConfig.getStateByIndex(0).getRight());
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(1).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(1).getRight());
        assertEquals(10.f, trafficLightConfig.getStateByIndex(2).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(2).getRight());
        assertEquals(12.5f, trafficLightConfig.getStateByIndex(3).getLeft());
        assertEquals(states4, trafficLightConfig.getStateByIndex(3).getRight());

        // should not insert with index greater than length 
        result = trafficLightConfig.insertState(12.5f, states4, 5);
        assertEquals(expected, result);
        // assert same values as before
        assertEquals(5.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states2, trafficLightConfig.getStateByIndex(0).getRight());
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(1).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(1).getRight());
        assertEquals(10.f, trafficLightConfig.getStateByIndex(2).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(2).getRight());
        assertEquals(12.5f, trafficLightConfig.getStateByIndex(3).getLeft());
        assertEquals(states4, trafficLightConfig.getStateByIndex(3).getRight());
    }

    // Test functionality and validation of remove state function
    // Dependent on addState functionality
    @Test
    void testRemoveState() {
        TrafficLightConfig trafficLightConfig = new TrafficLightConfig();
        boolean expected;
        boolean result;

        // Test functionality
        // Must first add states to remove, this is why this is dependent
        // on add state method functionality
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
        trafficLightConfig.addState(7.5f, states3);

        // remove two states and confirm correctness
        expected = true;

        result = trafficLightConfig.removeState(1);
        assertEquals(expected, result);
        assertEquals(10.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(0).getRight());
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(1).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(1).getRight());

        result = trafficLightConfig.removeState(0);
        assertEquals(expected, result);
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(0).getRight());

        // test validation
        expected = false;
        // Attempt to remove index out of bounds
        result = trafficLightConfig.removeState(1);
        assertEquals(expected, result);
        // assert no changes
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(0).getRight());

        // Attempt to remove with negative index
        result = trafficLightConfig.removeState(-1);
        assertEquals(expected, result);
        // assert no changes
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(0).getRight());

        // remove final state
        expected = true;
        result = trafficLightConfig.removeState(0);
        assertEquals(expected, result);

        // test validation; attempting to remove from empty states
        expected = false;
        result = trafficLightConfig.removeState(0);
        assertEquals(expected, result);
    }

    // Test functionality and validation of add state function
    @Test
    void testAddState() {
        TrafficLightConfig trafficLightConfig = new TrafficLightConfig();
        boolean expected;
        boolean result;

        // Test functionality, all valid states
        expected = true;

        List<Integer> states1 = new ArrayList<>();
        states1.add(0);
        states1.add(1);
        states1.add(1);
        states1.add(0);
        result = trafficLightConfig.addState(10.f, states1);
        assertEquals(expected, result);
        // get statebyindex returns state as pair object
        // pair.left = time, pair.right = list of states
        assertEquals(10.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(0).getRight());

        List<Integer> states2 = new ArrayList<>();
        states2.add(1);
        states2.add(0);
        states2.add(0);
        states2.add(1);
        result = trafficLightConfig.addState(10.f, states2);
        assertEquals(expected, result);
        assertEquals(10.f, trafficLightConfig.getStateByIndex(1).getLeft());
        assertEquals(states2, trafficLightConfig.getStateByIndex(1).getRight());

        List<Integer> states3 = new ArrayList<>();
        states3.add(0);
        states3.add(0);
        states3.add(0);
        states3.add(0);
        result = trafficLightConfig.addState(7.5f, states3);
        assertEquals(expected, result);
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(2).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(2).getRight());

        // Test validation
        // Should not accept empty state
        expected = false;

        List<Integer> states4 = new ArrayList<>();
        result = trafficLightConfig.addState(7.5f, states4);
        assertEquals(expected, result);
        // assert record should be same as before, no changes
        assertEquals(10.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(0).getRight());
        assertEquals(10.f, trafficLightConfig.getStateByIndex(1).getLeft());
        assertEquals(states2, trafficLightConfig.getStateByIndex(1).getRight());
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(2).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(2).getRight());

        // should not accept negative time
        result = trafficLightConfig.addState(-10.f, states3);
        assertEquals(expected, result);
        // assert record should be same as before, no changes
        assertEquals(10.f, trafficLightConfig.getStateByIndex(0).getLeft());
        assertEquals(states1, trafficLightConfig.getStateByIndex(0).getRight());
        assertEquals(10.f, trafficLightConfig.getStateByIndex(1).getLeft());
        assertEquals(states2, trafficLightConfig.getStateByIndex(1).getRight());
        assertEquals(7.5f, trafficLightConfig.getStateByIndex(2).getLeft());
        assertEquals(states3, trafficLightConfig.getStateByIndex(2).getRight());
    }

    // Test functionality of insert state function
    // Dependent on addState functionality, getState functionality
    @Test
    void testSetState() {
        /*
        * Set state given index, time and list of states
        * Essentially replaces the state at the given index with the given state
        */
        TrafficLightConfig trafficLightConfig = new TrafficLightConfig();
        boolean expected;
        boolean result;

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
        trafficLightConfig.addState(7.5f, states3);

        List<Integer> states4 = new ArrayList<>();
        states4.add(0);
        states4.add(0);
        states4.add(0);
        states4.add(1);

        // Test functionality of inserting and replacing states
        expected = true;
        result = trafficLightConfig.setState(2, 7.5f, states4);
        assertEquals(states4, trafficLightConfig.getStates(21.f));
        assertEquals(expected, result);

        result = trafficLightConfig.setState(2, 7.5f, states3);
        assertEquals(states3, trafficLightConfig.getStates(21.f));
        assertEquals(expected, result);

        // Test functionality of inserting combined with get states
        // the new states5 is longer than the one it is replacing
        // this tests that the returned states reflect the new timing

        List<Integer> states5 = new ArrayList<>();
        states4.add(0);
        states4.add(0);
        states4.add(0);
        states4.add(0);
        result = trafficLightConfig.setState(0, 20.f, states5);
        assertEquals(expected, result);

        assertEquals(states5, trafficLightConfig.getStates(0.f));
        assertEquals(states5, trafficLightConfig.getStates(10.f));
        assertEquals(states5, trafficLightConfig.getStates(15.f));
        assertEquals(states2, trafficLightConfig.getStates(25.f));
        assertEquals(states3, trafficLightConfig.getStates(35.f));

        // Test validation
        // Should reject when index < 0 or index > # of traffic light states
        expected = false;

        result = trafficLightConfig.setState(-1, 20.f, states5);
        assertEquals(expected, result);
        // assert the beahviour should be the same as before
        assertEquals(states5, trafficLightConfig.getStates(0.f));
        assertEquals(states5, trafficLightConfig.getStates(10.f));
        assertEquals(states5, trafficLightConfig.getStates(15.f));
        assertEquals(states2, trafficLightConfig.getStates(25.f));
        assertEquals(states3, trafficLightConfig.getStates(35.f));

        result = trafficLightConfig.setState(3, 20.f, states5);
        assertEquals(expected, result);
        // assert the beahviour should be the same as before
        assertEquals(states5, trafficLightConfig.getStates(0.f));
        assertEquals(states5, trafficLightConfig.getStates(10.f));
        assertEquals(states5, trafficLightConfig.getStates(15.f));
        assertEquals(states2, trafficLightConfig.getStates(25.f));
        assertEquals(states3, trafficLightConfig.getStates(35.f));
    }

    // Test functionality of get state function
    // Dependent on addState functionality
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
        trafficLightConfig.addState(7.5f, states3);

        // 0 -> 10 is state 1
        // 10 -> 20 is state 2
        // 20 -> 27.5 is state 3
        // 27.5 -> 37.5 is state 1
        // 37.5 -> 45 is state 2
        List<Integer> returnedStateAtZero = trafficLightConfig.getStates(0.f); // state 1
        List<Integer> returnedStateAtTen = trafficLightConfig.getStates(10.f); // state 2
        List<Integer> returnedStateAtFifteen = trafficLightConfig.getStates(15.f); // state 2
        List<Integer> returnedStateAtTwentyfive = trafficLightConfig.getStates(25.f); // state 3
        List<Integer> returnedStateAtThirty = trafficLightConfig.getStates(30.f); // state 1
        List<Integer> returnedStateAtThirtyFive = trafficLightConfig.getStates(37.5f); // state 2

        assertEquals(states1, returnedStateAtZero);
        assertEquals(states2, returnedStateAtTen);
        assertEquals(states2, returnedStateAtFifteen);
        assertEquals(states3, returnedStateAtTwentyfive);
        assertEquals(states1, returnedStateAtThirty);
        assertEquals(states2, returnedStateAtThirtyFive);
    }

}