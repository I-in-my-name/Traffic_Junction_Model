package com.trafficjunction.UI_Utilities;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * Lane object specifically for UI purposes.
 */
public class UILane {
    private ImageView lane; // The lane ImageView associated with this object.
    private boolean leftEnabled; // Enables left-turn options to be cycled through.
    private boolean rightEnabled; // Enables right-turn options to be cycled through.
    private boolean isLeft; // Whether the lane is a left-turn lane.
    private boolean isRight; // Whether the lane is a right-turn lane.

    private int currentImageIndex = 0; // The index of the current image it is assigned to.

    private ArrayList<String> laneImagePaths = new ArrayList<String>();
    private String currentImagePath;

    /*
     * Default constructor method, assumes middle lane and sets enableLeft and
     * enableRight to false.
     * 
     * @param laneObj - The ImageView that this object should be associated with.
     * 
     * @param position - The integer value of the lane's position (e.g. lane 1, lane
     * 2). Lane closest to the oncoming lanes is considered lane 0.
     */
    public UILane(ImageView laneObj) {
        this.lane = laneObj;
        this.leftEnabled = false;
        this.rightEnabled = false;
        this.isLeft = false;
        this.isRight = false;
        this.laneImagePaths.add("/assets/straightOnRoad.png"); // Add on the straight road image. This is the default.
        this.currentImagePath = laneImagePaths.get(0);

        // this.lane.setOnMouseClicked(event -> changeImage());
    }

    /* Method to disable this lane. */
    public void disableLane() {
        // TODO Disable left and right enabled.
        this.lane.setDisable(true);
        this.lane.setImage(new Image(getClass().getResourceAsStream("/assets/blackedOutRoad.png")));

    }

    /* Method to enable this lane. */
    public void enableLane() {
        // TODO Go back to previous left and right enabled.
        this.lane.setDisable(false);
        this.lane.setImage(new Image(getClass().getResourceAsStream(laneImagePaths.get(this.currentImageIndex))));

    }

    /*
     * Method to switch to the next image.
     */
    public void changeImage() {
        // TODO
        // Change image and decide whether the new image is left or a right turn. If it
        // is either, enable the respective turn.
        this.currentImageIndex = (currentImageIndex + 1) % laneImagePaths.size();
        this.lane.setImage(new Image(getClass().getResourceAsStream(laneImagePaths.get(currentImageIndex))));
    }

    /*
     * Function to add hover effect to a lane.
     */
    public void addHoverEffect() {
        this.lane.setOnMouseEntered(event -> {
            this.lane.setEffect(new javafx.scene.effect.Bloom(0.8));
            this.lane.setCursor(javafx.scene.Cursor.HAND);
        });

        this.lane.setOnMouseExited(event -> {
            this.lane.setEffect(null);
        });
    }

    /*
     * Function to remove hover effects from a lane.
     */
    public void removeHoverEffect() {
        this.lane.setOnMouseEntered(null);
        this.lane.setOnMouseExited(null);
    }

    /*
     * Getter method for the lane field.
     */
    public ImageView getLane() {
        return this.lane;
    }

    /*
     * Getter method for the isLeft field.
     */
    public boolean isLeft() {
        return this.isLeft;
    }

    /*
     * Getter method for the isRight field.
     */
    public boolean isRight() {
        return this.isRight;
    }

}