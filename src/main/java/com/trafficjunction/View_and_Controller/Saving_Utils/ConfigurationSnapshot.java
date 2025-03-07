package com.trafficjunction.View_and_Controller.Saving_Utils;

import com.trafficjunction.JunctionMetrics;

public class ConfigurationSnapshot {

    public JunctionMetrics link;
    private JunctionMetrics copy;

    public ConfigurationSnapshot(JunctionMetrics link) {
        this.link = link;
        int[] empty = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        copy = new JunctionMetrics(empty, empty);
        copy.copyValues(link);
    }

    public void restore() {
        link.copyValues(copy);
    }

}
