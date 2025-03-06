package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        junction.setLaneTrafficLight(0, 0, null);
    }

    /*
     * Simpler test case to see if the values are correct
     */
    @Test
    void simulationRunsCorrectly() {
        // calling setup again to ensure junction object is as we expect
        setup();

        TrafficLightConfig allRed = new TrafficLightConfig();
        allRed.addState(10.f, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        junction.setTrafficLightConfig(allRed);

        // Total of 60 vehicles per hour coming from north to some direction
        junction.setVehicleRate("nte", 200);
        junction.setVehicleRate("nts", 200);
        junction.setVehicleRate("ntw", 200);
        junction.setVehicleRate("ets", 0);
        junction.setVehicleRate("etw", 0);
        junction.setVehicleRate("etn", 0);
        junction.setVehicleRate("ste", 0);
        junction.setVehicleRate("stn", 0);
        junction.setVehicleRate("stw", 0);
        junction.setVehicleRate("wts", 0);
        junction.setVehicleRate("wte", 0);
        junction.setVehicleRate("wtn", 0);

        float time = 0.f;
        float timeStep = 1.f;
        float endTime = 120.f;

        while (time < endTime) {
            junction.update(timeStep);
            time += timeStep;

            System.out.println(junction);
        }

        junction.calculateMetrics(time);

        // All lights are red and one vehicle

        assertEquals(60, junction.getAverageWaitTime(0));
        assertEquals(60, junction.getMaxWaitTime(0));
        assertEquals(60, junction.getAverageQueueLength(0));
    }

    @Test
    void simulationRuns() {
        // calling setup again to ensure junction object is as we expect
        setup();

        float time = 0.f;
        float timeStep = 1.f;
        float endTime = 60.f * 60.f; // run for 1 hour

        while (time < endTime) {
            junction.update(timeStep);
            time += timeStep;

            // System.out.println(junction);
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
