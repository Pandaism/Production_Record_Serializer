package com.pandaism.serializer.controller.inputs;

import com.pandaism.serializer.controller.units.fleetmind.Tablets;
import com.pandaism.serializer.controller.units.mvi.InCar;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class InCarController extends InputPanes {
    public TextField id;
    public TextField cpu_serial;
    public TextField door_rev;
    public TextField software_rev;
    public TextField supplier_sn;
    public TextField sd_card;
    public TextField assigned_ip;

    public void appendData(ActionEvent actionEvent) {
        super.table.getItems().add(new InCar(
                new SimpleStringProperty(cpu_serial.getText()),
                new SimpleStringProperty(id.getText()),
                new SimpleStringProperty(door_rev.getText()),
                new SimpleStringProperty(software_rev.getText()),
                new SimpleStringProperty(supplier_sn.getText()),
                new SimpleStringProperty(sd_card.getText()),
                new SimpleStringProperty(assigned_ip.getText())
        ));

        cpu_serial.requestFocus();

        cpu_serial.setText("");
        id.setText("");
        door_rev.setText("");
        software_rev.setText("");
        supplier_sn.setText("");
        sd_card.setText("");
        assigned_ip.setText("");

        status_right.textProperty().set("Number of records: " + super.table.getItems().size());
    }
}
