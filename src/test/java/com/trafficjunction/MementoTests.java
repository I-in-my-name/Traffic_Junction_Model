package com.trafficjunction;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.trafficjunction.View_and_Controller.Saving_Utils.CareTaker;
import com.trafficjunction.View_and_Controller.Saving_Utils.ConfigurationSnapshot;

public class MementoTests {
    @Test
    void testAddition() {
        assertEquals(true, true);
    }

    @Test
    void testSnapshotBasicFunctionalityPositiveNegative() {
        CareTaker careTaker = new CareTaker();

        int[] inputNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        int[] inputDurs = { 10, 10, 10, 10, 10 };

        JunctionMetrics original = new JunctionMetrics(inputNums, inputDurs);

        Map<String, Integer> vehicleMap = new HashMap<>(original.getAllVehicleNums());

        ConfigurationSnapshot configurationSnapshot = new ConfigurationSnapshot(original);
        careTaker.addSnap(configurationSnapshot);

        original.setVehicleNum("nte", 30);
        assertEquals(30, original.getAllVehicleNums().get("nte"));
        assertEquals(1, vehicleMap.get("nte"));

        assertEquals(false, original.getAllVehicleNums().equals(vehicleMap));

        Map<String, Integer> newMap = original.getAllVehicleNums();

        careTaker.undo();

        assertEquals(vehicleMap, original.getAllVehicleNums());
    }

    @Test
    void testSnapshotLaneRestoration() {
        CareTaker careTaker = new CareTaker();

        int[] inputNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        int[] inputDurs = { 10, 10, 10, 10, 10 };

        JunctionMetrics original = new JunctionMetrics(inputNums, inputDurs);

        Map<String, Integer> vehicleMap = new HashMap<>(original.getAllVehicleNums());

        original.addRoad("north", 5, 5, 0, 0, 0, 0);
        ConfigurationSnapshot configurationSnapshot = new ConfigurationSnapshot(original);
        careTaker.addSnap(configurationSnapshot);

        original.addRoad("north", 5, 1, 1, 1, 1, 1);

        assertEquals(true, original.getNorth().equals(new Road(5, 1, 1, 1, 1, 1)));

        careTaker.undo();
        assertEquals(false, original.getNorth().equals(new Road(5, 1, 1, 1, 1, 1)));
        assertEquals(true, original.getNorth().equals(new Road(5, 5, 0, 0, 0, 0)));

        assertEquals(vehicleMap, original.getAllVehicleNums());
    }

    @Test
    void testUndoLimits() {
        CareTaker careTaker = new CareTaker();

        int[] inputNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        int[] inputDurs = { 10, 10, 10, 10, 10 };

        JunctionMetrics original = new JunctionMetrics(inputNums, inputDurs);

        original.addRoad("north", 1, 1, 0, 0, 0, 0);
        ConfigurationSnapshot snapOne = new ConfigurationSnapshot(original);
        careTaker.addSnap(snapOne);

        original.addRoad("north", 2, 2, 0, 0, 0, 0);
        ConfigurationSnapshot snapTwo = new ConfigurationSnapshot(original);
        careTaker.addSnap(snapTwo);

        original.addRoad("north", 3, 3, 0, 0, 0, 0);
        ConfigurationSnapshot snapThree = new ConfigurationSnapshot(original);
        careTaker.addSnap(snapThree);

        original.addRoad("north", 5, 5, 0, 0, 0, 0);
        assertEquals(true, original.getNorth().equals(new Road(5, 5, 0, 0, 0, 0)));

        careTaker.undo();
        assertEquals(true, original.getNorth().equals(new Road(3, 3, 0, 0, 0, 0)));

        careTaker.undo();
        assertEquals(true, original.getNorth().equals(new Road(2, 2, 0, 0, 0, 0)));

        careTaker.undo();
        // System.out.println(Arrays.toString(original.getNorth().getFormatted()));
        assertEquals(false, original.getNorth().equals(new Road(2, 2, 0, 0, 0, 0)));
        assertEquals(true, original.getNorth().equals(new Road(1, 1, 0, 0, 0, 0)));

        careTaker.undo();
        careTaker.undo();
        careTaker.undo();

        assertEquals(true, original.getNorth().equals(new Road(1, 1, 0, 0, 0, 0)));
    }
}
