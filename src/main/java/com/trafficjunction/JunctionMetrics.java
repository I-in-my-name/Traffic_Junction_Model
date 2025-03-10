package com.trafficjunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.trafficjunction.Junction_Classes.Junction;
import com.trafficjunction.Junction_Classes.TrafficLight;
import com.trafficjunction.Junction_Classes.TrafficLightConfig;

/*
 * Class to store user-input data in a single place.
 */
public class JunctionMetrics implements Serializable {
    private Map<String, Integer> vehicleNums;
    private Map<String, Integer> trafficLightDurs;

    private Road north;
    private Road east;
    private Road south;
    private Road west;

    public JunctionMetrics(int[] vehicleNums, int[] trafficLightDurs) {
        this.vehicleNums = new HashMap<String, Integer>();
        this.trafficLightDurs = new HashMap<String, Integer>();
        this.vehicleNums.put("nte", vehicleNums[0]);
        this.vehicleNums.put("nts", vehicleNums[1]);
        this.vehicleNums.put("ntw", vehicleNums[2]);
        this.vehicleNums.put("ets", vehicleNums[3]);
        this.vehicleNums.put("etw", vehicleNums[4]);
        this.vehicleNums.put("etn", vehicleNums[5]);
        this.vehicleNums.put("stw", vehicleNums[6]);
        this.vehicleNums.put("stn", vehicleNums[7]);
        this.vehicleNums.put("ste", vehicleNums[8]);
        this.vehicleNums.put("wtn", vehicleNums[9]);
        this.vehicleNums.put("wte", vehicleNums[10]);
        this.vehicleNums.put("wts", vehicleNums[11]);

        this.trafficLightDurs.put("north", trafficLightDurs[0]);
        this.trafficLightDurs.put("east", trafficLightDurs[1]);
        this.trafficLightDurs.put("south", trafficLightDurs[2]);
        this.trafficLightDurs.put("west", trafficLightDurs[3]);
        this.trafficLightDurs.put("allRed", trafficLightDurs[4]);

        north = new Road(5, 0, 0, 5, 0, 0);
        east = new Road(5, 0, 0, 5, 0, 0);
        south = new Road(5, 0, 0, 5, 0, 0);
        west = new Road(5, 0, 0, 5, 0, 0);
    }

    public JunctionMetrics(JunctionMetrics data) {
        this.vehicleNums = new HashMap<>(data.getAllVehicleNums());
        this.trafficLightDurs = new HashMap<>(data.getAllTrafficLightDurs());

    }

    public Junction intoJunction() {
        Junction junction = new Junction();

        // Set vehicle rates.
        String[] routes = { "nte", "nts", "ntw",
                "ets", "etw", "etn",
                "ste", "stn", "stw",
                "wts", "wte", "wtn" };
        for (String dir : routes) {
            junction.setVehicleRate(dir, vehicleNums.get(dir));
        }

        junction.setNumLanesEntry(0, north.getNumLanes());
        junction.setNumLanesExit(0, 5);
        for (int i = 0; i < north.getNumLanes(); i++) {
            junction.setLaneDirections(0, i, north.getIndexDir(i));
        }
        junction.setNumLanesEntry(1, east.getNumLanes());
        junction.setNumLanesExit(1, 5);
        for (int i = 0; i < east.getNumLanes(); i++) {
            junction.setLaneDirections(1, i, east.getIndexDir(i));
        }
        junction.setNumLanesEntry(2, south.getNumLanes());
        junction.setNumLanesExit(2, 5);
        for (int i = 0; i < south.getNumLanes(); i++) {
            junction.setLaneDirections(2, i, south.getIndexDir(i));
        }
        junction.setNumLanesEntry(3, west.getNumLanes());
        junction.setNumLanesExit(3, 5);
        for (int i = 0; i < west.getNumLanes(); i++) {
            junction.setLaneDirections(3, i, west.getIndexDir(i));
        }

        String[] directions = { "north", "east", "south", "west", "allRed" };
        for (int i = 0; i < 5; i++) {
            float time = (float) trafficLightDurs.get(directions[i]);
            junction.setTrafficLightStateTime(i, time); // Set time of traffic light states
        }

        junction.connectJunction();

        return junction;
    }

    /*
     * Getter method for the number of vehicles in a given direction.
     * 
     * @param direction - The direction to find the number of vehicles for.
     * 
     * @return int - The number of vehicles.
     */
    public int getVehicleNum(String direction) {
        return vehicleNums.get(direction);
    }

