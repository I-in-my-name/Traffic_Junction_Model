package com.trafficjunction.View_and_Controller;

import javafx.fxml.FXML;
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

    /*
     * Function to return the light durations for each road. Returns null if the
     * input is invalid.
     * 
     * @return Integer[] - An array of integers representing the light durations for
     * each road.
     */
    @FXML
    public Integer[] confirmLightDurations() {
        try {
            int northDuration = Integer.parseInt(northRoadDuration.getText());
            int eastDuration = Integer.parseInt(eastRoadDuration.getText());
            int southDuration = Integer.parseInt(southRoadDuration.getText());
            int westDuration = Integer.parseInt(westRoadDuration.getText());

            Integer[] durations = { northDuration, eastDuration, southDuration, westDuration };

            if (durations != null) {
                stage.close();
                return durations;
            }
            return durations;

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
        return null;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}