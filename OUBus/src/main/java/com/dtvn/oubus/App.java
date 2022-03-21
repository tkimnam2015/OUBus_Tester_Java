package com.dtvn.oubus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.StageStyle;

public class App extends Application {

    private static Scene scene;
    double x, y;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("FXMLStart"));
        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getSceneX() - x);
            stage.setY(event.getSceneY() - y);
        });
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
