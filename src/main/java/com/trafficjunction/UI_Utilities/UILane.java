package com.trafficjunction.UI_Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

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
    public ArrayList<RoadType> allAllowedRoads; // All the road types that are allowed for this lane.
    public int currentRoadCounter = 0;
    public boolean isDisabled = false;

    public UILane(ImageView laneObj) {
        this.lane = laneObj;
        this.leftEnabled = false;
        this.rightEnabled = false;
        this.roadType = new RoadType("/assets/straightOnRoad.png", true, false, false); // Add on the straight road.
        this.allAllowedRoads = new ArrayList<>();
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

        } else {
            this.lane.setImage(new Image(getClass().getResourceAsStream(this.roadType.getImagePath())));
        }

        this.leftEnabled = this.roadType.getLeft();
        this.rightEnabled = this.roadType.getRight();

        // If both left and right turns are allowed, ensure that the three way roadType
        // is added.
        RoadType allTurns = new RoadType("/assets/straightLeftRightRoad.png", true, true, true);
        if (this.leftEnabled && this.rightEnabled && !allAllowedRoads.contains(allTurns)) {
            this.allAllowedRoads.add(allTurns);
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
    }

    /*
     * Method to add left turn roadtypes to this lane.
     */
    public void addLeftTurns() {
        if (this.isDisabled) {
            return;
        }
        this.allAllowedRoads.removeIf(roadType -> roadType.getLeft());
        this.allAllowedRoads.add(new RoadType("/assets/leftOnlyRoad.png", false, true, false));
        this.allAllowedRoads.add(new RoadType("/assets/straightOnAndLeftRoad.png", true, true, false));
        sortAllowedRoads();
        update();
    }

    public void addLeftTurn() {
        this.removeLeftTurn();
        this.allAllowedRoads.add(new RoadType("/assets/leftOnlyRoad.png", false, true, false));
        sortAllowedRoads();
        update();
    }

    /*
     * Method to add right turn roadtypes to this lane.
     */
    public void addRightTurns() {
        if (this.isDisabled) {
            return;
        }
        this.allAllowedRoads.removeIf(roadType -> roadType.getRight());
        this.allAllowedRoads.add(new RoadType("/assets/rightOnlyRoad.png", false, false, true));
        this.allAllowedRoads.add(new RoadType("/assets/straightOnAndRightRoad.png", true, false, true));
        sortAllowedRoads();
        update();
    }

    public void addRightTurn() {
        this.removeRightTurn();
        this.allAllowedRoads.add(new RoadType("/assets/rightOnlyRoad.png", false, false, true));
        sortAllowedRoads();
    }

    public void addForward() {
        this.removeForward();
        this.allAllowedRoads.add(new RoadType("/assets/straightOnRoad.png", true, false, false));
        sortAllowedRoads();
    }

    // doesn't remove multiroad
    public void addMixedTurns() {
        this.removeMixedForward();
        this.allAllowedRoads.add(new RoadType("/assets/straightOnAndLeftRoad.png", true, true, false));
        this.allAllowedRoads.add(new RoadType("/assets/straightOnAndRightRoad.png", true, false, true));
        sortAllowedRoads();
        update();
    }

    public void addMultiRoad() {
        this.removeMultiRoad();
        this.allAllowedRoads.add(new RoadType("/assets/straightLeftRightRoad.png", true, true, true));
        sortAllowedRoads();
    }

    public void removeLeftTurns() {
        this.allAllowedRoads.removeIf(roadType -> roadType.getLeft());
        sortAllowedRoads();
    }

    public void removeRightTurns() {
        this.allAllowedRoads.removeIf(roadType -> roadType.getRight());
        sortAllowedRoads();
    }

    public void removeLeftTurn() {
        this.allAllowedRoads.removeIf(new Predicate<RoadType>() {
            @Override
            public boolean test(RoadType arg0) {
                return (!arg0.getStraight() && arg0.getLeft() && !arg0.getRight());
            }
        });
        sortAllowedRoads();
    }

    public void removeRightTurn() {
        this.allAllowedRoads.removeIf(new Predicate<RoadType>() {
            @Override
            public boolean test(RoadType arg0) {
                return (!arg0.getStraight() && !arg0.getLeft() && arg0.getRight());
            }
        });
        sortAllowedRoads();
    }

    public void removeForward() {
        this.allAllowedRoads.removeIf(new Predicate<RoadType>() {
            @Override
            public boolean test(RoadType arg0) {
                return (arg0.getStraight() && !arg0.getLeft() && !arg0.getRight());
            }
        });
        sortAllowedRoads();
    }

    // LF RF
    public void removeMixedForward() {
        this.allAllowedRoads.removeIf(new Predicate<RoadType>() {
            @Override
            public boolean test(RoadType arg0) {
                return (arg0.getStraight() && (arg0.getLeft() || arg0.getRight()));
            }
        });
        sortAllowedRoads();

    }

    public void removeMixedLeft() {
        this.allAllowedRoads.removeIf(new Predicate<RoadType>() {
            @Override
            public boolean test(RoadType arg0) {
                return (arg0.getStraight() && arg0.getLeft());
            }
        });
        sortAllowedRoads();
        update();
    }

    public void removeMixedRight() {
        this.allAllowedRoads.removeIf(new Predicate<RoadType>() {
            @Override
            public boolean test(RoadType arg0) {
                return (arg0.getStraight() && arg0.getRight());
            }
        });
        sortAllowedRoads();
        update();
    }

    public void removeMultiRoad() {
        this.allAllowedRoads.removeIf(new Predicate<RoadType>() {
            @Override
            public boolean test(RoadType arg0) {
                return (arg0.getStraight() && arg0.getLeft() && arg0.getRight());
            }
        });
        sortAllowedRoads();
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

    /*
     * Getter method for the roadtype.
     */
    public RoadType getRoadType() {
        return this.roadType;
    }

    public void sortAllowedRoads() {
        RoadType currentRoad = this.getRoadType();

        this.allAllowedRoads.sort(new Comparator<RoadType>() {
            @Override
            public int compare(RoadType arg0, RoadType arg1) {
                List<String> orderedString = Arrays.asList("L", "LF", "LFR", "R", "FR", "F");
                Integer firstPosition = Integer.valueOf(orderedString.indexOf(arg0.getAsChars()));
                Integer secondPosition = Integer.valueOf(orderedString.indexOf(arg1.getAsChars()));
                return firstPosition.compareTo(secondPosition);
            }
        });
        for (RoadType roadType : allAllowedRoads) {
            if (roadType.imagePath.equals(currentRoad.imagePath)) {
                currentRoadCounter = this.allAllowedRoads.indexOf(roadType);
            }
        }
    }
};