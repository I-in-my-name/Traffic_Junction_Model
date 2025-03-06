package com.trafficjunction.View_and_Controller.Saving_Utils;

import java.util.Map;

import com.trafficjunction.JunctionMetrics;

public class ConfigurationSnapshot {

    public JunctionMetrics link;
    Map<String, Integer> vehicleNumsMap;
    Map<String, Integer> trafficLightDurs;

    public ConfigurationSnapshot(JunctionMetrics link) {
        this.link = link;
        vehicleNumsMap = link.getAllVehicleNums();
        trafficLightDurs = link.getAllTrafficLightDurs();
    }

    public void restore() {
        link.setAllVehicleNums(vehicleNumsMap);
        link.setAllTrafficLightDurs(trafficLightDurs);
    }

}
