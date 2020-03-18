package com.pandaism.serializer.controller.units.fleetmind;

import com.pandaism.serializer.controller.units.Singular;
import javafx.beans.property.SimpleStringProperty;

public class Tablets extends Singular {
    private SimpleStringProperty imei;
    private SimpleStringProperty sim;
    private SimpleStringProperty docking_station;

    public Tablets(SimpleStringProperty cpu_serial, SimpleStringProperty imei, SimpleStringProperty sim, SimpleStringProperty docking_station) {
        super(cpu_serial);
        this.imei = imei;
        this.sim = sim;
        this.docking_station = docking_station;
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
