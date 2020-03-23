package com.pandaism.serializer.fxml;

import com.pandaism.serializer.controller.DataSheetController;
import com.pandaism.serializer.controller.inputs.InputPanes;
import com.pandaism.serializer.controller.units.fleetmind.DVR;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class SystemTab<T> extends Tab {
    private String customer;
    private String unit;
    private String saleOrder;

    public SystemTab(String customer, Object unit, String saleOrder, List<TableColumn<T, String>> columns, AnchorPane anchorPane, InputPanes inputPanes) throws IOException {
        super("SO#" + saleOrder + " " + customer);
        this.customer = customer;
        this.unit = unit.toString();
        this.saleOrder = saleOrder;

        this.closableProperty().set(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./data_sheet.fxml"));
        Node dataSheet = loader.load();
        DataSheetController<T> dataSheetController = loader.getController();
        TableView<T> data_table = dataSheetController.getData_table();

        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Remove Entry...");
        MenuItem editItem = new MenuItem("Edit Entry...");

        removeItem.addEventHandler(ActionEvent.ACTION, event -> {
            T selection = data_table.getSelectionModel().getSelectedItem();

            if(selection instanceof DVR) {

            }

            if(selection != null) {
                data_table.getItems().remove(selection);
                inputPanes.getStatus_right().textProperty().set("Number of records: " + data_table.getItems().size());
            }
        });

        editItem.addEventHandler(ActionEvent.ACTION, event -> {
            T selection = data_table.getSelectionModel().getSelectedItem();
            System.out.println(selection);
            if(selection != null) {
                data_table.getItems().replaceAll(selected -> {

                    return null;
                });
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

        contextMenu.getItems().add(removeItem);
        contextMenu.getItems().add(editItem);

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
}
