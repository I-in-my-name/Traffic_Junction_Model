package com.trafficjunction;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;

public class UserInputData {
    private InputData inputData;
    private OutputData outputData;
}

class InputData {

    private Map<String, Integer> directionInfo = new HashMap<>();

    public void InputData(int nte, int nts, int ntw, int ets, int etw, int etn, int ste, int stw, int stn, int wtn,
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