    /*
     * Setter method for the number of vehicles in a given direction.
     * 
     * @param direction - The direction to set the number of vehicles for.
     * 
     * @param num - The new number of vehicles.
     */
    public void setVehicleNum(String direction, int num) {
        vehicleNums.replace(direction, num);
    }

    /*
     * Getter method for the traffic light wait time in a given direction. This is
     * how long the light is green for on a certain road.
     * 
     * @param direction - The direction to find the green-light duration for.
     * 
     * @return int - The duration the light is green for for the given direction.
     */
    public int getTrafficLightTime(String direction) {
        return trafficLightDurs.get(direction);
    }

    /*
     * Setter method for the traffic light wait time in a given direction. This is
     * how long the light is green for on a certain road.
     * 
     * @param direction - The direction to set the green-light duration for.
     * 
     * @param num - The new duration.
     */
    public void setTrafficLightTime(String direction, int num) {
        trafficLightDurs.replace(direction, num);
    }

    /*
     * Getter method to get the entire map containing all number of vehicles in each
     * direction.
     * 
     * @return Map<String, Integer> - A string to integer mapping to get all number
     * of vehicles in every direction.
     */
    public Map<String, Integer> getAllVehicleNums() {
        return this.vehicleNums;
    }

    public void setAllVehicleNums(Map<String, Integer> map) {
        this.vehicleNums = map;
    }

    /*
     * Getter method to get the entire map containing all green-light durations in
     * each direction.
     * 
     * @return Map<String, Integer> - A string to integer mapping to get all the
     * green-light durations in each direction.
     */
    public Map<String, Integer> getAllTrafficLightDurs() {
        return this.trafficLightDurs;
    }

    public void setAllTrafficLightDurs(Map<String, Integer> map) {
        this.trafficLightDurs = map;
    }

    // overload method to make it easier in another method
    public void addRoad(String dir, int[] dirs) {
        int L = dirs[0];
        int LF = dirs[1];
        int F = dirs[2];
        int RF = dirs[3];
        int R = dirs[4];
        addRoad(dir, L + LF + F + RF + R, L, LF, F, RF, R);
    }

    /*
     * Method to add lanes to the juncti
     */
    public void addRoad(String dir, int numLanes, int l, int lf, int f, int rf, int r) {
        Road road = new Road(numLanes, l, lf, f, rf, r);

        switch (dir) {
            case "north":
                north = road;
                break;
            case "east":
                east = road;
                break;
            case "west":
                west = road;
                break;
            case "south":
                south = road;
                break;
            default:
                break;
        }

        return;
    }

    public void copyValues(JunctionMetrics copy) {
        this.setAllTrafficLightDurs(new HashMap<>(copy.getAllTrafficLightDurs()));
        this.setAllVehicleNums(new HashMap<>(copy.getAllVehicleNums()));
        this.setCardinals(Arrays.copyOf(copy.getCardinals(), 4));
    }

    private Road[] getCardinals() {
        Road[] toReturn = { getNorth(), getEast(), getSouth(), getWest() };
        return toReturn;
    }

    public void setCardinals(Road[] cardinalRoads) {
        north = cardinalRoads[0];
        east = cardinalRoads[1];
        south = cardinalRoads[2];
        west = cardinalRoads[3];
    }

    /*
     * Method to get the North Road object.
     * 
     * @return Road - The North Road object.
     */
    public Road getNorth() {
        return north;
    }

    /*
     * Method to get the East Road object.
     * 
     * @return Road - The East Road object.
     */
    public Road getEast() {
        return east;
    }

    /*
     * Method to get the South Road object.
     * 
     * @return Road - The South Road object.
     */
    public Road getSouth() {
        return south;
    }

    /*
     * Method to get the West Road object.
     * 
     * @return Road - The West Road object.
     */
    public Road getWest() {
        return west;
    }

    public boolean equals(JunctionMetrics otherMetrics) {
        return this.getNorth().equals(otherMetrics.getNorth()) && this.getEast().equals(otherMetrics.getEast())
                && this.getSouth().equals(otherMetrics.getSouth()) && this.getWest().equals(otherMetrics.getWest());
    }

