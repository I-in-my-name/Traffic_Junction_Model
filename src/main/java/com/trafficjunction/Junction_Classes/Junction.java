package com.trafficjunction;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


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
        for (int i; i<4; i++) {
            entry_lanes.add(new ArrayList<>());
            exit_lanes.add(new ArrayList<>());
        }
        
        // Create one trafficlight
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

    /** 
     * Set the number of entry lanes in a specified direction
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
                Lane lane = new Lane(); 
                entry_lanes.get(side).add(lane);
            }
        }
    }

    public boolean setLaneDirections(int side, int index, String direction) {
        // Check the validity of the direction string
        //if (direction) {
        //    return false;
        //}
        // Check that a valid side and index has been given
        if ((side >= 0 && index >= 0) && (entry_lanes.size() >= side) && (entry_lanes.get(side).size() >= index)) {
            // If so then set direction
            entry_lanes.get(side).get(index).setDirection(direction);
            return true;
        } else {
            // Return false if side or index are not valid
            return false;
        }
    }

    public boolean setLaneBus(int side, int index, boolean type) {
        // Check that a valid side and index has been given
        if ((side >= 0 && index >= 0) && (entry_lanes.size() >= side) && (entry_lanes.get(side).size() >= index)) {
            // If so then set the bus lane
            entry_lanes.get(side).get(index).setBusLane(type);
            return true;
        } else {
            // Return false if side or index are not valid
            return false;
        }
    }
    // set exit lane as bus ...

    public boolean verifyJunction() {
        
    }

    public boolean connectLanes() {

    }

    public void setLaneTrafficLight(int side, int index, TrafficLight light) {

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
