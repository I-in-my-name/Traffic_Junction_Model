package com.trafficjunction.View_and_Controller;

// import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

enum Direction {
    NORTH, EAST, SOUTH, WEST
}

public class PrimaryController {

    // FXML elements
    @FXML
    private GridPane vehicleNumGrid;
    @FXML
    private ImageView nLane1, nLane2, nLane3, nLane4, nLane5;
    @FXML
    private ImageView eLane1, eLane2, eLane3, eLane4, eLane5;
    @FXML
    private ImageView sLane1, sLane2, sLane3, sLane4, sLane5;
    @FXML
    private ImageView wLane1, wLane2, wLane3, wLane4, wLane5;

    @FXML
    private Button northLaneAdd, northLaneSub, eastLaneAdd, eastLaneSub, southLaneAdd, southLaneSub, westLaneAdd, westLaneSub;

    // Store the current image index for each lane.
    private final Map<ImageView, Integer> laneImageIndex = new HashMap<>();

    // Store the image paths for each lane type.
    private final String[] laneImagePaths = {
        "/assets/straightOnRoad.png",
        "/assets/leftOnlyRoad.png",
        "/assets/rightOnlyRoad.png",
        "/assets/straightOnAndLeftRoad.png",
        "/assets/straightOnAndRightRoad.png",
        "/assets/straightLeftRightRoad.png"
    };

    int northLaneNum = 5;
    int eastLaneNum = 5;
    int southLaneNum = 5;
    int westLaneNum = 5; 

    /*
     * The initialise function is called when the FXML file is loaded. It is used to set up the scene. 
     */
    @FXML
    private void initialize() {
        // Input validation against words.
        for (Node node : vehicleNumGrid.getChildren()) {
            if (node instanceof TextField) {
                applyNumericRestriction((TextField) node);
            }
        }

        // Add all lanes to a hashmap to track their current index.
        ImageView[] lanes = {nLane1, nLane2, nLane3, nLane4, nLane5, eLane1, eLane2, eLane3, eLane4, eLane5, sLane1, sLane2, sLane3, sLane4, sLane5, wLane1, wLane2, wLane3, wLane4, wLane5};
        for (ImageView lane : lanes) {
            laneImageIndex.put(lane, 0);
            // Assign the changeLaneType method to each lane.
            lane.setOnMouseClicked(event -> changeLaneType(lane));
            // Add hover effect to each lane.
            addHoverEffect(lane);
        }

        // Store the lanes in arrays by road for easier access.
        ImageView[] northRoadAllLanes = {nLane1, nLane2, nLane3, nLane4, nLane5};
        ImageView[] eastRoadAllLanes = {eLane1, eLane2, eLane3, eLane4, eLane5};
        ImageView[] southRoadAllLanes = {sLane1, sLane2, sLane3, sLane4, sLane5};
        ImageView[] westRoadAllLanes = {wLane1, wLane2, wLane3, wLane4, wLane5};

        // Assign the correct functions to each button.
        northLaneAdd.setOnAction(event -> {
            if (northLaneNum < 5) {
                northLaneNum++;  // Directly update instance variable
                addLane(northRoadAllLanes, northLaneNum);
            }
        });
        northLaneSub.setOnAction(event -> {
            if (northLaneNum > 1) {
                northLaneNum--;
                subtractLane(northRoadAllLanes, northLaneNum);
            }
        });
        eastLaneAdd.setOnAction(event -> {
            if (eastLaneNum < 5) {
                eastLaneNum++;
                addLane(eastRoadAllLanes, eastLaneNum);
            }
        });
        eastLaneSub.setOnAction(event -> {
            if (eastLaneNum > 1) {
                eastLaneNum--;
                subtractLane(eastRoadAllLanes, eastLaneNum);
            }
        });
        southLaneAdd.setOnAction(event -> {
            if (southLaneNum < 5) {
                southLaneNum++;
                addLane(southRoadAllLanes, southLaneNum);
            }
        });
        southLaneSub.setOnAction(event -> {
            if (southLaneNum > 1) {
                southLaneNum--;
                subtractLane(southRoadAllLanes, southLaneNum);
            }
        });
        westLaneAdd.setOnAction(event -> {
            if (westLaneNum < 5) {
                westLaneNum++;
                addLane(westRoadAllLanes, westLaneNum);
            }
        });
        westLaneSub.setOnAction(event -> {
            if (westLaneNum > 1) {
                westLaneNum--;
                subtractLane(westRoadAllLanes, westLaneNum);
            }
        });
    }

    @FXML
    private void subtractLane(ImageView[] lanes, int laneNum) {
        for (int i = laneNum; i < 5; i++) {
            lanes[i].setDisable(true);
            lanes[i].setImage(new Image(getClass().getResourceAsStream("/assets/blackedOutRoad.png")));
        }
    }

    @FXML
    private void addLane(ImageView[] lanes, int laneNum) {
        for (int i = 0; i < laneNum; i++) {
            lanes[i].setDisable(false);
            lanes[i].setImage(new Image(getClass().getResourceAsStream(laneImagePaths[laneImageIndex.get(lanes[i])])));
        }
    }

    /**
     * Adds a hover effect to the lane.
     * @param lane - The lane to add the hover effect to.
     */
    private void addHoverEffect(ImageView lane) {
        lane.setOnMouseEntered(event -> {
            lane.setEffect(new javafx.scene.effect.Bloom(0.8));  // Glow effect
            lane.setCursor(javafx.scene.Cursor.HAND);           // Change cursor
        });

        lane.setOnMouseExited(event -> {
            lane.setEffect(null);  // Remove effect on hover exit
        });
    }


    /*
     * This method is used to apply a numeric restriction to a text field. It only allows the user to input positive numbers of length 6 or less.
     * @param textField - The text field to apply the restriction to.
     */
    private void applyNumericRestriction(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d{0,6}") ? change : null;
        };
        textField.setTextFormatter(new TextFormatter<>(filter));
    }


    /**
     * This method is called when the user presses the "Run Simulation" button. Used to get all input parameters.
     * @return An array of integers representing the input vehicle parameters.
     */
    @FXML
    private int[] runSimulationButtonPress() {
        int[] returnVal = new int[12];
        int counter = 0;
        for (Node node : vehicleNumGrid.getChildren()) {
            if (node instanceof TextField) {
                int val;
                if (((TextField) node).getText().equals("")) {
                    val = 0;
                } else {
                    val = Integer.parseInt(((TextField) node).getText());
                }
                returnVal[counter] = val;
                counter += 1;
            }
        }
        return returnVal;
    }

    /*
     * This method is called when the user clicks on a lane. It changes the lane type to the next one in the list.
     * @param lane - The lane that was clicked on.
     */
    @FXML
    private void changeLaneType(ImageView lane) {
        int imageIndex = laneImageIndex.get(lane);
        imageIndex = (imageIndex + 1) % laneImagePaths.length;
        laneImageIndex.put(lane, imageIndex);

        lane.setImage(new Image(getClass().getResourceAsStream(laneImagePaths[imageIndex])));
    }

}
