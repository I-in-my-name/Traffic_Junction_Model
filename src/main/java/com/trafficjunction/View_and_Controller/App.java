package com.trafficjunction.View_and_Controller;

import com.trafficjunction.observer.*;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application implements Observer {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Subject.registerObserver(this);

        scene = new Scene(loadFXML("primary"));
        stage.setTitle("Traffic Junction Simulator");
        Image icon = new Image(getClass().getResourceAsStream("/assets/trafficCone.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        System.out.println("com/trafficjunction/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/trafficjunction/primary.fxml"));
        return fxmlLoader.load();
    }

    public void notify(Event event) {
    }

    public static void main(String[] args) {
        launch();
    }

}