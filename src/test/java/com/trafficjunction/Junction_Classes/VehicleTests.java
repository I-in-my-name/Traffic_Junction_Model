package com.trafficjunction.Junction_Classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class VehicleTests {

    // Depends on Lane.getVehicles, addComingLane, addGoingLane
    // Test working functionality of update function
    @Test
    void testUpdatePositive() {
        /*
        * @param    Lane    lane the vehicle is in
        * @param    float   time the current time
        */

        // Test case of car at front of queue, traffic light green
        // i.e. car should: 
        TrafficLight trafficLight = new TrafficLight();
        trafficLight.setState(1); // green

        Lane entryLane = new Lane(30.f, trafficLight, "lfr");
        Lane exitLane = new Lane(30.f, trafficLight, "lfr");
        exitLane.addComingLane(entryLane);
        entryLane.addGoingLane(exitLane);

        Vehicle vehicle = new Vehicle(1.f, 1.f, 1.f);
        entryLane.addVehicle(vehicle);
        float initialPosition = entryLane.getVehicles().get(0).getLeft();

        //// if 0 time has passed, nothing should change.
        assertEquals(initialPosition, entryLane.getVehicles().get(0).getLeft());
        System.out.println(entryLane);
        vehicle.update(1.f, entryLane);
        System.out.println(entryLane);
        assertEquals(0, exitLane.getVehicleNum());

        //// if 1 time has passed, something should change
        //// vehicle should be in next lane
        //// (as instataneous acceleration so immediately vehicle speed = 1 distance / time unit,
        //// so in one time unit go 1 distance > distance remaining in lane -> in next lane).
        vehicle.update(2.f, entryLane);

        ////interim tests
        assertEquals(1,entryLane.getTrafficLight().getState());
        assertEquals(false,entryLane.isFull());
        assertEquals(true, entryLane.getGoingTo().size() > 0);
        vehicle.update(1.f, entryLane);

        System.out.println(entryLane);

        assertEquals(0, entryLane.getVehicleNum());
        assertEquals(1, exitLane.getVehicleNum());
        //// TODO: What is position of vehicle in exit lane? Should be back of lane?
    }

}