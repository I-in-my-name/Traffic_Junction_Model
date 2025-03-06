package com.trafficjunction.Junction_Classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JunctionTests {

    private static Junction junction;

    @BeforeAll
    static void setup() {
        junction = new Junction();
    }

    @Test
    void testLaneBus() {
        // Test typical use case
        boolean result;
        boolean expected;

        // Setup junction so we know its values
        expected = true;
        Junction junction = new Junction();
        junction.addEntryLane(0);
        junction.addEntryLane(0);
        junction.addEntryLane(1);
        junction.addEntryLane(1);
        junction.addEntryLane(2);
        junction.addEntryLane(2);

        result = junction.setLaneBus(0, 1, true);
        assertEquals(expected, result);
        assertEquals(true, junction.getEntryLanes().get(0).get(1).isBusLane());

        // Test validation for index that doesn't exist
        expected = false;
        result = junction.setLaneBus(0, 2, true);
        assertEquals(expected, result);
    }

    // Dependent on addEntryLane
    @Test
    void testSetLaneTrafficLight() {
        Junction junction = new Junction();

        junction.addEntryLane(0);
        junction.addEntryLane(0);
        junction.addEntryLane(1);

        TrafficLight light1 = new TrafficLight();
        TrafficLight light2 = new TrafficLight();

        // Test basic functionality of setting light
        boolean expected = true;
        boolean result;

        result = junction.setLaneTrafficLight(0, 1, light1);
        assertEquals(expected, result);
        assertEquals(light1, junction.getEntryLanes().get(0).get(1).getTrafficLight());

        result = junction.setLaneTrafficLight(0, 1, light2);
        assertEquals(expected, result);
        assertEquals(light2, junction.getEntryLanes().get(0).get(1).getTrafficLight());

        // Test validation. No lanes for side 2, so should reject
        expected = false;
        result = junction.setLaneTrafficLight(2, 0, light1);
        assertEquals(expected, result);

        // No index 1 for side 1, so should reject
        result = junction.setLaneTrafficLight(1, 1, light1);
        assertEquals(expected, result);
    }

    // Test functionality and validation of remove exit lane
    // dependent on addExitLane
    // the same exact test as removeEntryLane
    // TODO: Merge these methods into one? Or just okay to repeat logic?
    // might be okay to just repeat it as combining could more complex than its
    // worth
    @Test
    void testRemoveExitLane() {
        /**
         * Remove Exit Lane method
         * Parameters - the side which we want to remove a lane in.
         * 
         * @return - If the lane was removed.
         */
        // new junction so we know it has 0 exit lanes to begin with
        Junction junction = new Junction();
        boolean expected;
        boolean result;
        int expectedSize;

        // test validation for side < 0 and side > 3
        expected = false;

        result = junction.removeExitLane(-1);
        assertEquals(expected, result);

        result = junction.removeExitLane(4);
        assertEquals(expected, result);

        // test functionality for removing lanes
        // must first add lanes to remove (this is why this is dependent
        // on addExitLane method)
        junction.addExitLane(1);
        junction.addExitLane(1);
        junction.addExitLane(1);

        expected = true;

        result = junction.removeExitLane(1);
        expectedSize = 2;
        assertEquals(expectedSize, junction.getexitLanes().get(1).size());
        assertEquals(expected, result);

        result = junction.removeExitLane(1);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getexitLanes().get(1).size());
        assertEquals(expected, result);

        result = junction.removeExitLane(1);
        expectedSize = 0;
        assertEquals(expectedSize, junction.getexitLanes().get(1).size());
        assertEquals(expected, result);

        junction.addExitLane(0);
        result = junction.removeExitLane(0);
        expectedSize = 0;
        assertEquals(expectedSize, junction.getexitLanes().get(0).size());
        assertEquals(expected, result);

        junction.addExitLane(2);
        junction.addExitLane(2);
        junction.addExitLane(2);
        junction.addExitLane(2);
        result = junction.removeExitLane(2);
        expectedSize = 3;
        assertEquals(expectedSize, junction.getexitLanes().get(2).size());
        assertEquals(expected, result);

        junction.addExitLane(3);
        junction.addExitLane(3);
        junction.addExitLane(3);
        junction.addExitLane(3);
        junction.addExitLane(3);
        junction.addExitLane(3);
        result = junction.removeExitLane(3);
        expectedSize = 4;
        assertEquals(expectedSize, junction.getexitLanes().get(3).size());
        assertEquals(expected, result);

        // test validation when attempting to remove from 0 exit lanes
        expected = false;
        result = junction.removeExitLane(1);
        expectedSize = 0;
        assertEquals(expectedSize, junction.getexitLanes().get(1).size());
        assertEquals(expected, result);
    }

    // Test functionality and validation of remove entry lane
    // dependent on addEntryLane
    @Test
    void testRemoveEntryLane() {
        /**
         * Remove Entry Lane method
         * Parameters - the side which we want to remove a lane in.
         * 
         * @return - If the lane was removed.
         */
        // new junction so we know it has 0 entry lanes to begin with
        Junction junction = new Junction();
        boolean expected;
        boolean result;
        int expectedSize;

        // test validation for side < 0 and side > 3
        expected = false;

        result = junction.removeEntryLane(-1);
        assertEquals(expected, result);

        result = junction.removeEntryLane(4);
        assertEquals(expected, result);

        // test functionality for removing lanes
        // must first add lanes to remove (this is why this is dependent
        // on addEntryLane method)
        junction.addEntryLane(1);
        junction.addEntryLane(1);
        junction.addEntryLane(1);

        expected = true;

        result = junction.removeEntryLane(1);
        expectedSize = 2;
        assertEquals(expectedSize, junction.getEntryLanes().get(1).size());
        assertEquals(expected, result);

        result = junction.removeEntryLane(1);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getEntryLanes().get(1).size());
        assertEquals(expected, result);

        result = junction.removeEntryLane(1);
        expectedSize = 0;
        assertEquals(expectedSize, junction.getEntryLanes().get(1).size());
        assertEquals(expected, result);

        junction.addEntryLane(0);
        result = junction.removeEntryLane(0);
        expectedSize = 0;
        assertEquals(expectedSize, junction.getEntryLanes().get(0).size());
        assertEquals(expected, result);

        junction.addEntryLane(2);
        junction.addEntryLane(2);
        junction.addEntryLane(2);
        junction.addEntryLane(2);
        result = junction.removeEntryLane(2);
        expectedSize = 3;
        assertEquals(expectedSize, junction.getEntryLanes().get(2).size());
        assertEquals(expected, result);

        junction.addEntryLane(3);
        junction.addEntryLane(3);
        junction.addEntryLane(3);
        junction.addEntryLane(3);
        junction.addEntryLane(3);
        junction.addEntryLane(3);
        result = junction.removeEntryLane(3);
        expectedSize = 4;
        assertEquals(expectedSize, junction.getEntryLanes().get(3).size());
        assertEquals(expected, result);

        // test validation when attempting to remove from 0 entry lanes
        expected = false;
        result = junction.removeEntryLane(1);
        expectedSize = 0;
        assertEquals(expectedSize, junction.getEntryLanes().get(1).size());
        assertEquals(expected, result);
    }

    // Test functionality and validation of add entry lane
    @Test
    void testAddEntryLane() {
        /*
         * addEntryLane
         * Should add a default entry lane to the given side if that entry side
         * has less than 4 lanes already
         */
        // new junction so we know it has 0 entry lanes to begin with
        Junction junction = new Junction();
        boolean expected;
        boolean result;
        int expectedSize;

        // Side parameter only valid when >= 0 and < 4,
        // test this validation
        expected = false;

        result = junction.addEntryLane(-1);
        assertEquals(expected, result);

        result = junction.addEntryLane(4);
        assertEquals(expected, result);

        // Test functionality of adding a lane
        expected = true;

        result = junction.addEntryLane(0);
        assertEquals(expected, result);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getEntryLanes().get(0).size());

        result = junction.addEntryLane(0);
        assertEquals(expected, result);
        expectedSize = 2;
        assertEquals(expectedSize, junction.getEntryLanes().get(0).size());

        result = junction.addEntryLane(0);
        assertEquals(expected, result);
        expectedSize = 3;
        assertEquals(expectedSize, junction.getEntryLanes().get(0).size());

        result = junction.addEntryLane(0);
        assertEquals(expected, result);
        expectedSize = 4;
        assertEquals(expectedSize, junction.getEntryLanes().get(0).size());

        result = junction.addEntryLane(0);
        assertEquals(expected, result);
        expectedSize = 5;
        assertEquals(expectedSize, junction.getEntryLanes().get(0).size());

        result = junction.addEntryLane(1);
        assertEquals(expected, result);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getEntryLanes().get(1).size());

        result = junction.addEntryLane(2);
        assertEquals(expected, result);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getEntryLanes().get(2).size());

        result = junction.addEntryLane(3);
        assertEquals(expected, result);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getEntryLanes().get(3).size());

        // Test validation of not adding more than 5 to one side
        expected = false;
        result = junction.addEntryLane(0);
        assertEquals(expected, result);
        expectedSize = 5;
        assertEquals(expectedSize, junction.getEntryLanes().get(0).size());
    }

    // Test functionality and validation of add exit lane
    // same exact testing logic of adding entry lane test (above)
    // TODO: Merge these methods / logic in some way?
    @Test
    void testAddExitLane() {
        /*
         * addExitLane
         * Should add a default exit lane to the given side if that exit side
         * has less than 4 lanes already
         */
        // new junction so we know it has 0 entry lanes to begin with
        Junction junction = new Junction();
        boolean expected;
        boolean result;
        int expectedSize;

        // Side parameter only valid when >= 0 and < 4,
        // test this validation
        expected = false;

        result = junction.addExitLane(-1);
        assertEquals(expected, result);

        result = junction.addExitLane(4);
        assertEquals(expected, result);

        // Test functionality of adding a lane
        expected = true;

        result = junction.addExitLane(0);
        assertEquals(expected, result);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getexitLanes().get(0).size());

        result = junction.addExitLane(0);
        assertEquals(expected, result);
        expectedSize = 2;
        assertEquals(expectedSize, junction.getexitLanes().get(0).size());

        result = junction.addExitLane(0);
        assertEquals(expected, result);
        expectedSize = 3;
        assertEquals(expectedSize, junction.getexitLanes().get(0).size());

        result = junction.addExitLane(0);
        assertEquals(expected, result);
        expectedSize = 4;
        assertEquals(expectedSize, junction.getexitLanes().get(0).size());

        result = junction.addExitLane(0);
        assertEquals(expected, result);
        expectedSize = 5;
        assertEquals(expectedSize, junction.getexitLanes().get(0).size());

        result = junction.addExitLane(1);
        assertEquals(expected, result);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getexitLanes().get(1).size());

        result = junction.addExitLane(2);
        assertEquals(expected, result);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getexitLanes().get(2).size());

        result = junction.addExitLane(3);
        assertEquals(expected, result);
        expectedSize = 1;
        assertEquals(expectedSize, junction.getexitLanes().get(3).size());

        // Test validation of not adding more than 5 to one side
        expected = false;
        result = junction.addExitLane(0);
        assertEquals(expected, result);
        expectedSize = 5;
        assertEquals(expectedSize, junction.getexitLanes().get(0).size());
    }

    // Test functionality of verifyJunction
    // Dependent on addEntryLane
    @Test
    void testVerifyJunctionPositive() {
        // junction is valid if 4 entry lanes and each entry lane has valid direction

        // Test plan
        // Case 1: No entry lanes. Expected: False
        // Case 2: 3 entry lanes (all valid directions). Expected: False
        // Case 3: 4 entry lanes (all valid directions). Expected: true
        // Case 4: 4 entry lanes (one invalid direction). Expected: false

        // case 1
        Junction junctionOne = new Junction();
        boolean expected = false;
        assertEquals(expected, junctionOne.verifyJunction());

        // case 2
        junctionOne.getEntryLanes().get(0).add(new Lane(1.f, new TrafficLight(), "L"));
        junctionOne.getEntryLanes().get(1).add(new Lane(1.f, new TrafficLight(), "F"));
        junctionOne.getEntryLanes().get(2).add(new Lane(1.f, new TrafficLight(), "R"));
        assertEquals(expected, junctionOne.verifyJunction());

        // case 3
        Junction junctionTwo = new Junction();
        junctionTwo.getEntryLanes().get(0).add(new Lane(1.f, new TrafficLight(), "L"));
        junctionTwo.getEntryLanes().get(1).add(new Lane(1.f, new TrafficLight(), "R"));
        junctionTwo.getEntryLanes().get(2).add(new Lane(1.f, new TrafficLight(), "FL"));
        junctionTwo.getEntryLanes().get(3).add(new Lane(1.f, new TrafficLight(), "LF"));
        expected = true;
        assertEquals(expected, junctionTwo.verifyJunction());

        // case 4
        Junction junctionThree = new Junction();
        junctionThree.getEntryLanes().get(0).add(new Lane(1.f, new TrafficLight(), "L"));
        junctionThree.getEntryLanes().get(1).add(new Lane(1.f, new TrafficLight(), "R"));
        junctionThree.getEntryLanes().get(2).add(new Lane(1.f, new TrafficLight(), "FL"));
        junctionThree.getEntryLanes().get(3).add(new Lane(1.f, new TrafficLight(), "LFA"));
        expected = false;
        assertEquals(expected, junctionThree.verifyJunction());
    }

    @Test
    void testsetNumLanesEntryNegative() {
        // Testing function:
        /**
         * Returns a boolean success or failure
         * Given number must >= 0 and <= 5 (otherwise failure)
         * If success, set the number of entry lanes at the
         * given side to the given number
         * 
         * @param side   Side to update as a String
         * @param number the number of lanes to set to
         * @return boolean success or failure
         */

        // test negative numbers
        boolean result = junction.setNumLanesEntry(0, -1);
        assertEquals(false, result);
        // test number = 0 (edge case)
        result = junction.setNumLanesEntry(0, 0);
        assertEquals(true, result);
        // test number > 5
        result = junction.setNumLanesEntry(0, 10);
        assertEquals(false, result);

        // test side validation
        // 0 <= side <= 3
        // test negative
        result = junction.setNumLanesEntry(-1, 2);
        assertEquals(false, result);
        // test > 3
        result = junction.setNumLanesEntry(4, 2);
        assertEquals(false, result);
    }

    @Test
    void testsetNumLanesEntryPositive() {
        // Testing function:
        /**
         * Returns a boolean success or failure
         * Given number must > 0 and <= 5 (otherwise failure)
         * If success, set the number of entry lanes at the
         * given side to the given number
         * 
         * @param side   Side to update as a String
         * @param number the number of lanes to set to
         * @return boolean success or failure
         */
        boolean result;
        // test it works
        // So given (int side, int number) the lane at side should have number lanes
        result = junction.setNumLanesEntry(0, 3);
        assertEquals(true, result);
        // should be 3 lanes in 0
        assertEquals(3, junction.getEntryLanes().get(0).size());

        result = junction.setNumLanesEntry(0, 5);
        assertEquals(true, result);
        // should be 5 lanes in 0
        assertEquals(5, junction.getEntryLanes().get(0).size());

        result = junction.setNumLanesEntry(2, 4);
        assertEquals(true, result);
        // should be 4 lanes in 2
        assertEquals(4, junction.getEntryLanes().get(2).size());

        result = junction.setNumLanesEntry(1, 3);
        assertEquals(true, result);
        // should be 3 lanes in 0
        assertEquals(3, junction.getEntryLanes().get(1).size());

        result = junction.setNumLanesEntry(3, 0);
        assertEquals(true, result);
        // should be 0 lanes in 3
        assertEquals(0, junction.getEntryLanes().get(3).size());
    }

    // passing invalid data and ensuring error handling
    @Test
    void testSetLaneDirectionsNegative() {
        /*
         * Returns boolean
         * 
         * Given a side and index, set the lane's direction to the one given
         * 
         * @param side int the no. side it is (0-3)
         * 
         * @param index int the index lane to set (>= 0)
         * 
         * @param direction string the new direction to set it to
         */
        boolean result;

        // test 0 <= side <= 3
        result = junction.setLaneDirections(-1, 2, "l");
        assertEquals(false, result);
        result = junction.setLaneDirections(10, 2, "f");
        assertEquals(false, result);

        // test 0 <= index
        result = junction.setLaneDirections(2, -1, "l");
        assertEquals(false, result);

        // test for large values of index
        result = junction.setLaneDirections(2, 1000, "l");
        assertEquals(false, result);

        // test direction must only contain "lfr" chars
        result = junction.setLaneDirections(2, 2, "l ");
        assertEquals(false, result);
        result = junction.setLaneDirections(2, 2, "");
        assertEquals(false, result);
        result = junction.setLaneDirections(2, 2, "frj");
        assertEquals(false, result);
    }

    // passing valid data and ensuring correct behaviour
    // Dependent on setNumLanesEntry, getEntryLanes, Lane.getDirection
    @Test
    void testSetLaneDirectionsPositive() {
        /*
         * Returns boolean
         * 
         * Given a side and index, set the lane's direction to the one given
         * 
         * @param side int the no. side it is (0-3)
         * 
         * @param index int the index lane to set (>= 0)
         * 
         * @param direction string the new direction to set it to
         */
        boolean result;
        String expectedDirection, newDirection;
        int side, index;

        // setting number of entry lanes for each side for testing purposed
        // (can't test lane has set direction if lane doesn't exist)
        junction.setNumLanesEntry(0, 4);
        junction.setNumLanesEntry(1, 3);
        junction.setNumLanesEntry(2, 1);
        junction.setNumLanesEntry(3, 2);

        side = 0;
        index = 3;
        expectedDirection = "l";
        result = junction.setLaneDirections(side, index, expectedDirection);
        assertEquals(true, result);
        newDirection = junction.getEntryLanes().get(side).get(index).getDirection();
        assertEquals(expectedDirection, newDirection);

        side = 1;
        index = 0;
        expectedDirection = "lr";
        result = junction.setLaneDirections(side, index, expectedDirection);
        assertEquals(true, result);
        newDirection = junction.getEntryLanes().get(side).get(index).getDirection();
        assertEquals(expectedDirection, newDirection);

        side = 2;
        index = 0;
        expectedDirection = "r";
        result = junction.setLaneDirections(side, index, expectedDirection);
        assertEquals(true, result);
        newDirection = junction.getEntryLanes().get(side).get(index).getDirection();
        assertEquals(expectedDirection, newDirection);

        side = 3;
        index = 1;
        expectedDirection = "lfr";
        result = junction.setLaneDirections(side, index, expectedDirection);
        assertEquals(true, result);
        newDirection = junction.getEntryLanes().get(side).get(index).getDirection();
        assertEquals(expectedDirection, newDirection);
    }

    @Test
    void testCreateVehicle() {
        Junction junction = new Junction();

        junction.addEntryLane(0);
        junction.addEntryLane(0);
        junction.addEntryLane(1);
        junction.addEntryLane(1);
        junction.addEntryLane(2);
        junction.addEntryLane(2);
        junction.addEntryLane(3);
        junction.addEntryLane(3);

        junction.addExitLane(0);
        junction.addExitLane(0);
        junction.addExitLane(1);
        junction.addExitLane(1);
        junction.addExitLane(2);
        junction.addExitLane(2);
        junction.addExitLane(3);
        junction.addExitLane(3);

        junction.connectJunction();

        junction.createVehicles(10.f);

        System.out.println(junction);
    }

}
