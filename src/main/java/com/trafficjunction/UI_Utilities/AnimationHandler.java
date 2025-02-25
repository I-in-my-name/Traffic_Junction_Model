package com.trafficjunction.UI_Utilities;

import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class AnimationHandler {
    Coordinates[][] inLanes = {
            { new Coordinates(377, 74), new Coordinates(412, 74), new Coordinates(446, 74), new Coordinates(479, 74),
                    new Coordinates(512, 74) }, // North
            { new Coordinates(555, 281), new Coordinates(555, 314), new Coordinates(555, 348),
                    new Coordinates(555, 381), new Coordinates(555, 415) }, // East
            { new Coordinates(343, 460), new Coordinates(311, 460), new Coordinates(277, 460),
                    new Coordinates(244, 460), new Coordinates(212, 460) }, // South
            { new Coordinates(170, 247), new Coordinates(170, 215), new Coordinates(170, 181),
                    new Coordinates(170, 148), new Coordinates(170, 116) } // West
    };

    Coordinates[][] outLanes = {
            { new Coordinates(343, 74), new Coordinates(311, 74), new Coordinates(277, 74), new Coordinates(244, 74),
                    new Coordinates(212, 74) }, // North
            { new Coordinates(555, 247), new Coordinates(555, 215), new Coordinates(555, 181),
                    new Coordinates(555, 148), new Coordinates(555, 116) }, // East
            { new Coordinates(377, 460), new Coordinates(412, 460), new Coordinates(446, 460),
                    new Coordinates(479, 460), new Coordinates(512, 460) }, // South
            { new Coordinates(170, 281), new Coordinates(170, 314), new Coordinates(170, 348),
                    new Coordinates(170, 381), new Coordinates(170, 415) } // West
    };

    public void createAnimation(int inDir, int inLane, int outDir, int outLane, ImageView car) {
        car.toFront();
        car.setLayoutX(inLanes[inDir][inLane].getX());
        car.setLayoutY(inLanes[inDir][inLane].getY());

        Path path = new Path();
        // Path starts at these coordinates.
        path.getElements().add(new MoveTo(inLanes[inDir][inLane].getX(), inLanes[inDir][inLane].getY()));

        // Create an arc that ends at the end coordinates with a radius of half the
        // distance between the coordinates.
        ArcTo arcTo = new ArcTo();
        arcTo.setX(outLanes[outDir][outLane].getX());
        arcTo.setY(outLanes[outDir][outLane].getY());

        float radius = (float) Math.sqrt(
                Math.pow(outLanes[outDir][outLane].getX() - inLanes[inDir][inLane].getX(), 2)
                        + Math.pow(outLanes[outDir][outLane].getY() - inLanes[inDir][inLane].getY(), 2))
                / 2;

        arcTo.setRadiusX(radius);
        arcTo.setRadiusY(radius);
        arcTo.setLargeArcFlag(true);
        arcTo.setSweepFlag(true);
        path.getElements().add(arcTo);

        // Animate the image along the path.
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.seconds(3));
        transition.setPath(path);
        transition.setNode(car);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.setAutoReverse(true);
        System.out.println("Starting position is " + car.localToScene(car.getBoundsInLocal()));
        transition.play();
        System.out.println("Car is moving from " + inLanes[inDir][inLane] + " to " + outLanes[outDir][outLane]);
        return;
    }

}

class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "X: " + x + " Y: " + y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
