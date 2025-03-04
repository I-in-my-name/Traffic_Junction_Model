package com.trafficjunction.UI_Utilities;

import java.util.Objects;

/*
 * Class implementing different road types. Each one is associated with a different image and has different
 * turning properties.
 */

public class RoadType {
    String imagePath; // The path to the image of the road.
    boolean straight; // Whether the road is going straight.
    boolean left; // Whether the road is a left turn.
    boolean right; // Whether the road is a right turn.

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
    public String getAsChars(){
        StringBuilder sb = new StringBuilder();

        if(this.getLeft()) sb.append("L");
        if(this.getStraight()) sb.append("F");
        if(this.getRight()) sb.append("R");

        return sb.toString();
    }

    /*
     * Method to compare roadtype objects.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof RoadType)) {
            return false;
        }

        RoadType roadType = (RoadType) o;

        return roadType.getImagePath().equals(this.imagePath) &&
                roadType.getStraight() == this.straight &&
                roadType.getLeft() == this.left &&
                roadType.getRight() == this.right;
    }

    /*
     * Method to generate a hashcode for the roadtype object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.imagePath, this.straight, this.left, this.right);
    }
}
