package com.pandaism.serializer.controller.inputs;

import com.pandaism.serializer.controller.units.fleetmind.DVR;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class DVRController extends InputPanes{
    public TextField cpu_serial;
    public TextField monitor;
    public TextField imei;
    public TextField sim;
    public TextField rfid;
    public TextField relay;

    public void appendData(ActionEvent actionEvent) {
        super.table.getItems().add(new DVR(new SimpleStringProperty(cpu_serial.getText()), new SimpleStringProperty(monitor.getText()), new SimpleStringProperty(imei.getText()), new SimpleStringProperty(sim.getText()), new SimpleStringProperty(rfid.getText()), new SimpleStringProperty(relay.getText())));

        cpu_serial.requestFocus();

        cpu_serial.setText("");
        monitor.setText("");
        imei.setText("");
        sim.setText("");
        rfid.setText("");
        relay.setText("");

        status_right.textProperty().set("Number of records: " + super.table.getItems().size());
    }
}
