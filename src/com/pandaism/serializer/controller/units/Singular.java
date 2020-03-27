package com.pandaism.serializer.controller.units;

import javafx.beans.property.SimpleStringProperty;

public class Singular {
    public SimpleStringProperty cpu_serial;

    public byte[] cpuBytes;

    public Singular(SimpleStringProperty cpu_serial) {
        this.cpu_serial = cpu_serial;
    }

    public String getCpu_serial() {
        return cpu_serial.get();
    }

    public byte[] getCpuBytes() {
        return cpuBytes;
    }

    public void setCpuBytes(byte[] cpuBytes) {
        this.cpuBytes = cpuBytes;
    }
}
