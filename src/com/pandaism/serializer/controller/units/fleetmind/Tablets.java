package com.pandaism.serializer.controller.units.fleetmind;

import com.pandaism.serializer.controller.units.Singular;
import com.pandaism.serializer.thread.URLConnectionThread;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a Fleetmind Tablet unit (G1 and M1)
 */
public class Tablets extends Singular {
    public SimpleStringProperty imei;
    public SimpleStringProperty sim;
    public SimpleStringProperty docking_station;

    public byte[] simBytes;
    public byte[] docking_stationBytes;

    public Tablets(SimpleStringProperty cpu_serial, SimpleStringProperty imei, SimpleStringProperty sim, SimpleStringProperty docking_station) {
        super(cpu_serial);
        this.imei = imei;
        this.sim = sim;
        this.docking_station = docking_station;

        super.service.execute(new URLConnectionThread<>(this));
        super.service.shutdown();
    }

    public byte[] getSimBytes() {
        return simBytes;
    }

    public void setSimBytes(byte[] simBytes) {
        this.simBytes = simBytes;
    }

    public byte[] getDocking_stationBytes() {
        return docking_stationBytes;
    }

    public void setDocking_stationBytes(byte[] docking_stationBytes) {
        this.docking_stationBytes = docking_stationBytes;
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

    public String getDocking_station() {
        return docking_station.get();
    }

    public SimpleStringProperty docking_stationProperty() {
        return docking_station;
    }
}
