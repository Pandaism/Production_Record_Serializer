package com.pandaism.serializer.controller.units.mvi;

import com.pandaism.serializer.controller.units.Singular;
import javafx.beans.property.SimpleStringProperty;

public class InCar extends Singular {
    private SimpleStringProperty id;
    private SimpleStringProperty door_rev;
    private SimpleStringProperty software_rev;
    private SimpleStringProperty supplier_sn;
    private SimpleStringProperty sd_card;
    private SimpleStringProperty assigned_ip;

    public InCar(SimpleStringProperty cpu_serial, SimpleStringProperty id, SimpleStringProperty door_rev, SimpleStringProperty software_rev, SimpleStringProperty supplier_sn, SimpleStringProperty sd_card, SimpleStringProperty assigned_ip) {
        super(cpu_serial);

        this.id = id;
        this.door_rev = door_rev;
        this.software_rev = software_rev;
        this.supplier_sn = supplier_sn;
        this.sd_card = sd_card;
        this.assigned_ip = assigned_ip;
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getDoor_rev() {
        return door_rev.get();
    }

    public SimpleStringProperty door_revProperty() {
        return door_rev;
    }

    public String getSoftware_rev() {
        return software_rev.get();
    }

    public SimpleStringProperty software_revProperty() {
        return software_rev;
    }

    public String getSupplier_sn() {
        return supplier_sn.get();
    }

    public SimpleStringProperty supplier_snProperty() {
        return supplier_sn;
    }

    public String getSd_card() {
        return sd_card.get();
    }

    public SimpleStringProperty sd_cardProperty() {
        return sd_card;
    }

    public String getAssigned_ip() {
        return assigned_ip.get();
    }

    public SimpleStringProperty assigned_ipProperty() {
        return assigned_ip;
    }
}
