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
        // Case 5: 5 entry lanes (all valid directions). Expected false

        // case 1
        Junction junction = new Junction();
        boolean expected = false;
        assertEquals(expected, junction.verifyJunction());

        // case 2
        junction.getEntryLanes().get(0).add(new Lane(1.f, new TrafficLight(), "L"));
        junction.getEntryLanes().get(1).add(new Lane(1.f, new TrafficLight(), "F"));
        junction.getEntryLanes().get(2).add(new Lane(1.f, new TrafficLight(), "R"));
        assertEquals(expected, junction.verifyJunction());

        // case 3
        Junction junction = new Junction();
        junction.getEntryLanes().get(0).add(new Lane(1.f, new TrafficLight(), "L"));
        junction.getEntryLanes().get(1).add(new Lane(1.f, new TrafficLight(), "R"));
        junction.getEntryLanes().get(2).add(new Lane(1.f, new TrafficLight(), "FL"));
        junction.getEntryLanes().get(3).add(new Lane(1.f, new TrafficLight(), "LF"));
        expected = true;
        assertEquals(expected, junction.verifyJunction());

        // case 4
        Junction junction = new Junction();
        junction.getEntryLanes().get(0).add(new Lane(1.f, new TrafficLight(), "L"));
        junction.getEntryLanes().get(1).add(new Lane(1.f, new TrafficLight(), "R"));
        junction.getEntryLanes().get(2).add(new Lane(1.f, new TrafficLight(), "FL"));
        junction.getEntryLanes().get(3).add(new Lane(1.f, new TrafficLight(), "LFA"));
        expected = false;
        assertEquals(expected, junction.verifyJunction());

        // case 4
        Junction junction = new Junction();
        junction.getEntryLanes().get(0).add(new Lane(1.f, new TrafficLight(), "L"));
        junction.getEntryLanes().get(1).add(new Lane(1.f, new TrafficLight(), "R"));
        junction.getEntryLanes().get(2).add(new Lane(1.f, new TrafficLight(), "FL"));
        junction.getEntryLanes().get(3).add(new Lane(1.f, new TrafficLight(), "LF"));
        junction.getEntryLanes().get(4).add(new Lane(1.f, new TrafficLight(), "F"));
        expected = false;
        assertEquals(expected, junction.verifyJunction());
    }
    
    @Test
    void testsetNumLanesEntryNegative() {
        // Testing function:
        /**
        * Returns a boolean success or failure
        * Given number must >= 0 and <= 5 (otherwise failure)
        * If success, set the number of entry lanes at the
        * given side to the given number
        * @param    side    Side to update as a String
        * @param    number  the number of lanes to set to
        * @return   boolean success or failure
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
        * @param    side    Side to update as a String
        * @param    number  the number of lanes to set to
        * @return   boolean success or failure
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
         * @param side      int     the no. side it is (0-3)
         * @param index     int     the index lane to set (>= 0)
         * @param direction string  the new direction to set it to
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
         * @param side      int     the no. side it is (0-3)
         * @param index     int     the index lane to set (>= 0)
         * @param direction string  the new direction to set it to
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

}

