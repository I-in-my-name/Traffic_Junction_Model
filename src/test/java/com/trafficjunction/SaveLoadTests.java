package com.trafficjunction;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SaveLoadTests {

    @Test
    void serialSaveLoadTest() {

        int[] inputNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        int[] inputDurs = { 10, 10, 10, 10, 10 };

        JunctionMetrics original = new JunctionMetrics(inputNums, inputDurs);
        JunctionMetrics loaded;

        try {
            File saveFile = File.createTempFile("temp", "txt");
            original.saveObject(saveFile);

            // first value will be set to 10
            original.setVehicleNum("nte", 10);

            loaded = JunctionMetrics.loadObject(saveFile);

            assertEquals(10, original.getVehicleNum("nte"));
            assertEquals(1, loaded.getVehicleNum("nte"));
        } catch (Exception ignored) {
            assertEquals(true, false);
        }

    }

    @Test
    void badFileLoad() {
        JunctionConfiguration original = new JunctionConfiguration();
        JunctionConfiguration loaded;

        int[] inputValues = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        original.setDirectionInfo(inputValues);

        try {
            File saveFile = File.createTempFile("temp", "txt");
            original.saveObject(saveFile);
            File emptyFile = File.createTempFile("empty", "txt");

            // first value will be set to 10

            loaded = JunctionConfiguration.loadObject(emptyFile);

            assertEquals(null, loaded);

        } catch (Exception ignored) {
        }
    }

}