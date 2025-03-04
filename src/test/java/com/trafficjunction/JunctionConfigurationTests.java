package com.trafficjunction;  

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class JunctionConfigurationTests {
    @Test
    void testGetDirectionInfo() {
        JunctionConfiguration original = new JunctionConfiguration();
        JunctionConfiguration loaded = new JunctionConfiguration();

        int[] inputValues = { 1,2,3,4,5,6,7,8,9,10,11,12 };
        original.setDirectionInfo(inputValues);
        loaded.setDirectionInfo(original.getDirectionInfo());

        assertEquals(true, Arrays.equals(loaded.getDirectionInfo(),original.getDirectionInfo()));
    }
}