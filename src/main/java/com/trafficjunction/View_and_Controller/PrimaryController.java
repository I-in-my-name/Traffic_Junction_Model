package com.trafficjunction.View_and_Controller;

// import java.io.IOException;
import java.util.function.UnaryOperator;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

public class PrimaryController {

    @FXML
    private GridPane vehicleNumGrid;

    // private int imageIndex = 0;
    // private final String[] imagePaths = {
    //     "/images/straightOnRoad.png",
    //     "/images/leftOnlyRoad.png",
    //     "/images/rightOnlyRoad.png",
    //     "/images/straightOnAndLeftRoad.png",
    //     "/images/straightOnAndRightRoad.png",
    //     "/images/straightLeftRightRoad.png"
    // };

    // @FXML
    // private void initialize() {
    //     imageView.setImage(new Image(getClass().getResourceAsStream("/images/straightOnRoad.png")));
    // }
    
    @FXML
    private void initialize() {
        // Input validation against words.
        for (Node node : vehicleNumGrid.getChildren()) {
            if (node instanceof TextField) {
                applyNumericRestriction((TextField) node);
            }
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

}
