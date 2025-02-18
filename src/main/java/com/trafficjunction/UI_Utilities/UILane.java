package com.trafficjunction.UI_Utilities;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/*
 * Lane object specifically for UI purposes.
 */
public class UILane {
    private ImageView lane; // The lane ImageView associated with this object.
    private int position; // The integer value of this lane's position (e.g. lane 1, lane 2). Lane closest to the oncoming lanes is considered lane 1.
    private boolean enableLeft; // Enables left-turn options to be cycled through.
    private boolean enableRight; // Enables right-turn options to be cycled through.
    private int currentImageIndex = 0; // The index of the current image it is assigned to.

    private ArrayList<String> laneImagePaths = new ArrayList<String>();
    private String currentImagePath;

    /*
     * Default constructor method, assumes middle lane and sets enableLeft and enableRight to false.
     * @param laneObj - The ImageView that this object should be associated with.
     */
    public UILane(ImageView laneObj, int position) {
        this.lane = laneObj;
        this.enableLeft = false;
        this.enableRight = false;
        this.laneImagePaths.add("/assets/straightOnRoad.png");
        this.currentImagePath = laneImagePaths.get(0);

        this.lane.setOnMouseClicked(event -> changeImage());
    }

    /*
     * Secondary constructor methods which allows enableLeft and enableRight to be specified.
     * @param laneObj - The ImageView that this object should be associated with.
     * @param position - The integer value of the lane's position (e.g. lane 1, lane 2). Lane closest to the oncoming lanes is considered lane 1.
     * @param enableLeft - A boolean value deciding if the lane should be allowed to cycle between left-turn options.
     * @param enableRight - A boolean value deciding if the lane should be allowed to cycle between right-turn options.
     */
    public UILane(ImageView laneObj, boolean enableLeft, boolean enableRight) {
        this.lane = laneObj;
        this.enableLeft = false;
        this.enableRight = false;

        this.laneImagePaths.add("/assets/straightOnRoad.png");
        this.currentImagePath = laneImagePaths.get(0);
        if (enableLeft) {
            this.enableLeft();
            addHoverEffect();
        }
        if (enableRight) {
            this.enableRight();
            addHoverEffect();
        }


    }

    /* Method to disable this lane. */
    public void disableLane() {
        this.lane.setDisable(true);
        this.lane.setImage(new Image(getClass().getResourceAsStream("/assets/blackedOutRoad.png")));

    }

    /* Method to enable this lane. */
    public void enableLane() {
        this.lane.setDisable(false);
        this.lane.setImage(new Image(getClass().getResourceAsStream(laneImagePaths.get(this.currentImageIndex))));

    }

    /*
     * Method to switch to the next image.
     */
    public void changeImage() {
        this.currentImageIndex = (currentImageIndex + 1) % laneImagePaths.size();
        this.lane.setImage(new Image(getClass().getResourceAsStream(laneImagePaths.get(currentImageIndex))));
    }

    /*
     * Method to enable left-turn options.
     */
    public void enableLeft() {
        // Checks if left was disabled before.
        if (!this.enableLeft) {
            this.switchLeft(); // Toggle left to true.
            addHoverEffect();

            // Add left turn images.
            laneImagePaths.add("/assets/leftOnlyRoad.png");
            laneImagePaths.add("/assets/straightOnAndLeftRoad.png");
            
            // If right is also true, then add turning both lanes.
            if (this.enableRight) {
                laneImagePaths.add("/assets/straightLeftRightRoad.png");
            }
        }
    }

    /*
     * Method to enable right-turn options.
     */
    public void enableRight() {
        // Checks if right was disabled before.
        if (!this.enableRight) {
            this.switchRight(); // Toggle right to true.
            addHoverEffect();

            // Add right turn images.
            laneImagePaths.add("/assets/rightOnlyRoad.png");
            laneImagePaths.add("/assets/straightOnAndRightRoad.png");

            // If left is also true, then add turning both lanes.
            if (this.enableLeft) {
                laneImagePaths.add("/assets/straightLeftRightRoad.png");
            }
        }
    }

    /*
     * Method to disable left-turn options.
     */
    public void disableLeft() {
        // Checks if left was disabled before.
        if (this.enableLeft) {
            this.switchLeft(); // Toggle left to false.

            // Remove left turn images.
            laneImagePaths.remove("/assets/leftOnlyRoad.png");
            laneImagePaths.remove("/assets/straightOnAndLeftRoad.png");

            // Attempts to remove the turning to both direction image. May not always work but returns false in that case and continues to run.
            laneImagePaths.remove("/assets/straightLeftRightRoad.png");

            // Change to a new image if the current image is now invalid.
            if (!laneImagePaths.contains(currentImagePath)) {
                changeImage();
            }

            // Check if right turns are enabled. If not, disable hover effects.
            if (!this.enableRight) {
                removeHoverEffect();
            }
        }
    }

    /*
     * Method to disable right-turn options.
     */
    public void disableRight() {
        // Checks if right was disabled before.
        if (this.enableRight) {
            this.switchRight(); // Toggle right to false.

            // Remove left turn images.
            laneImagePaths.remove("/assets/rightOnlyRoad.png");
            laneImagePaths.remove("/assets/straightOnAndRightRoad.png");

            // Attempts to remove the turning to both direction image. May not always work but returns false in that case and continues to run.
            laneImagePaths.remove("/assets/straightLeftRightRoad.png");

            // Change to a new image if the current image is now invalid.
            if (!laneImagePaths.contains(currentImagePath)) {
                changeImage();
            }

            // Check if left turns are enabled. If not, disable hover effects.
            if (!this.enableLeft) {
                removeHoverEffect();
            }
        }
    }

    /*
     * Method to toggle left-turn options.
     */
    public void switchLeft() {
        this.enableLeft = !this.enableLeft;
    }

    /*
     * Method to toggle right-turn options.
     */
    public void switchRight() {
        this.enableRight = !this.enableRight;
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
     * Getter method for position field.
     */
    public int getPosition() {
        return this.position;
    }

}