package com.pandaism.serializer.fxml;

import com.pandaism.serializer.controller.DataSheetController;
import com.pandaism.serializer.controller.inputs.InputPanes;
import com.pandaism.serializer.controller.units.Singular;
import com.pandaism.serializer.controller.units.fleetmind.DVR;
import com.pandaism.serializer.controller.units.fleetmind.Tablets;
import com.pandaism.serializer.controller.units.it.AP;
import com.pandaism.serializer.controller.units.mvi.BWX;
import com.pandaism.serializer.controller.units.mvi.InCar;
import com.pandaism.serializer.controller.units.mvi.Interview;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class SystemTab<T> extends Tab {
    private String customer;
    private String unit;
    private String saleOrder;
    private TableView<T> data_table;

    public SystemTab(String customer, Object unit, String saleOrder, List<TableColumn<T, String>> columns, AnchorPane anchorPane, InputPanes inputPanes) throws IOException {
        super("SO#" + saleOrder + " " + customer);
        this.customer = customer;
        this.unit = unit.toString();
        this.saleOrder = saleOrder;

        this.closableProperty().set(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./data_sheet.fxml"));
        Node dataSheet = loader.load();
        DataSheetController<T> dataSheetController = loader.getController();
        this.data_table = dataSheetController.getData_table();

        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Remove Entry...");

        removeItem.addEventHandler(ActionEvent.ACTION, event -> {
            T selection = data_table.getSelectionModel().getSelectedItem();

            if(selection != null) {
                data_table.getItems().remove(selection);
                inputPanes.getStatus_right().textProperty().set("Number of records: " + data_table.getItems().size());
            }
        });

        data_table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(data_table, event.getScreenX(), event.getScreenY());
            } else {
                if (contextMenu.isShowing()) {
                    contextMenu.hide();
                }
            }
        });

        columns.forEach(column -> {
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            column.setOnEditCommit(event -> {
                T selected = event.getRowValue();

                if(selected instanceof DVR) {
                    switch (event.getTableColumn().getText()) {
                        case "Monitor Serial":
                            ((DVR) selected).monitorProperty().set(event.getNewValue());
                            break;
                        case "IMEI":
                            ((DVR) selected).imeiProperty().set(event.getNewValue());
                            break;
                        case "SIM":
                            ((DVR) selected).simProperty().set(event.getNewValue());
                            break;
                        case "RFID":
                            ((DVR) selected).rfidProperty().set(event.getNewValue());
                            break;
                        case "Relay":
                            ((DVR) selected).relayProperty().set(event.getNewValue());
                            break;
                    }
                }

                if(selected instanceof Tablets) {
                    switch (event.getTableColumn().getText()) {
                        case "IMEI":
                            ((Tablets) selected).imeiProperty().set(event.getNewValue());
                            break;
                        case "SIM":
                            ((Tablets) selected).simProperty().set(event.getNewValue());
                            break;
                        case "Docking Station":
                            ((Tablets) selected).docking_stationProperty().set(event.getNewValue());
                            break;
                    }
                }

                if(selected instanceof InCar) {
                    switch (event.getTableColumn().getText()) {
                        case "Door Rev":
                            ((InCar) selected).door_revProperty().set(event.getNewValue());
                            break;
                        case "Software Rev":
                            ((InCar) selected).software_revProperty().set(event.getNewValue());
                            break;
                        case "Supplier SN":
                            ((InCar) selected).supplier_snProperty().set(event.getNewValue());
                            break;
                        case "SD Card":
                            ((InCar) selected).sd_cardProperty().set(event.getNewValue());
                            break;
                        case "Assigned IP":
                            ((InCar) selected).assigned_ipProperty().set(event.getNewValue());
                            break;
                    }
                }

                if(selected instanceof Interview) {
                    switch (event.getTableColumn().getText()) {
                        case "Door Rev":
                            ((Interview) selected).door_revProperty().set(event.getNewValue());
                            break;
                        case "Software Rev":
                            ((Interview) selected).software_revProperty().set(event.getNewValue());
                            break;
                        case "Supplier SN":
                            ((Interview) selected).supplier_snProperty().set(event.getNewValue());
                            break;
                        case "SD Card":
                            ((Interview) selected).sd_cardProperty().set(event.getNewValue());
                            break;
                    }
                }

                if(selected instanceof BWX) {
                    if ("Antenna SN".equals(event.getTableColumn().getText())) {
                        ((BWX) selected).docking_stationProperty().set(event.getNewValue());
                    }
                }

                if(selected instanceof AP) {
                    if ("Antenna SN".equals(event.getTableColumn().getText())) {
                        ((AP) selected).antenna_serialProperty().set(event.getNewValue());
                    }
                }

                if(selected instanceof Singular) {
                    if(event.getTableColumn().getText().equals("CPU Serial") ||
                            event.getTableColumn().getText().equals("Tablet Serial") ||
                            event.getTableColumn().getText().equals("Serial Number") ||
                            event.getTableColumn().getText().equals("FB Serial") ||
                            event.getTableColumn().getText().equals("BWX Serial") ||
                            event.getTableColumn().getText().equals("Server SN") ||
                            event.getTableColumn().getText().equals("AP SN")) {
                        ((Singular)selected).cpu_serial.set(event.getNewValue());
                    }
                }

                data_table.getItems().remove(event.getTablePosition().getRow());
                data_table.getItems().add(event.getTablePosition().getRow(), selected);
            });
        });

        contextMenu.getItems().add(removeItem);

        data_table.getColumns().addAll(columns);
        dataSheetController.getData_table().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        inputPanes.setTable(dataSheetController.getData_table());
        inputPanes.setStatus_left(dataSheetController.status_left);
        inputPanes.setStatus_right(dataSheetController.status_right);

        dataSheetController.getInput_pane().getChildren().add(anchorPane);

        this.setContent(dataSheet);
    }

    public String getCustomer() {
        return customer;
    }

    public String getUnit() {
        return unit;
    }

    public String getSaleOrder() {
        return saleOrder;
    }

    public TableView<T> getData_table() {
        return data_table;
    }
}
