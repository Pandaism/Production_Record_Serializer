package com.pandaism.serializer.controller.inputs;

import com.pandaism.serializer.controller.units.fleetmind.Tablets;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class TabletController extends InputPanes {
    public TextField cpu_serial;
    public TextField imei;
    public TextField sim;
    public TextField docking_station;

    public void appendData(ActionEvent actionEvent) {
        super.table.getItems().add(new Tablets(new SimpleStringProperty(cpu_serial.getText()), new SimpleStringProperty(imei.getText()), new SimpleStringProperty(sim.getText()), new SimpleStringProperty(docking_station.getText())));

        cpu_serial.requestFocus();

        cpu_serial.setText("");
        imei.setText("");
        sim.setText("");
        docking_station.setText("");
    }
}
