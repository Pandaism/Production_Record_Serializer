package com.pandaism.serializer.controller.units.it;

import com.pandaism.serializer.controller.units.Singular;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represent an IT Production Server
 */
public class Server extends Singular {
    public Server(SimpleStringProperty cpu_serial) {
        super(cpu_serial);
    }
}
