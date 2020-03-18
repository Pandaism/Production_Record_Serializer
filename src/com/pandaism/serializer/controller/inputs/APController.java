package com.pandaism.serializer.controller.inputs;

import com.pandaism.serializer.controller.units.mvi.BWX;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class APController extends InputPanes {
    public TextField cpu_serial;
    public TextField antenna_serial;

    public void appendData(ActionEvent actionEvent) {
        super.table.getItems().add(new BWX(
                new SimpleStringProperty(cpu_serial.getText()),
                new SimpleStringProperty(antenna_serial.getText())
        ));

        cpu_serial.requestFocus();

        cpu_serial.setText("");
        antenna_serial.setText("");
    }
}
