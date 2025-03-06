package com.trafficjunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trafficjunction.Junction_Classes.Junction;
import com.trafficjunction.Junction_Classes.TrafficLight;
import com.trafficjunction.Junction_Classes.TrafficLightConfig;

/*
 * Class to store user-input data in a single place.
 */
public class JunctionMetrics {
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
    }

    public JunctionMetrics(JunctionMetrics data) {
        this.vehicleNums = new HashMap<String, Integer>(data.getAllVehicleNums());
        this.trafficLightDurs = new HashMap<String, Integer>(data.getAllTrafficLightDurs());

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

        String[] directions = { "north", "east", "south", "west" };
        for (int i = 0; i < 4; i++) {
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

    public List<JunctionMetrics> getPermutations() {
        // TODO return permutations
        return null;
    }
}

class Road {
    int numLanes;
    int left;
    int leftForward;
    int forward;
    int rightForward;
    int right;

    public Road(int numLanes, int left, int leftForward, int forward, int rightForward, int right) {
        this.numLanes = numLanes;
        this.left = left;
        this.leftForward = leftForward;
        this.forward = forward;
        this.rightForward = rightForward;
        this.right = right;
    }

    public String getIndexDir(int index) {
        if (index < left) {
            return "L";
        } else if (index < left + leftForward) {
            return "LF";
        } else if (index < left + leftForward + forward) {
            return "F";
        } else if (index < left + leftForward + forward + rightForward) {
            return "FR";
        } else if (index < left + leftForward + forward + rightForward + right) {
            return "R";
        }
        return null;
    }

    /*
     * Getter method for the number of lanes.
     * 
     * @return int - The number of lanes.
     */
    public int getNumLanes() {
        return this.numLanes;
    }

    /*
     * Getter method for the number of lanes turning strictly left.
     * 
     * @return int - The number of strict-left lanes.
     */
    public int getLeft() {
        return this.left;
    }

    /*
     * Getter method for the number of lanes that are a forward AND left turn.
     * 
     * @return int - The number of lanes.
     */
    public int getLeftForward() {
        return this.leftForward;
    }

    /*
     * Getter method for the number of lanes going strictly forward.
     * 
     * @return int - The number of lanes.
     */
    public int getForward() {
        return this.forward;
    }

    /*
     * Getter method for the number of lanes going forward AND right.
     * 
     * @return int - The number of lanes.
     */
    public int getRightForward() {
        return this.rightForward;
    }

    /*
     * Getter method for the number of strict right turns.
     * 
     * @return int - The number of lanes.
     */
    public int getRight() {
        return this.right;
    }
}
