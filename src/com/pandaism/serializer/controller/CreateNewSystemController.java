package com.pandaism.serializer.controller;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateNewSystemController {
    public ComboBox<Object> fleetmind_unit_dropdown;
    public ComboBox<Object> mvi_unit_dropdown;
    public ComboBox<Object> it_unit_dropdown;
    public TabPane tabpane;
    public TextField fleet_sale_order;
    public TextField fleet_customer;
    public TextField mvi_sale_order;
    public TextField mvi_customer;
    public TextField it_sales_order;
    public TextField it_customer;
    public TextField spare_sales_order;
    public TextField spare_customer;

    private Object[] fleetmindSystems = {"DVR", "M1", "G1", "SSV9", new Separator(), "Fleetmind Cameras", new Separator(), "Fleetmind Custom"};
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

    private Object[] mviSystems = {"Flashback In-Car", "Flashback Interview Room", new Separator(), "BWX-100", new Separator(), "MVI Custom"};
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

    private Object[] itSystems = {"Server", "AP-AC-OUT", new Separator(), "IT Custom"};
    private List<TableColumn<Server, String>> serverCols = Collections.singletonList(setCellValueFactory("Server SN", "cpu_serial"));
    private List<TableColumn<AP, String>> apCols = Arrays.asList(setCellValueFactory("AP SN", "cpu_serial"), setCellValueFactory("Antenna SN", "antenna_serial"));

    private Stage mainStage;
    private TabPane dataSheetPane;
    private Menu edit_menu;

    public void initialize() {
        this.fleetmind_unit_dropdown.getItems().addAll(this.fleetmindSystems);
        this.mvi_unit_dropdown.getItems().addAll(this.mviSystems);
        this.it_unit_dropdown.getItems().addAll(this.itSystems);

        this.fleetmind_unit_dropdown.getSelectionModel().selectFirst();
        this.mvi_unit_dropdown.getSelectionModel().selectFirst();
        this.it_unit_dropdown.getSelectionModel().selectFirst();

    }

    private <T> TableColumn<T, String> setCellValueFactory(String name, String data) {
        TableColumn<T, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(data));

        return column;
    }

    private Tab createDataTab(String salesOrder, String company, Object unit) throws IOException {
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

    private <T extends Singular> SystemTab getTab(FXMLLoader loader, String salesOrder, String company, Object unit, List<TableColumn<T, String>> columns) throws IOException {
        SystemTab tab = new SystemTab<>(company, unit, salesOrder, columns, loader.load(), loader.getController());

        if(this.edit_menu.isDisable()) {
            this.edit_menu.disableProperty().set(false);
        }

        close();
        return tab;
    }

    public void create_data_sheet() throws IOException {
        String company = this.tabpane.getSelectionModel().getSelectedItem().getText();
        switch (company) {
            case "Fleetmind":
                this.dataSheetPane.getTabs().add(createDataTab(this.fleet_sale_order.getText(), "[" + company + "] " + this.fleet_customer.getText(), this.fleetmind_unit_dropdown.getSelectionModel().getSelectedItem()));
                break;
            case "MVI":
                this.dataSheetPane.getTabs().add(createDataTab(this.mvi_sale_order.getText(), "[" + company + "] " + this.mvi_customer.getText(), this.mvi_unit_dropdown.getSelectionModel().getSelectedItem()));
                break;
            case "IT Production":
                this.dataSheetPane.getTabs().add(createDataTab(this.it_sales_order.getText(), "[" + company + "] " + this.it_customer.getText(), this.it_unit_dropdown.getSelectionModel().getSelectedItem()));
                break;
            case "Spare Production":
                this.dataSheetPane.getTabs().add(createDataTab(this.spare_sales_order.getText(), "[" + company + "] " + this.spare_customer.getText()));
                break;
        }
    }

    private Tab createDataTab(String salesOrder, String company) throws IOException {
        return createDataTab(salesOrder, company, null);
    }

    public void close() {
        this.mainStage.close();
    }

    void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    void setDataSheetPane(TabPane dataSheetPane) {
        this.dataSheetPane = dataSheetPane;
    }

    void setEdit_menu(Menu edit_menu) {
        this.edit_menu = edit_menu;
    }

}
