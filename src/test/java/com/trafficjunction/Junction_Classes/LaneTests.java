package com.trafficjunction.Junction_Classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


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
       lane.setDirection("f ");
       // direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       directionBefore = lane.getDirection();
       lane.setDirection("r ");
       // direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       // Test actual changing
       lane.setDirection("f");
       //assertEquals(result, true);
       assertEquals("f", lane.getDirection());

       lane.setDirection("r");
       //assertEquals(result, true);
       assertEquals("r", lane.getDirection());

       lane.setDirection("l");
       //assertEquals(result, true);
       assertEquals("l", lane.getDirection());

       lane.setDirection("f");
       //assertEquals(result, true);
       assertEquals("f", lane.getDirection());

       //Also dealing with multiple letters in one 
       lane.setDirection("lfr");
       //assertEquals(result, true);
       assertEquals("lfr", lane.getDirection());

       directionBefore = lane.getDirection();
       lane.setDirection("frl ");
       // direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       directionBefore = lane.getDirection();
       lane.setDirection("frll");
       // direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       lane.setDirection("frr");
       // direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

       directionBefore = lane.getDirection();
       lane.setDirection("frL");
       //direction should be unchanged
       //assertEquals(result, false);
       assertEquals(directionBefore, lane.getDirection());

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
       assertEquals(25.12f, lane.getLength());

       lane.setLength(0.01f);
       assertEquals(0.01f, lane.getLength());

       lane.setLength(10);
       assertEquals(10, lane.getLength());
    }

    @Test
    void testIsFull() {
        /*
         * Returns boolean
         * 
         * Returns true if lane is full, false otherwise
         * Lane is full if there is a car at the back of the queue
         * 
         * No parameters
         */
        boolean result;

         // Test this function by creating empty lane
         // should not be full at start
         // add cars, test is full

         // creating new empty lane as other tests may have added or remove vehicles
         Lane emptyLane = new Lane(10.0f, new TrafficLight(), "F");
         float vehicleLength = 1.f;

        // Since vehicle length of 1.f, and lane length of 10, the lane should
        // be full at 5 vehicles as there is also a 1.f. space after each vehicle

        // should be empty still
        result = emptyLane.isFull();
        assertEquals(false, result);

        // mock values for speed and max speed, irrelevant to this test
         emptyLane.addVehicle(new Vehicle(0.f, 60.f, vehicleLength));
         emptyLane.update(1.f);
         // should still not be full
         result = emptyLane.isFull();
         assertEquals(false, result);

         emptyLane.addVehicle(new Vehicle(1.f, 1.f, vehicleLength));
         emptyLane.update(2.f);
         emptyLane.addVehicle(new Vehicle(2.f, 1.f, vehicleLength));
         emptyLane.update(3.f);
         emptyLane.addVehicle(new Vehicle(3.f, 1.f, vehicleLength));
         emptyLane.update(4.f);
         // should still not be full. At 4 vehicles right now
         result = emptyLane.isFull();
         assertEquals(false, result);

        // 5th vehicle. Should be full after this.
         emptyLane.addVehicle(new Vehicle(4.f, 1.f, vehicleLength));
         emptyLane.update(5.f);

        // should still not be full. At 5 vehicles right now
         result = emptyLane.isFull();
         assertEquals(false, result);
         emptyLane.addVehicle(new Vehicle(5.f, 1.f, vehicleLength));
         emptyLane.update(6.f);
        // sexpected behaviour. full at 6 cars on a lane length 10 
         result = emptyLane.isFull();
         assertEquals(true, result);
    }

    @Test
    void testAddVehicle() {
        /*
         * Returns boolean
         * 
         * Returns true if successfully added vehicle, false otherwise
         * Adds vehicle if adding it doesn't make it full.
         * 
         * @param vehicle   Vehicle    instance of vehicle to add
         */
        // new lane to ensure it is empty
        boolean result;

        float laneLength = 3.5f;
        float vehicleLength = 1.f;

        // traffic light and direction are mock values, irrelevant to test
        Lane lane = new Lane(laneLength, new TrafficLight(), "lfr");
        // speed values are mock values, irrelevant to test
        float vSpeed = laneLength * 3.6f;
        Vehicle vehicle = new Vehicle(0.f, vSpeed, vehicleLength);

        // we need to wait some time before a new car can be added
        // gap of size 1: C_C where C = car and _ is a gap of length 1 meter
        TrafficLight trafficLight = new TrafficLight();
        trafficLight.setState(0);
        lane.setTrafficLight(trafficLight);

        lane.update(1.f);
        result = lane.addVehicle(vehicle);
        assertEquals(true, result);
        lane.update(1.f);
        assertEquals(1, lane.getVehicleNum());
        result = lane.addVehicle(vehicle);
        assertEquals(true, result);
        assertEquals(2, lane.getVehicleNum());

        // Should not add 4th vehicle as 4 > 3.5 -> vehicle lengths > lane length
        result = lane.addVehicle(vehicle);
        assertEquals(false, result);
        // should still have 3 vehicles
        assertEquals(2, lane.getVehicleNum());
    }

    // Dependent on addVehicle
    @Test
    void testRemoveVehicle() {
        /*
         * Returns boolean
         * 
         * Returns true if successfully removed vehicle, false otherwise
         * Remove vehicle from front of queue everytime.
         * Return false is size is 0, i.e. no vehicles to remove
         * 
         * No parameters
         */
        // TODO: Test that the first in list goes vehicle1, vehicle2, then vehicle3
        // not sure how to use JUnit test custom object equality like that

        boolean result;

        // mock values irrelevant to test
        // start with 3 vehicles
        Lane lane = new Lane(100.f, new TrafficLight(), "f");
        Vehicle vehicle1 = new Vehicle(1.f, 1.f, 1.f);
        Vehicle vehicle2 = new Vehicle(1.f, 1.f, 1.f);
        Vehicle vehicle3 = new Vehicle(1.f, 1.f, 1.f);
        lane.addVehicle(vehicle1);
        System.out.println(lane);
        lane.update(1.f);
        lane.update(2.f);
        lane.update(3.f);
        lane.update(4.f);
        lane.update(10.f);
        lane.update(20.f);
        lane.update(30.f);
        System.out.println(lane);
        lane.addVehicle(vehicle2);
        System.out.println(lane);
        lane.update(1.f);
        System.out.println(lane);
        lane.addVehicle(vehicle3);
        System.out.println(lane);

        // remove those 3 vehicles, should work
        result = lane.removeVehicle();
        assertEquals(true, result);
        result = lane.removeVehicle();
        assertEquals(true, result);
        result = lane.removeVehicle();
        assertEquals(true, result);

        // this should not work as no vehicles left
        result = lane.removeVehicle();
        assertEquals(false, result);
    }

}
