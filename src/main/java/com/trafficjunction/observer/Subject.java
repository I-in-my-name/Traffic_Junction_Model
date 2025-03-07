package com.trafficjunction.observer;

import java.util.ArrayList;

public class Subject {

    private static ArrayList<Observer> observers = new ArrayList<>();

    public static void notifyObservers(Event event) {
        for (Observer observer : observers) {
            observer.notify(event);
        }
    }

    public static void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public static void deregisterObserver(Observer observer) {
        observers.remove(observer);
    }
    
}
