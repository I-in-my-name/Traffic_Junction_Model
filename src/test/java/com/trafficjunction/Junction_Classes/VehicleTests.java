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
        trafficLight.setState(0); // red

        Lane entryLane = new Lane(30.f, trafficLight, "lfr");
        Lane exitLane = new Lane(30.f, trafficLight, "lfr");
        exitLane.addComingLane(entryLane);
        entryLane.addGoingLane(exitLane);

        System.out.println(entryLane.getGoingTo());

        float speed = 60.f;
        Vehicle vehicle = new Vehicle(1.f, speed, 1.f);
        entryLane.addVehicle(vehicle);
        float initialPosition = entryLane.getVehicles().get(0).getLeft();

        //// if 0 time has passed, nothing should change.
        assertEquals(initialPosition, entryLane.getVehicles().get(0).getLeft());
        System.out.println("\nTime: 1.f");
        System.out.print("Entry:\t");System.out.println(entryLane);
        System.out.print("Exit:\t");System.out.println(exitLane);

        System.out.println("\nTime: 1.f (updated but nothing should change since time has not passed)");
        System.out.println(vehicle.update(1.f, entryLane, 0));
        System.out.print("Entry:\t");System.out.println(entryLane);
        System.out.print("Exit:\t");System.out.println(exitLane);
        
        assertEquals(0, exitLane.getVehicleNum());

        //// if 1 time has passed, something should change
        //// vehicle should be in a different position (It will have moved by [speed / 3.6 which] is the conversion from kmph to mps)
        //vehicle.update(2.f, entryLane);

        ////interim tests
        System.out.println("\nTime: 2.f (moved)");
        System.out.println(vehicle.update(2.f, entryLane,0));
        System.out.print("Entry:\t");System.out.println(entryLane);
        System.out.print("Exit:\t");System.out.println(exitLane);
        float newPosition = 30.f - speed / 3.6f;
        assertEquals(newPosition, entryLane.getVehicles().get(0).getLeft());
        
        
        System.out.println("\nTime: 3.f (moved but still in the entry lane)");
        System.out.println(vehicle.update(3.f, entryLane,0));
        System.out.print("Entry:\t");System.out.println(entryLane);
        System.out.print("Exit:\t");System.out.println(exitLane);
        assertEquals(0.f, entryLane.getVehicles().get(0).getLeft());

        System.out.println("\nTime: 4.f (still stuck in entry lane)");
        System.out.println(vehicle.update(4.f, entryLane,0));
        System.out.print("Entry:\t");System.out.println(entryLane);
        System.out.print("Exit:\t");System.out.println(exitLane);

        trafficLight.setState(1); // Green

        System.out.println("\nTime: 4.5f (moved, now in exit lane)");
        System.out.println(vehicle.update(4.5f, entryLane,0));
        System.out.print("Entry:\t");System.out.println(entryLane);
        System.out.print("Exit:\t");System.out.println(exitLane);
        newPosition = 30.f - speed / 3.6f * 1.5f;
        assertEquals(0, entryLane.getVehicleNum());
        assertEquals(1, exitLane.getVehicleNum());
        //assertEquals(newPosition, exitLane.getVehicles().get(0).getLeft());

        System.out.print(vehicle.getMetrics().getWaitTimes());
    }

}