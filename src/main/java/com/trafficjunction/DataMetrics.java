package com.trafficjunction;

public class DataMetrics {
    private InputData inputData;
    private OutputData outputData;
    // private JunctionInformation junctionInformation;
}

class InputData {
    private int nte; // North to East
    private int nts; // North to South
    private int ntw; // North to West
    
    private int ets; // East to South
    private int etw; // East to West
    private int etn; // East to North

    private int ste; // South to East
    private int stw; // South to West
    private int stn; // South to North

    private int wtn; // West to North
    private int wte; // West to East
    private int wts; // West to South

    public void InputData(int nte, int nts, int ntw, int ets, int etw, int etn, int ste, int stw, int stn, int wtn, int wte, int wts) {
        this.nte = nte;
        this.nts = nts;
        this.ntw = ntw;
        this.ets = ets;
        this.etw = etw;
        this.etn = etn;
        this.ste = ste;
        this.stn = stn;
        this.stw = stw;
        this.wte = wte;
        this.wtn = wtn;
        this.wts = wts;
    }

    public int getNorthToEast() {
        return this.nte;
    }
    public int getNorthToSouth() {
        return this.nts;
    }
}

class OutputData {
    private int pedScore; // Pedestrian Friendliness Score
    private int maxQueueLength;
    private int maxWaitTime; // In seconds.
    private int averageWaitTime; // In seconds.
    private int emissionScore;

    public void OutputData(int pedScore, int maxQueueLength, int maxWaitTime, int averageWaitTime, int emissionScore) {
        this.pedScore = pedScore;
        this.maxQueueLength = maxQueueLength;
        this.maxWaitTime = maxWaitTime;
        this.averageWaitTime = averageWaitTime;
        this.emissionScore = emissionScore;
    }
}

// class JunctionInformation {

// }