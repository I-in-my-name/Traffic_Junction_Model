package com.trafficjunction.Junction_Classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class VehicleMetricsTests {
    @Test void testCalculateMetricsPositive() {
        // test with start existing time of 0
        float startExistingTime = 0.f;
        VehicleMetrics metrics = new VehicleMetrics(startExistingTime);

        // example timestamps
        // moving for 9 seconds
        metrics.startMoving(1.f);
        metrics.stopMoving(10.f);
        // moving for 5 seconds
        metrics.startMoving(15.f);
        metrics.stopMoving(20.f);
        // moving for 11 seconds
        metrics.startMoving(30.f);
        metrics.stopMoving(41.f);

        //Calculating metrics after final stop at 41.f (CONFIRM IF THIS IS CORRECT?)
        metrics.calculateTotalWaitTime(41.f);
        float resultTotalWaitTime = metrics.getTotalWaitTime();

        float expectedWaitTime = 9 + 5 + 11;
        // extra 1.0 time unit of waiting becuase started moving 1 unit after
        // started existing, which counts as a wait time.
        expectedWaitTime += 1;
        
        assertEquals(expectedWaitTime, resultTotalWaitTime);

        // Now testing with non zero start existing time, otherwise same
        // values as before so wait time should be equal
        startExistingTime = 55.f;
        metrics = new VehicleMetrics(startExistingTime);

        // example timestamps
        // moving for 9 seconds
        metrics.startMoving(startExistingTime + 1.f);
        metrics.stopMoving(startExistingTime + 10.f);
        // moving for 5 seconds
        metrics.startMoving(startExistingTime + 15.f);
        metrics.stopMoving(startExistingTime + 20.f);
        // moving for 11 seconds
        metrics.startMoving(startExistingTime + 30.f);
        metrics.stopMoving(startExistingTime + 41.f);

        metrics.calculateTotalWaitTime(startExistingTime + 41.f);
        resultTotalWaitTime = metrics.getTotalWaitTime();

        expectedWaitTime = 9.f + 5.f + 11.f;
        // extra 1.0 time unit of waiting becuase started moving 1 unit after
        // started existing, which counts as a wait time.
        expectedWaitTime += 1.f;
        
        assertEquals(expectedWaitTime, resultTotalWaitTime);
    }
}