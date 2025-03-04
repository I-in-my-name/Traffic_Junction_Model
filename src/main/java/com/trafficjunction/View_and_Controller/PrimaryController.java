package com.trafficjunction.View_and_Controller;

import java.io.File;
import java.io.IOException;

import com.trafficjunction.JunctionConfiguration;
import com.trafficjunction.UI_Utilities.DataSanitisation;
import com.trafficjunction.UI_Utilities.UILane;
import com.trafficjunction.UI_Utilities.AnimationHandler;
import com.trafficjunction.View_and_Controller.Saving_Utils.CareTaker;
import com.trafficjunction.View_and_Controller.Saving_Utils.ConfigurationSnapshot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController {

    // FXML elements
    @FXML
    private GridPane vehicleNumGrid;
    @FXML
    private ImageView nLane0, nLane1, nLane2, nLane3, nLane4;
    @FXML
    private ImageView eLane0, eLane1, eLane2, eLane3, eLane4;
    @FXML
    private ImageView sLane0, sLane1, sLane2, sLane3, sLane4;
    @FXML
    private ImageView wLane0, wLane1, wLane2, wLane3, wLane4;
    @FXML
    private ImageView trafficLightButton;

    // Buttons for adding and subtracting the number of lanes.
    @FXML
    private Button northLaneAdd, northLaneSub, eastLaneAdd, eastLaneSub, southLaneAdd, southLaneSub, westLaneAdd,
            westLaneSub;

    // Current number of lanes for each road.
    int northLaneNum = 5;
    int eastLaneNum = 5;
    int southLaneNum = 5;
    int westLaneNum = 5;

    // UILane objects
    private UILane[] northRoadAllLanes;
    private UILane[] eastRoadAllLanes;
    private UILane[] southRoadAllLanes;
    private UILane[] westRoadAllLanes;

    // File system FXML Links:
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem loadMenuItem;

    // File system Java resources:
    public FileChooser fileChooser = new FileChooser();

    // Undo Redo FXML Links:
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;

    // Undo Redo Java resources:
    private CareTaker careTaker = new CareTaker();
    private JunctionConfiguration configuration = new JunctionConfiguration();

    // Anchor that all junction components are contained within.
    @FXML
    private AnchorPane junctionAnchor;

    @FXML
    private void initialize() {
        // Input validation against words.
        for (Node node : vehicleNumGrid.getChildren()) {
            if (node instanceof TextField) {
                DataSanitisation.applyNumericRestriction((TextField) node);
            }
        }

        AnimationHandler animationHandler = new AnimationHandler(junctionAnchor);

        // Add button press to traffic light to open the window.
        trafficLightButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trafficjunction/trafficLight.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Traffic Light Controller");
                stage.initModality(Modality.APPLICATION_MODAL);

                // Get controller and set the stage
                TrafficLightController controller = loader.getController();
                controller.setStage(stage);

                stage.show();
            } catch (IOException e) {
                System.out.println("Error loading traffic light FXML: " + e);
                e.printStackTrace();
            }
        });

        buttonHoverEffects();

        // Initialize UILane objects
        northRoadAllLanes = new UILane[] {
                new UILane(nLane0),
                new UILane(nLane1),
                new UILane(nLane2),
                new UILane(nLane3),
                new UILane(nLane4)
        };

        eastRoadAllLanes = new UILane[] {
                new UILane(eLane0),
                new UILane(eLane1),
                new UILane(eLane2),
                new UILane(eLane3),
                new UILane(eLane4)
        };

        southRoadAllLanes = new UILane[] {
                new UILane(sLane0),
                new UILane(sLane1),
                new UILane(sLane2),
                new UILane(sLane3),
                new UILane(sLane4)
        };

        westRoadAllLanes = new UILane[] {
                new UILane(wLane0),
                new UILane(wLane1),
                new UILane(wLane2),
                new UILane(wLane3),
                new UILane(wLane4)
        };

        // Assign change image function to all lanes.
        for (UILane lane : northRoadAllLanes) {
            lane.getLane().setOnMouseClicked(event -> updateLanes(lane, northRoadAllLanes, northLaneNum));
        }
        for (UILane lane : eastRoadAllLanes) {
            lane.getLane().setOnMouseClicked(event -> updateLanes(lane, eastRoadAllLanes, eastLaneNum));
        }
        for (UILane lane : southRoadAllLanes) {
            lane.getLane().setOnMouseClicked(event -> updateLanes(lane, southRoadAllLanes, southLaneNum));
        }
        for (UILane lane : westRoadAllLanes) {
            lane.getLane().setOnMouseClicked(event -> updateLanes(lane, westRoadAllLanes, westLaneNum));
        }

        // Allow right turns on the rightmost lane.
        northRoadAllLanes[0].addRightTurns();
        eastRoadAllLanes[0].addRightTurns();
        southRoadAllLanes[0].addRightTurns();
        westRoadAllLanes[0].addRightTurns();

        // Allow left turns on the leftmost lane.
        northRoadAllLanes[4].addLeftTurns();
        eastRoadAllLanes[4].addLeftTurns();
        southRoadAllLanes[4].addLeftTurns();
        westRoadAllLanes[4].addLeftTurns();

        // Assign the correct functions to each button.
        northLaneAdd.setOnAction(event -> {
            if (northLaneNum < 5) {
                northLaneNum++;
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

        // ######################### Trialling animation ###################//

        // animationHandler.addToAnchorPane();
        animationHandler.chooseAnimation('W', 'N', 3);

        // ######################### Saving and Memento's section ###################//

        fileChooser.setTitle("value");
        loadMenuItem.setOnAction((ActionEvent event) -> {
            File chosenFile = fileChooser.showOpenDialog((Stage) vehicleNumGrid.getScene().getWindow());
            try {
                JunctionConfiguration loadedConfiguration = JunctionConfiguration.loadObject(chosenFile);
                configuration.setDirectionInfo(loadedConfiguration.getDirectionInfo());
                careTaker.addSnap(new ConfigurationSnapshot(configuration));
                System.out.println("SNAPSHOT ADDED");
                populateFieldsWithData(configuration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        saveMenuItem.setOnAction((ActionEvent event) -> {
            File chosenFile = fileChooser.showSaveDialog((Stage) vehicleNumGrid.getScene().getWindow());
            try {
                // move into program
                gatherUserData().saveObject(chosenFile);
            } catch (Exception e) {
            }
        });

    }

    @FXML
    private void subtractLane(UILane[] lanes, int laneNum) {
        System.out.println("Subtracting lane");
        for (int i = laneNum; i < 5; i++) {
            lanes[i].disableLane();
            lanes[i].update();
        }
        lanes[laneNum - 1].addLeftTurns();
    }

    @FXML
    private void addLane(UILane[] lanes, int laneNum) {
        System.out.println("Adding lane");
        for (int i = 0; i < laneNum; i++) {
            lanes[i].enableLane();
            lanes[i].update();
        }
        lanes[laneNum - 1].addLeftTurns();
        lanes[laneNum - 2].removeLeftTurns();
    }

    @FXML
    private void runSimulationButtonPress() {
        JunctionConfiguration userData = gatherUserData();

        // TODO call simulation

    }

    private void updateLanes(UILane lane, UILane[] laneArr, int laneNum) {
        // Change the image of the current lane.

        lane.changeImage();

        /*
         * Now, go through every lane in this road.
         * If the lane is a left-turn lane. Allow lane to the right of it to turn left.
         * If the lane is a right-turn lane, allow lane to the left of it to turn left.
         * Do not change lanes that are disabled.
         */
        for (int i = 0; i < laneNum; i++) {
            System.out.println("On lane " + i);
            // Check for left turns.
            if (laneArr[i].getRoadType().getLeft()) {
                if (i > 0) {
                    laneArr[i - 1].addLeftTurns();
                }
            }

            // Do the same for right turns.
            if (laneArr[i].getRoadType().getRight()) {
                if (i < laneArr.length - 1) {
                    laneArr[i + 1].addRightTurns();
                }
            }

            // If the road is a straight road, make sure the lanes to the left and right
            // are not left or right turn lanes. UNLESS they are the end roads.
            if (!laneArr[i].getRoadType().getLeft() && !laneArr[i].getRoadType().getRight()) {
                if (i > 0) {
                    laneArr[i - 1].removeLeftTurns();
                }
                if (i < laneArr.length - 1) {
                    laneArr[i + 1].removeRightTurns();
                }
            }

            // Update any lanes that may not have been affected in case.
            laneArr[i].update();
        }

        for (int i = laneNum - 1; i >= 0; i--) {
            System.out.println("On lane " + i);

            // Check for left turns.
            if (laneArr[i].getRoadType().getLeft()) {
                if (i > 0) {
                    laneArr[i - 1].addLeftTurns();
                }
            }

            // Do the same for right turns.
            if (laneArr[i].getRoadType().getRight()) {
                if (i < laneArr.length - 1) {
                    laneArr[i + 1].addRightTurns();
                }
            }

            // If the road is a straight road, make sure the lanes to the left and right
            // are not left or right turn lanes. UNLESS they are the end roads.
            if (!laneArr[i].getRoadType().getLeft() && !laneArr[i].getRoadType().getRight()) {
                if (i > 0) {
                    laneArr[i - 1].removeLeftTurns();
                }
                if (i < laneArr.length - 1) {
                    laneArr[i + 1].removeRightTurns();
                }
            }

            // Update any lanes that may not have been affected in case.
            laneArr[i].update();
        }

        // Re-updating the end lanes in case they were changed.
        laneArr[laneNum - 1].addLeftTurns();
        laneArr[0].addRightTurns();

    }

    /*
     * Function to apply button hover effects to required buttons. Call in the
     * initialize function.
     */
    private void buttonHoverEffects() {
        String buttonHoverStyle = "-fx-effect: dropShadow(gaussian, rgba(0, 200, 255, 0.7), 15, 0.5, 0, 0);" +
                "-fx-scale-x: 1.1;" +
                "-fx-scale-y: 1.1;";

        // Apply to traffic light button.
        trafficLightButton.setOnMouseEntered(event -> {
            trafficLightButton.setStyle(buttonHoverStyle);
        });

        trafficLightButton.setOnMouseExited(event -> {
            trafficLightButton.setStyle("");
        });
    }

    /*
     * Both of the next two functions must be added to whenever new data is decided
     * to be relevant
     */
    private JunctionConfiguration gatherUserData() {
        // This is notably in order.
        int[] sequentialList = new int[12];
        int index = 0;
        for (Node child : vehicleNumGrid.getChildren()) {
            try {
                TextField field = (TextField) child;
                String text = field.getText(); // replaces invisible/non-printable characters
                int number = 0;

                if (!text.isEmpty()) {
                    number = Integer.parseInt(text);
                }
                sequentialList[index] = number;
                index++;
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        JunctionConfiguration data = new JunctionConfiguration();
        if (data.setDirectionInfo(sequentialList))
            return data;

        // TODO ERROR HANDLING, needs to be handled elsewhere to appropriately show
        // error message.
        return null;
    }

    private boolean populateFieldsWithData(JunctionConfiguration configuration) {
        int[] directionalThroughput = configuration.getDirectionInfo();
        int index = 0;
        for (Node child : vehicleNumGrid.getChildren()) {
            try {
                TextField field = (TextField) child;
                field.setText(String.valueOf(directionalThroughput[index]));
                index++;
            } catch (Exception ignored) {
            }
        }
        return true;
    }

    @FXML
    private void undo() {
        System.out.println("undo pressed");
        careTaker.undo();
        populateFieldsWithData(configuration);

    }

    @FXML
    private void redo() {
        System.out.println("undo pressed");
        careTaker.redo();
        populateFieldsWithData(configuration);
    }
}
