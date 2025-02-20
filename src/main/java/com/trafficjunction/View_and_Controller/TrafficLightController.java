package com.trafficjunction.View_and_Controller;

import com.trafficjunction.UI_Utilities.DataSanitisation;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TrafficLightController {

    @FXML
    private TextField northRoadDuration;
    @FXML
    private TextField eastRoadDuration;
    @FXML
    private TextField southRoadDuration;
    @FXML
    private TextField westRoadDuration;

    @FXML
    private Stage stage;

    @FXML
    private void initialize() {
        // Make sure only numbers can be inputted into the traffic light fields.
        DataSanitisation.applyNumericRestriction(northRoadDuration);
        DataSanitisation.applyNumericRestriction(eastRoadDuration);
        DataSanitisation.applyNumericRestriction(southRoadDuration);
        DataSanitisation.applyNumericRestriction(westRoadDuration);
    }

    /*
     * Function to return the light durations for each road. Returns null if the
     * input is invalid.
     * 
     * @return Integer[] - An array of integers representing the light durations for
     * each road.
     */
    @FXML
    public Integer[] confirmLightDurations() {
        // Check if any fields are empty.
        if (northRoadDuration.getText().trim().isEmpty() || eastRoadDuration.getText().trim().isEmpty()
                || southRoadDuration.getText().trim().isEmpty() || westRoadDuration.getText().trim().isEmpty()) {
            showErrorAlert("Error", "Please fill in all fields.");
            return null;
        }

        try {
            int northRoadDuration = Integer.parseInt(this.northRoadDuration.getText().trim());
            int eastRoadDuration = Integer.parseInt(this.eastRoadDuration.getText().trim());
            int southRoadDuration = Integer.parseInt(this.southRoadDuration.getText().trim());
            int westRoadDuration = Integer.parseInt(this.westRoadDuration.getText().trim());

            if (northRoadDuration <= 0 || eastRoadDuration <= 0 || southRoadDuration <= 0 || westRoadDuration <= 0) {
                showErrorAlert("Error", "Please enter a positive integer for each field.");
                return null;
            }

            Integer[] durations = { northRoadDuration, eastRoadDuration, southRoadDuration, westRoadDuration };

            // TODO
            // Add a confirmation window and send data to data store.
            stage.close();
            return durations;
        } catch (NumberFormatException e) {
            showErrorAlert("Error", "Please enter a valid integer for each field.");
            return null;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(stage);
        alert.showAndWait();
    }

}