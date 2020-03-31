package com.pandaism.serializer.controller.units.mvi;

import com.pandaism.serializer.controller.units.Singular;
import com.pandaism.serializer.thread.URLConnectionThread;
import javafx.beans.property.SimpleStringProperty;
/**
 * Represent a MVI BWX Unit
 */
public class BWX extends Singular {
    public SimpleStringProperty docking_station;

    public byte[] docking_stationBytes;

    public BWX(SimpleStringProperty cpu_serial, SimpleStringProperty docking_station) {
        super(cpu_serial);
        this.docking_station = docking_station;

        super.service.execute(new URLConnectionThread<>(this));
        super.service.shutdown();
    }

    public byte[] getDocking_stationBytes() {
        return docking_stationBytes;
    }

    public void setDocking_stationBytes(byte[] docking_stationBytes) {
        this.docking_stationBytes = docking_stationBytes;
    }

    public String getDocking_station() {
        return docking_station.get();
    }

    public SimpleStringProperty docking_stationProperty() {
        return docking_station;
    }
}
