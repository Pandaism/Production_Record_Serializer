package com.pandaism.serializer.controller.units;

import javafx.beans.property.SimpleStringProperty;

public class Singular {
    private SimpleStringProperty cpu_serial;

    public Singular(SimpleStringProperty cpu_serial) {
        this.cpu_serial = cpu_serial;
    }

    public String getCpu_serial() {
        return cpu_serial.get();
    }
}
