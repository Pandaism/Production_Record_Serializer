package com.pandaism.serializer.controller.units;

import com.pandaism.serializer.Main;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Singular {
    public SimpleStringProperty cpu_serial;
    protected ExecutorService service;

    public byte[] cpuBytes;

    public Singular(SimpleStringProperty cpu_serial) {
        this.cpu_serial = cpu_serial;

        this.service = Executors.newFixedThreadPool(1);
        Main.EXECUTORS.add(this.service);
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
