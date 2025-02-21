package com.trafficjunction;

public class JunctionMetrics {
    private int pedScore; // Pedestrian Friendliness Score
    private int maxQueueLength;
    private int maxWaitTime; // In seconds.
    private int averageWaitTime; // In seconds.
    private int emissionScore; // In ??

    public JunctionMetrics(int pedScore, int maxQueueLength, int maxWaitTime, int averageWaitTime, int emissionScore) {
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
