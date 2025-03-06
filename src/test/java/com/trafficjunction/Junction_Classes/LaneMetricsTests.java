package com.trafficjunction.Junction_Classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LaneMetricsTests {

    @Test
    void testMaxQueueLengthPositive() {
        LaneMetrics laneMetrics = new LaneMetrics();

        // Simple typical test case
        // Record different sized queues at different times
        // Output should be the highest queue given, which will be 50

        laneMetrics.updateQueueSize(2);
        laneMetrics.updateQueueSize(23);
        laneMetrics.updateQueueSize(49);
        laneMetrics.updateQueueSize(0);
        laneMetrics.updateQueueSize(23);
        laneMetrics.updateQueueSize(50); // 50 is biggest queue
        laneMetrics.updateQueueSize(45);
        laneMetrics.updateQueueSize(10);
        laneMetrics.updateQueueSize(3);

        int expectedQueueLength = 50;
        int actualQueueLength = laneMetrics.getMaxQueueLength();

        assertEquals(expectedQueueLength, actualQueueLength);
    }

    // Dependent on VehicleMetrics working correctly
    @Test
    void testCalculateMetricsPositive() {
        LaneMetrics laneMetrics = new LaneMetrics();
        // Testing one example case
        // Will test it produces expected values for average wait time,
        // max wait time, and average queue length
        VehicleMetrics vehicleOne = new VehicleMetrics(0.f);
        VehicleMetrics vehicleTwo = new VehicleMetrics(5.f);
        VehicleMetrics vehicleThree = new VehicleMetrics(10.f);

        // wait time = total time - time spent moving
        // = 100 - (1 - 0) - (30 - 4) - (70 - 50) - (100 - 90)
        // = 100 - 1 - 26 - 20 - 10 = 43
        vehicleOne.stopMoving(1.f);
        vehicleOne.startMoving(4.f);
        vehicleOne.stopMoving(30.f);
        vehicleOne.startMoving(50.f);
        vehicleOne.stopMoving(70.f);
        vehicleOne.startMoving(90.f);
        vehicleOne.stopMoving(100.f);
        vehicleOne.calculateTotalWaitTime(100.f);

        assertEquals(43.f, vehicleOne.getTotalWaitTime());

        // wait time = total time - time spent moving
        // = (100 - 5) - (40 - 5) - (100 - 60)
        // = 95 - 35 - 40 = 20
        vehicleTwo.stopMoving(40.f);
        vehicleTwo.startMoving(60.f);
        vehicleTwo.stopMoving(100.f);
        vehicleTwo.calculateTotalWaitTime(100.f);

        assertEquals(20.f, vehicleTwo.getTotalWaitTime());

        // wait time = total time - time spent moving
        // = 10 - (10 - 10) = 0
        vehicleThree.stopMoving(10.f);
        vehicleThree.calculateTotalWaitTime(10.f);

        laneMetrics.addVehicleMetric(vehicleOne);
        laneMetrics.addVehicleMetric(vehicleTwo);
        laneMetrics.addVehicleMetric(vehicleThree);

        float expectedAverageWaitTime = (0.f + 20.f + 43.f) / 3.f;
        float expectedMaxWaitTime = 43.f;

        laneMetrics.calculateMetrics(100.f); // ?? what should the value be

        assertEquals(expectedAverageWaitTime, laneMetrics.getAverageWaitTime());
        assertEquals(expectedMaxWaitTime, laneMetrics.getMaxWaitTime());
    }

}
