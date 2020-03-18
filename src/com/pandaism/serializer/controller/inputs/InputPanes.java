package com.pandaism.serializer.controller.inputs;

import javafx.scene.control.TableView;

public abstract class InputPanes {
    TableView table;

    public <T> void setTable(TableView<T> table) {
        this.table = table;
    }
}
