package com.pandaism.serializer.fxml;

import com.pandaism.serializer.controller.DataSheetController;
import com.pandaism.serializer.controller.inputs.InputPanes;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        data_table.getColumns().addAll(columns);
        dataSheetController.getData_table().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        inputPanes.setTable(dataSheetController.getData_table());

        dataSheetController.getInput_pane().getChildren().add(anchorPane);

        this.setContent(dataSheet);
    }

    @Override
    public EventHandler<Event> getOnCloseRequest() {
        return super.getOnCloseRequest();
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
