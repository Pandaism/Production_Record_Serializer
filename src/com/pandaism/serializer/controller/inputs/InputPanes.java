package com.pandaism.serializer.controller.inputs;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public abstract class InputPanes {
    TableView table;
    Label status_left;
    Label status_right;

    public <T> void setTable(TableView<T> table) {
        this.table = table;
    }

    public void setStatus_left(Label status_left) {
        this.status_left = status_left;
    }

    public void setStatus_right(Label status_right) {
        this.status_right = status_right;
    }

    public Label getStatus_right() {
        return status_right;
    }
}
