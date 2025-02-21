package com.trafficjunction;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.trafficjunction.View_and_Controller.Saving_Utils.CareTaker;
import com.trafficjunction.View_and_Controller.Saving_Utils.ConfigurationSnapshot;

public class MementoTests {
    @Test
    void testAddition() {
        assertEquals(true,true);
    }

    @Test
    void testSnapshotBasicFunctionalityPositiveNegative() {
        JunctionConfiguration original = new JunctionConfiguration();

        int[] inputValues = { 1,2,3,4,5,6,7,8,9,10,11,12};
        original.setDirectionInfo(inputValues);

        ConfigurationSnapshot configurationSnapshot = new ConfigurationSnapshot(original);
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), inputValues));
        
        int[] replacementValues = {0,0,0,0,0,0,0,0,0,0,0,0,0};
        original.setDirectionInfo(replacementValues);
        assertEquals(false,Arrays.equals(original.getDirectionInfo(), inputValues));

        configurationSnapshot.restore();
        
        assertEquals(true, Arrays.equals(original.getDirectionInfo(), inputValues));

    }
    
    @Test
    void testUndoLimits() {
        CareTaker careTaker = new CareTaker();
        JunctionConfiguration original = new JunctionConfiguration();

        int[] inputValues = { 1,2,3,4,5,6,7,8,9,10,11,12};
        original.setDirectionInfo(inputValues);

        ConfigurationSnapshot snapOne = new ConfigurationSnapshot(original);
        careTaker.addSnap(snapOne);

        int[] differentValuesOne = { 1,2,3,4,5,6,7,8,0,0,0,0};
        original.setDirectionInfo(differentValuesOne);

        ConfigurationSnapshot snapTwo = new ConfigurationSnapshot(original);
        careTaker.addSnap(snapTwo);

        int[] differentValuesTwo = { 0,0,3,4,5,6,7,8,0,0,0,0};
        original.setDirectionInfo(differentValuesTwo);

        ConfigurationSnapshot snapThree = new ConfigurationSnapshot(original);
        careTaker.addSnap(snapThree);


        assertEquals(true,Arrays.equals(original.getDirectionInfo(), differentValuesTwo));
        
        //imagine changes to original, on undo it reverts to the last snapshot, here they happen to be the same state
        careTaker.undo();
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), differentValuesTwo));
        careTaker.undo();
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), differentValuesOne));
        careTaker.undo();
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), inputValues));
        careTaker.undo();
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), inputValues));

        System.out.println("UNDO: " + original.getDirectionInfo()[0]);

        //check the index reacts appropriately
        careTaker.redo();
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), differentValuesOne));
    }

    @Test
    void testRedoLimits() {
        CareTaker careTaker = new CareTaker();
        JunctionConfiguration original = new JunctionConfiguration();

        int[] inputValues = { 1,2,3,4,5,6,7,8,9,10,11,12};
        original.setDirectionInfo(inputValues);

        ConfigurationSnapshot snapOne = new ConfigurationSnapshot(original);
        careTaker.addSnap(snapOne);

        int[] differentValuesOne = { 1,2,3,4,5,6,7,8,0,0,0,0};
        original.setDirectionInfo(differentValuesOne);

        ConfigurationSnapshot snapTwo = new ConfigurationSnapshot(original);
        careTaker.addSnap(snapTwo);

        int[] differentValuesTwo = { 0,0,3,4,5,6,7,8,0,0,0,0};
        original.setDirectionInfo(differentValuesTwo);

        ConfigurationSnapshot snapThree = new ConfigurationSnapshot(original);
        careTaker.addSnap(snapThree);


        assertEquals(true,Arrays.equals(original.getDirectionInfo(), differentValuesTwo));
        

        careTaker.undo();
        careTaker.undo();
        careTaker.undo();
        
        //at last recorded snapshot
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), inputValues));

        careTaker.redo();
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), differentValuesOne));
        careTaker.redo();
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), differentValuesTwo));
        careTaker.redo();
        assertEquals(true,Arrays.equals(original.getDirectionInfo(), differentValuesTwo));

    }

}
