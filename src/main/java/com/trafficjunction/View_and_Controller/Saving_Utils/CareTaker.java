package com.trafficjunction.View_and_Controller.Saving_Utils;

import java.util.ArrayList;

public class CareTaker {
    ArrayList<ConfigurationSnapshot> allSnapshots;
    public int index;

    // starts at -1 so that record indexing is kept properly
    public CareTaker() {
        allSnapshots = new ArrayList<>();
        index = -1;
    }

    public void addSnap(ConfigurationSnapshot configSnap) {
        allSnapshots.add(configSnap);
        index++;
        System.out.println("SNAP " + index + " Added");
    }

    public void undo() {
        if (index >= 0) {
            allSnapshots.get(index).restore();
            index--;
            System.out.println("UNDO hit, now on index " + index);
            if (index < 0)
                index++;
        }
    }

    public void redo() {
        if (index + 1 < allSnapshots.size()) {
            index++;
            allSnapshots.get(index).restore();
            if (index >= allSnapshots.size())
                index--;
        } else {
            System.out.println("MAN");
        }
    }
}