    public boolean saveObject(File objectFile) {
        try (
                // try with resource so auto closes IO streams after
                FileOutputStream file = new FileOutputStream(objectFile);
                ObjectOutputStream out = new ObjectOutputStream(file);) {
            out.writeObject(this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JunctionMetrics loadObject(File objectFile) {
        try (
                // try with resource so auto closes IO streams after
                FileInputStream file = new FileInputStream(objectFile);
                ObjectInputStream in = new ObjectInputStream(file);) {
            return (JunctionMetrics) in.readObject();
        } catch (Exception e) {
            e.printStackTrace(); // TODO return permutations
            return null;
        }
    }

    public String[][] getRoadsFormatted() {
        String[][] toReturn = {
                getNorth().getFormatted(),
                getEast().getFormatted(),
                getSouth().getFormatted(),
                getWest().getFormatted()
        };
        return toReturn;
    }

    private boolean isValidLaneDirections(int[] laneDirections, int numLanesTotal) {
        // Order is L, LF, F, RF, R
        // must be true: LF + F + RF > 0
        // and L + LF > 0
        // and R + RF > 0
        // as need lanes pointing each direction
        // and 6 > L + LF + F + RF + R > 0
        int left = laneDirections[0];
        int leftForward = laneDirections[1];
        int forward = laneDirections[2];
        int rightForward = laneDirections[3];
        int right = laneDirections[4];

        System.out.println(left);
        System.out.println(leftForward);
        System.out.println(forward);
        System.out.println(rightForward);
        System.out.println(right);

        System.out.println(numLanesTotal);
        if (left + leftForward + forward + rightForward + right != numLanesTotal)
            return false;

        if (left < 0 || leftForward < 0 || forward < 0 || rightForward < 0 || right < 0)
            return false;

        if (left + leftForward == 0)
            return false;

        if (right + rightForward == 0)
            return false;

        if (leftForward + forward + rightForward == 0)
            return false;

        if (left + leftForward + forward + right + rightForward > 5 ||
                left + leftForward + forward + right + rightForward <= 0)
            return false;

        return true;
    }

    private List<int[]> getAllValidLaneDirections(int numLanesTotal) {
        ArrayList<int[]> validLaneDirections = new ArrayList<>();
        for (int left = 0; left <= 5; left++) {
            for (int leftForward = 0; leftForward <= 5 - left; leftForward++) {
                for (int forward = 0; forward <= 5 - left - leftForward; forward++) {
                    for (int rightForward = 0; rightForward <= 5 - left - leftForward - forward; rightForward++) {
                        for (int right = 0; right <= 5 - left - leftForward - forward - rightForward; right++) {
                            int[] directions = {
                                    left, leftForward, forward, rightForward, right
                            };
                            if (!isValidLaneDirections(directions, numLanesTotal))
                                continue;
                            validLaneDirections.add(directions);
                        }
                    }
                }
            }
        }
        return validLaneDirections;
    }

    public SortedMap<Float, JunctionMetrics> getPermutations() {
        // Loops
        int index = 0;
        SortedMap<Float, JunctionMetrics> permutations = new TreeMap<>();
        Junction tempJunction;

        List<int[]> allValidNorthDirections = getAllValidLaneDirections(north.numLanes);
        List<int[]> allValidEastDirections = getAllValidLaneDirections(east.numLanes);
        List<int[]> allValidSouthDirections = getAllValidLaneDirections(south.numLanes);
        List<int[]> allValidWestDirections = getAllValidLaneDirections(west.numLanes);
        for (int northIndex = 0; northIndex < allValidNorthDirections.size(); northIndex++) {
            for (int eastIndex = 0; eastIndex < allValidEastDirections.size(); eastIndex++) {
                for (int southIndex = 0; southIndex < allValidSouthDirections.size(); southIndex++) {
                    for (int westIndex = 0; westIndex < allValidWestDirections.size(); westIndex++) {
                        JunctionMetrics perm = new JunctionMetrics(this);
                        perm.addRoad("north", allValidNorthDirections.get(northIndex));
                        perm.addRoad("east", allValidEastDirections.get(eastIndex));
                        perm.addRoad("south", allValidSouthDirections.get(southIndex));
                        perm.addRoad("west", allValidWestDirections.get(westIndex));
                        index++;
                        System.out.println(index);
                        tempJunction = perm.intoJunction();
                        permutations.put(tempJunction.computeOverallScore(), perm);
                    }
                    while (permutations.size() > 20) {
                        System.out.println("delete");
                        permutations.remove(permutations.firstKey());
                    }
                }
                if (index >= 5000) {
                    break;
                }
            }
            if (index >= 50000) {
                break;
            }
        }
        System.out.println("WOAH");
        return permutations;
    }
}
