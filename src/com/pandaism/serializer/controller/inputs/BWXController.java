package com.pandaism.serializer.controller.inputs;

import com.pandaism.serializer.controller.units.mvi.BWX;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class BWXController extends InputPanes {
    public TextField cpu_serial;
    public TextField docking_station;

    public void appendData(ActionEvent actionEvent) {
        super.table.getItems().add(new BWX(
                new SimpleStringProperty(cpu_serial.getText()),
                new SimpleStringProperty(docking_station.getText())
        ));

        cpu_serial.requestFocus();

        cpu_serial.setText("");
        docking_station.setText("");
    }
}
