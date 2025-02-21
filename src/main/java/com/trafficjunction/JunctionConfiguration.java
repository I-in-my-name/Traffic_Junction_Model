package com.trafficjunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.trafficjunction.JunctionConfiguration.directions;

public class JunctionConfiguration implements Serializable{
    enum directions{
        nte,nts,ntw,

        ets,etw,etn,

        stw,stn,ste,

        wtn,wte,wts,
    }
    /*
     * Of Note:
     * the ordering here follows a NESW system and a clockwise sub system, i.e 
     * input is expected to be n: .... e:... s:... w:... but the ...'s follow a different pattern:
     * consider all values in east, 
     * E to N is last in the list e:... because going clockwise from East, South is next.directionalThroughput
     */
    private Map<directions, Integer> directionalThroughput = new HashMap<>();

    //Logic: Partial configurations must be valid for the save features to work, so no constructor inputs.
    public boolean setDirectionInfo(int[] input) {
        //potentially a section for further validation to return false
        directions[] values = directions.values();
        for (int i = 0; i < values.length; i++) {
            directionalThroughput.put(values[i], input[i]);
        }
        return true;
    }
    //returns according to system outlined in enum outlined
    public int[] getDirectionInfo(){
        int[] info = new int[12]; 
        directions[] values = directions.values();
        for (int i = 0; i < values.length; i++) {
            info[i] = directionalThroughput.get(values[i]);
        }

        return info;
    }

    /*
     * @param direction: The direction for which the information is required.
     * 
     * @param num: The number of vehicles in the given direction.
     * 
     * @return bool: True if the value has been updated, or false if the direction
     * is invalid.
     */
    public boolean setOneDirection(directions direction, int num) {
        directionalThroughput.replace(direction, num);
        return true;
    }


    /*
     * @param direction: The direction for which the information is required.
     * 
     * @return: The number of vehicles in the given direction, or null if invalid
     * direction.
     */
    public Integer getOneDirection(directions direction) {
        return directionalThroughput.get(direction);
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
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }
}
