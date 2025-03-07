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

        assertEquals(false, original.getNorth().equals(new Road(1, 1, 1, 0, 1, 1)));

        careTaker.undo();
        assertEquals(false, original.getNorth().equals(new Road(5, 1, 1, 1, 1, 1)));
        assertEquals(false, original.getNorth().equals(new Road(5, 1, 1, 1, 1, 1)));

        assertEquals(vehicleMap, original.getAllVehicleNums());
    }

    @Test
    void testUndoLimits() {
        // CareTaker careTaker = new CareTaker();
        // JunctionConfiguration original = new JunctionConfiguration();

        // int[] inputValues = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        // original.setDirectionInfo(inputValues);

        // ConfigurationSnapshot snapOne = new ConfigurationSnapshot(original);
        // careTaker.addSnap(snapOne);

        // int[] differentValuesOne = { 1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0, 0 };
        // original.setDirectionInfo(differentValuesOne);

        // ConfigurationSnapshot snapTwo = new ConfigurationSnapshot(original);
        // careTaker.addSnap(snapTwo);

        // int[] differentValuesTwo = { 0, 0, 3, 4, 5, 6, 7, 8, 0, 0, 0, 0 };
        // original.setDirectionInfo(differentValuesTwo);

        // ConfigurationSnapshot snapThree = new ConfigurationSnapshot(original);
        // careTaker.addSnap(snapThree);

        // assertEquals(true, Arrays.equals(original.getDirectionInfo(),
        // differentValuesTwo));

        // // imagine changes to original, on undo it reverts to the last snapshot, here
        // // they happen to be the same state
        // careTaker.undo();
        // assertEquals(true, Arrays.equals(original.getDirectionInfo(),
        // differentValuesTwo));
        // careTaker.undo();
        // assertEquals(true, Arrays.equals(original.getDirectionInfo(),
        // differentValuesOne));
        // careTaker.undo();
        // assertEquals(true, Arrays.equals(original.getDirectionInfo(), inputValues));
        // careTaker.undo();
        // assertEquals(true, Arrays.equals(original.getDirectionInfo(), inputValues));

        // System.out.println("UNDO: " + original.getDirectionInfo()[0]);

        // // check the index reacts appropriately
        // careTaker.redo();
        // assertEquals(true, Arrays.equals(original.getDirectionInfo(),
        // differentValuesOne));
    }

    @Test
    void testRedoLimits() {
        // CareTaker careTaker = new CareTaker();
        // JunctionConfiguration original = new JunctionConfiguration();

        // int[] inputValues = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        // original.setDirectionInfo(inputValues);

        // ConfigurationSnapshot snapOne = new ConfigurationSnapshot(original);
        // careTaker.addSnap(snapOne);

        // int[] differentValuesOne = { 1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0, 0 };
        // original.setDirectionInfo(differentValuesOne);

        // ConfigurationSnapshot snapTwo = new ConfigurationSnapshot(original);
        // careTaker.addSnap(snapTwo);

        // int[] differentValuesTwo = { 0, 0, 3, 4, 5, 6, 7, 8, 0, 0, 0, 0 };
        // original.setDirectionInfo(differentValuesTwo);

        // ConfigurationSnapshot snapThree = new ConfigurationSnapshot(original);
        // careTaker.addSnap(snapThree);

        // assertEquals(true, Arrays.equals(original.getDirectionInfo(),
        // differentValuesTwo));

        // careTaker.undo();
        // careTaker.undo();
        // careTaker.undo();

        // // at last recorded snapshot
        // assertEquals(true, Arrays.equals(original.getDirectionInfo(), inputValues));

        // careTaker.redo();
        // assertEquals(true, Arrays.equals(original.getDirectionInfo(),
        // differentValuesOne));
        // careTaker.redo();
        // assertEquals(true, Arrays.equals(original.getDirectionInfo(),
        // differentValuesTwo));
        // careTaker.redo();
        // assertEquals(true, Arrays.equals(original.getDirectionInfo(),
        // differentValuesTwo));

    }

}
