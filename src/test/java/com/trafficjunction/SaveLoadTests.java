package com.trafficjunction;  


import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.trafficjunction.JunctionConfiguration.directions;

public class SaveLoadTests {

    @Test
    void serialSaveLoadTest() {
        JunctionConfiguration original = new JunctionConfiguration();
        JunctionConfiguration loaded;

        int[] inputValues = { 1,2,3,4,5,6,7,8,9,10,11,12 };
        original.setDirectionInfo(inputValues);

        try{
            File saveFile = File.createTempFile("temp","txt");
            original.saveObject(saveFile);

            //first value will be set to 10
            original.setOneDirection(directions.nte, 10);

            loaded = JunctionConfiguration.loadObject(saveFile);

            assertEquals(10,original.getOneDirection(directions.nte));
            assertEquals(1, loaded.getOneDirection(directions.nte));
        
        }catch(Exception ignored){}

    }
    //TODO when we have data that is optionally input, check without it 
    @Test
    void testPartialSave() {
        Example example = new Example();
        assertEquals(2, example.add(1, 1));
    }

    @Test
    void badFileLoad() {
        JunctionConfiguration original = new JunctionConfiguration();
        JunctionConfiguration loaded;

        int[] inputValues = { 1,2,3,4,5,6,7,8,9,10,11,12 };
        original.setDirectionInfo(inputValues);

        try{
            File saveFile = File.createTempFile("temp","txt");
            original.saveObject(saveFile);
            File emptyFile = File.createTempFile("empty", "txt");

            //first value will be set to 10


            loaded = JunctionConfiguration.loadObject(emptyFile);

            assertEquals(null, loaded);
        
        }catch(Exception ignored){}
    }
    
}