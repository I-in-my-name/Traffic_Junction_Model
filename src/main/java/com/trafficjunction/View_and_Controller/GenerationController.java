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

    public GenerationController() {
        System.out.println("SecondWindowController constructor called!");
    }

    public void setGenerationData(Map<Float, JunctionMetrics> incomingData) {
        generationData = incomingData;
    }

    public void displayGenerationProfiles() {
        stage = (Stage) generatedProfiles.getScene().getWindow();

        System.out.println("Here");

        for (Map.Entry<Float, JunctionMetrics> entrySet : generationData.entrySet()) {
            System.out.println("Inside");
            AnchorPane newProfile = makeGeneratedProfile(optionNumberCounter, entrySet.getValue());

            Platform.runLater(() -> {
                System.out.println("RUNNNN");

                generatedProfiles.setSpacing(10);
                generatedProfiles.setPadding(new Insets(10));

                generatedProfiles.getChildren().add(newProfile);
                generatedProfiles.requestLayout();
                System.out.println(":()");

            });

            break;
        }
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

        // <VBox alignment="TOP_CENTER" layoutX="5.0" layoutY="10.0" prefHeight="480.0"
        // prefWidth="240.0" spacing="5.0" style="-fx-border-color: black;
        // -fx-border-radius: 10;">
        // <children>
        // <ImageView fitHeight="200.0" fitWidth="200.0" pickOnBounds="true"
        // preserveRatio="true">
        // <image>
        // <Image url="@../../assets/istockphoto-1147544807-612x612.jpg" />
        // </image>
        // </ImageView>
        // <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Option 1"
        // textAlignment="CENTER">
        // <font>
        // <Font name="System Bold" size="16.0" />
        // </font>
        // </Text>
        // <HBox>
        // <children>
        // <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Overall Score:&#10;Average
        // Waiting Time:&#10;Vehicle Throughput:&#10;&#10;"
        // wrappingWidth="170.13671875">
        // <font>
        // <Font size="14.0" />
        // </font>
        // </Text>
        // <Text strokeType="OUTSIDE" strokeWidth="0.0"
        // text="986/1000&#10;1m43s&#10;450" textAlignment="RIGHT"
        // wrappingWidth="62.95703125">
        // <font>
        // <Font size="14.0" />
        // </font>
        // </Text>
        // </children>
        // </HBox>
        // <Button mnemonicParsing="false" prefHeight="49.0" prefWidth="222.0"
        // text="Expand">
        // <font>
        // <Font name="System Bold" size="16.0" />
        // </font>
        // </Button>
        // <Button mnemonicParsing="false" prefHeight="49.0" prefWidth="222.0"
        // text="Delete" textFill="RED">
        // <font>
        // <Font name="System Bold" size="16.0" />
        // </font>
        // </Button>
        // </children>
        // <padding>
        // <Insets bottom="5.0" left="5.0" right="5.0" top="10.0" />
        // </padding>
        // </VBox>
        // </children>
        // <padding>
        // <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
        // </padding>
    }
}
