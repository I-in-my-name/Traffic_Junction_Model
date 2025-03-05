package com.trafficjunction.observer;

import java.util.List;

public class Event {

    private EventType type;
    private List<Float> data;

    public Event(EventType type, List<Float> data) {
        this.type = type;
        this.data = data;
    }

    public EventType getType() {
        return type;
    }

    public List<Float> getData() {
        return data;
    }
    
}