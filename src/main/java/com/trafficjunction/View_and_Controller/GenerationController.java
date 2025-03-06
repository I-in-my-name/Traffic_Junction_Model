package com.trafficjunction.View_and_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GenerationController {

    private Stage primaryStage;

    public GenerationController() {
        System.out.println("SecondWindowController constructor called!");

        return;
    }

    @FXML
    public void initialize() {
        System.out.println("SecondWindowController initialized!");

    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        if (primaryStage != null) {
            primaryStage.show();
        }

        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
}
