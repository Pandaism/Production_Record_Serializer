package com.pandaism.serializer.controller.units.it;

import com.pandaism.serializer.controller.units.Singular;
import com.pandaism.serializer.thread.URLConnectionThread;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represent an IT Production AP
 */
public class AP extends Singular {
    public SimpleStringProperty antenna_serial;

    public byte[] antenna_serialBytes;

    public AP(SimpleStringProperty cpu_serial, SimpleStringProperty antenna_serial) {
        super(cpu_serial);
        this.antenna_serial = antenna_serial;

        super.service.execute(new URLConnectionThread<>(this));
        super.service.shutdown();
    }

    public byte[] getAntenna_serialBytes() {
        return antenna_serialBytes;
    }

    public void setAntenna_serialBytes(byte[] antenna_serialBytes) {
        this.antenna_serialBytes = antenna_serialBytes;
    }

    public String getAntenna_serial() {
        return antenna_serial.get();
    }

    public SimpleStringProperty antenna_serialProperty() {
        return antenna_serial;
    }
}
