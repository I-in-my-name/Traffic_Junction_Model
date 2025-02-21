package com.trafficjunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class JunctionConfiguration {
    /*
     * Of Note:
     * the ordering here follows a NESW system and a clockwise sub system, i.e 
     * input is expected to be n: .... e:... s:... w:... but the ...'s follow a different pattern:
     * consider all values in east, 
     * E to N is last in the list e:... because going clockwise from East, South is next.directionalThroughput
     */
    private Map<String, Integer> directionalThroughput = new HashMap<>();

    //Logic: Partial configurations must be valid for the save features to work, so no constructor inputs.
    public boolean setDirectionInfo(int[] input) {

        //potentially a section for further validation to return false

        directionalThroughput.put("nte", input[0]);
        directionalThroughput.put("nts", input[1]);
        directionalThroughput.put("ntw", input[2]);
        directionalThroughput.put("ets", input[3]);
        directionalThroughput.put("etw", input[4]);
        directionalThroughput.put("etn", input[5]);
        directionalThroughput.put("ste", input[6]);
        directionalThroughput.put("stn", input[7]);
        directionalThroughput.put("stw", input[8]);
        directionalThroughput.put("wte", input[9]);
        directionalThroughput.put("wtn", input[10]);
        directionalThroughput.put("wts", input[11]);
        return true;
    }
    /*
     * @param direction: The direction for which the information is required.
     * 
     * @return: The number of vehicles in the given direction, or null if invalid
     * direction.
     */
    public Integer getDirectionInfo(String direction) {
        return directionalThroughput.get(direction);
    }

    /*
     * @param direction: The direction for which the information is required.
     * 
     * @param num: The number of vehicles in the given direction.
     * 
     * @return bool: True if the value has been updated, or false if the direction
     * is invalid.
     */
    public boolean setDirectionInfo(String direction, int num) {
        if (directionalThroughput.containsKey(direction)) {
            directionalThroughput.put(direction, num);
            return true;
        } else {
            return false;
        }
    }





    public boolean saveObject(File objectFile){
        try(
            //try with resource so auto closes IO streams after
            FileOutputStream file = new FileOutputStream(objectFile);
            ObjectOutputStream out = new ObjectOutputStream(file);
            ){
            out.writeObject(this);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static JunctionConfiguration loadObject(File objectFile){
        try(
            //try with resource so auto closes IO streams after
            FileInputStream file = new FileInputStream(objectFile);
            ObjectInputStream in = new ObjectInputStream(file);
            ){
            return (JunctionConfiguration) in.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}
