package com.pandaism.serializer.controller;

import com.pandaism.serializer.util.TabLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Object[] mviSystems = {"Flashback In-Car", "Flashback Interview Room", new Separator(), "BWX-100", new Separator(), "MVI Custom"};
    private Object[] itSystems = {"Server", "AP-AC-OUT", new Separator(), "IT Custom"};

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

    public void create_data_sheet() throws IOException {
        String company = this.tabpane.getSelectionModel().getSelectedItem().getText();
        TabLoader tabLoader = new TabLoader();

        switch (company) {
            case "Fleetmind":
                this.dataSheetPane.getTabs().add(tabLoader.createDataTab(this.fleet_sale_order.getText(), "[" + company + "] " + this.fleet_customer.getText(), this.fleetmind_unit_dropdown.getSelectionModel().getSelectedItem()));
                break;
            case "MVI":
                this.dataSheetPane.getTabs().add(tabLoader.createDataTab(this.mvi_sale_order.getText(), "[" + company + "] " + this.mvi_customer.getText(), this.mvi_unit_dropdown.getSelectionModel().getSelectedItem()));
                break;
            case "IT Production":
                this.dataSheetPane.getTabs().add(tabLoader.createDataTab(this.it_sales_order.getText(), "[" + company + "] " + this.it_customer.getText(), this.it_unit_dropdown.getSelectionModel().getSelectedItem()));
                break;
            case "Spare Production":
                this.dataSheetPane.getTabs().add(tabLoader.createDataTab(this.spare_sales_order.getText(), "[" + company + "] " + this.spare_customer.getText()));
                break;
        }

        if(this.edit_menu.isDisable()) {
            this.edit_menu.disableProperty().set(false);
        }
        close();
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
