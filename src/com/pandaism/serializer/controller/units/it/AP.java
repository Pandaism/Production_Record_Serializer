package com.pandaism.serializer.controller.units.it;

import com.pandaism.serializer.controller.units.Singular;
import javafx.beans.property.SimpleStringProperty;

public class AP extends Singular {
    private SimpleStringProperty antenna_serial;

    public AP(SimpleStringProperty cpu_serial, SimpleStringProperty antenna_serial) {
        super(cpu_serial);
        this.antenna_serial = antenna_serial;
    }

    public String getAntenna_serial() {
        return antenna_serial.get();
    }

    public SimpleStringProperty antenna_serialProperty() {
        return antenna_serial;
    }
}
