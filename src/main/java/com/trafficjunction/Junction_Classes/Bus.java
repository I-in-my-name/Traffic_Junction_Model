package com.trafficjunction.Junction_Classes;
import java.util.List;

public class Bus extends Vehicle {

    // TODO: What differentiates bus and car vehicles? 
    // Can they just be modelled as vehicle objects w/ different 
    // time, speeds, and lengths? + Carrying capcity 
    // Bus could enforce lower maximum speed and higher length in its constructor
    
    public Bus(float b_time, List<Lane> b_route) {
        super(b_time, 60.f, 8.f, b_route);
    }
}