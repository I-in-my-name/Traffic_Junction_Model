package com.trafficjunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserInputData implements Serializable{
    private InputData inputData;
    private OutputData outputData;

    public boolean saveObject(String fileName){
        try(
            //try with resource so auto closes IO streams after
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            ){
            out.writeObject(this);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static UserInputData loadObject(File objectFile){
        try(
            //try with resource so auto closes IO streams after
            FileInputStream file = new FileInputStream(objectFile);
            ObjectInputStream in = new ObjectInputStream(file);
            ){
            return (UserInputData) in.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}

class InputData{

    private Map<String, Integer> directionInfo = new HashMap<>();

    public InputData(int nte, int nts, int ntw, int ets, int etw, int etn, int ste, int stw, int stn, int wtn,
            int wte, int wts) {
        directionInfo.put("nte", nte);
        directionInfo.put("nts", nts);
        directionInfo.put("ntw", ntw);
        directionInfo.put("ets", ets);
        directionInfo.put("etw", etw);
        directionInfo.put("etn", etn);
        directionInfo.put("ste", ste);
        directionInfo.put("stn", stn);
        directionInfo.put("stw", stw);
        directionInfo.put("wte", wte);
        directionInfo.put("wtn", wtn);
        directionInfo.put("wts", wts);
    }
    //convenience overload to reduce the code in PrimaryController
    public InputData(int[] input) {
        directionInfo.put("nte", input[0]);
        directionInfo.put("nts", input[1]);
        directionInfo.put("ntw", input[2]);
        directionInfo.put("ets", input[3]);
        directionInfo.put("etw", input[4]);
        directionInfo.put("etn", input[5]);
        directionInfo.put("ste", input[6]);
        directionInfo.put("stn", input[7]);
        directionInfo.put("stw", input[8]);
        directionInfo.put("wte", input[9]);
        directionInfo.put("wtn", input[10]);
        directionInfo.put("wts", input[11]);
    }
    /*
     * @param direction: The direction for which the information is required.
     * 
     * @return: The number of vehicles in the given direction, or null if invalid
     * direction.
     */
    public Integer getDirectionInfo(String direction) {
        return directionInfo.get(direction);
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
        if (directionInfo.containsKey(direction)) {
            directionInfo.put(direction, num);
            return true;
        } else {
            return false;
        }
    }
}

class OutputData {
    private int pedScore; // Pedestrian Friendliness Score
    private int maxQueueLength;
    private int maxWaitTime; // In seconds.
    private int averageWaitTime; // In seconds.
    private int emissionScore; // In ??

    public void OutputData(int pedScore, int maxQueueLength, int maxWaitTime, int averageWaitTime, int emissionScore) {
        this.pedScore = pedScore;
        this.maxQueueLength = maxQueueLength;
        this.maxWaitTime = maxWaitTime;
        this.averageWaitTime = averageWaitTime;
        this.emissionScore = emissionScore;
    }

    /* Getter methods for output data. */
    public int getPedScore() {
        return pedScore;
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public int getAverageWaitTime() {
        return averageWaitTime;
    }

    public int getEmissionScore() {
        return emissionScore;
    }
}
