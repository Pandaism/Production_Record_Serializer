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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ApplicationController {
    public TabPane data_sheet_tab_pane;
    public Menu edit_menu;

    public void create_new(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/create_new_system.fxml"));
        Parent root = loader.load();
        Stage createNewStage = new Stage();

        CreateNewSystemController createNewSystemController = loader.getController();
        createNewSystemController.setMainStage(createNewStage);
        createNewSystemController.setDataSheetPane(this.data_sheet_tab_pane);
        createNewSystemController.setEdit_menu(this.edit_menu);

        createNewStage.setScene(new Scene(root, 640, 180));
        createNewStage.show();
    }

    public void open(ActionEvent actionEvent) {

    }

    public void open_saveas(ActionEvent actionEvent) {

    }

    public void quit(ActionEvent actionEvent) {

    }

    public void export(ActionEvent actionEvent) throws IOException {
        export(((SystemTab) this.data_sheet_tab_pane.getSelectionModel().getSelectedItem()));
    }

    public void export_all(ActionEvent actionEvent) {

    }

    private void export(SystemTab tab) throws IOException {
        if(tab != null) {
            String company = tab.getTitle().substring(1, tab.getTitle().indexOf(']'));
            String customer = tab.getTitle().substring(tab.getTitle().indexOf(']') + 2);

            File companyFolder = new File("./records/" + company + "/");
            File customerFolder = new File("./records/" + company + "/" + customer + "/");
            File unitFolder = new File("./records/" + company + "/" + customer + "/" + tab.getUnit() + "/");

            File xlsxFile = new File("./records/" + company + "/" + customer + "/" + tab.getUnit() + "/SO" + tab.getSaleOrder() + ".xlsx");
            File csvFile = new File("./records/" + company + "/" + customer + "/" + tab.getUnit() + "/SO" + tab.getSaleOrder() + ".csv");

            if (!companyFolder.exists()) {
                if (companyFolder.mkdirs()) {
                    new Alert(Alert.AlertType.INFORMATION, "Company Folder: " + company + " created...", ButtonType.OK).show();
                }
            }

            if (!customerFolder.exists()) {
                if (customerFolder.mkdirs()) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Folder: " + customer + " created...", ButtonType.OK).show();
                }
            }

            if (!unitFolder.exists()) {
                if (unitFolder.mkdirs()) {
                    new Alert(Alert.AlertType.INFORMATION, "Unit Folder: " + tab.getUnit() + " created...", ButtonType.OK).show();
                }
            }

            createExcel(tab, xlsxFile);
            createCSV(tab, csvFile);
        }
    }

    private void createCSV(SystemTab tab, File csvFile) {

    }

    private void createExcel(SystemTab tab, File xlsxFile) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet datasheet = workbook.createSheet("Data_Sheet");

        Row header = datasheet.createRow(0);
        ObservableList<TableColumn> columns = tab.getData_table().getColumns();
        header.createCell(0).setCellValue("Kit");
        header.createCell(1).setCellValue("Truck");

        for(int col = 0; col < columns.size(); col++) {
            Cell cell = header.createCell(col + 2);
            cell.setCellValue(columns.get(col).getText());
            datasheet.autoSizeColumn(col + 2);
        }

        data(tab, datasheet, xlsxFile);
    }

    private void data(SystemTab tab, Sheet datasheet, File xlsxFile) throws IOException {
        TableView data_table = tab.getData_table();
        ObservableList items = data_table.getItems();

        switch (tab.getUnit()) {
            case "DVR":

                for(int item = 0; item < items.size(); item++) {
                    DVR dvr = (DVR) items.get(item);

                    Row serialRow  = datasheet.createRow(1 + (item * 2));
                    Row barcodeRow  = datasheet.createRow(serialRow.getRowNum() + 1);
                    barcodeRow.setHeight((short) 600);

                    serialRow.createCell(0).setCellValue(item + 1);

                    serialRow.createCell(2).setCellValue(dvr.getMonitor());
                    getBarcode(barcodeRow, datasheet, dvr.getMonitor(), 2, 206, 50);

                    serialRow.createCell(3).setCellValue(dvr.getCpu_serial());
                    getBarcode(barcodeRow, datasheet, dvr.getCpu_serial(), 3, 206, 50);

                    serialRow.createCell(4).setCellValue(dvr.getImei());

                    serialRow.createCell(5).setCellValue(dvr.getSim());
                    getBarcode(barcodeRow, datasheet, dvr.getSim(), 5, 301, 50);

                    serialRow.createCell(6).setCellValue(dvr.getRfid());
                    getBarcode(barcodeRow, datasheet, dvr.getSim(), 6, 167, 50);

                    serialRow.createCell(7).setCellValue(dvr.getRelay());

                }

                datasheet.setColumnWidth(2, 256 * 30);
                datasheet.setColumnWidth(3, 256 * 30);
                datasheet.setColumnWidth(4, 256 * 16);
                datasheet.setColumnWidth(5, 256 * 40);
                datasheet.setColumnWidth(6, 256 * 30);
                break;
            case "M1": case "G1":
                System.out.println("Tablets: " + tab.getUnit());
                break;
            case "SSV9": case "Fleetmind Cameras": case "Fleetmind Custom": case "MVI Custom": case "IT Custom":
                System.out.println("Singular" + tab.getUnit());
                break;
            case "Flashback In-Car":
                System.out.println("Flashback In-Car");
                break;
            case "Flashback Interview Room":
                System.out.println("Flashback Interview Room");
                break;
            case "BWX-100":
                System.out.println("BWX-100");
                break;
            case "Server":
                System.out.println("Server");
                break;
            case "AP-AC-OUT":
                System.out.println("AP-AC-OUT");
                break;
        }

        if(!xlsxFile.exists()) {
            if(xlsxFile.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(xlsxFile);
                datasheet.getWorkbook().write(outputStream);
                outputStream.close();
                datasheet.getWorkbook().close();

                new Alert(Alert.AlertType.INFORMATION, xlsxFile.getPath() + " created...", ButtonType.OK).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "This file already exist...", ButtonType.OK).show();
        }
    }

    private void getBarcode(Row barcodeRow, Sheet datasheet, String input, int c1, int width, int height) throws IOException {
        URLConnection connection = new URL("https://www.barcodesinc.com/generator/image.php?code=" + input + "&style=197&type=C128B&width=" + width + "&height=" + height + "&xres=1&font=3").openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        InputStream monitorStream = connection.getInputStream();
        byte[] monitorBytes = IOUtils.toByteArray(monitorStream);
        int monitorIdx = datasheet.getWorkbook().addPicture(monitorBytes, Workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = datasheet.getWorkbook().getCreationHelper();
        Drawing drawing = datasheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();

        anchor.setCol1(c1);
        anchor.setRow1(barcodeRow.getRowNum());
        anchor.setCol2(c1 + 1);
        anchor.setRow2(barcodeRow.getRowNum() + 1);

        drawing.createPicture(anchor, monitorIdx);
        barcodeRow.createCell(2);
    }
}
