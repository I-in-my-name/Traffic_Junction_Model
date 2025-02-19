package com.trafficjunction.Junction_Classes;

public class Bus extends Vehicle {

    // TODO: What differentiates bus and car vehicles? 
    // Can they just be modelled as vehicle objects w/ different 
    // time, speeds, and lengths? + Carrying capcity 
    // Bus could enforce lower maximum speed and higher length in its constructor
    
    public Bus(float time, float max_speed, float length) {
        super(time, max_speed, length);
    }
    
}