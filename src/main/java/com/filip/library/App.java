package com.filip.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.AnchorPane;

public class App extends Application {

    private static Scene scene;

    private static AnchorPane anchor;

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {

        App.setStage(stage);
        FXMLLoader loader = new FXMLLoader(App.class.getResource("primary.fxml"));
        App.setAnchor(loader.load());
        App.setScene(new Scene(anchor));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        App.launch();
    }

    public static synchronized AnchorPane getAnchor() {
        return App.anchor;
    }

    public static synchronized void setAnchor(AnchorPane anchor) {
        App.anchor = anchor;
    }

    public synchronized static Scene getScene() {
        return scene;
    }

    public synchronized static void setScene(Scene scene) {
        App.scene = scene;
    }

    public static synchronized Stage getStage() {
        return stage;
    }

    public static synchronized void setStage(Stage stage) {
        App.stage = stage;
    }

}
