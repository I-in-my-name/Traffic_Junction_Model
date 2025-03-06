package com.trafficjunction.UI_Utilities;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
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

    private Path[] westToNorthPaths;
    private Line[] westToEastPaths;
    private Path[] westToSouthPaths;

    private Path[] northToEastPaths;
    private Line[] northToSouthPaths;
    private Path[] northToWestPaths;

    private Path[] eastToSouthPaths;
    private Line[] eastToWestPaths;
    private Path[] eastToNorthPaths;

    private Path[] southToWestPaths;
    private Line[] southToNorthPaths;
    private Path[] southToEastPaths;

    private AnchorPane pane;

    public AnimationHandler(AnchorPane pane) {
        this.pane = pane;
        addToAnchorPane(); // Generate all the paths and add them to the anchor pane.
    }

    /*
     * Function to create an arc path.
     * 
     * @param startX - The x-coordinate of the start of the path.
     * 
     * @param startY - The y-coordinate of the start of the path.
     * 
     * @param endX - The x-coordinate of the end of the path.
     * 
     * @param endY - The y-coordinate of the end of the path.
     * 
     * @param sweep - A boolean value dictating whether the arc has a sweep flag or
     * not (inversed direction of curve.)
     * 
     * @param colour - The colour of the arc to be created. TODO: WILL BE REMOVED
     * LATER AS PATHS SHOULD BE INVISIBLE.
     * 
     * @return Path - The created path.
     */
    public static Path createArc(double startX, double startY, double endX, double endY,
            boolean sweep, Paint colour) {
        Path path = new Path();
        path.getElements().add(new MoveTo(startX, startY));

        // Divide or multiply by a constant to decrease / increase the tightness of
        // curves.
        double radius = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        radius *= 0.7;

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

    /*
     * Function to create straight lines for when cars carry on straight in the
     * simulator.
     * 
     * @param startX - The x-coordinate of the start of the path.
     * 
     * @param startY - The y-coordinate of the start of the path.
     * 
     * @param endX - The x-coordinate of the end of the path.
     * 
     * @param endY - The y-coordinate of the end of the path
     * 
     * @return Line - The created line.
     */
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

    // ---- Paths from South.
    public Path[] generateSouthToWestPaths() {
        double[][] startPoints = { { 343, 460 }, { 311, 460 }, { 277, 460 }, { 244, 460 }, { 212, 460 } };
        double[][] endPoints = { { 170, 281 }, { 170, 314 }, { 170, 348 }, { 170, 381 }, { 170, 415 } };

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

    public Line[] generateSouthToNorth() {
        double[][] startPoints = { { 343, 460 }, { 311, 460 }, { 277, 460 }, { 244, 460 }, { 212, 460 } };
        double[][] endPoints = { { 343, 74 }, { 311, 74 }, { 277, 74 }, { 244, 74 }, { 212, 74 } };

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

    public Path[] generateSouthToEastPaths() {
        double[][] startPoints = { { 343, 460 }, { 311, 460 }, { 277, 460 }, { 244, 460 }, { 212, 460 } };
        double[][] endPoints = { { 555, 247 }, { 555, 215 }, { 555, 181 }, { 555, 148 }, { 555, 116 } };

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

    /*
     * Function to animate an image along the given path by creating a path
     * transition. One of two functions using polymorphism due to PathTransitions
     * being created from both Paths and Lines in JavaFX.
     * 
     * @param path - The path for the image to travel along.
     * 
     * @param car - The imageview to travel along the path.
     * 
     * @return PathTransition - The created PathTransition AKA the animation.
     */
    public PathTransition animateCarAlongPath(Path path, ImageView car) {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(car);
        pathTransition.setPath(path);
        pathTransition.setDuration(Duration.seconds(3));
        pathTransition.setCycleCount(1);
        pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();

        // Have the car fade out after finishing its animation.
        pathTransition.setOnFinished(event -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.3), car);
            fadeOut.setToValue(0);
            // Remove the car from the scene entirely.
            fadeOut.setOnFinished(event2 -> {
                ((AnchorPane) car.getParent()).getChildren().remove(car);
            });
            fadeOut.play();
        });

        return pathTransition;
    }

    /*
     * Function to animate an image along the given line by creating a path
     * transition. One of two functions using polymorphism due to PathTransitions
     * being created from both Paths and Lines in JavaFX.
     * 
     * @param line - The line for the image to travel along.
     * 
     * @param car - The imageview to travel along the line.
     * 
     * @return PathTransition - The created PathTransition AKA the animation.
     */
    public PathTransition animateCarAlongPath(Line line, ImageView car) {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(car);
        pathTransition.setPath(line);
        pathTransition.setDuration(Duration.seconds(3));
        pathTransition.setCycleCount(1);
        pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();

        // Have the car fade out after finishing its animation.
        pathTransition.setOnFinished(event -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.3), car);
            fadeOut.setToValue(0);
            // Remove the car from the scene entirely.
            fadeOut.setOnFinished(event2 -> {
                ((AnchorPane) car.getParent()).getChildren().remove(car);
            });
            fadeOut.play();
        });

        return pathTransition;
    }

    /*
     * Function to "spawn" a car ImageView within the FXML file.
     * 
     * @return ImageView - The ImageView object of the car that has been generated.
     */
    public ImageView createCar() {
        Image carImage = new Image(getClass().getResourceAsStream("/assets/car.png"));
        ImageView car = new ImageView(carImage);

        car.setFitWidth(46);
        car.setFitHeight(46);
        car.setRotate(180);

        return car;
    }

    /*
     * Function to add all the paths to the FXML file. Takes the anchor pane as the
     * root that all the paths are being added to.
     * 
     * @param pane - The parent anchor pane to add all the paths inside of.
     */
    public void addToAnchorPane() {
        this.westToNorthPaths = generateWestToNorthPaths();
        this.westToEastPaths = generateWestToEastPaths();
        this.westToSouthPaths = generateWestToSouthPaths();
        this.northToEastPaths = generateNorthToEastPaths();
        this.northToSouthPaths = generateNorthToSouthPaths();
        this.northToWestPaths = generateNorthToWestPaths();
        this.eastToSouthPaths = generateEastToSouthPaths();
        this.eastToWestPaths = generateEastToWestPaths();
        this.eastToNorthPaths = generateEastToNorthPaths();
        this.southToWestPaths = generateSouthToWestPaths();
        this.southToNorthPaths = generateSouthToNorth();
        this.southToEastPaths = generateSouthToEastPaths();

        Path[][] allArcs = { westToNorthPaths, westToSouthPaths, northToEastPaths, northToWestPaths,
                eastToSouthPaths, eastToNorthPaths, southToWestPaths, southToEastPaths };
        Line[][] allLines = { westToEastPaths, northToSouthPaths, eastToWestPaths, southToNorthPaths };

        for (int i = 0; i < allArcs.length; i++) {
            for (int j = 0; j < allArcs[i].length; j++) {
                this.pane.getChildren().add(allArcs[i][j]);
            }
        }

        for (int i = 0; i < allLines.length; i++) {
            for (int j = 0; j < allArcs[i].length; j++) {
                this.pane.getChildren().add(allLines[i][j]);
            }
        }
    }

    /*
     * The main function to run an animation of a car going through the junction.
     * 
     * @param entry - The char defining which road the car enters from - must be of
     * 'N', 'E', 'S', 'W'.
     * 
     * @param exit - The char defining which road the car exits to - must be of 'N',
     * 'E', 'S', 'W'.
     * 
     * @param lane - The lane number the car will travel along. - Must be between 0
     * and 4.
     */
    public void chooseAnimation(char entry, char exit, int lane) {

        if (0 > lane || lane > 4) {
            System.out.println("Invalid lane number. Must be between 0 and 4 inclusive.");
            return;
        }

        ImageView car = createCar();
        pane.getChildren().add(car);
        switch (entry) {
            case 'N':
                switch (exit) {
                    case 'E':
                        animateCarAlongPath(this.northToEastPaths[lane], car);
                        break;
                    case 'S':
                        animateCarAlongPath(this.northToSouthPaths[lane], car);
                        break;
                    case 'W':
                        animateCarAlongPath(this.northToWestPaths[lane], car);
                        break;
                    default:
                        System.out.println("Invalid character exit");
                        break;
                }
                break;

            case 'E':
                switch (exit) {
                    case 'S':
                        animateCarAlongPath(this.eastToSouthPaths[lane], car);
                        break;
                    case 'W':
                        animateCarAlongPath(this.eastToWestPaths[lane], car);
                        break;
                    case 'N':
                        animateCarAlongPath(this.eastToNorthPaths[lane], car);
                        break;
                    default:
                        System.out.println("Invalid character exit");
                        break;
                }
                break;

            case 'S':
                switch (exit) {
                    case 'W':
                        animateCarAlongPath(this.southToWestPaths[lane], car);
                        break;
                    case 'N':
                        animateCarAlongPath(this.southToNorthPaths[lane], car);
                        break;
                    case 'E':
                        animateCarAlongPath(this.southToEastPaths[lane], car);
                        break;
                    default:
                        System.out.println("Invalid character exit");
                        break;
                }
                break;

            case 'W':
                switch (exit) {
                    case 'N':
                        animateCarAlongPath(this.westToNorthPaths[lane], car);
                        break;
                    case 'E':
                        animateCarAlongPath(this.westToEastPaths[lane], car);
                        break;
                    case 'S':
                        animateCarAlongPath(this.westToSouthPaths[lane], car);
                        break;
                    default:
                        System.out.println("Invalid character exit");
                        break;
                }
                break;
            default:
                System.out.println("Invalid character entry");
                break;
        }

        System.out.println("Animation function finished.");
    }
}