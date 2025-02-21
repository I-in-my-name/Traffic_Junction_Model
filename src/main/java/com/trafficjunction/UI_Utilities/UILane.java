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

    private RoadType roadType; // The road type associated with this lane.
    private ArrayList<RoadType> allAllowedRoads; // All the road types that are allowed for this lane.
    private int currentRoadCounter = 0;
    public boolean isDisabled = false;

    public UILane(ImageView laneObj) {
        this.lane = laneObj;
        this.leftEnabled = false;
        this.rightEnabled = false;
        this.roadType = new RoadType("/assets/straightOnRoad.png", true, false, false); // Add on the straight road.
        this.allAllowedRoads = new ArrayList<RoadType>();
        this.allAllowedRoads.add(roadType);

        // Update image to maintain consistency.
        this.update();
    }

    /*
     * Method to update the lane image, and make sure enabled variables are in line
     * with image.
     */
    public void update() {
        // For when the lane is disabled.
        if (!this.isDisabled) {
            // Account for overflow if lanes are removed but counter is not updated.
            if (this.currentRoadCounter > this.allAllowedRoads.size() - 1) {
                this.currentRoadCounter = 0;
            }

            // Get current roadtype.
            this.roadType = this.allAllowedRoads.get(this.currentRoadCounter);
            this.lane.setImage(new Image(getClass().getResourceAsStream(this.roadType.getImagePath())));
        }

        this.leftEnabled = this.roadType.getLeft();
        this.rightEnabled = this.roadType.getRight();

        // If both left and right turns are allowed, ensure that the three way roadType
        // is added.
        if (this.leftEnabled && this.rightEnabled) {
            this.allAllowedRoads.add(new RoadType("/assets/straightLeftRightRoad.png", true, true, true));
        }
    }

    /* Method to enable this lane. */
    public void enableLane() {
        this.isDisabled = false;
        this.lane.setDisable(false);
        update();
    }

    /* Method to disable this lane. */
    public void disableLane() {
        this.isDisabled = true;
        this.lane.setDisable(true);
        this.roadType = new RoadType("/assets/blackedOutRoad.png", false, false, false);
        this.update();
    }

    /*
     * Method to switch to the next image. Should only be called after the lane has
     * been clicked.
     */
    public void changeImage() {
        this.currentRoadCounter = (this.currentRoadCounter + 1) % this.allAllowedRoads.size();
        this.update();
        return;
    }

    /*
     * Method to add left turn roadtypes to this lane.
     */
    public void addLeftTurns() {
        this.allAllowedRoads.removeIf(roadType -> roadType.getLeft());
        this.allAllowedRoads.add(new RoadType("/assets/leftOnlyRoad.png", false, true, false));
        this.allAllowedRoads.add(new RoadType("/assets/straightOnAndLeftRoad.png", true, true, false));
        update();
    }

    /*
     * Method to add right turn roadtypes to this lane.
     */
    public void addRightTurns() {
        this.allAllowedRoads.removeIf(roadType -> roadType.getRight());
        this.allAllowedRoads.add(new RoadType("/assets/rightOnlyRoad.png", false, false, true));
        this.allAllowedRoads.add(new RoadType("/assets/straightOnAndRightRoad.png", true, false, true));
        update();
    }

    public void removeLeftTurns() {
        this.allAllowedRoads.removeIf(roadType -> roadType.getLeft());
        update();
    }

    public void removeRightTurns() {
        this.allAllowedRoads.removeIf(roadType -> roadType.getRight());
        update();
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
     * Getter method for lane ImageView.
     */
    public ImageView getLane() {
        return this.lane;
    }

    public RoadType getRoadType() {
        return this.roadType;
    }
};