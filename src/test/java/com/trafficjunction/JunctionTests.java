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

        // test it works 
        // So given (int side, int number) the lane at side should have number lanes
        result = junction.setNumLanesEntry(0, 3);
        assertEquals(true, result);
        assertEquals(3, # of lanes on side 0);
        assertEquals(3, junction.getEntryLanes().get(0).size())
    }

}

