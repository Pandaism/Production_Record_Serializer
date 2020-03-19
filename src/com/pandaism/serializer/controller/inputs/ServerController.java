package com.pandaism.serializer.controller.inputs;

import com.pandaism.serializer.controller.units.it.Server;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class ServerController extends InputPanes {

    public TextField cpu_serial;

    public void appendData(ActionEvent actionEvent) {
        super.table.getItems().add(new Server(new SimpleStringProperty(cpu_serial.getText())));

        cpu_serial.requestFocus();

        cpu_serial.setText("");

        status_right.textProperty().set("Number of records: " + super.table.getItems().size());
    }
}
