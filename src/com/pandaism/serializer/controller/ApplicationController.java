package com.pandaism.serializer.controller;

import com.pandaism.serializer.fxml.SystemTab;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationController {
    public TabPane data_sheet_tab_pane;
    public Menu edit_menu;

    private CreateNewSystemController createNewSystemController;

    public void create_new(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/create_new_system.fxml"));
        Parent root = loader.load();
        Stage createNewStage = new Stage();

        this.createNewSystemController = loader.getController();
        this.createNewSystemController.setMainStage(createNewStage);
        this.createNewSystemController.setDataSheetPane(this.data_sheet_tab_pane);
        this.createNewSystemController.setEdit_menu(this.edit_menu);

        createNewStage.setScene(new Scene(root, 640, 180));
        createNewStage.show();
    }

    public void open(ActionEvent actionEvent) {

    }

    public void open_saveas(ActionEvent actionEvent) {

    }

    public void quit(ActionEvent actionEvent) {

    }

    public void export(ActionEvent actionEvent) {
        System.out.println(((SystemTab)this.data_sheet_tab_pane.getSelectionModel().getSelectedItem()).getCustomer());
    }
}
