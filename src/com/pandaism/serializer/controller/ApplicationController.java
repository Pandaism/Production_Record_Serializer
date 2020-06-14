package com.pandaism.serializer.controller;

import com.pandaism.serializer.Main;
import com.pandaism.serializer.controller.units.Singular;
import com.pandaism.serializer.controller.units.fleetmind.DVR;
import com.pandaism.serializer.controller.units.fleetmind.Tablets;
import com.pandaism.serializer.controller.units.it.AP;
import com.pandaism.serializer.controller.units.it.Server;
import com.pandaism.serializer.controller.units.mvi.BWX;
import com.pandaism.serializer.controller.units.mvi.InCar;
import com.pandaism.serializer.controller.units.mvi.Interview;
import com.pandaism.serializer.fxml.SystemTab;
import com.pandaism.serializer.util.TabLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationController {
    public TabPane data_sheet_tab_pane;
    public Menu edit_menu;

    /**
     * Handles creating frame for new orders
     *
     * @throws IOException
     */
    public void create_new() throws IOException {
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

    public void _import() {
        FileChooser fileChooser = new FileChooser();
        File recordsFolder = new File("./records/");
        if(recordsFolder.exists()) fileChooser.setInitialDirectory(recordsFolder);

        Map<File, String> errorFiles = new HashMap<>();

        List<File> files = fileChooser.showOpenMultipleDialog(null);
        if(files != null) {
            files.forEach(file -> {
                if(file != null) {
                    String extension = file.getName().substring(file.getName().indexOf('.') + 1);
                    if(extension.equals("xlsx")) {

                    } else if(extension.equals("csv")) {
                        try {
                            importCSV(file);
                        } catch (IOException e) {
                            errorFiles.put(file, e.getMessage());
                        }
                    } else {
                        errorFiles.put(file, "Invalid file type");
                    }
                }
            });

            if(errorFiles.size() > 0) {
                StringBuilder error = new StringBuilder("The following files cannot be imported:\n");

                for(File file : errorFiles.keySet()) {
                    error.append(file.getPath()).append(" : ").append(errorFiles.get(file));
                }

                new Alert(Alert.AlertType.ERROR, error.toString(), ButtonType.CLOSE).show();
            }
        }
    }

    private void importCSV(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> datas;

        try(Stream<String> lines = Files.lines(file.toPath())) {
            datas = lines.collect(Collectors.toList());
            datas.remove(0);
            String[] path = file.getPath().split(Pattern.quote(System.getProperty("file.separator")));

            String salesOrder = path[path.length - 1].substring(0, path[path.length - 1].indexOf("."));
            String company = path[path.length - 5];
            String customer = path[path.length - 4];
            String unit = path[path.length - 3];

            TabLoader tabLoader = new TabLoader();

            switch (company) {
                case "IT Production":
                    this.data_sheet_tab_pane.getTabs().add(tabLoader.createDataTab(salesOrder, "[" + company + "] " + customer, unit));
                    break;
                case "MVI":
                    this.data_sheet_tab_pane.getTabs().add(tabLoader.createDataTab(salesOrder, "[" + company + "] " + customer, unit));
                    break;
                case "Fleetmind":
                    SystemTab fleetTab = tabLoader.createDataTab(salesOrder, "[" + company + "] " + customer, unit);
                    this.data_sheet_tab_pane.getTabs().add(fleetTab);

                    for(String data : datas) {
                        String[] line = data.split(",");
                        switch (unit) {
                            case "DVR":
                                fleetTab.getData_table().getItems().add(new DVR(new SimpleStringProperty(line[1]), new SimpleStringProperty(line[0]), new SimpleStringProperty(line[2]), new SimpleStringProperty(line[3]), new SimpleStringProperty(line[4]), new SimpleStringProperty(line[5])));
                                break;
                            case "M1":
                                fleetTab.getData_table().getItems().add(new Tablets(new SimpleStringProperty(line[0]), new SimpleStringProperty(line[1]), new SimpleStringProperty(line[2]), new SimpleStringProperty(line[3])));
                                break;
                            case "G1":
                                break;
                            case "SSV9":
                                break;
                            case "Fleetmind Cameras":
                                break;
                            case "Fleetmind Custom":
                                break;
                        }
                    }
                    break;
                case "Spare Production":
                    this.data_sheet_tab_pane.getTabs().add(tabLoader.createDataTab(salesOrder, "[" + company + "] " + customer));
                    break;
            }
        }
    }

    public void quit() {
        Main.close(this.data_sheet_tab_pane);
    }

    /**
     * Handles exporting tab through menu item
     *
     * @throws IOException
     */
    public void export() throws IOException {
        if(Main.exportable) {
            export(((SystemTab) this.data_sheet_tab_pane.getSelectionModel().getSelectedItem()));
        } else {
            new Alert(Alert.AlertType.ERROR, "Awaiting thread processing...", ButtonType.OK);
        }
    }

    /**
     * Handles exporting all tab through menu item
     *
     * @throws IOException
     */
    public void export_all() throws IOException {
        if(Main.exportable) {
            ObservableList<Tab> tabs = this.data_sheet_tab_pane.getTabs();
            for(Tab tab : tabs) {
                export((SystemTab) tab);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Awaiting thread processing...", ButtonType.OK);
        }
    }

    /**
     * Handles file and folder creation
     *
     * @param tab the targeted tab
     * @throws IOException
     */
    private void export(SystemTab tab) throws IOException {
        if(tab != null) {
            String company = tab.getTitle().substring(1, tab.getTitle().indexOf(']'));
            String customer = tab.getTitle().substring(tab.getTitle().indexOf(']') + 2);

            File xlsxFile = new File("./records/" + company + "/" + customer + "/" + tab.getUnit() + "/SO" + tab.getSaleOrder() + "/" + tab.getSaleOrder() + ".xlsx");
            File csvFile = new File("./records/" + company + "/" + customer + "/" + tab.getUnit() + "/SO" + tab.getSaleOrder() + "/" + tab.getSaleOrder() + ".csv");

            if(!xlsxFile.getParentFile().exists() || !csvFile.getParentFile().exists()) {
                if(xlsxFile.getParentFile().mkdirs()) {
                    new Alert(Alert.AlertType.INFORMATION, "Parent Folder: " + xlsxFile.getParentFile().getPath() + " created...", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Attempt to make parent Folder: " + xlsxFile.getParentFile().getPath() + " failed...", ButtonType.OK).show();
                }
            }

            createExcel(tab, xlsxFile);
            createCSV(tab, csvFile);
        }
    }

    /**
     * Handles Creation for csv record file
     *
     * @param tab the targeted tab
     * @param csvFile the csv file to export to
     * @throws IOException
     */
    private void createCSV(SystemTab tab, File csvFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
        ObservableList items = tab.getData_table().getItems();

        switch (tab.getUnit()) {
            case "DVR":
                writer.write("Monitor Serial,CPU Serial,IMEI,SIM,RFID,Relay\n");
                for (Object item : items) {
                    writer.write(((DVR) item).getMonitor() + ",");
                    writer.write(((DVR) item).getCpu_serial() + ",");
                    writer.write(((DVR) item).getImei() + ",");
                    writer.write(((DVR) item).getSim() + ",");
                    writer.write(((DVR) item).getRfid() + ",");
                    writer.write(((DVR) item).getRelay() + "\n");
                }
                break;
            case "M1":
            case "G1":
                writer.write("Tablet Serial,IMEI,SIM,Docking Station\n");
                for (Object item : items) {
                    writer.write(((Tablets) item).getCpu_serial() + ",");
                    writer.write(((Tablets) item).getImei() + ",");
                    writer.write(((Tablets) item).getSim() + ",");
                    writer.write(((Tablets) item).getDocking_station() + "\n");
                }
                break;
            case "SSV9":
            case "Fleetmind Cameras":
            case "Fleetmind Custom":
            case "MVI Custom":
            case "IT Custom":
                writer.write("Serial Number\n");
                for (Object item : items) {
                    writer.write(((Singular) item).getCpu_serial() + "\n");
                }
                break;
            case "Flashback In-Car":
                writer.write("FB Serial,Door Rev,Software Rev,Supplier SN,SD Card, Assigned IP\n");
                for (Object item : items) {
                    writer.write(((InCar) item).getCpu_serial() + ",");
                    writer.write(((InCar) item).getDoor_rev() + ",");
                    writer.write(((InCar) item).getSoftware_rev() + ",");
                    writer.write(((InCar) item).getSupplier_sn() + "\n");
                    writer.write(((InCar) item).getSd_card() + "\n");
                    writer.write(((InCar) item).getAssigned_ip() + "\n");
                }
                break;
            case "Flashback Interview Room":
                writer.write("FB Serial,Door Rev,Software Rev,Supplier SN,SD Card\n");
                for (Object item : items) {
                    writer.write(((Interview) item).getCpu_serial() + ",");
                    writer.write(((Interview) item).getDoor_rev() + ",");
                    writer.write(((Interview) item).getSoftware_rev() + ",");
                    writer.write(((Interview) item).getSupplier_sn() + "\n");
                    writer.write(((Interview) item).getSd_card() + "\n");
                }
                break;
            case "BWX-100":
                writer.write("BWX Serial, Docking Station\n");
                for (Object item : items) {
                    writer.write(((BWX) item).getCpu_serial() + "\n");
                    writer.write(((BWX) item).getDocking_station() + "\n");
                }
                break;
            case "Server":
                writer.write("Server SN\n");
                for (Object item : items) {
                    writer.write(((Singular) item).getCpu_serial() + "\n");
                }
                break;
            case "AP-AC-OUT":
                writer.write("AP SN, Antenna SN\n");
                for (Object item : items) {
                    writer.write(((AP) item).getCpu_serial() + "\n");
                    writer.write(((AP) item).getAntenna_serial() + "\n");
                }
                break;
        }

        writer.close();
    }

    /**
     * Handles Creation and header for excel workbook
     *
     * @param tab the targeted tab
     * @param xlsxFile the excel file to export to
     * @throws IOException
     */
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

    /**
     * Handles appending data for all system branches
     *
     * @param tab the targeted tab
     * @param datasheet the excel sheet being worked on
     * @param xlsxFile the excel file to export to
     * @throws IOException
     */
    private void data(SystemTab tab, Sheet datasheet, File xlsxFile) throws IOException {
        TableView data_table = tab.getData_table();
        ObservableList items = data_table.getItems();

        switch (tab.getUnit()) {
            case "DVR":
                for (int item = 0; item < items.size(); item++) {
                    DVR dvr = (DVR) items.get(item);

                    Row serialRow = datasheet.createRow(1 + (item * 2));
                    Row barcodeRow = datasheet.createRow(serialRow.getRowNum() + 1);
                    barcodeRow.setHeight((short) 600);
                    serialRow.createCell(0).setCellValue(item + 1);

                    serialRow.createCell(2).setCellValue(dvr.getMonitor());
                    getBarcode(datasheet, dvr.getMonitorBytes(), 2, barcodeRow);
                    serialRow.createCell(3).setCellValue(dvr.getCpu_serial());
                    getBarcode(datasheet, dvr.getCpuBytes(), 3, barcodeRow);
                    serialRow.createCell(4).setCellValue(dvr.getImei());
                    serialRow.createCell(5).setCellValue(dvr.getSim());
                    getBarcode(datasheet, dvr.getSimBytes(), 5, barcodeRow);
                    serialRow.createCell(6).setCellValue(dvr.getRfid());
                    getBarcode(datasheet, dvr.getRfidBytes(), 6, barcodeRow);
                    serialRow.createCell(7).setCellValue(dvr.getRelay());

                }

                datasheet.setColumnWidth(2, 256 * 30);
                datasheet.setColumnWidth(3, 256 * 30);
                datasheet.setColumnWidth(4, 256 * 16);
                datasheet.setColumnWidth(5, 256 * 40);
                datasheet.setColumnWidth(6, 256 * 30);
                break;
            case "M1":
            case "G1":
                for (int item = 0; item < items.size(); item++) {
                    Tablets tablets = (Tablets) items.get(item);

                    Row serialRow = datasheet.createRow(1 + (item * 2));
                    Row barcodeRow = datasheet.createRow(serialRow.getRowNum() + 1);
                    barcodeRow.setHeight((short) 600);
                    serialRow.createCell(0).setCellValue(item + 1);

                    serialRow.createCell(2).setCellValue(tablets.getCpu_serial());
                    getBarcode(datasheet, tablets.getCpuBytes(), 2, barcodeRow);
                    serialRow.createCell(3).setCellValue(tablets.getImei());
                    serialRow.createCell(4).setCellValue(tablets.getSim());
                    getBarcode(datasheet, tablets.getSimBytes(), 4, barcodeRow);
                    serialRow.createCell(5).setCellValue(tablets.getDocking_station());
                    getBarcode(datasheet, tablets.getDocking_stationBytes(), 5, barcodeRow);

                }

                datasheet.setColumnWidth(2, 256 * 30);
                datasheet.setColumnWidth(3, 256 * 16);
                datasheet.setColumnWidth(4, 256 * 40);
                datasheet.setColumnWidth(5, 256 * 30);
                break;
            case "SSV9":
            case "Fleetmind Cameras":
            case "Fleetmind Custom":
            case "MVI Custom":
            case "IT Custom":
                for (int item = 0; item < items.size(); item++) {
                    Singular tablets = (Singular) items.get(item);

                    Row serialRow = datasheet.createRow(1 + (item * 2));
                    Row barcodeRow = datasheet.createRow(serialRow.getRowNum() + 1);
                    barcodeRow.setHeight((short) 600);
                    serialRow.createCell(0).setCellValue(item + 1);

                    serialRow.createCell(2).setCellValue(tablets.getCpu_serial());
                    getBarcode(datasheet, tablets.getCpuBytes(), 2, barcodeRow);

                }

                datasheet.setColumnWidth(2, 256 * 30);
                break;
            case "Flashback In-Car":
                for (int item = 0; item < items.size(); item++) {
                    InCar inCar = (InCar) items.get(item);

                    Row serialRow = datasheet.createRow(1 + (item * 2));
                    Row barcodeRow = datasheet.createRow(serialRow.getRowNum() + 1);
                    barcodeRow.setHeight((short) 600);
                    serialRow.createCell(0).setCellValue(item + 1);

                    serialRow.createCell(2).setCellValue(inCar.getCpu_serial());
                    getBarcode(datasheet, inCar.getCpuBytes(), 2, barcodeRow);
                    serialRow.createCell(3).setCellValue(inCar.getDoor_rev());
                    serialRow.createCell(4).setCellValue(inCar.getSoftware_rev());
                    serialRow.createCell(5).setCellValue(inCar.getSupplier_sn());
                    serialRow.createCell(6).setCellValue(inCar.getSd_card());
                    getBarcode(datasheet, inCar.getSd_cardBytes(), 6, barcodeRow);
                    serialRow.createCell(7).setCellValue(inCar.getAssigned_ip());

                }

                datasheet.setColumnWidth(2, 256 * 30);
                datasheet.setColumnWidth(5, 256 * 30);
                datasheet.setColumnWidth(7, 256 * 14);
                break;
            case "Flashback Interview Room":
                for (int item = 0; item < items.size(); item++) {
                    Interview interview = (Interview) items.get(item);

                    Row serialRow = datasheet.createRow(1 + (item * 2));
                    Row barcodeRow = datasheet.createRow(serialRow.getRowNum() + 1);
                    barcodeRow.setHeight((short) 600);
                    serialRow.createCell(0).setCellValue(item + 1);

                    serialRow.createCell(2).setCellValue(interview.getCpu_serial());
                    getBarcode(datasheet, interview.getCpuBytes(), 2, barcodeRow);
                    serialRow.createCell(3).setCellValue(interview.getDoor_rev());
                    serialRow.createCell(4).setCellValue(interview.getSoftware_rev());
                    serialRow.createCell(5).setCellValue(interview.getSupplier_sn());
                    serialRow.createCell(6).setCellValue(interview.getSd_card());
                    getBarcode(datasheet, interview.getSd_cardBytes(), 6, barcodeRow);

                }

                datasheet.setColumnWidth(2, 256 * 30);
                datasheet.setColumnWidth(5, 256 * 30);
                break;
            case "BWX-100":
                for (int item = 0; item < items.size(); item++) {
                    BWX bwx = (BWX) items.get(item);

                    Row serialRow = datasheet.createRow(1 + (item * 2));
                    Row barcodeRow = datasheet.createRow(serialRow.getRowNum() + 1);
                    barcodeRow.setHeight((short) 600);
                    serialRow.createCell(0).setCellValue(item + 1);

                    serialRow.createCell(2).setCellValue(bwx.getCpu_serial());
                    getBarcode(datasheet, bwx.getCpuBytes(), 2, barcodeRow);
                    serialRow.createCell(3).setCellValue(bwx.getDocking_station());
                    getBarcode(datasheet, bwx.getDocking_stationBytes(), 3, barcodeRow);

                }

                datasheet.setColumnWidth(2, 256 * 30);
                datasheet.setColumnWidth(3, 256 * 30);
                break;
            case "Server":
                for (int item = 0; item < items.size(); item++) {
                    Server server = (Server) items.get(item);

                    Row serialRow = datasheet.createRow(1 + (item * 2));
                    Row barcodeRow = datasheet.createRow(serialRow.getRowNum() + 1);
                    barcodeRow.setHeight((short) 600);
                    serialRow.createCell(0).setCellValue(item + 1);

                    serialRow.createCell(2).setCellValue(server.getCpu_serial());
                    getBarcode(datasheet, server.getCpuBytes(), 2, barcodeRow);

                }

                datasheet.setColumnWidth(2, 256 * 30);
                break;
            case "AP-AC-OUT":
                for (int item = 0; item < items.size(); item++) {
                    AP ap = (AP) items.get(item);

                    Row serialRow = datasheet.createRow(1 + (item * 2));
                    Row barcodeRow = datasheet.createRow(serialRow.getRowNum() + 1);
                    barcodeRow.setHeight((short) 600);
                    serialRow.createCell(0).setCellValue(item + 1);

                    serialRow.createCell(2).setCellValue(ap.getCpu_serial());
                    getBarcode(datasheet, ap.getCpuBytes(), 2, barcodeRow);
                    serialRow.createCell(3).setCellValue(ap.getAntenna_serial());
                    getBarcode(datasheet, ap.getAntenna_serialBytes(), 3, barcodeRow);
                }

                datasheet.setColumnWidth(2, 256 * 30);
                datasheet.setColumnWidth(3, 256 * 30);
                break;
        }

        if (!xlsxFile.exists()) {
            if (xlsxFile.createNewFile()) {
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

    /**
     * Handles appending barcode pictures to sheet
     *
     * @param datasheet the excel sheet being worked on
     * @param bytes barcode information in bytes
     * @param c1 top right anchor cell
     * @param barcodeRow row for barcode to be appended to
     */
    private void getBarcode(Sheet datasheet, byte[] bytes, int c1, Row barcodeRow) {
        if(bytes != null) {
            int monitorIdx = datasheet.getWorkbook().addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            CreationHelper helper = datasheet.getWorkbook().getCreationHelper();
            Drawing drawing = datasheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(c1);
            anchor.setRow1(barcodeRow.getRowNum());
            anchor.setCol2(c1 + 1);
            anchor.setRow2(barcodeRow.getRowNum() + 1);

            Picture picture = drawing.createPicture(anchor, monitorIdx);
            barcodeRow.createCell(2);
        }
    }

    public TabPane getData_sheet_tab_pane() {
        return data_sheet_tab_pane;
    }
}
