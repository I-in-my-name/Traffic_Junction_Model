package com.trafficjunction.View_and_Controller;

import java.io.File;
import java.io.IOException;

import com.trafficjunction.JunctionConfiguration;
import com.trafficjunction.UI_Utilities.AnimationHandler;
import com.trafficjunction.UI_Utilities.DataSanitisation;
import com.trafficjunction.UI_Utilities.RoadType;
import com.trafficjunction.UI_Utilities.UILane;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
        // for (Tab tab : vehicleTabs.getTabs()) {
        // for (Node node : tab.)
        // if (node instanceof TextField) {
        // DataSanitisation.applyNumericRestriction((TextField) node);
        // }
        // }

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

        // fileChooser.setTitle("value");
        // loadMenuItem.setOnAction((ActionEvent event) -> {
        // File chosenFile = fileChooser.showOpenDialog((Stage)
        // vehicleNumGrid.getScene().getWindow());
        // try {
        // JunctionConfiguration loadedConfiguration =
        // JunctionConfiguration.loadObject(chosenFile);
        // configuration.setDirectionInfo(loadedConfiguration.getDirectionInfo());
        // careTaker.addSnap(new ConfigurationSnapshot(configuration));
        // System.out.println("SNAPSHOT ADDED");
        // populateFieldsWithData(configuration);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // });

        // saveMenuItem.setOnAction((ActionEvent event) -> {
        // File chosenFile = fileChooser.showSaveDialog((Stage)
        // vehicleNumGrid.getScene().getWindow());
        // try {
        // // move into program
        // gatherUserData().saveObject(chosenFile);
        // } catch (Exception e) {
        // }
        // });

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
        // JunctionConfiguration userData = gatherUserData();

        // TODO call simulation
        return;

    }

    private boolean verifyLane(String[] lanetypes, boolean oneLightMode) {

        // idea to follow: L LF RF R AND there is maximum ONE LF and ONE RF
        // CANNOT HAVE LF AND M OR RF AND M but can have lf and rf. for onelane

        int[] features = laneVerificationFeatures(lanetypes);
        int leftIndex = features[0];
        int leftForwardIndex = features[1];
        int rightForwardIndex = features[2];
        int rightIndex = features[3];

        if (oneLightMode) {
            String holdem;
            for (int i = 0; i < lanetypes.length; i++) {
                holdem = lanetypes[i];
                // If we have two Left forward turns it is invalid
                if (holdem.contains("LF") && leftForwardIndex != i)
                    return false;
                // If we have two right forward turns it is invalid
                if (holdem.contains("FR") && rightForwardIndex != i)
                    return false;
                // If we have a multilane and either a leftforward or righforward lane it is
                // invalid.
                if (holdem.contains("LFR") && (rightForwardIndex != -100 || leftForwardIndex != 100))
                    return false;
            }
        }

        // the idea here is that we need to follow the format: L LF RF R
        if (leftIndex < rightIndex || leftIndex < rightForwardIndex || rightIndex > leftForwardIndex)
            return false;
        if (rightIndex > rightForwardIndex && rightForwardIndex != -100
                || (leftIndex < leftForwardIndex && leftForwardIndex != 100))
            return false;
        if (rightForwardIndex > leftForwardIndex)
            return false;

        return true;
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
        for (int i = 0; i < lane.allAllowedRoads.size(); i++) {
            System.out.println(lane.allAllowedRoads.get(i).getAsChars());
        }
        System.out.println("RoadType = " + lane.getRoadType().getAsChars());
        System.out.println("counter = " + lane.currentRoadCounter);
        System.out.println("We say the image is: " + lane.allAllowedRoads.get(lane.currentRoadCounter).getAsChars());
        System.out.println("We say the image is: " + lane.allAllowedRoads.get(lane.currentRoadCounter).getImagePath());
        lane.update();

        for (int i = 0; i < laneArr.length; i++) {
            if (laneArr[i].getRoadType().getLeft())
                sb.append("L");
            if (laneArr[i].getRoadType().getStraight())
                sb.append("F");
            if (laneArr[i].getRoadType().getRight())
                sb.append("R");

            roadTypeArray[i] = sb.toString();
            System.out.println(sb.toString());

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
        System.out.println("RoadType = " + lane.getRoadType().getAsChars());
        System.out.println("counter = " + lane.currentRoadCounter);
        System.out.println("We say the image is: " + lane.allAllowedRoads.get(lane.currentRoadCounter).getAsChars());
        System.out.println("We say the image is: " + lane.allAllowedRoads.get(lane.currentRoadCounter).getImagePath());
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
        System.out.println("RUT: " + rightUpTo);
        System.out.println("RoadType = " + lane.getRoadType().getAsChars());
        System.out.println("counter = " + lane.currentRoadCounter);
        System.out.println("We say the image is: " + lane.allAllowedRoads.get(lane.currentRoadCounter).getAsChars());
        System.out.println("We say the image is: " + lane.allAllowedRoads.get(lane.currentRoadCounter).getImagePath());

        if (rightForwardIndex != rightUpTo) {
            if (rightUpTo < laneNum - 1) {
                // next lane can turn right
                laneArr[rightUpTo + 1].addRightTurn();
            }
        }
        System.out.println(laneArr[laneNum - 1].getRoadType().getAsChars());
        if (leftForwardIndex != leftUpTo) {
            if (leftUpTo > 0) {
                // next lane can turn left
                laneArr[leftUpTo - 1].addLeftTurn();
            }

        }

        System.out.println(laneArr[laneNum - 1].getRoadType().getAsChars());

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
        System.out.println(laneArr[laneNum - 1].getRoadType().getAsChars());
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

    /*
     * Both of the next two functions must be added to whenever new data is decided
     * to be relevant
     */
    // private JunctionConfiguration gatherUserData() {
    // // This is notably in order.
    // int[] sequentialList = new int[12];
    // int index = 0;
    // for (Node child : vehicleNumGrid.getChildren()) {
    // try {
    // TextField field = (TextField) child;
    // String text = field.getText(); // replaces invisible/non-printable characters
    // int number = 0;

    // if (!text.isEmpty()) {
    // number = Integer.parseInt(text);
    // }
    // sequentialList[index] = number;
    // index++;
    // } catch (Exception ignored) {
    // ignored.printStackTrace();
    // }
    // }
    // JunctionConfiguration data = new JunctionConfiguration();
    // if (data.setDirectionInfo(sequentialList))
    // return data;

    // // TODO ERROR HANDLING, needs to be handled elsewhere to appropriately show
    // // error message.
    // return null;
    // }

    // private boolean populateFieldsWithData(JunctionConfiguration configuration) {
    // int[] directionalThroughput = configuration.getDirectionInfo();
    // int index = 0;
    // for (Node child : vehicleNumGrid.getChildren()) {
    // try {
    // TextField field = (TextField) child;
    // field.setText(String.valueOf(directionalThroughput[index]));
    // index++;
    // } catch (Exception ignored) {
    // }
    // }
    // return true;
    // }

    @FXML
    private void undo() {
        System.out.println("undo pressed");
        careTaker.undo();
        // populateFieldsWithData(configuration);

    }

    @FXML
    private void redo() {
        System.out.println("undo pressed");
        careTaker.redo();
        // populateFieldsWithData(configuration);
    }
}
