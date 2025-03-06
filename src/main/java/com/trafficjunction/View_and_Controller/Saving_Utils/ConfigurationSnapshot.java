package com.trafficjunction.View_and_Controller.Saving_Utils;

import com.trafficjunction.JunctionConfiguration;

public class ConfigurationSnapshot {

    public JunctionConfiguration link;
    int[] directionInfo;

    public ConfigurationSnapshot(JunctionConfiguration link) {
        this.link = link;
        directionInfo = link.getDirectionInfo().clone();
    }

    public void restore() {
        link.setDirectionInfo(directionInfo);
    }

}
