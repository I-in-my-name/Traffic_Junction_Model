package com.trafficjunction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LaneTests {

    private static Lane lane;

    @BeforeAll
    static void setup() {
        // Mock lane object
        // length of 10, traffic light in state 1, forward direction, not a bus lane
        lane = new Lane(10, new TrafficLight(1), "F", false);
    }
    
    @Test
    void testSetDirection() {
        // Testing function:
        /**
        * Returns void
        * Sets the direction to the given direction with validation
        * Only accepts given direction if "F" "L" or "R"
        * Otherwise direction remains the same
        * @param    direction   String to set new direction
        */
       String directionBefore;
       boolean result;

       // test validation 
       directionBefore = lane.getDirection();
       result = lane.setDirection("A");
       // direction should be unchanged
       assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       directionBefore = lane.getDirection();
       result = lane.setDirection("");
       // direction should be unchanged
       assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       directionBefore = lane.getDirection();
       result = lane.setDirection("F ");
       // direction should be unchanged
       assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       directionBefore = lane.getDirection();
       result = lane.setDirection("R ");
       // direction should be unchanged
       assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       // Test actual changing
       result = lane.setDirection("F");
       assertEquals(result, true);
       assertEquals("F", lane.getDirection());

       result = lane.setDirection("R");
       assertEquals(result, true);
       assertEquals("R", lane.getDirection());

       result = lane.setDirection("L");
       assertEquals(result, true);
       assertEquals("L", lane.getDirection());

       result = lane.setDirection("F");
       assertEquals(result, true);
       assertEquals("F", lane.getDirection());
    }

    @Test
    void testSetLengthTest() {
        // Testing function:
        /**
        * Returns void
        * Sets the length to the given length with validation
        * Only accepts given length if > 0
        // TODO WHAT OTHER VALIDATION? < X?
        * Otherwise length remains the same
        * @param    length   float to set new direction
        */
       float lengthBefore;

       // test validation 
       lengthBefore = lane.getLength();
       lane.setLength(-1);
       // direction should be unchanged
       assertEquals(directionBefore, lane.getLength());

       lengthBefore = lane.getLength();
       lane.setLength(0);
       // direction should be unchanged
       assertEquals(directionBefore, lane.getLength());

       lengthBefore = lane.getLength();
       lane.setLength(10000000000000000000000000);
       // direction should be unchanged
       assertEquals(directionBefore, lane.getLength());

       lengthBefore = lane.getLength();
       lane.setLength(-100.1);
       // direction should be unchanged
       assertEquals(directionBefore, lane.getLength());

       // Test actual changing
       lane.setLength(10);
       assertEquals(10, lane.getLength());

       lane.setLength(25.12);
       assertEquals(25.12, lane.getLength());

       lane.setLength(0.01);
       assertEquals(0.01, lane.getLength());

       lane.setLength(10);
       assertEquals(10, lane.getLength());
    }

    @Test
    void testIsFull() {
        
    }

}
