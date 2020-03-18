package com.pandaism.serializer.controller.inputs;

import com.pandaism.serializer.controller.units.mvi.InCar;
import com.pandaism.serializer.controller.units.mvi.Interview;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class InterviewController extends InputPanes {
    public TextField id;
    public TextField cpu_serial;
    public TextField door_rev;
    public TextField software_rev;
    public TextField supplier_sn;
    public TextField sd_card;

    public void appendData(ActionEvent actionEvent) {
        super.table.getItems().add(new Interview(
                new SimpleStringProperty(cpu_serial.getText()),
                new SimpleStringProperty(id.getText()),
                new SimpleStringProperty(door_rev.getText()),
                new SimpleStringProperty(software_rev.getText()),
                new SimpleStringProperty(supplier_sn.getText()),
                new SimpleStringProperty(sd_card.getText())
        ));

        cpu_serial.requestFocus();

        cpu_serial.setText("");
        id.setText("");
        door_rev.setText("");
        software_rev.setText("");
        supplier_sn.setText("");
        sd_card.setText("");
    }
}
