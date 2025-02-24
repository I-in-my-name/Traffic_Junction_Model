package com.trafficjunction.Junction_Classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Junction {

    private List<List<Lane>> entry_lanes = new ArrayList<>();   // Entry lanes
    private List<List<Lane>> exit_lanes = new ArrayList<>();    // Exit lanes

    private List<Lane> between_lanes = new ArrayList<>();
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
        for (int i = 0; i < 3; i++) {
            traffic_lights.add(new TrafficLight());
        }
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
     * @return 
     */
    public JunctionMetrics getJunctionMetric() {
        return metrics;
    }

    /**
     * Add Entry Lane method
     * Parameters - the side which we want to add a lane in.
     * @return - If the lane was added.
     */
    public boolean addEntryLane(int side) {
        // check 0 <= side <= 3
        if (side < 0 || side > 3)
            return false;
        // Get the road on the desired side
        List<Lane> road = entry_lanes.get(side);
        // Check if the road has less than 5 lanes
        if (road.size() > 4) {
            return false;
        }
        // Add a default lane to the road
        // TODO DEFAULT ENTRY LANE?
        Lane lane = new Lane(30.f, traffic_lights.get(0), "F");
        road.add(lane);
        return true;
    }

    /**
     * Remove Entry Lane method
     * Parameters - the side which we want to remove a lane in.
     * @return - If the lane was removed.
     */
    public boolean removeEntryLane(int side) {
        // check 0 <= side <= 3
        if (side < 0 || side > 3)
            return false;
        // Get the road on the desired side
        List<Lane> road = entry_lanes.get(side);
        // Check that the road will have at least 0 lanes after removal
        if (road.size() <= 0) {
            return false;
        }
        // Remove the lane at the end of the list
        int index = road.size() - 1;
        road.remove(index);
        return true;
    }

    /**
     * Add Exit Lane method
     * Parameters - the side which we want to add a lane in.
     * @return - If the lane was added.
     */
    public boolean addExitLane(int side) {
        // check 0 <= side <= 3
        if (side < 0 || side > 3)
            return false;
        // Get the road on the desired side
        List<Lane> road = exit_lanes.get(side);
        // Check if the road has less than 5 lanes
        if (road.size() > 4) {
            return false;
        }
        // Add a default lane to the road
        // TODO DEFAULT EXIT LANE?
        Lane lane = new Lane(30.f, null, null);
        road.add(lane);
        return true;
    }

    /**
     * Remove Exit Lane method
     * Parameters - the side which we want to remove a lane in.
     * @return - If the lane was removed.
     */
    public boolean removeExitLane(int side) {
        // check 0 <= side <= 3
        if (side < 0 || side > 3)
            return false;
        // Get the road on the desired side
        List<Lane> road = exit_lanes.get(side);
        // Check that the road will have at least 0 lanes after removal
        if (road.size() <= 0) {
            return false;
        }
        // Remove the lane at the end of the list
        int index = road.size() - 1;
        road.remove(index);
        return true;
    }


    // Written test for
    /** 
     * Set the number of entry lanes in a specified direction
     * Parameters - the side of the junction and the number of lanes wanted
     * @return - List<lanes> entry_lanes
     */
    public boolean setNumLanesEntry(int side, int number) {
        
        //Checks to see if a reasonable amount of lanes has been chosen
        //And that the value for 'sides' is valid
        if ((number < 0 || number > 5) || (side < 0 || side > 3)){
            // If not do not add any lanes and retun false
            return false;
        }
        List<Lane> road = entry_lanes.get(side);
        int to_add = number - road.size();
        if (to_add > 0) {       // If we need to add lanes
            for (int i = 0; i < to_add; i++) {  // Adds number of lanes needed
                this.addEntryLane(side);
            }
        } else if (to_add < 0) {    // If we need to remove lanes
            for (int i = 0; i > to_add; i--) {  // Removes number of lanes needed
                this.removeEntryLane(side);
            }
        } // If to_add is 0 then nothing needs to happen
        return true;
    }

    public boolean setNumLanesExit(int side, int number) {
        //Checks to see if a reasonable amount of lanes has been chosen
        if (number < 0 || number > 5){
            // If not do not add any lanes and retun false
            return false;
        } else {
            // If a reasonable amount of lanes has been chosen
            // Add the specified amount of lanes to the junction
            exit_lanes.set(side, new ArrayList<>());

            for (int i = 0; i < number; i++) {
                // TODO DEFAULT LANE? LENGTH OF 30 AND NULL VALUE FOR DIRECTION
                Lane exit_lane = new Lane(30.f, null, null);
                exit_lanes.get(side).add(exit_lane);
            }
            return true;
        }
    }

    // Written test for
    public boolean setLaneDirections(int side, int index, String direction) {
        // Check the validity of the direction string
            // Check size
        if (direction.length() > 3) {
            return false;   // If direction string is longer than 3 characters, then it is definitely invalid
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

    // Written test for
    // valid junction:
    // - 4 entry lanes
    // - each entry lane has valid direction (string containing max one of each char 'R', 'L', 'F')
    /**
     * Returns true if this junction is valid.
     * A valid junction is defined by its entry lanes. If it has 4 valid entry lanes 
     * (meaning 4 lists of lanes as entryLanes attribute) then it is valid.
     * A valid lane is one with a valid direction.
     */
    public boolean verifyJunction() {
        if (entry_lanes.size() != 4)
            return false;
        for (int i = 0; i < 4; i++) {
            for (Lane lane : entry_lanes.get(i)) {
                // TODO: Move validate lane direction logic into shared thing somewhere
                // currently repeating this logic in different ways in different functions
                // ideally should not do that
                int length = lane.getDirection().toUpperCase().length();
                if ( length <= 3 && length > 0) {
                    for (char c : lane.getDirection().toUpperCase().toCharArray()) {
                        if (c != 'L' && c != 'R' && c != 'F')
                            return false;
                    }
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    /** 
     * Connects two lanes
     * Creates an 'inbetween lane' and connects the entry/exit lanes to it
     * Parameters - takes the entry and exit lanes that need to be connected
     * @return - Returns whether the method succesfully managed to connect the lanes
     */
    public void connectLanes(Lane entry, Lane exit) {
        Lane between = new Lane(10.f, null, null);
        this.between_lanes.add(between);
        
        entry.addGoingLane(between);
        exit.addComingLane(between);
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
                Lane entry_lane = entry_road.get(j);          // Gets a lane
                String direction = entry_lane.getDirection(); // Gets the lane's direction
                for (char c : direction.toCharArray()) {    // For every char/direction the lane is connected in
                    // Initialise variables
                    int exit_i;     // Index to get the side of the exit lanes
                    Lane exit_lane; // The exit lane that needs to be connected to
                    switch (c) {
                        case 'L':   // Left
                            exit_i = (i+1) % 4; // The index on the left of side (i)
                            exit_lane = exit_lanes.get(exit_i).get(j);
                            this.connectLanes(entry_lane, exit_lane);
                            break;
                        case 'F':   // Forward
                            exit_i = (i+2) % 4; // Gets the opposing index
                            exit_lane = exit_lanes.get(exit_i).get(j);
                            this.connectLanes(entry_lane, exit_lane);
                            break;
                        case 'R':   // Right
                            exit_i = (i+3) % 4; // The index on the left of side (i)
                            exit_lane = exit_lanes.get(exit_i).get(j);
                            this.connectLanes(entry_lane, exit_lane);
                            break;
                    }
                }
            }
        }

        return true;
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

    // TODO: Is this a simple setter or any validation required?
    public void setTrafficLightConfig(TrafficLightConfig config) {
        
    }

    // side1 = junction entry direction, side2 = junction exit direction
    // objective: return (random) 1 of the list of routes between entry & exit
    // user can configure different lane types e.g. only left turn
    public void findRoute(int side1, int side2) {
        // side 1 (chooses lane) -> middle lane -> side 2 (chooses lane)
        // find random lane on side 1 that goes to middle and goes to side 2

        int turn = ((side2 - side1 + 4) % 4);
        
        switch(turn) {
            case 0: // this shouldn't be
            case 1: // left
            case 2: // forward
            case 3: // right
            default: // probably won't need
        }
        // check if the leftmost lane goes left
    }

    /* Calls everything to update: looks through all exiting lanes, updates all 
    for loop through all the exiting lanes and call the update lane function, then move to middle and starting lanes
    - need to create an attribute that stores the middle lanes - done, use (between_lanes)
    - purpose of this method is to move things by the time interval

    */
    public void update(float time) {
        this.timer += time;     // Add time increment
        this.updateLights();    // Update traffic lights
        this.createVehicles();
        // update vehicles from exit lanes -> middle lanes -> starting lanes
        // use 3 for loops for each lane type 
        for (List<Lane> exitLaneList : exit_lanes) { // double for loop due to nested list for class attribute: outermost list stores list of lanes + then dir
            for (Lane lane : exitLaneList) {
                lane.update(time);
            }
        }
        for (Lane lane : between_lanes) {
            lane.update(time); // only one list of middle lanes because only going through them once in simulation
        }
        for (List<Lane> entryLaneList : entry_lanes) {
            for (Lane lane : entryLaneList) {
                lane.update(time);
            }
        }
    }

    public void updateLights() {
        // Get desired state for traffic lights:
        List<Integer> state_list = tl_config.getStates(this.timer);
        // Loop through this list to update the lights
        for (int i = 0; i < state_list.size(); i++) {
            int state = state_list.get(i);  // Get the desired state for the trafficlight
            this.traffic_lights.get(i).setState(state); // Update the traffic light
        }
    }

    public void createVehicles() {
        
        float dt = 0.5f; // simulation time step

        for (Map.Entry<String, Integer> rateEntry : vehicle_rate.entrySet()) { // loop over each vehicle rate key
            String key = rateEntry.getKey(); // e.g. ets
            int rate = rateEntry.getValue(); // vehicles per hour for this movement

            // calculate probabiloty that vehicle arrives during this time step
            double arrivalProbability = (rate/3600.0) * dt;

            // if vehicle arrives
            if (Math.random() < arrivalProbability) {
                // parse the key:
                // the first character represents the entry side (n,e,s,w)
                // the third character represents the desired exit direction
                char entrySide = key.charAt(0);
                char destLetter = key.charAt(2);
                char turn = 'F';  // default to straight (F)

                // determine the turning movement based on entry side and destination
                // e.g. if a vehicle is coming from North, and destLetter is 'e' then turn 'L'
                switch (entrySide) {
                case 'n':
                    if (destLetter == 'e') turn = 'L';
                    else if (destLetter == 's') turn = 'F';
                    else if (destLetter == 'w') turn = 'R';
                    break;
                case 'e':
                    if (destLetter == 's') turn = 'L';
                    else if (destLetter == 'w') turn = 'F';
                    else if (destLetter == 'n') turn = 'R';
                    break;
                case 's':
                    if (destLetter == 'w') turn = 'L';
                    else if (destLetter == 'n') turn = 'F';
                    else if (destLetter == 'e') turn = 'R';
                    break;
                case 'w':
                    if (destLetter == 'n') turn = 'L';
                    else if (destLetter == 'e') turn = 'F';
                    else if (destLetter == 's') turn = 'R';
                    break;
                default:
                    // if  entry side is not valid, skip key
                    continue;
                }

                // map entry side to an index in entry_lanes e.g. 'n' --> 0
                int sideIndex = 0;
                switch (entrySide) {
                    case 'n': sideIndex = 0; break;
                    case 'e': sideIndex = 1; break;
                    case 's': sideIndex = 2; break;
                    case 'w': sideIndex = 3; break;
                }

                // get list of entry lanes for this side
                List<Lane> lanes = entry_lanes.get(sideIndex);
                boolean vehicleAdded = false;

                // look for matching entry lane
                for (Lane lane : lanes) {
                    // lane.getDirection should return a relative direction string like 'L', 'F', 'R'
                    if (lane.getDirection() != null && lane.getDirection().contains(String.valueOf(turn))) {
                        // create new vehicle
                        // default values: max speed = 10.0f, length = 2.0f (MIGHT NEED OPTIMISE LOGIC TO CATER TO BUSSES/BIKES)
                        // timer = creation time (amount of time since simulation started)
                        /*
                         * Remove comment next time you're developing
                         * Vehicle vehicle = new Vehicle();
                         */


                        // try to add vehicle to lane
                        /*
                        * Remove comment next time you're developing
                        *if (lane.addVehicle()) {
                        *    vehicleAdded = true;
                        *    break;
                        *}
                        */
                    }
                }
                
            }
        }        
    }

    public void calculateMetrics() {
    
    }

    public JunctionMetrics getMetrics() {
        return null;
    }

    // want to be able to save all a junction's features into a text file (e.g. csv or JSON)
    public String convertToText() {
        return null;
    }

    // should also be able to create the junction from the text file created above
    public void constructFromText(String text) {

    }
    
}
