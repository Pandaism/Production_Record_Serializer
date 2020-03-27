package com.pandaism.serializer.controller.units.fleetmind;

import com.pandaism.serializer.controller.units.Singular;
import javafx.beans.property.SimpleStringProperty;

public class DVR extends Singular {
    public SimpleStringProperty monitor;
    public SimpleStringProperty imei;
    public SimpleStringProperty sim;
    public SimpleStringProperty rfid;
    public SimpleStringProperty relay;

    public byte[] monitorBytes;
    public byte[] simBytes;
    public byte[] rfidBytes;

    public DVR(SimpleStringProperty cpu_serial, SimpleStringProperty monitor, SimpleStringProperty imei, SimpleStringProperty sim, SimpleStringProperty rfid, SimpleStringProperty relay) {
        super(cpu_serial);
        this.monitor = monitor;
        this.imei = imei;
        this.sim = sim;
        this.rfid = rfid;
        this.relay = relay;
    }

    public byte[] getMonitorBytes() {
        return monitorBytes;
    }

    public void setMonitorBytes(byte[] monitorBytes) {
        this.monitorBytes = monitorBytes;
    }

    public byte[] getSimBytes() {
        return simBytes;
    }

    public void setSimBytes(byte[] simBytes) {
        this.simBytes = simBytes;
    }

    public byte[] getRfidBytes() {
        return rfidBytes;
    }

    public void setRfidBytes(byte[] rfidBytes) {
        this.rfidBytes = rfidBytes;
    }

    public String getMonitor() {
        return monitor.get();
    }

    public SimpleStringProperty monitorProperty() {
        return monitor;
    }

    public String getImei() {
        return imei.get();
    }

    public SimpleStringProperty imeiProperty() {
        return imei;
    }

    public String getSim() {
        return sim.get();
    }

    public SimpleStringProperty simProperty() {
        return sim;
    }

    public String getRfid() {
        return rfid.get();
    }

    public SimpleStringProperty rfidProperty() {
        return rfid;
    }

    public String getRelay() {
        return relay.get();
    }

    public SimpleStringProperty relayProperty() {
        return relay;
    }
}
