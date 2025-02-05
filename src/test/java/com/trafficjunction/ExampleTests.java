package com.trafficjunction;  

import com.trafficjunction.Example; 

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExampleTests {
    @Test
    void testAddition() {
        Example example = new Example();
        assertEquals(2, example.add(1, 1));
    }
}