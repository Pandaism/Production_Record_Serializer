package com.pandaism.serializer.controller;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class DataSheetController<T> {
    public TableView<T> data_table;
    public AnchorPane input_pane;
    public Label status_left;
    public Label status_right;

    public TableView<T> getData_table() {
        return data_table;
    }

    public AnchorPane getInput_pane() {
        return input_pane;
    }

    public Label getStatus_left() {
        return status_left;
    }

    public Label getStatus_right() {
        return status_right;
    }
}
