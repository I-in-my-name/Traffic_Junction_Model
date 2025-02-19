package com.trafficjunction.Junction_Classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class VehicleMetricsTests {
    @Test void testCalculateMetricsPositive() {
        // test with start existing time of 0, WE assume vehicles start off moving
        float startExistingTime = 0.f;
        VehicleMetrics metrics = new VehicleMetrics(startExistingTime);

        // example timestamps
        // moving for 10 seconds
        metrics.stopMoving(10.f);
        // moving for 5 seconds
        // stopped for 5 second
        metrics.startMoving(15.f);
        metrics.stopMoving(20.f);
        // moving for 11 seconds
        // stopped for 10 second
        metrics.startMoving(30.f);
        metrics.stopMoving(41.f);

        //Calculating metrics after final stop at 41.f (CONFIRM IF THIS IS CORRECT?)
        //metrics.calculateTotalWaitTime(41.f);
        float resultTotalWaitTime = metrics.getTotalWaitTime();

        float expectedMovingTime = 10 + 5 + 11;
        float expectedIdleTime = 5 + 10;

        
        assertEquals(expectedIdleTime, resultTotalWaitTime);

        // Now testing with non zero start existing time, otherwise same
        // values as before so wait time should be equal
        startExistingTime = 55.f;
        metrics = new VehicleMetrics(startExistingTime);

        // example timestamps

        //Again we assume the vehicle starts off moving
        // moving for 10 seconds
        metrics.stopMoving(startExistingTime + 10.f);
        // moving for 5 seconds
        // stopped for 5 second
        metrics.startMoving(startExistingTime + 15.f);
        metrics.stopMoving(startExistingTime + 20.f);
        // moving for 11 seconds
        // stopped for 10 second
        metrics.startMoving(startExistingTime + 30.f);
        metrics.stopMoving(startExistingTime + 41.f);

        metrics.calculateTotalWaitTime(startExistingTime + 41.f);
        resultTotalWaitTime = metrics.getTotalWaitTime();

        expectedIdleTime = 9.f + 5.f + 11.f;
        expectedIdleTime = 5 + 10;
        
        assertEquals(expectedIdleTime, resultTotalWaitTime);
    }
}