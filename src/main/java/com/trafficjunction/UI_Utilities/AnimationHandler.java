package com.trafficjunction.UI_Utilities;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class AnimationHandler {
        public static Path createArc(double startX, double startY, double endX, double endY,
                        boolean sweep, Paint colour) {
                Path path = new Path();
                path.getElements().add(new MoveTo(startX, startY));

                // Divide or multiply by a constant to decrease / increase the tightness of
                // curves.
                double radius = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
                radius *= 1;

                ArcTo arcTo = new ArcTo();
                arcTo.setX(endX);
                arcTo.setY(endY);
                arcTo.setRadiusX(radius);
                arcTo.setRadiusY(radius);
                arcTo.setSweepFlag(sweep);
                arcTo.setLargeArcFlag(false);

                path.getElements().add(arcTo);
                path.setStroke(colour);
                path.setStrokeWidth(2);

                return path;
        }

        public static Line createStraight(double startX, double startY, double endX, double endY) {
                Path path = new Path();
                path.getElements().add(new MoveTo(startX, startY));

                Line line = new Line();
                line.setStartX(startX);
                line.setStartY(startY);
                line.setEndX(endX);
                line.setEndY(endY);
                line.setStroke(Color.BLUE);

                return line;
        }

        /*
         * ========== FUNCTIONS TO GENERATE PATHS ===========
         * Functions to generate all paths for going from the west to the north.
         * 
         * @return Path[] / Line[] - An array of paths indexed by the appropriate lane.
         */

        // --- Paths from West.
        public Path[] generateWestToNorthPaths() {
                double[][] startPoints = { { 170, 247 }, { 170, 215 }, { 170, 181 }, { 170, 148 }, { 170, 116 } };
                double[][] endPoints = { { 343, 74 }, { 311, 74 }, { 277, 74 }, { 244, 74 }, { 212, 74 } };

                // Offset the coordinates.
                int offset = 25;
                for (int i = 0; i < startPoints.length; i++) {
                        startPoints[i][0] += offset;
                        startPoints[i][1] += offset;
                        endPoints[i][0] += offset;
                        endPoints[i][1] += offset;
                }

                Path[] paths = new Path[startPoints.length];

                for (int i = 0; i < startPoints.length; i++) {
                        paths[i] = createArc(startPoints[i][0], startPoints[i][1], endPoints[i][0], endPoints[i][1],
                                        false, Color.RED);
                }

                return paths;
        }

        public Line[] generateWestToEastPaths() {
                double[][] startPoints = { { 170, 247 }, { 170, 215 }, { 170, 181 }, { 170, 148 }, { 170, 116 } };
                double[][] endPoints = { { 555, 247 }, { 555, 215 }, { 555, 181 }, { 555, 148 }, { 555, 116 } };

                // Offset the coordinates.
                int offset = 25;
                for (int i = 0; i < startPoints.length; i++) {
                        startPoints[i][0] += offset;
                        startPoints[i][1] += offset;
                        endPoints[i][0] += offset;
                        endPoints[i][1] += offset;
                }

                Line[] paths = new Line[startPoints.length];

                for (int i = 0; i < startPoints.length; i++) {
                        paths[i] = createStraight(startPoints[i][0], startPoints[i][1], endPoints[i][0],
                                        endPoints[i][1]);
                }

                return paths;
        }

        public Path[] generateWestToSouthPaths() {
                double[][] startPoints = { { 170, 247 }, { 170, 215 }, { 170, 181 }, { 170, 148 }, { 170, 116 } };
                double[][] endPoints = { { 377, 460 }, { 412, 460 }, { 446, 460 }, { 479, 460 }, { 512, 460 } };

                // Offset the coordinates.
                int offset = 25;
                for (int i = 0; i < startPoints.length; i++) {
                        startPoints[i][0] += offset;
                        startPoints[i][1] += offset;
                        endPoints[i][0] += offset;
                        endPoints[i][1] += offset;
                }

                Path[] paths = new Path[startPoints.length];

                for (int i = 0; i < startPoints.length; i++) {
                        paths[i] = createArc(startPoints[i][0], startPoints[i][1], endPoints[i][0], endPoints[i][1],
                                        true, Color.GREEN);
                }

                return paths;
        }

        // ---- Paths from North.
        public Path[] generateNorthToEastPaths() {
                double[][] startPoints = { { 377, 74 }, { 407, 74 }, { 442, 74 }, { 475, 74 }, { 510, 74 } };
                double[][] endPoints = { { 555, 247 }, { 555, 215 }, { 555, 181 }, { 555, 148 }, { 555, 116 } };

                // Offset the coordinates.
                int offset = 25;
                for (int i = 0; i < startPoints.length; i++) {
                        startPoints[i][0] += offset;
                        startPoints[i][1] += offset;
                        endPoints[i][0] += offset;
                        endPoints[i][1] += offset;
                }

                Path[] paths = new Path[startPoints.length];

                for (int i = 0; i < startPoints.length; i++) {
                        paths[i] = createArc(startPoints[i][0], startPoints[i][1], endPoints[i][0], endPoints[i][1],
                                        false, Color.RED);
                }

                return paths;
        }

        public Line[] generateNorthToSouthPaths() {
                double[][] startPoints = { { 377, 74 }, { 407, 74 }, { 442, 74 }, { 475, 74 }, { 510, 74 } };
                double[][] endPoints = { { 377, 460 }, { 407, 460 }, { 442, 460 }, { 475, 460 }, { 510, 460 } };

                // Offset the coordinates.
                int offset = 25;
                for (int i = 0; i < startPoints.length; i++) {
                        startPoints[i][0] += offset;
                        startPoints[i][1] += offset;
                        endPoints[i][0] += offset;
                        endPoints[i][1] += offset;
                }

                Line[] paths = new Line[startPoints.length];

                for (int i = 0; i < startPoints.length; i++) {
                        paths[i] = createStraight(startPoints[i][0], startPoints[i][1], endPoints[i][0],
                                        endPoints[i][1]);
                }

                return paths;
        }

        public Path[] generateNorthToWestPaths() {
                double[][] startPoints = { { 377, 74 }, { 407, 74 }, { 442, 74 }, { 475, 74 }, { 510, 74 } };
                double[][] endPoints = { { 170, 281 }, { 170, 314 }, { 170, 348 }, { 170, 381 }, { 170, 415 } };

                // Offset the coordinates.
                int offset = 25;
                for (int i = 0; i < startPoints.length; i++) {
                        startPoints[i][0] += offset;
                        startPoints[i][1] += offset;
                        endPoints[i][0] += offset;
                        endPoints[i][1] += offset;
                }

                Path[] paths = new Path[startPoints.length];

                for (int i = 0; i < startPoints.length; i++) {
                        paths[i] = createArc(startPoints[i][0], startPoints[i][1], endPoints[i][0], endPoints[i][1],
                                        true, Color.GREEN);
                }

                return paths;
        }

        // ---- Paths from East.
        public Path[] generateEastToSouthPaths() {
                double[][] startPoints = { { 555, 281 }, { 555, 314 }, { 555, 348 }, { 555, 381 }, { 555, 415 } };
                double[][] endPoints = { { 377, 460 }, { 407, 460 }, { 442, 460 }, { 475, 460 }, { 510, 460 } };

                // Offset the coordinates.
                int offset = 20;
                for (int i = 0; i < startPoints.length; i++) {
                        startPoints[i][0] += offset;
                        startPoints[i][1] += offset;
                        endPoints[i][0] += offset;
                        endPoints[i][1] += offset;
                }

                Path[] paths = new Path[startPoints.length];

                for (int i = 0; i < startPoints.length; i++) {
                        paths[i] = createArc(startPoints[i][0], startPoints[i][1], endPoints[i][0], endPoints[i][1],
                                        false, Color.RED);
                }

                return paths;
        }

        public Line[] generateEastToWestPaths() {
                double[][] startPoints = { { 555, 281 }, { 555, 314 }, { 555, 348 }, { 555, 381 }, { 555, 415 } };
                double[][] endPoints = { { 170, 281 }, { 170, 314 }, { 170, 348 }, { 170, 381 }, { 170, 415 } };

                // Offset the coordinates.
                int offset = 20;
                for (int i = 0; i < startPoints.length; i++) {
                        startPoints[i][0] += offset;
                        startPoints[i][1] += offset;
                        endPoints[i][0] += offset;
                        endPoints[i][1] += offset;
                }

                Line[] paths = new Line[startPoints.length];

                for (int i = 0; i < startPoints.length; i++) {
                        paths[i] = createStraight(startPoints[i][0], startPoints[i][1], endPoints[i][0],
                                        endPoints[i][1]);
                }

                return paths;
        }

        public Path[] generateEastToNorthPaths() {
                double[][] startPoints = { { 555, 281 }, { 555, 314 }, { 555, 348 }, { 555, 381 }, { 555, 415 } };
                double[][] endPoints = { { 343, 74 }, { 311, 74 }, { 277, 74 }, { 244, 74 }, { 212, 74 } };

                // Offset the coordinates.
                int offset = 20;
                for (int i = 0; i < startPoints.length; i++) {
                        startPoints[i][0] += offset;
                        startPoints[i][1] += offset;
                        endPoints[i][0] += offset;
                        endPoints[i][1] += offset;
                }

                Path[] paths = new Path[startPoints.length];

                for (int i = 0; i < startPoints.length; i++) {
                        paths[i] = createArc(startPoints[i][0], startPoints[i][1], endPoints[i][0], endPoints[i][1],
                                        true, Color.GREEN);
                }

                return paths;
        }

        /*
         * ========== END OF FUNCTIONS TO GENERATE PATHS ==========
         */

        public PathTransition animateCarAlongPath(Path path, ImageView car) {
                PathTransition pathTransition = new PathTransition();
                pathTransition.setNode(car);
                pathTransition.setPath(path);
                pathTransition.setDuration(Duration.seconds(3));
                pathTransition.setCycleCount(3);
                pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
                pathTransition.play();

                return pathTransition;
        }

        public PathTransition animateCarAlongPath(Line line, ImageView car) {
                PathTransition pathTransition = new PathTransition();
                pathTransition.setNode(car);
                pathTransition.setPath(line);
                pathTransition.setDuration(Duration.seconds(3));
                pathTransition.setCycleCount(3);
                pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
                pathTransition.play();

                return pathTransition;
        }

        public ImageView createImage(double x, double y) {
                Image carImage = new Image(getClass().getResourceAsStream("/assets/car.png"));
                ImageView car = new ImageView(carImage);

                car.setFitWidth(46);
                car.setFitHeight(46);
                car.setRotate(180);
                // AnchorPane.setLeftAnchor(car, x);
                // AnchorPane.setTopAnchor(car, y);

                return car;
        }

        public void addToAnchorPane(AnchorPane pane) {
                Path[] paths = generateWestToNorthPaths();
                Line[] paths2 = generateWestToEastPaths();
                Path[] paths3 = generateWestToSouthPaths();
                Path[] paths4 = generateNorthToEastPaths();
                Line[] paths5 = generateNorthToSouthPaths();
                Path[] paths6 = generateNorthToWestPaths();
                Path[] paths7 = generateEastToSouthPaths();
                Line[] paths8 = generateEastToWestPaths();
                Path[] paths9 = generateEastToNorthPaths();

                for (Path path : paths) {
                        pane.getChildren().add(path);
                }
                // ----- FILLER CODE FOR DEBUGGING ------
                for (Line path : paths2) {
                        pane.getChildren().add(path);
                }
                for (Path path : paths3) {
                        pane.getChildren().add(path);
                }
                for (Path path : paths4) {
                        pane.getChildren().add(path);
                }
                for (Line path : paths5) {
                        pane.getChildren().add(path);
                }
                for (Path path : paths6) {
                        pane.getChildren().add(path);
                }
                for (Path path : paths7) {
                        pane.getChildren().add(path);
                }
                for (Line path : paths8) {
                        pane.getChildren().add(path);
                }
                for (Path path : paths9) {
                        pane.getChildren().add(path);
                }

                // --- end of filler code. ---
                ImageView car = createImage(170, 247);
                pane.getChildren().add(car);

                animateCarAlongPath(paths[0], car);
        }

}