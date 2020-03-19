package com.pandaism.serializer.controller.inputs;

import com.pandaism.serializer.controller.units.Singular;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class SingularController extends InputPanes{
    public TextField cpu_serial;

    public void appendData(ActionEvent actionEvent) {
        super.table.getItems().add(new Singular(new SimpleStringProperty(cpu_serial.getText())));

        cpu_serial.requestFocus();

        cpu_serial.setText("");

        status_right.textProperty().set("Number of records: " + super.table.getItems().size());
    }
}
