package com.pandaism.serializer.controller;

import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class DataSheetController<T> {
    public TableView<T> data_table;
    public AnchorPane input_pane;

    public TableView<T> getData_table() {
        return data_table;
    }

    public AnchorPane getInput_pane() {
        return input_pane;
    }
}
