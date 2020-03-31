package com.pandaism.serializer;

import com.pandaism.serializer.thread.ExecutorSchedulerThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 TODO: qwq ouo owo uwu
 */
public class Main extends Application {
    public static List<ExecutorService> EXECUTORS = new ArrayList<>(); // List to store all URLConnection executors
    public static ExecutorService executorSchedulerService; // Executor for checking if all URLConnections are finshed
    public static boolean exportable = true; // State for if data is ready to be exported, used in executorSchedulerService

    @Override
    public void start(Stage primaryStage) throws Exception{
        executorSchedulerService = Executors.newFixedThreadPool(1);
        executorSchedulerService.execute(new ExecutorSchedulerThread());

        Parent root = FXMLLoader.load(getClass().getResource("./fxml/application.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
