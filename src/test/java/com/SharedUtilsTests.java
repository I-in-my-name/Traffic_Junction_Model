package com;  

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.trafficjunction.SharedUtils;

public class SharedUtilsTests {
    @Test
    void testPositive() {
        String[] lanes = {"L","L","L","L","L"};
        assertEquals(true, SharedUtils.verifyLane(lanes, true));

        String[] lanesR = {"R","R","R","R","R"};
        assertEquals(true, SharedUtils.verifyLane(lanesR, true));

        String[] lanesF = {"F","F","F","F","F"};
        assertEquals(true, SharedUtils.verifyLane(lanesF, true));

        String[] lanes2 = {"L","LF","F","FR","R"};
        assertEquals(true, SharedUtils.verifyLane(lanes2, true));

        String[] lanes3 = {"R","R","R","R","R"};
        assertEquals(true, SharedUtils.verifyLane(lanes3, true));

        String[] lanes4 = {"R","R","R","FR","R"};
        assertEquals(false, SharedUtils.verifyLane(lanes4, true));

        String[] lanes5 = {"L","LF","LF","F","F"};
        assertEquals(false, SharedUtils.verifyLane(lanes5, true));

        String[] lanes6 = {"L","LF","F","FR","FR"};
        assertEquals(false, SharedUtils.verifyLane(lanes6, true));
    }
}