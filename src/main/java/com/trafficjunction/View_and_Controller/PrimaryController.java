package com.trafficjunction.View_and_Controller;

// import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PrimaryController {

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

    ImageView[] northRoad = {nLane1, nLane2, nLane3, nLane4, nLane5};
    ImageView[] eastRoad = {eLane1, eLane2, eLane3, eLane4, eLane5};
    ImageView[] southRoad = {sLane1, sLane2, sLane3, sLane4, sLane5};
    ImageView[] westRoad = {wLane1, wLane2, wLane3, wLane4, wLane5};
    

    // Store the current image index for each lane.
    private final Map<ImageView, Integer> laneImageIndex = new HashMap<>();


    private final String[] imagePaths = {
        "/assets/straightOnRoad.png",
        "/assets/leftOnlyRoad.png",
        "/assets/rightOnlyRoad.png",
        "/assets/straightOnAndLeftRoad.png",
        "/assets/straightOnAndRightRoad.png",
        "/assets/straightLeftRightRoad.png"
    };

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
        }


    }


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
        imageIndex = (imageIndex + 1) % imagePaths.length;
        laneImageIndex.put(lane, imageIndex);

        lane.setImage(new Image(getClass().getResourceAsStream(imagePaths[imageIndex])));
    }

}
