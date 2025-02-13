package com.trafficjunction.Junction_Classes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LaneTests {

    private static Lane lane;

    @BeforeAll
    static void setup() {
        // Mock lane object
        // length of 10, traffic light in state 1, forward direction
        lane = new Lane(10.0f, new TrafficLight(), "F");
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
       //result = lane.setDirection("A");
       // direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       directionBefore = lane.getDirection();
       lane.setDirection("");
       // direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       directionBefore = lane.getDirection();
       lane.setDirection("F ");
       // direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       directionBefore = lane.getDirection();
       lane.setDirection("R ");
       // direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       // Test actual changing
       lane.setDirection("F");
       //assertEquals(result, true);
       assertEquals("F", lane.getDirection());

       lane.setDirection("R");
       //assertEquals(result, true);
       assertEquals("R", lane.getDirection());

       lane.setDirection("L");
       //assertEquals(result, true);
       assertEquals("L", lane.getDirection());

       lane.setDirection("F");
       //assertEquals(result, true);
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
       assertEquals(lengthBefore, lane.getLength());

       lengthBefore = lane.getLength();
       lane.setLength(0);
       // direction should be unchanged
       assertEquals(lengthBefore, lane.getLength());

       lengthBefore = lane.getLength();
       lane.setLength(1000000);
       // direction should be unchanged
       assertEquals(lengthBefore, lane.getLength());

       lengthBefore = lane.getLength();
       lane.setLength(-100.1f);
       // direction should be unchanged
       assertEquals(lengthBefore, lane.getLength());

       // Test actual changing
       lane.setLength(10);
       assertEquals(10, lane.getLength());

       lane.setLength(25.12f);
       assertEquals(25.12, lane.getLength());

       lane.setLength(0.01f);
       assertEquals(0.01, lane.getLength());

       lane.setLength(10);
       assertEquals(10, lane.getLength());
    }

    @Test
    void testIsFull() {
        
    }

}
