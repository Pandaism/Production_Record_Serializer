package com.pandaism.serializer.controller.units.mvi;

import com.pandaism.serializer.controller.units.Singular;
import com.pandaism.serializer.thread.URLConnectionThread;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represent a MVI Flashback Interview Room Unit
 */
public class Interview extends Singular {
    public SimpleStringProperty id;
    public SimpleStringProperty door_rev;
    public SimpleStringProperty software_rev;
    public SimpleStringProperty supplier_sn;
    public SimpleStringProperty sd_card;

    public byte[] sd_cardBytes;

    public Interview(SimpleStringProperty cpu_serial, SimpleStringProperty id, SimpleStringProperty door_rev, SimpleStringProperty software_rev, SimpleStringProperty supplier_sn, SimpleStringProperty sd_card) {
        super(cpu_serial);
        this.id = id;
        this.door_rev = door_rev;
        this.software_rev = software_rev;
        this.supplier_sn = supplier_sn;
        this.sd_card = sd_card;

        super.service.execute(new URLConnectionThread<>(this));
        super.service.shutdown();
    }

    public byte[] getSd_cardBytes() {
        return sd_cardBytes;
    }

    public void setSd_cardBytes(byte[] sd_cardBytes) {
        this.sd_cardBytes = sd_cardBytes;
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
}
