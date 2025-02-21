package com.trafficjunction.UI_Utilities;

/*
 * Class implementing different road types. Each one is associated with a different image and has different
 * turning properties.
 */

public class RoadType {
    String imagePath; // The path to the image of the road.
    boolean straight; // Whether the road allows going straight.
    boolean left; // Whether the road allows turning left.
    boolean right; // Whether the road allows turning right.

    /*
     * Constructor method for the RoadType class.
     */
    public RoadType(String imagePath, boolean straight, boolean left, boolean right) {
        this.imagePath = imagePath;
        this.straight = straight;
        this.left = left;
        this.right = right;
    }

    /*
     * Getter method for whether the road allows going straight.
     */
    public boolean getStraight() {
        return straight;
    }

    /*
     * Getter method for whether the road allows turning left.
     */
    public boolean getLeft() {
        return left;
    }

    /*
     * Getter method for whether the road allows turning right.
     */
    public boolean getRight() {
        return right;
    }

    /*
     * Getter method for the image path of the road.
     */
    public String getImagePath() {
        return imagePath;
    }
}
