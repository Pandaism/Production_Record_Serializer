package com.pandaism.serializer.controller.units.mvi;

import com.pandaism.serializer.controller.units.Singular;
import javafx.beans.property.SimpleStringProperty;

public class BWX extends Singular {
    private SimpleStringProperty docking_station;

    public BWX(SimpleStringProperty cpu_serial, SimpleStringProperty docking_station) {
        super(cpu_serial);
        this.docking_station = docking_station;
    }

    public String getDocking_station() {
        return docking_station.get();
    }

    public SimpleStringProperty docking_stationProperty() {
        return docking_station;
    }
}
