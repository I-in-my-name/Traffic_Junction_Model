package com.trafficjunction.Junction_Classes;

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

        //TrafficLight
        junction.setLaneTrafficLight(0, 0, null);
    }

    //@Test
    void simulationRuns() {
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

