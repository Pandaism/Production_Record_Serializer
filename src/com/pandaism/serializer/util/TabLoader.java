package com.pandaism.serializer.util;

import com.pandaism.serializer.controller.units.Singular;
import com.pandaism.serializer.controller.units.fleetmind.DVR;
import com.pandaism.serializer.controller.units.fleetmind.Tablets;
import com.pandaism.serializer.controller.units.it.AP;
import com.pandaism.serializer.controller.units.it.Server;
import com.pandaism.serializer.controller.units.mvi.BWX;
import com.pandaism.serializer.controller.units.mvi.InCar;
import com.pandaism.serializer.controller.units.mvi.Interview;
import com.pandaism.serializer.fxml.SystemTab;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabLoader {
    private List<TableColumn<DVR, String>> dvrCols = Arrays.asList(
            setCellValueFactory("Monitor Serial", "monitor"),
            setCellValueFactory("CPU Serial", "cpu_serial"),
            setCellValueFactory("IMEI", "imei"),
            setCellValueFactory("SIM", "sim"),
            setCellValueFactory("RFID", "rfid"),
            setCellValueFactory("Relay", "relay")
    );
    private List<TableColumn<Tablets, String>> tabletCols = Arrays.asList(
            setCellValueFactory("Tablet Serial", "cpu_serial"),
            setCellValueFactory("IMEI", "imei"),
            setCellValueFactory("SIM", "sim"),
            setCellValueFactory("Docking Station", "docking_station")
    );
    private List<TableColumn<Singular, String>> serialCols = Collections.singletonList(
            setCellValueFactory("Serial Number", "cpu_serial")
    );

    private List<TableColumn<InCar, String>> incarCols = Arrays.asList(
            setCellValueFactory("FB Serial", "cpu_serial"),
            setCellValueFactory("Door Rev", "door_rev"),
            setCellValueFactory("Software Rev", "software_rev"),
            setCellValueFactory("Supplier SN", "supplier_sn"),
            setCellValueFactory("SD Card", "sd_card"),
            setCellValueFactory("Assigned IP", "assigned_ip")
    );
    private List<TableColumn<Interview, String>> interviewCols = Arrays.asList(
            setCellValueFactory("FB Serial", "cpu_serial"),
            setCellValueFactory("Door Rev", "door_rev"),
            setCellValueFactory("Software Rev", "software_rev"),
            setCellValueFactory("Supplier SN", "supplier_sn"),
            setCellValueFactory("SD Card", "sd_card")
    );
    private List<TableColumn<BWX, String>> bwxCols = Arrays.asList(
            setCellValueFactory("BWX Serial", "cpu_serial"),
            setCellValueFactory("Docking Station", "docking_station")
    );

    private List<TableColumn<Server, String>> serverCols = Collections.singletonList(setCellValueFactory("Server SN", "cpu_serial"));
    private List<TableColumn<AP, String>> apCols = Arrays.asList(setCellValueFactory("AP SN", "cpu_serial"), setCellValueFactory("Antenna SN", "antenna_serial"));

    private <T> TableColumn<T, String> setCellValueFactory(String name, String data) {
        TableColumn<T, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(data));

        return column;
    }

    public Tab createDataTab(String salesOrder, String company, Object unit) throws IOException {
        FXMLLoader loader;
        SystemTab systemTab = null;

        switch (unit.toString()) {
            case "DVR":
                List<TableColumn<DVR, String>> dvrColumns = this.dvrCols;
                loader = new FXMLLoader(getClass().getResource("../fxml/fleetmind_dvr_template.fxml"));

                systemTab = getTab(loader, salesOrder, company,unit, dvrColumns);
                break;
            case "M1": case "G1":
                List<TableColumn<Tablets, String>> tableColumns = this.tabletCols;
                loader = new FXMLLoader(getClass().getResource("../fxml/fleetmind_tablet_template.fxml"));

                systemTab = getTab(loader, salesOrder, company,unit, tableColumns);
                break;
            case "SSV9": case "Fleetmind Cameras": case "Fleetmind Custom": case "MVI Custom": case "IT Custom":
                List<TableColumn<Singular, String>> singleColumns = this.serialCols;
                loader = new FXMLLoader(getClass().getResource("../fxml/singluar_serial_template.fxml"));

                systemTab =  getTab(loader, salesOrder, company,unit, singleColumns);
                break;
            case "Flashback In-Car":
                List<TableColumn<InCar, String>> incarColumns = this.incarCols;
                loader = new FXMLLoader(getClass().getResource("../fxml/mvi_fbincar_template.fxml"));

                systemTab =  getTab(loader, salesOrder, company,unit, incarColumns);
                break;
            case "Flashback Interview Room":
                List<TableColumn<Interview, String>> interviewColumns = this.interviewCols;
                loader = new FXMLLoader(getClass().getResource("../fxml/mvi_fbintr_template.fxml"));

                systemTab =  getTab(loader, salesOrder, company,unit, interviewColumns);
                break;
            case "BWX-100":
                List<TableColumn<BWX, String>> bwxColumns = this.bwxCols;
                loader = new FXMLLoader(getClass().getResource("../fxml/mvi_bwx_template.fxml"));

                systemTab =  getTab(loader, salesOrder, company,unit, bwxColumns);
                break;
            case "Server":
                List<TableColumn<Server, String>> serverColumns = this.serverCols;
                loader = new FXMLLoader(getClass().getResource("../fxml/it_server_template.fxml"));

                systemTab =  getTab(loader, salesOrder, company,unit, serverColumns);
                break;
            case "AP-AC-OUT":
                List<TableColumn<AP, String>> apColumns = this.apCols;
                loader = new FXMLLoader(getClass().getResource("../fxml/it_ap_template.fxml"));

                systemTab =  getTab(loader, salesOrder, company,unit, apColumns);
                break;
        }

        return systemTab;
    }

    public Tab createDataTab(String salesOrder, String company) throws IOException {
        return createDataTab(salesOrder, company, null);
    }

    private <T extends Singular> SystemTab getTab(FXMLLoader loader, String salesOrder, String company, Object unit, List<TableColumn<T, String>> columns) throws IOException {
        return new SystemTab<>(company, unit, salesOrder, columns, loader.load(), loader.getController());
    }
}
