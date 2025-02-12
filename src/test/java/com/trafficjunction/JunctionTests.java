package com.trafficjunction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JunctionTests {

    private static Junction junction;

    @BeforeAll
    static void setup() {
        junction = new Junction();
    }
    
    @Test
    void testSetNumLanesEntry() {
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

        // test negative numbers
        boolean result = junction.setNumLanesEntry(0, -1);
        assertEquals(false, result);
        // test number = 0 (edge case)
        result = junction.setNumLanesEntry(0, 0);
        assertEquals(false, result);
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

        // test it works 
        // So given (int side, int number) the lane at side should have number lanes
        result = junction.setNumLanesEntry(0, 3);
        assertEquals(true, result);
        // should be 3 lanes in 0 
        assertEquals(3, junction.getEntryLanes().get(0).size())

        result = junction.setNumLanesEntry(0, 5);
        assertEquals(true, result);
        // should be 5 lanes in 0 
        assertEquals(5, junction.getEntryLanes().get(0).size())

        result = junction.setNumLanesEntry(2, 4);
        assertEquals(true, result);
        // should be 4 lanes in 2 
        assertEquals(4, junction.getEntryLanes().get(2).size())

        result = junction.setNumLanesEntry(1, 3);
        assertEquals(true, result);
        // should be 3 lanes in 0 
        assertEquals(3, junction.getEntryLanes().get(1).size())

        result = junction.setNumLanesEntry(3, 0);
        assertEquals(true, result);
        // should be 0 lanes in 3 
        assertEquals(0, junction.getEntryLanes().get(3).size())
    }

}

