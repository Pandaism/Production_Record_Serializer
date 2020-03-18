package com.pandaism.serializer.controller.units.fleetmind;

import com.pandaism.serializer.controller.units.Singular;
import javafx.beans.property.SimpleStringProperty;

public class DVR extends Singular {
    private SimpleStringProperty monitor;
    private SimpleStringProperty imei;
    private SimpleStringProperty sim;
    private SimpleStringProperty rfid;
    private SimpleStringProperty relay;

    public DVR(SimpleStringProperty cpu_serial, SimpleStringProperty monitor, SimpleStringProperty imei, SimpleStringProperty sim, SimpleStringProperty rfid, SimpleStringProperty relay) {
        super(cpu_serial);
        this.monitor = monitor;
        this.imei = imei;
        this.sim = sim;
        this.rfid = rfid;
        this.relay = relay;
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
