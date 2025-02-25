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

    @FXML
    private ImageView carImage;

    AnimationHandler animationHandler = new AnimationHandler();

    @FXML
    private void initialize() {
        // Input validation against words.
        for (Node node : vehicleNumGrid.getChildren()) {
            if (node instanceof TextField) {
                DataSanitisation.applyNumericRestriction((TextField) node);
            }
        }

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
                new UILane(nLane0, 0),
                new UILane(nLane1, 1),
                new UILane(nLane2, 2),
                new UILane(nLane3, 3),
                new UILane(nLane4, 4)
        };

        eastRoadAllLanes = new UILane[] {
                new UILane(eLane0, 0),
                new UILane(eLane1, 1),
                new UILane(eLane2, 2),
                new UILane(eLane3, 3),
                new UILane(eLane4, 4)
        };

        southRoadAllLanes = new UILane[] {
                new UILane(sLane0, 0),
                new UILane(sLane1, 1),
                new UILane(sLane2, 2),
                new UILane(sLane3, 3),
                new UILane(sLane4, 4)
        };

        westRoadAllLanes = new UILane[] {
                new UILane(wLane0, 0),
                new UILane(wLane1, 1),
                new UILane(wLane2, 2),
                new UILane(wLane3, 3),
                new UILane(wLane4, 4)
        };

        // Assign change image function to all lanes.
        for (UILane lane : northRoadAllLanes) {
            lane.getLane().setOnMouseClicked(event -> updateImage(lane, northRoadAllLanes));
        }
        for (UILane lane : eastRoadAllLanes) {
            lane.getLane().setOnMouseClicked(event -> updateImage(lane, eastRoadAllLanes));
        }
        for (UILane lane : southRoadAllLanes) {
            lane.getLane().setOnMouseClicked(event -> updateImage(lane, southRoadAllLanes));
        }
        for (UILane lane : westRoadAllLanes) {
            lane.getLane().setOnMouseClicked(event -> updateImage(lane, westRoadAllLanes));
        }

        // Allow right turns on the rightmost lane.
        northRoadAllLanes[0].enableRight();
        eastRoadAllLanes[0].enableRight();
        southRoadAllLanes[0].enableRight();
        westRoadAllLanes[0].enableRight();

        // Allow left turns on the leftmost lane.
        northRoadAllLanes[4].enableLeft();
        eastRoadAllLanes[4].enableLeft();
        southRoadAllLanes[4].enableLeft();
        westRoadAllLanes[4].enableLeft();

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

        animationHandler.createAnimation(3, 0, 2, 0, carImage);

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

        System.out.println("End position is " + carImage.localToScene(carImage.getBoundsInLocal()));

    }

    /*
     * Function to disable lanes.
     * 
     * @param lanes - The array of UILane objects to disable. Enables left turns for
     * the left-most lane.
     * 
     * @param laneNum - The current number of lanes that should be ENABLED.
     */
    @FXML
    private void subtractLane(UILane[] lanes, int laneNum) {
        for (int i = laneNum; i < 5; i++) {
            lanes[i].disableLane();
        }

        // Allow left turns on the highest lane number.
        lanes[laneNum - 1].enableLeft();

    }

    /*
     * Function to enable another lane. Also enables left turns for the left-most
     * lane.
     * 
     * @param lanes - The array of UILane objects to enable.
     * 
     * @param laneNum - The current number of lanes that should be ENABLED.
     */
    @FXML
    private void addLane(UILane[] lanes, int laneNum) {
        for (int i = 0; i < laneNum; i++) {
            lanes[i].enableLane();
        }

        // Allow left turns on the highest lane number.
        lanes[laneNum - 1].enableLeft();
        // Disable left turns from the previous highest lane number.
        lanes[laneNum - 2].disableLeft();
    }

    @FXML
    private void runSimulationButtonPress() {
        JunctionConfiguration userData = gatherUserData();

        // TODO call simulation

    }

    private void updateImage(UILane lane, UILane[] laneArr) {
        lane.changeImage();

        // Update the surrounding lanes based on the current lane's type.
        if (lane.getPosition() > 0) {
            if (lane.isLeft()) {
                System.out.println("Enabling left for lane " + laneArr[lane.getPosition() - 1].getPosition());
                laneArr[lane.getPosition() - 1].enableLeft();
            } else {
                System.out.println("Disabling left for lane " + laneArr[lane.getPosition() - 1].getPosition());
                laneArr[lane.getPosition() - 1].disableLeft();
            }
        }

        if (lane.getPosition() < laneArr.length - 1) {
            if (lane.isRight()) {
                System.out.println("Enabling right for lane " + laneArr[lane.getPosition() + 1].getPosition());
                laneArr[lane.getPosition() + 1].enableRight();
            } else {
                System.out.println("Disabling right for lane " + laneArr[lane.getPosition() + 1].getPosition());
                laneArr[lane.getPosition() + 1].disableRight();
            }
        }
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
