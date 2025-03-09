package com.trafficjunction.View_and_Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.trafficjunction.JunctionConfiguration;
import com.trafficjunction.JunctionMetrics;
import com.trafficjunction.Junction_Classes.Junction;
import com.trafficjunction.UI_Utilities.AnimationHandler;
import com.trafficjunction.UI_Utilities.DataSanitisation;
import com.trafficjunction.UI_Utilities.RoadType;
import com.trafficjunction.UI_Utilities.UILane;
import com.trafficjunction.View_and_Controller.Saving_Utils.CareTaker;
import com.trafficjunction.View_and_Controller.Saving_Utils.ConfigurationSnapshot;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController {

    protected static final String VBoxBuilder = null;
    // FXML elements
    @FXML
    private TabPane vehicleTabs;
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

    // Configuration FXML Links:
    @FXML
    private TextField NTE;
    @FXML
    private TextField NTS;
    @FXML
    private TextField NTW;

    @FXML
    private TextField ETS;
    @FXML
    private TextField ETW;
    @FXML
    private TextField ETN;

    @FXML
    private TextField STW;
    @FXML
    private TextField STN;
    @FXML
    private TextField STE;

    @FXML
    private TextField WTN;
    @FXML
    private TextField WTE;
    @FXML
    private TextField WTS;

    TextField[] allTextFields;

    @FXML
    private Button runSimulationButton;
    @FXML
    private Button cancelSimulationButton;

    // The FXML components used to display the metrics.
    @FXML
    private TextField northAvgWT;
    @FXML
    private TextField northMaxWT;
    @FXML
    private TextField northAvgQL;
    @FXML
    private TextField northMaxQL;

    @FXML
    private TextField eastAvgWT;
    @FXML
    private TextField eastMaxWT;
    @FXML
    private TextField eastAvgQL;
    @FXML
    private TextField eastMaxQL;

    @FXML
    private TextField southAvgWT;
    @FXML
    private TextField southMaxWT;
    @FXML
    private TextField southAvgQL;
    @FXML
    private TextField southMaxQL;

    @FXML
    private TextField westAvgWT;
    @FXML
    private TextField westMaxWT;
    @FXML
    private TextField westAvgQL;
    @FXML
    private TextField westMaxQL;

    @FXML
    private TextField overallAvgWT;
    @FXML
    private TextField overallMaxWT;
    @FXML
    private TextField overallAvgQL;
    @FXML
    private TextField overallMaxQL;
    @FXML
    private TextField overallScore;

    // File system FXML Links:
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem loadMenuItem;

    // File system Java resources:
    public FileChooser fileChooser = new FileChooser();

    // Undo Redo FXML Links:
    @FXML
    private Menu undoButton;
    @FXML
    private Menu redoButton;

    private Stage stage;

    // Undo Redo Java resources:
    private CareTaker careTaker = new CareTaker();

    // Value to decide whether the simulation is currently running.
    private boolean isRunningSimulation = false;

    private JunctionMetrics junctionMetrics;

    // Anchor that all junction components are contained within.
    @FXML
    private AnchorPane junctionAnchor;

    int[] trafficLightDurs = new int[5];

    @FXML
    private void initialize() {
        try {
            populateInputDataMetrics();
        } catch (Exception ignored) {
        }
        int[] dummyData = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        junctionMetrics = new JunctionMetrics(dummyData, dummyData);
        careTaker.addSnap(new ConfigurationSnapshot(junctionMetrics));

        // Initialize the stage when the anchor pane is ready, getting it from any FXML
        // component
        Platform.runLater(() -> {
            stage = (Stage) junctionAnchor.getScene().getWindow();
        });

        TextField[] allTextFields = { NTE, NTS, NTW, ETS, ETW, ETN, STW, STN, STE, WTN, WTE, WTS };
        this.allTextFields = allTextFields;

        // Input validation against words.
        for (TextField node : allTextFields) {
            DataSanitisation.applyNumericRestriction(node);
        }

        // Declare the animation handler for animations.
        AnimationHandler animationHandler = new AnimationHandler(junctionAnchor);

        // Hide the cancel simulation button until the simulation is running.
        cancelSimulationButton.setDisable(true);
        cancelSimulationButton.setVisible(false);

        // Add button press to traffic light to open the window.
        trafficLightButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trafficjunction/trafficLight.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Traffic Light Configuration");
                stage.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(getClass().getResourceAsStream("/assets/trafficLightIcon.png"));
                stage.getIcons().add(icon);

                // Get controller and set the stage
                TrafficLightController controller = loader.getController();
                controller.setStage(stage);

                stage.showAndWait();

                trafficLightDurs = controller.confirmLightDurations();

            } catch (IOException e) {
                System.out.println("Error loading traffic light FXML: " + e);
                e.printStackTrace();
            }
        });

        buttonHoverEffects();

        setAllLanesToDefaults();

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
            File chosenFile = fileChooser.showOpenDialog((Stage) NTE.getScene().getWindow());
            if (chosenFile.canRead()) {
                try {
                    JunctionMetrics loadedMetrics = JunctionMetrics.loadObject(chosenFile);
                    junctionMetrics.copyValues(loadedMetrics);
                    careTaker.addSnap(new ConfigurationSnapshot(junctionMetrics));
                    populateFieldsWithData(junctionMetrics);
                } catch (NullPointerException nullPointerException) {
                    // Exited out of file chooser so ignore
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Wrong Filetype");
                    errorAlert.setContentText(
                            "The File you are trying to access is of the wrong filetype. Please ensure you have selected the correct file.");
                    errorAlert.showAndWait();
                }
            } else {
                System.out.println("RRRRR");
            }
        });

        saveMenuItem.setOnAction((ActionEvent event) -> {
            File chosenFile = fileChooser.showSaveDialog((Stage) NTE.getScene().getWindow());
            if (chosenFile != null) {
                try {
                    // move into program
                    populateInputDataMetrics();
                    junctionMetrics.saveObject(chosenFile);
                } catch (Exception ignored) {
                }
            }
        });

        Label undoLabel = new Label("Undo");
        undoLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                undo();
            }
        });
        undoButton.setGraphic(undoLabel);

        Label redoLabel = new Label("Redo");
        redoLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                redo();
            }
        });
        redoButton.setGraphic(redoLabel);
    }

    private void setAllLanesToDefaults() {
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

    /*
     * Function to count the different road types in a road.
     * 
     * @param lanes - The UILane array of lanes we are looking at.
     * 
     * @param laneNum - The number of active lanes in this array.
     * 
     * @return int[] - Return the number of each type of lane. Stored in an array
     * with the indexing of {L, LF, F, RF, R}.
     */
    private int[] countRoadTypes(UILane[] lanes, int laneNum) {
        int[] data = { 0, 0, 0, 0, 0 };

        for (int i = 0; i < laneNum; i++) {
            String laneString = lanes[i].getRoadType().getAsChars();
            switch (laneString) {
                case "L":
                    data[0] += 1;
                    break;
                case "LF":
                    data[1] += 1;
                    break;
                case "F":
                    data[2] += 1;
                    break;
                case "FR":
                    data[3] += 1;
                    break;
                case "R":
                    data[4] += 1;
                    break;
                default:
                    break;
            }
        }

        return data;
    }

    /*
     * Function to populate the DataMetrics object with the user's input data.
     */
    private void populateInputDataMetrics() {

        // Get all the vehicle number data and insert into this array.
        int[] vehicleNums = new int[12];
        for (int i = 0; i < vehicleNums.length; i++) {
            try {
                vehicleNums[i] = Integer.parseInt(allTextFields[i].getText());
            } catch (Exception e) {
                vehicleNums[i] = 0;
            }
        }

        // Checks if the user has not decided their own values for the traffic light
        // configuration. If not, the default is 30.
        boolean allZero = true;
        for (int i = 0; i < trafficLightDurs.length; i++) {
            if (trafficLightDurs[i] != 0)
                allZero = false;
        }
        if (allZero) {
            int[] temp = { 30, 30, 30, 30, 30 };
            this.trafficLightDurs = temp;
        }

        // Add all data to the junction metrics object.
        junctionMetrics.copyValues(new JunctionMetrics(vehicleNums, trafficLightDurs));

        // Count the number of each type of lane in each road.
        try {
            int[] laneData;
            laneData = countRoadTypes(northRoadAllLanes, northLaneNum);
            junctionMetrics.addRoad("north", northLaneNum, laneData[0], laneData[1], laneData[2], laneData[3],
                    laneData[4]);
            laneData = countRoadTypes(eastRoadAllLanes, eastLaneNum);
            junctionMetrics.addRoad("east", eastLaneNum, laneData[0], laneData[1], laneData[2], laneData[3],
                    laneData[4]);
            laneData = countRoadTypes(southRoadAllLanes, southLaneNum);
            junctionMetrics.addRoad("south", southLaneNum, laneData[0], laneData[1], laneData[2], laneData[3],
                    laneData[4]);
            laneData = countRoadTypes(westRoadAllLanes, westLaneNum);
            junctionMetrics.addRoad("west", westLaneNum, laneData[0], laneData[1], laneData[2], laneData[3],
                    laneData[4]);
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }
    };

    /*
     * Function to populate the UI components with the data metrics taken from the
     * simulation.
     */
    private void populateOutputDataMetrics(Map<String, String> metrics) {
        northAvgWT.setText(metrics.get("North Average Wait Time"));
        northMaxWT.setText(metrics.get("North Max Wait Time"));
        northAvgQL.setText(metrics.get("North Average Queue Length"));
        northMaxQL.setText(metrics.get("North Max Queue Length"));

        eastAvgWT.setText(metrics.get("East Average Wait Time"));
        eastMaxWT.setText(metrics.get("East Max Wait Time"));
        eastAvgQL.setText(metrics.get("East Average Queue Length"));
        eastMaxQL.setText(metrics.get("East Max Queue Length"));

        southAvgWT.setText(metrics.get("South Average Wait Time"));
        southMaxWT.setText(metrics.get("South Max Wait Time"));
        southAvgQL.setText(metrics.get("South Average Queue Length"));
        southMaxQL.setText(metrics.get("South Max Queue Length"));

        westAvgWT.setText(metrics.get("West Average Wait Time"));
        westMaxWT.setText(metrics.get("West Max Wait Time"));
        westAvgQL.setText(metrics.get("West Average Queue Length"));
        westMaxQL.setText(metrics.get("West Max Queue Length"));

        overallAvgWT.setText(metrics.get("Overall Average Wait Time"));
        overallMaxWT.setText(metrics.get("Overall Max Wait Time"));
        overallAvgQL.setText(metrics.get("Overall Average Queue Length"));
        overallMaxQL.setText(metrics.get("Overall Max Queue Length"));
        overallScore.setText(metrics.get("Overall Score"));
    }

    /*
     * Function to verify that the vehicle number input matches the lane input. E.g.
     * Inputting that 10 vehicles turn left but no left lanes for a given road.
     * 
     * @return boolean - False if the numbers do not match, true if the system is
     * valid.
     */
    private boolean verifyLaneTrafficInformation() {
        // Check left and right turns for each road. If there is a left or right input
        // but no left or right lane, return false.

        // VERIFY NORTH
        // Left Turn
        if (junctionMetrics.getVehicleNum("nte") > 0
                && (junctionMetrics.getNorth().getLeft() == 0 && junctionMetrics.getNorth().getLeftForward() == 0)) {
            showErrorAlert("Invalid Lane Setup", "There are no left-turn lanes from the North road.");
            return false;
        }
        // Forward
        if (junctionMetrics.getVehicleNum("nts") > 0
                && (junctionMetrics.getNorth().getLeftForward() == 0 && junctionMetrics.getNorth().getForward() == 0)
                && junctionMetrics.getNorth().getRightForward() == 0) {
            showErrorAlert("Invalid Lane Setup", "There are no forward lanes from the North road.");
            return false;
        }
        // Right turn
        if (junctionMetrics.getVehicleNum("ntw") > 0
                && (junctionMetrics.getNorth().getRightForward() == 0 && junctionMetrics.getNorth().getRight() == 0)) {
            showErrorAlert("Invalid Lane Setup", "There are no right-turn lanes from the North road.");
            return false;
        }

        // VERIFY EAST
        // Left turn
        if (junctionMetrics.getVehicleNum("ets") > 0
                && (junctionMetrics.getEast().getLeft() == 0 && junctionMetrics.getEast().getLeftForward() == 0)) {
            showErrorAlert("Invalid Lane Setup", "There are no left-turn lanes from the East road.");
            return false;
        }
        // Forward
        if (junctionMetrics.getVehicleNum("etw") > 0
                && (junctionMetrics.getEast().getLeftForward() == 0 && junctionMetrics.getEast().getForward() == 0)
                && junctionMetrics.getEast().getRightForward() == 0) {
            showErrorAlert("Invalid Lane Setup", "There are no forward lanes from the East road.");
            return false;
        }
        // Right turn
        if (junctionMetrics.getVehicleNum("etn") > 0
                && (junctionMetrics.getEast().getRightForward() == 0 && junctionMetrics.getEast().getRight() == 0)) {
            showErrorAlert("Invalid Lane Setup", "There are no right-turn lanes from the East road.");
            return false;
        }

        // VERIFY SOUTH
        // Left turn
        if (junctionMetrics.getVehicleNum("stw") > 0
                && (junctionMetrics.getSouth().getLeft() == 0 && junctionMetrics.getSouth().getLeftForward() == 0)) {
            showErrorAlert("Invalid Lane Setup", "There are no left-turn lanes from the South road.");
            return false;
        }
        // Forward
        if (junctionMetrics.getVehicleNum("stn") > 0
                && (junctionMetrics.getSouth().getLeftForward() == 0 && junctionMetrics.getSouth().getForward() == 0)
                && junctionMetrics.getSouth().getRightForward() == 0) {
            showErrorAlert("Invalid Lane Setup", "There are no forward lanes from the South road.");
            return false;
        }
        // Right turn
        if (junctionMetrics.getVehicleNum("ste") > 0
                && (junctionMetrics.getSouth().getRightForward() == 0 && junctionMetrics.getSouth().getRight() == 0)) {
            showErrorAlert("Invalid Lane Setup", "There are no right-turn lanes from the South road.");
            return false;
        }

        // VERIFY WEST
        // Left turn
        if (junctionMetrics.getVehicleNum("wtn") > 0
                && (junctionMetrics.getWest().getLeft() == 0 && junctionMetrics.getWest().getLeftForward() == 0)) {
            showErrorAlert("Invalid Lane Setup", "There are no left-turn lanes from the West road.");
            return false;
        }
        // Forward
        if (junctionMetrics.getVehicleNum("wte") > 0
                && (junctionMetrics.getWest().getLeftForward() == 0 && junctionMetrics.getWest().getForward() == 0)
                && junctionMetrics.getWest().getRightForward() == 0) {
            showErrorAlert("Invalid Lane Setup", "There are no forward lanes from the West road.");
            return false;
        }
        // Right turn
        if (junctionMetrics.getVehicleNum("wts") > 0
                && (junctionMetrics.getWest().getRightForward() == 0 && junctionMetrics.getWest().getRight() == 0)) {
            showErrorAlert("Invalid Lane Setup", "There are no right-turn lanes from the West road.");
            return false;
        }

        return true;
    }

    /*
     * Function to occur when the run simulation button is pressed. Will start the
     * simulation and the button itself will change to a cancel simulation button.
     */
    @FXML
    private void runSimulationButtonPress() {

        // Populate the data metrics to be used as parameters. This populates the
        // junctionMetrics object.
        populateInputDataMetrics();

        // TODO Verify that the vehicle inputs match the lanes.
        if (!verifyLaneTrafficInformation()) {
            // Invalid lane setup.
            System.out.println("Invalid setup.");
            return;
        }

        // Flag that the simulation is now running.
        isRunningSimulation = true;

        // Change the cancel simulation button to be visible and the other to be
        // invisible.
        runSimulationButton.setDisable(true);
        runSimulationButton.setVisible(false);
        cancelSimulationButton.setDisable(false);
        cancelSimulationButton.setVisible(true);

        careTaker.addSnap(new ConfigurationSnapshot(junctionMetrics));

        Junction junction = junctionMetrics.intoJunction();

        float runTime = 3600.f; // 1 hour in seconds
        float timeIncrement = 0.1f; // One tenth of a second
        float clock = 0;
        while (clock < runTime) {
            junction.update(timeIncrement);
            clock += timeIncrement;
        }
        System.err.println(junction);

        // Get metrics:
        Map<String, String> metrics = junction.getMetrics();

        // Populate the UI with these metrics.
        populateOutputDataMetrics(metrics);

        // TODO call animation
        return;

    }

    @FXML
    private void cancelSimulationButtonPress() {
        // Set the run simulation button to be visible and enabled, and disable the
        // cancel button.
        runSimulationButton.setDisable(false);
        runSimulationButton.setVisible(true);
        cancelSimulationButton.setDisable(true);
        cancelSimulationButton.setVisible(false);

        // Update the flag to show the simulation is no longer running.
        isRunningSimulation = false;
    }

    private int[] laneVerificationFeatures(String[] lanetypes) {
        // approach: find the leftmost right turn and the rightmost left turn for both
        // L/R and LF/RF,
        // verify that all forward roads are between L/R and any LFR are between LF/RF
        // left is at index 5 and right at index 0
        int leftIndex = 100; // rightmost,
        int rightIndex = -100; // lefttmost,
        int leftForwardIndex = 100; // rightmost,
        int rightForwardIndex = -100; // leftmost,

        String holdem;
        for (int i = 0; i < lanetypes.length; i++) {
            holdem = lanetypes[i];
            if (holdem.contains("LF") && leftForwardIndex == 100)
                leftForwardIndex = i;
            if (holdem.contains("L") && !holdem.contains("LF") && leftIndex == 100)
                leftIndex = i;

            if (holdem.contains("FR") && rightForwardIndex < i)
                rightForwardIndex = i;
            if (holdem.contains("R") && !holdem.contains("FR") && rightIndex < i)
                rightIndex = i;
        }
        int[] toReturn = { leftIndex, leftForwardIndex, rightForwardIndex, rightIndex };
        return toReturn;
    }

    private void updateLanes(UILane lane, UILane[] laneArr, int laneNum) {

        for (int i = 0; i < laneNum; i++) {
            laneArr[i].sortAllowedRoads();
            laneArr[i].update();
        }
        StringBuilder sb = new StringBuilder();
        String[] roadTypeArray = new String[5];

        lane.changeImage();
        // System.out.println("RoadType = " + lane.getRoadType().getAsChars());
        // System.out.println("counter = " + lane.currentRoadCounter);
        // System.out.println("We say the image is: " +
        // lane.allAllowedRoads.get(lane.currentRoadCounter).getAsChars());
        // System.out.println("We say the image is: " +
        // lane.allAllowedRoads.get(lane.currentRoadCounter).getImagePath());

        lane.update();

        for (int i = 0; i < laneArr.length; i++) {
            if (laneArr[i].getRoadType().getLeft())
                sb.append("L");
            if (laneArr[i].getRoadType().getStraight())
                sb.append("F");
            if (laneArr[i].getRoadType().getRight())
                sb.append("R");

            roadTypeArray[i] = sb.toString();

            sb.setLength(0);
        }
        int[] features = laneVerificationFeatures(roadTypeArray);
        int leftIndex = features[0];
        int leftForwardIndex = features[1];
        int rightForwardIndex = features[2];
        int rightIndex = features[3];

        // to simplify the loop, find out exactly where all things should be able to
        // turn right and left
        int rightUpTo;
        if (rightForwardIndex == -100) {
            rightUpTo = rightIndex;
        } else {
            rightUpTo = rightForwardIndex;
        }
        int leftUpTo;
        if (leftForwardIndex == 100) {
            leftUpTo = leftIndex;
        } else {
            leftUpTo = leftForwardIndex;
        }
        // System.out.println("RoadType = " + lane.getRoadType().getAsChars());
        // System.out.println("counter = " + lane.currentRoadCounter);
        // System.out.println("We say the image is: " +
        // lane.allAllowedRoads.get(lane.currentRoadCounter).getAsChars());
        // System.out.println("We say the image is: " +
        // lane.allAllowedRoads.get(lane.currentRoadCounter).getImagePath());
        // remember index 0 equlas rightmost and 5 = leftmost
        for (int i = 0; i < laneNum; i++) {
            // sort exactly right turns for all lanes
            laneArr[i].addForward();

            laneArr[i].removeRightTurns();
            if (i <= rightUpTo) {
                laneArr[i].addRightTurn();
                laneArr[i].removeForward();
                if (i == rightUpTo) {
                    System.out.println("lane " + i);
                    laneArr[i].addRightTurns();
                }
            }

            // sort exactly left turns for all lanes
            laneArr[i].removeLeftTurns();
            if (i >= leftUpTo) {
                laneArr[i].addLeftTurn();
                laneArr[i].removeForward();
                if (i == leftUpTo) {
                    laneArr[i].addLeftTurns();
                }
            }

            laneArr[i].sortAllowedRoads();
            laneArr[i].update();
        }

        if (rightForwardIndex != rightUpTo) {
            if (rightUpTo < laneNum - 1) {
                // next lane can turn right
                laneArr[rightUpTo + 1].addRightTurn();
            }
        }
        if (leftForwardIndex != leftUpTo) {
            if (leftUpTo > 0) {
                // next lane can turn left
                laneArr[leftUpTo - 1].addLeftTurn();
            }
        }

        // need to check for any LFR and if none then all straight roads CAN become LFR
        // IF no FR or LF
        if (rightForwardIndex == 100 && leftForwardIndex == -100) {
            for (int i = 0; i < laneNum; i++) {
                RoadType roadType = laneArr[i].getRoadType();
                if (roadType.getStraight() && (roadType.getLeft() || !roadType.getRight())) {
                    laneArr[i].addMultiRoad();
                    laneArr[i].update();
                }
            }
        } else {
            // make sure no one can be LFR
            for (int i = 0; i < laneNum; i++) {
                laneArr[i].removeMultiRoad();
                laneArr[i].update();
            }
        }
        boolean mixedLane = false;
        for (int i = 0; i < laneNum; i++) {
            if (laneArr[i].getRoadType().getAsChars().equals("LFR"))
                mixedLane = true;
        }
        if (!mixedLane) {
            if (leftUpTo != 100)
                laneArr[leftUpTo].addForward();
            if (rightUpTo != -100)
                laneArr[rightUpTo].addForward();
        }
        // The leftmost and rightmost lanes should have left and right turns
        // respectively.
        laneArr[laneNum - 1].addLeftTurn();
        laneArr[0].addRightTurn();
    }

    // Change the image of the current lane.

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

    private boolean populateFieldsWithData(JunctionConfiguration configuration) {
        int[] directionalThroughput = configuration.getDirectionInfo();

        TextField[] directionalFields = {
                NTE, NTS, NTW,
                ETS, ETW, ETN,
                STW, STN, STE,
                WTN, WTE, WTS
        };

        for (int i = 0; i < directionalFields.length; i++) {
            directionalFields[i].setText(Integer.toString(directionalThroughput[i]));
        }
        return true;
    }

    private boolean populateFieldsWithData(JunctionMetrics newMetrics) {

        Map<String, Integer> numbersMap = newMetrics.getAllVehicleNums();
        NTE.setText(Integer.toString(numbersMap.get("nte")));
        NTW.setText(Integer.toString(numbersMap.get("ntw")));
        NTS.setText(Integer.toString(numbersMap.get("nts")));

        ETS.setText(Integer.toString(numbersMap.get("ets")));
        ETW.setText(Integer.toString(numbersMap.get("etw")));
        ETN.setText(Integer.toString(numbersMap.get("etn")));

        STW.setText(Integer.toString(numbersMap.get("stw")));
        STN.setText(Integer.toString(numbersMap.get("stn")));
        STE.setText(Integer.toString(numbersMap.get("ste")));

        WTN.setText(Integer.toString(numbersMap.get("wtn")));
        WTE.setText(Integer.toString(numbersMap.get("wte")));
        WTS.setText(Integer.toString(numbersMap.get("wts")));

        try {
            String[][] formattedRoad = newMetrics.getRoadsFormatted();

            // setAllToDefault
            getLanesToForward();

            // correct all laneNums

            int[] allNums = {
                    northLaneNum,
                    eastLaneNum,
                    southLaneNum,
                    westLaneNum,
            };
            for (int i = 0; i < formattedRoad.length; i++) {
                allNums[i] = 5 - countDisabledLanesFromMetrics(formattedRoad[i]);
            }

            UILane[][] allLanes = {
                    northRoadAllLanes,
                    eastRoadAllLanes,
                    southRoadAllLanes,
                    westRoadAllLanes
            };
            for (int i = 0; i < allLanes.length; i++) {
                int highestIndex = formattedRoad[i].length - 1;
                boolean rightToLeft = true;
                while (rightToLeft && highestIndex >= 0) {
                    String nextString = formattedRoad[i][highestIndex];
                    if (nextString.equals("R") || nextString.equals("FR")) {
                        changeUntilCorrect(allLanes[i][allLanes[i].length - 1 - highestIndex],
                                allLanes[i], allNums[i], nextString);
                        highestIndex--;
                        System.out.println(nextString);
                    } else {
                        rightToLeft = false;
                    }
                }
                for (int j = 0; j <= highestIndex; j++) {
                    changeUntilCorrect(allLanes[i][allLanes[i].length - 1 - j],
                            allLanes[i], allNums[i], formattedRoad[i][j]);
                }
            }

            // for (int i = 0; i < allLanes.length; i++) {
            // rightToLeft = false;
            // count = 0;
            // for (int j = 0; j < allLanes[i].length; j++) {
            // System.out.println(formattedRoad[i][j]);
            // }
            // while (!rightToLeft && count < formattedRoad[i].length) {
            // String nextString = formattedRoad[i][formattedRoad[i].length - 1 - count];
            // if (nextString.equals("L") || nextString.equals("LF")) {
            // changeUntilCorrect(allLanes[i][formattedRoad[i].length - 1 - count],
            // allLanes[i], allNums[i], nextString);
            // count++;
            // } else {
            // rightToLeft = true;
            // }
            // }
            // for (int j = 0; j < formattedRoad[i].length - count; j++) {
            // changeUntilCorrect(allLanes[i][j],
            // allLanes[i], allNums[i], formattedRoad[i][j]);
            // }
            // for (int j = 0; j < formattedRoad[i].length; j++) {
            // changeUntilCorrect(allLanes[i][j], northRoadAllLanes, northLaneNum,
            // formattedRoad[i][j]);
            // }
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }

        return true;
    }

    private void getLanesToForward() {
        UILane[][] allLanes = {
                northRoadAllLanes,
                eastRoadAllLanes,
                southRoadAllLanes,
                westRoadAllLanes
        };
        int[] allNums = {
                northLaneNum,
                eastLaneNum,
                southLaneNum,
                westLaneNum,
        };
        for (int i = 0; i < allLanes.length; i++) {
            fixRoadToForward(allLanes[i], allNums[i]);
        }
    }

    private void fixRoadToForward(UILane[] lanes, int laneNum) {
        for (int i = 0; i < lanes.length; i++) {
            int rightMostLeft = 100;
            int leftMostRight = -100;
            for (int j = 0; j < lanes.length; j++) {
                if (lanes[j].getRoadType().getRight() && j > leftMostRight)
                    leftMostRight = j;
                if (lanes[j].getRoadType().getLeft() && j < rightMostLeft)
                    rightMostLeft = j;
            }
            if (rightMostLeft != 100) {
                while (!lanes[rightMostLeft].getRoadType().getImagePath().equals("/assets/straightOnRoad.png")) {

                    updateLanes(lanes[rightMostLeft], lanes, laneNum);
                    System.out.println(lanes[rightMostLeft].allAllowedRoads);
                }
            }
            if (leftMostRight != -100) {
                while (!lanes[leftMostRight].getRoadType().getImagePath().equals("/assets/straightOnRoad.png")) {
                    updateLanes(lanes[leftMostRight], lanes, laneNum);
                }
            }
            System.out.println("OUT");
        }
        System.out.println("Actually out");
    }

    private int countDisabledLanesFromMetrics(String[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("D"))
                count++;
        }
        return count;
    }

    private boolean changeUntilCorrect(UILane lane, UILane[] lanes, int laneNum, String direction) {
        switch (direction) {
            case "L":
                updateLanes(lane, lanes, laneNum);
                break;
            case "LF":
                updateLanes(lane, lanes, laneNum);
                updateLanes(lane, lanes, laneNum);
                return false;
            case "F":
                return false;
            case "R":
                updateLanes(lane, lanes, laneNum);
                if (lane == lanes[laneNum - 1]) {
                    updateLanes(lane, lanes, laneNum);
                    updateLanes(lane, lanes, laneNum);
                }
                return false;
            case "FR":
                updateLanes(lane, lanes, laneNum);
                updateLanes(lane, lanes, laneNum);
                if (lane == lanes[laneNum - 1]) {
                    updateLanes(lane, lanes, laneNum);
                    updateLanes(lane, lanes, laneNum);
                }
                return false;
        }
        return true;
    }

    @FXML
    private void undo() {

        careTaker.undo();
        populateFieldsWithData(junctionMetrics);
    }

    @FXML
    private void redo() {
        System.out.println("red pressed");
        careTaker.redo();
        populateFieldsWithData(junctionMetrics);
    }

    /*
     * Function that runs when the "Show Other Options" button is pressed on the UI.
     * Will switch to the junction generation window.
     */
    @FXML
    private void showJunctionOptions(ActionEvent event) {
        // JunctionMetrics baseConfiguration = junctionMetrics;
        List<JunctionMetrics> permutations = this.junctionMetrics.getPermutations();
        Map<Float, JunctionMetrics> generationData;
        Junction junction;
        for (JunctionMetrics permutation : permutations) {
            junction = permutation.intoJunction();
            // TODO run simulation
            // TODO get data and add
        }

        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trafficjunction/generationWindow.fxml"));
            Parent root = loader.load();

            GenerationController generationController = loader.getController();
            Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            generationController.setPrimaryStage(primaryStage);

            // Create a new Scene and Stage (window)
            Stage genStage = new Stage();
            genStage.setScene(new Scene(root));
            Image icon = new Image(getClass().getResourceAsStream("/assets/trafficCone.png"));
            genStage.getIcons().add(icon);
            genStage.setTitle("Generated Alternative Junctions");

            genStage.show();

            // Hide the current window.
            primaryStage.hide();

        } catch (IOException e) {
            e.printStackTrace();
        }

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
