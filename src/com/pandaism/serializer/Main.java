package com.pandaism.serializer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Main extends Application {
    public static ArrayList<ExecutorService> EXECUTORS = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("./fxml/application.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
