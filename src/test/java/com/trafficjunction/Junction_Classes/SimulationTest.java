package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SimulationTest {

    private static Junction junction;

    @BeforeAll
    static void setup() {
        junction = new Junction();
        junction.addEntryLane(0);
        junction.addExitLane(0);
        junction.addEntryLane(1);
        junction.addExitLane(1);
        junction.addEntryLane(2);
        junction.addExitLane(2);
        junction.addEntryLane(3);
        junction.addExitLane(3);

        junction.connectJunction();

        // TrafficLight
        //junction.setLaneTrafficLight(0, 0, null);
    }

    /*
     * Simpler test case to see if the values are correct
     */
    @Test
    void simulationRunsCorrectly() {
        // calling setup again to ensure junction object is as we expect
        setup();

        TrafficLightConfig allRed = new TrafficLightConfig();
        allRed.addState(100.f, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        //allRed.addState(30.f, new ArrayList<>(Arrays.asList(1, 0, 0, 0)));
        junction.setTrafficLightConfig(allRed);

        // Total of 60 vehicles per hour coming from north to some direction
        junction.setVehicleRate("nte", 200);
        junction.setVehicleRate("nts", 200);
        junction.setVehicleRate("ntw", 200);
        //junction.setVehicleRate("ets", 0);
        //junction.setVehicleRate("etw", 0);
        //junction.setVehicleRate("etn", 0);
        //junction.setVehicleRate("ste", 0);
        //junction.setVehicleRate("stn", 0);
        //junction.setVehicleRate("stw", 0);
        //junction.setVehicleRate("wts", 0);
        //junction.setVehicleRate("wte", 0);
        //junction.setVehicleRate("wtn", 0);

        float time = 0.f;
        float timeStep = 0.1f;
        float endTime = 200.f;

        while (time < endTime) {
            junction.update(timeStep);
            time += timeStep;
        }

        junction.calculateMetrics(time);

        LaneMetrics metr = junction.getEntryLanes().get(0).get(0).getMetrics();

        System.out.println(metr.toString());
        System.out.println(junction);

        // All lights are red and one vehicle

        float averageWaitTime = junction.getAverageWaitTime(0);
        float maxWaitTime = junction.getMaxWaitTime(0);
        
        // average wait time should be about half the runtime 200/2 = 100
        assertTrue(85.f < averageWaitTime && averageWaitTime < 115.f);
        // Vehicle travelling 60 kmph, entry lane is 100m long, max wait time is the vehicle that gets created first
        // travelTime = 100 / (60 / 3.6) = 6
        // expectedMaxWaitTime = 200 - 6 = 194
        assertTrue(190.f < maxWaitTime && maxWaitTime < 200.f);
        //int expectedAverageQueue = 
        assertEquals(60, junction.getAverageQueueLength(0));
    }

    @Test
    void simulationRuns() {
        // calling setup again to ensure junction object is as we expect
        setup();
        junction.setVehicleRate("nte", 200);
        junction.setVehicleRate("nts", 200);
        junction.setVehicleRate("ntw", 200);

        junction.setVehicleRate("wte", 50);

        float time = 0.f;
        float timeStep = 1.f;
        float endTime = 60.f * 60.f; // run for 1 hour

        while (time < endTime) {
            junction.update(timeStep);
            time += timeStep;

            //System.out.println(junction);
        }

        junction.calculateMetrics(time);

        System.out.println(junction.getAverageWaitTime(0));
        System.out.println(junction.getAverageWaitTime(1));
        System.out.println(junction.getAverageWaitTime(2));
        System.out.println(junction.getAverageWaitTime(3));

        System.out.println(junction.getMaxWaitTime(0));
        System.out.println(junction.getMaxWaitTime(1));
        System.out.println(junction.getMaxWaitTime(2));
        System.out.println(junction.getMaxWaitTime(3));

        System.out.println(junction.getMaxQueueLength(0));
        System.out.println(junction.getMaxQueueLength(1));
        System.out.println(junction.getMaxQueueLength(2));
        System.out.println(junction.getMaxQueueLength(3));
    }

}
