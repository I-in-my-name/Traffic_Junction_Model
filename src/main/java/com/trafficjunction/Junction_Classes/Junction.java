package com.trafficjunction.Junction_Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Junction {

    // Questions: what sort of lists / arrays? Array lists?
    private List<List<Lane>> entry_lanes = new ArrayList<>();   // Entry lanes
    private List<List<Lane>> exit_lanes = new ArrayList<>();    // Exit lanes
    /** 
     * Indexes for directions
     * 0 - North
     * 1 - East
     * 2 - South
     * 3 - West
     */
    
    private Map<String, Integer> vehicle_rate;  // Rate of vehicles coming into the junction for each direction
    // Vehicle backlog?

    private List<TrafficLight> traffic_lights;  // List of all traffic lights
    private TrafficLightConfig tl_config;       // The configuration for the traffic lights

    private float timer;                // How much time has passed since the start of the simulation

    private JunctionMetrics metrics;    // The metrics for the juntion
    

    public Junction() {
        // Initialise

        // Set vehicle rate to be 0 for all directions
        vehicle_rate = new HashMap<>();
        vehicle_rate.put("nte", 0);
        vehicle_rate.put("nts", 0);
        vehicle_rate.put("ntw", 0);
        vehicle_rate.put("ets", 0);
        vehicle_rate.put("etw", 0);
        vehicle_rate.put("etn", 0);
        vehicle_rate.put("ste", 0);
        vehicle_rate.put("stn", 0);
        vehicle_rate.put("stw", 0);
        vehicle_rate.put("wts", 0);
        vehicle_rate.put("wte", 0);
        vehicle_rate.put("wtn", 0);

        // Add a lane to each direction
        for (int i = 0; i<4; i++) {
            entry_lanes.add(new ArrayList<>());
            exit_lanes.add(new ArrayList<>());
        }
        
        // Create one trafficlight
        traffic_lights = new ArrayList<>();
        traffic_lights.add(new TrafficLight());
    }

    /** 
     * Accesses and returns the entry lanes of the junction
     * @return - List<lanes> entry_lanes
     */
    public List<List<Lane>> getEntryLanes() {
        return entry_lanes;
    }


    /** 
     * Accesses and returns the exitt lanes of the junction
     * @return - List<lanes> exit_lanes
     */
    public List<List<Lane>> getexitLanes() {
        return exit_lanes;
    }


    /** 
     * Accesses and returns the traffic lights of the junction
     * @return - List<TrafficLights> traffic_lights
     */
    public List<TrafficLight> getTrafficLights() {
        return traffic_lights;
    }

    /** 
     * Accesses and returns the rate of vehicles coming into the junction
     * @return - List<TrafficLights> traffic_lights
     */
    public Map<String, Integer> getVehicle_rate() {
        return vehicle_rate;
    }
    

    ///////getVehicleBacklog////////// notr sure how to implement yet


    /** 
     * Accesses and returns the traffic lights configuration for junction
     * @return - TrafficLightsConfig tl_config
     */
    public TrafficLightConfig getTLConfig() {
        return tl_config;
    }



    /** 
     * Accesses and returns the timer for the simulation
     * @return - float timer
     */
    public float getTimer() {
        return timer;
    }


    /** 
     * Accesses and returns the metrics of the junction
     * @return - List<lanes> entry_lanes
     */
    public JunctionMetrics getJunctionMetric() {
        return metrics;
    }

    // Written test for
    /** 
     * Set the number of entry lanes in a specified direction
     * 
     * This also sets the #exiting lanes in the opposite direction
     * 
     * @return - List<lanes> entry_lanes
     */
    public boolean setNumLanesEntry(int side, int number) {
        
        //Checks to see if a reasonable amount of lanes has been chosen
        if (number < 1 || number > 5){
            // If not do not add any lanes and retun false
            return false;
        } else {
            // If a reasonable amount of lanes has been chosen
            // Add the specified amount of lanes to the junction
            for (int i = 0; i < number; i++) {
                // TODO DEFAULT LANE? LENGTH OF 30 AND NULL VALUE FOR DIRECTION
                Lane lane = new Lane(30.f, traffic_lights.get(0), null); 
                entry_lanes.get(side).add(lane);
                int exit_side = (side + 2) % 4;
                exit_lanes
            }
            return true;
        }
    }

    // Written test for
    public boolean setLaneDirections(int side, int index, String direction) {
        // Check the validity of the direction string
        if (direction.length() > 3) {   // Check size
            return false;   // If direction string is longer than 3 characters, then it is definetely invalid
        }
        for (int i = 0; i < 4; i++) {   // Check content
            String character = Character.toString(direction.charAt(i)); // Gets the i'th characthter in direction, then converted to string
            String allowed = "lfr";     // Allowed characters
            if (allowed.contains(character)) {
                return false;
            }
        }
        // Check that a valid side and index has been given
        if ((side >= 0 && index >= 0) && (entry_lanes.size() >= side) && (entry_lanes.get(side).size() >= index)) {
            // If it is valid set direction
            entry_lanes.get(side).get(index).setDirection(direction);
            return true;
        } else {
            // Return false if side or index are not valid
            return false;
        }
    }

    // TODO: Write test for
    public boolean setLaneBus(int side, int index, boolean type) {
        // Check that a valid side and index has been given
        if ((side >= 0 && index >= 0) && (entry_lanes.size() >= side) && (entry_lanes.get(side).size() >= index)) {
            // If it is valid then set the bus lane
            Lane selected_lane = entry_lanes.get(side).get(index);
            selected_lane.setBusLane(type);
            return true;
        } else {
            // Return false if side or index are not valid
            return false;
        }
    }
    // set exit lane as bus ...

    public boolean verifyJunction() {

        return false;
    }

    /** 
     * Connects two lanes
     * 
     * Description...
     * 
     * @return - Returns wether the method succesfully managed to connect the lanes
     */
    public void connectLanes(Lane entry, Lane exit) {
        Lane between = new Lane(10.f, null, null);
    }

    /** 
     * Connects all the lanes in the junction
     * 
     * Description...
     * 
     * @return - Returns wether the method succesfully managed to connect all the lanes in the junction
     */
    public boolean connectJunction() {
        if (!this.verifyJunction()) {   // Verify that the junction is valid before 'building' it.
            return false;
        }
        /**
         * Loop through each direction
         * 0-North, 1-East, 2-South, 3-West
         * List<List<Lane>> entry_lanes = new ArrayList<>();   // Entry lanes
         * List<List<Lane>> exit_lanes = new ArrayList<>();    // Exit lanes
         * 
         */
        for (int i = 0; i < 4; i++) {   // For each road 
            List<Lane> entry_road = entry_lanes.get(i);
            //List<String> directions = new ArrayList<>();    // Store directions for present lanes
            for (int j = 0; j < entry_road.size(); j++) {   // For each lane 
                Lane lane = entry_road.get(j);          // Gets a lane
                String direction = lane.getDirection(); // Gets the lane's direction
                for (char c : direction.toCharArray()) {    // For every char/direction the lane is connected in
                    int exit_i;
                    int exit_j;
                    switch (c) {
                        case 'l':
                            exit_i = (i+1) % 4;
                            exit_j = exit_lanes.get(exit_i).size() - j;
                            break;
                        case 'f':
                            exit_i = (i+2) % 4;
                            exit_j = exit_lanes.get(exit_i).size() - j;
                            break;
                        case 'r':
                            exit_i = (i+3) % 4;
                            exit_j = exit_lanes.get(exit_i).size() - j;
                            break;
                    }
                }
            }
        }

        return false;
    }

    public boolean setLaneTrafficLight(int side, int index, TrafficLight light) {
        if ((side >= 0 && index >= 0) && (entry_lanes.size() >= side) && (entry_lanes.get(side).size() >= index)) {
            Lane selected_lane = entry_lanes.get(side).get(index);
            selected_lane.setTrafficLight(light);
            return true;
        } else {
            return false;
        }
    }

    public void setTrafficLightConfig(TrafficLightConfig config) {
        
    }

    public void findRoute(int side1, int side2) {

    }

    public void update(float time) {

    }

    public void updateLights() {

    }

    public void createVehicles() {

    }

    public void calculateMetrics() {

    }

    public JunctionMetrics getMetrics() {
        return null;
    }

    public String convertToText() {
        return null;
    }

    public void constructFromText(String text) {

    }
    
}
