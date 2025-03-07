package com.trafficjunction.Junction_Classes;

import java.io.File;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

import com.trafficjunction.JunctionMetrics;

public class JunctionMetricsTest {
        @Test
        void populateCardinals() {
                int[] inputNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
                int[] inputDurs = { 10, 10, 10, 10, 10 };
                JunctionMetrics junctionMetrics = new JunctionMetrics(inputNums, inputDurs);

                // Count the number of each type of lane in each road.
                int[] laneData = { 5, 0, 0, 0, 0 };

                junctionMetrics.addRoad("north", 5, laneData[0], laneData[1], laneData[2], laneData[3],
                                laneData[4]);
                junctionMetrics.addRoad("east", 5, laneData[0], laneData[1], laneData[2], laneData[3],
                                laneData[4]);
                junctionMetrics.addRoad("south", 5, laneData[0], laneData[1], laneData[2], laneData[3],
                                laneData[4]);
                junctionMetrics.addRoad("west", 5, laneData[0], laneData[1], laneData[2], laneData[3],
                                laneData[4]);

                assertEquals(true, junctionMetrics.getNorth() != null);
        }

        @Test
        void checkPopulateOnLoad() {
                int[] inputNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
                int[] inputDurs = { 10, 10, 10, 10, 10 };
                JunctionMetrics junctionMetrics = new JunctionMetrics(inputNums, inputDurs);

                // Count the number of each type of lane in each road.
                int[] laneData = { 5, 0, 0, 0, 0 };

                junctionMetrics.addRoad("north", 5, laneData[0], laneData[1], laneData[2], laneData[3],
                                laneData[4]);
                junctionMetrics.addRoad("east", 5, laneData[0], laneData[1], laneData[2], laneData[3],
                                laneData[4]);
                junctionMetrics.addRoad("south", 5, laneData[0], laneData[1], laneData[2], laneData[3],
                                laneData[4]);
                junctionMetrics.addRoad("west", 5, laneData[0], laneData[1], laneData[2], laneData[3],
                                laneData[4]);

                JunctionMetrics loaded;
                assertEquals(true, junctionMetrics.getNorth() != null);

                try {
                        File saveFile = File.createTempFile("temp", "txt");
                        junctionMetrics.saveObject(saveFile);

                        // first value will be set to 10
                        junctionMetrics.setVehicleNum("nte", 10);

                        loaded = JunctionMetrics.loadObject(saveFile);
                        assertEquals(true, loaded.getNorth() != null);

                } catch (Exception ignored) {
                        assertEquals(true, false);
                }
        }

        @Test
        void checkCopyValues() {
                int[] inputNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
                int[] inputDurs = { 10, 10, 10, 10, 10 };
                JunctionMetrics junctionMetrics = new JunctionMetrics(inputNums, inputDurs);
                junctionMetrics.addRoad("north", 5, 1, 1, 1, 1, 1);

                int[] empty = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                JunctionMetrics copyMetrics = new JunctionMetrics(empty, empty);
                copyMetrics.copyValues(junctionMetrics);

                copyMetrics.setVehicleNum("nte", 0);
                junctionMetrics.addRoad("north", 1, 1, 0, 0, 0, 0);
                assertEquals(false, junctionMetrics.equals(copyMetrics));

                // 1)));
                assertEquals(0, copyMetrics.getVehicleNum("nte"));
                assertEquals(1, junctionMetrics.getVehicleNum("nte"));
        }

}
