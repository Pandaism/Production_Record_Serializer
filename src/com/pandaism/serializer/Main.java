package com.pandaism.serializer;

import com.pandaism.serializer.controller.ApplicationController;
import com.pandaism.serializer.fxml.SystemTab;
import com.pandaism.serializer.thread.ExecutorSchedulerThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {
    public static List<ExecutorService> EXECUTORS = new ArrayList<>(); // List to store all URLConnection executors
    public static ExecutorService executorSchedulerService; // Executor for checking if all URLConnections are finshed
    public static boolean exportable = true; // State for if data is ready to be exported, used in executorSchedulerService

    @Override
    public void start(Stage primaryStage) throws Exception{
        executorSchedulerService = Executors.newFixedThreadPool(1);
        executorSchedulerService.execute(new ExecutorSchedulerThread());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./fxml/application.fxml"));
        Parent root = loader.load();
        ApplicationController applicationController = loader.getController();

        primaryStage.setTitle("Production Record Serializer");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            close(applicationController.getData_sheet_tab_pane());
        });
        primaryStage.show();
    }



    public static void close(TabPane data_sheet_tab_pane) {
        if(data_sheet_tab_pane != null) {
            if(data_sheet_tab_pane.getTabs().size() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Tabs are still active ensure they have been properly saved", ButtonType.FINISH, ButtonType.CANCEL).showAndWait().ifPresent(buttonType -> {
                    if(buttonType == ButtonType.FINISH) {
                        executorSchedulerService.shutdown();
                        Platform.exit();
                        System.exit(0);
                    }
                });
            } else {
                executorSchedulerService.shutdown();
                Platform.exit();
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
