package com.trafficjunction;

import java.io.Serializable;

public class Road implements Serializable {
    int numLanes;
    int left;
    int leftForward;
    int forward;
    int rightForward;
    int right;

    public Road(int numLanes, int left, int leftForward, int forward, int rightForward, int right) {
        this.numLanes = numLanes;
        this.left = left;
        this.leftForward = leftForward;
        this.forward = forward;
        this.rightForward = rightForward;
        this.right = right;
    }

    public String getIndexDir(int index) {
        if (index < left) {
            return "L";
        } else if (index < left + leftForward) {
            return "LF";
        } else if (index < left + leftForward + forward) {
            return "F";
        } else if (index < left + leftForward + forward + rightForward) {
            return "FR";
        } else if (index < left + leftForward + forward + rightForward + right) {
            return "R";
        }
        return null;
    }

    /*
     * Getter method for the number of lanes.
     * 
     * @return int - The number of lanes.
     */
    public int getNumLanes() {
        return this.numLanes;
    }

    /*
     * Getter method for the number of lanes turning strictly left.
     * 
     * @return int - The number of strict-left lanes.
     */
    public int getLeft() {
        return this.left;
    }

    /*
     * Getter method for the number of lanes that are a forward AND left turn.
     * 
     * @return int - The number of lanes.
     */
    public int getLeftForward() {
        return this.leftForward;
    }

    /*
     * Getter method for the number of lanes going strictly forward.
     * 
     * @return int - The number of lanes.
     */
    public int getForward() {
        return this.forward;
    }

    /*
     * Getter method for the number of lanes going forward AND right.
     * 
     * @return int - The number of lanes.
     */
    public int getRightForward() {
        return this.rightForward;
    }

    /*
     * Getter method for the number of strict right turns.
     * 
     * @return int - The number of lanes.
     */
    public int getRight() {
        return this.right;
    }

    // L LF F FR R formatted string
    public String[] getFormatted() {
        String[] directions = { "D", "D", "D", "D", "D" };
        int index = 0;
        for (int i = 0; i < getLeft(); i++) {
            directions[index] = "L";
            index++;
        }
        for (int i = 0; i < getLeftForward(); i++) {
            directions[index] = "LF";
            index++;
        }
        for (int i = 0; i < getForward(); i++) {
            directions[index] = "F";
            index++;
        }
        for (int i = 0; i < getRightForward(); i++) {
            directions[index] = "FR";
            index++;
        }
        for (int i = 0; i < getRight(); i++) {
            directions[index] = "R";
            index++;
        }

        return directions;
    }

    public boolean equals(Road otherRoad) {
        // System.out.println(otherRoad.forward);
        // System.out.println(this.forward);
        return otherRoad.getForward() == this.getForward() && otherRoad.getLeft() == this.getLeft()
                && otherRoad.getRight() == this.getRight() && this.getNumLanes() == otherRoad.getNumLanes()
                && otherRoad.getRightForward() == this.getRightForward()
                && this.getLeftForward() == otherRoad.getLeftForward();
    }
}
