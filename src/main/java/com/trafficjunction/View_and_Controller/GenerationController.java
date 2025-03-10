package com.trafficjunction.View_and_Controller;

import java.io.File;
import java.util.Map;

import com.trafficjunction.JunctionMetrics;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GenerationController {

    private Stage primaryStage;

    @FXML
    HBox generatedProfiles;

    Stage stage;

    Map<Float, JunctionMetrics> generationData;
    int optionNumberCounter = 0;

    public void setGenerationData(Map<Float, JunctionMetrics> incomingData) {
        generationData = incomingData;
    }

    public void displayGenerationProfiles() {
        stage = (Stage) generatedProfiles.getScene().getWindow();

        for (Map.Entry<Float, JunctionMetrics> entrySet : generationData.entrySet()) {
            AnchorPane newProfile = makeGeneratedProfile(optionNumberCounter, entrySet.getValue());

            Platform.runLater(() -> {
                generatedProfiles.setSpacing(10);
                generatedProfiles.setPadding(new Insets(10));

                generatedProfiles.getChildren().add(newProfile);
                generatedProfiles.requestLayout();
            });

            break;
        }
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

    private AnchorPane makeGeneratedProfile(int optionNumber, JunctionMetrics metrics) {
        AnchorPane anchorPane = new AnchorPane();

        VBox vBox = new VBox(5);

        File imageFile = new File("/assets/istockphoto-1147544807-612x612.jpg");
        // Create an Image object
        Image image = new Image(imageFile.toURI().toString());
        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image);

        vBox.getChildren().add(imageView);

        Text optionText = new Text("Option " + optionNumber);
        vBox.getChildren().add(optionText);

        HBox hBox = new HBox();
        vBox.getChildren().add(hBox);

        // All values verified using scenebuilder before migration to java creation
        Text miniMetrics = new Text("Overall Score:\nAverage Waiting Time:\nVehicle Throughput:\n\n");
        miniMetrics.setStrokeType(StrokeType.OUTSIDE);
        miniMetrics.setStrokeWidth(0.0);
        miniMetrics.setWrappingWidth(170.13671875);
        hBox.getChildren().add(miniMetrics);

        Button expandButton = new Button("Expand");
        expandButton.setPrefHeight(49.0);
        expandButton.setPrefWidth(222.0);
        expandButton.setFont(Font.font("System Bold", 16));
        vBox.getChildren().add(expandButton);

        Button removeButton = new Button("Remove");
        removeButton.setPrefHeight(49.0);
        removeButton.setPrefWidth(222.0);
        removeButton.setFont(Font.font("System Bold", 16));
        vBox.getChildren().add(removeButton);

        return anchorPane;
    }
}
