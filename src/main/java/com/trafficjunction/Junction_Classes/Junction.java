package com.trafficjunction;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class Junction {

    // Questions: what sort of lists / arrays? Array lists?
    private List<List<Lane>> entry_lanes = new ArrayList<>();
    private List<List<Lane>> exit_lanes = new ArrayList<>();
    /** 
     * Indexes for directions
     * 0 - North
     * 1 - East
     * 2 - South
     * 3 - West
     */
    
    private Map<String, Integer> vehicle_rate;
    // Vehicle backlog?

    private List<TrafficLight> traffic_lights;
    private TrafficLightConfig tl_config;

    private float timer;

    private JunctionMetrics metrics;
    

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
            entry_lanes.add(new ArrayList<>())
            exit_lanes.add(new ArrayList<>())
        }
        
        // Create one trafficlight
    }

    /** 
     * Accesses and returns the entry lanes of the junction
     * @
     * @return - List<lanes> entry_lanes
     */
    public List<Lane> getEntryLanes() {
        return entry_lanes;
    }


    /** 
     * Accesses and returns the exitt lanes of the junction
     * @
     * @return - List<lanes> exit_lanes
     */
    public List<Lane> getexitLanes() {
        return exit_lanes;
    }


    /** 
     * Accesses and returns the traffic lights of the junction
     * @
     * @return - List<TrafficLights> traffic_lights
     */
    public List<TrafficLight> getTrafficLights() {
        return traffic_lights;
    }

    public Map<String, Integer> getVehicle_rate() {
        return vehicle_rate;
    }
    

    ///////getVehicleBacklog////////// notr sure how to implement yet


    /** 
     * Accesses and returns the traffic lights configuration for junction
     * @
     * @return - TrafficLightsConfig tl_config
     */
    public TrafficLightConfig getTLConfig() {
        return tl_config;
    }



    /** 
     * Accesses and returns the timer for the simulation
     * @
     * @return - float timer
     */
    public float getTimer() {
        return timer
    }


    /** 
     * Accesses and returns the metrics of the junction
     * @
     * @return - List<lanes> entry_lanes
     */
    public JunctionMetrics getJunctionMetric() {
        return metrics;
    }

    
    public boolean setNumLanesEntry(int side, int number) {
        for (int i = 0; i ++; i < number) {
            Lane lane = new Lane();
            entry_lanes.get(side).add(lane);
        }
    }

    public boolean setLaneDirections(int side, int index, int direction) {
        Lane theLane = entry_lanes.get(side).get(lane);
        if (theLane != null) {
            theLane.setDirection(direction);
            return true;
        } else {
            return false;
        }
    }

    public void setLaneBus(int side, int index, boolean type) {
        entry_lanes.get(side).get(lane).setBusLane(type);
    }

    public void verifyJunction() {
        
    }

    public void connectLanes() {

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
