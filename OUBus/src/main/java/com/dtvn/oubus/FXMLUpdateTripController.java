package com.dtvn.oubus;

import com.dtvn.config.JDBCUtils;
import com.dtvn.config.Utils;
import com.dtvn.pojo.Trip;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLUpdateTripController implements Initializable {
    
    @FXML
    private TextField textFieldTripName;
    @FXML
    private TextField textFieldDeparture;
    @FXML
    private TextField textFieldDestination;
    @FXML
    private DatePicker datePickerDate;
    @FXML
    private TextField textFieldTime;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private TableView<Trip> tableTrip;
    @FXML
    private TableColumn<Trip, String> columnID;
    @FXML
    private TableColumn<Trip, String> columnTripName;
    @FXML
    private TableColumn<Trip, String> columnDeparture;
    @FXML
    private TableColumn<Trip, String> columnDestination;
    @FXML
    private TableColumn<Trip, String> columnDate;
    @FXML
    private TableColumn<Trip, String> columnTime;
    @FXML
    private TableColumn<Trip, Double> columnPrice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadTripFromDatabase();
            mouseClicked();
        } catch (SQLException ex) {
            Utils.getAlertInfo("Load data to TableView failed!", Alert.AlertType.ERROR).show();
        }

    }

    public void loadTripFromDatabase() throws SQLException {
        columnID.setCellValueFactory(new PropertyValueFactory("idTrip"));
        columnTripName.setCellValueFactory(new PropertyValueFactory("nameTrip"));
        columnDeparture.setCellValueFactory(new PropertyValueFactory("departure"));
        columnDestination.setCellValueFactory(new PropertyValueFactory("destination"));
        columnDate.setCellValueFactory(new PropertyValueFactory("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory("time"));
        columnPrice.setCellValueFactory(new PropertyValueFactory("price"));
        this.tableTrip.setItems(FXCollections.observableArrayList(Utils.getTrip()));
    }
    public void reset(){
        this.textFieldTripName.setText("");
        this.textFieldDeparture.setText("");
        this.textFieldDestination.setText("");
        this.datePickerDate.setValue(null);
        this.textFieldTime.setText("");
        this.textFieldPrice.setText("");
        this.tableTrip.getSelectionModel().select(null);
    }
    public void resetHandler(ActionEvent event) {
        reset();
    }

    public void backHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLTripManager.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Trips Managerment");
        stage.show();
    }

    public void mouseClicked() {
        this.tableTrip.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                Trip trip = this.tableTrip.getSelectionModel().getSelectedItem();
                if (trip != null) {
                    textFieldTripName.setText(trip.getNameTrip());
                    textFieldDeparture.setText(trip.getDeparture());
                    textFieldDestination.setText(trip.getDestination());
                    datePickerDate.setValue(LocalDate.parse(trip.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    textFieldTime.setText(trip.getTime());
                    textFieldPrice.setText(Double.toString(trip.getPrice()));
                } else {
                    textFieldTripName.setText("");
                    textFieldDeparture.setText("");
                    textFieldDestination.setText("");
                    datePickerDate.setValue(null);
                    textFieldTime.setText("");
                    textFieldPrice.setText("");
                }
            });
            return row;
        });
    }

    public void saveHandler(ActionEvent event) {
        Trip trip = tableTrip.getSelectionModel().getSelectedItem();
        if (trip != null) {
            if (textFieldDeparture.getText().equals("") || textFieldDestination.getText().equals("") || datePickerDate.getValue() == null || textFieldTime.getText().equals("") || textFieldPrice.getText().equals("")) {
                Utils.getAlertInfo("Please, input data!", Alert.AlertType.ERROR).show();
            } else {
                Alert confirmSave = Utils.getAlertInfo("Are you sure to edit this trip?", Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = confirmSave.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        String sqlUpdate = "UPDATE ChuyenDi SET tenChuyen = ?, diemKhoiHanh = ?, diemDen = ?, ngayDi = ?, gioDi = ?, giaTien = ? WHERE maChuyen = ?";
                        Connection conn = JDBCUtils.getConn();
                        conn.setAutoCommit(false);
                        PreparedStatement stm = conn.prepareStatement(sqlUpdate);
                        stm.setString(1, textFieldDeparture.getText() + " - " + textFieldDestination.getText());
                        stm.setString(2, textFieldDeparture.getText());
                        stm.setString(3, textFieldDestination.getText());
                        stm.setString(4, datePickerDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        stm.setString(5, textFieldTime.getText());
                        stm.setString(6, textFieldPrice.getText());
                        stm.setInt(7, trip.getIdTrip());
                        stm.executeUpdate();
                        conn.commit();
                        stm.close();
                        conn.close();
                        this.tableTrip.getItems().clear();
                        this.tableTrip.setItems(FXCollections.observableArrayList(Utils.getTrip()));
                        reset();
                        Utils.getAlertInfo("Edit question successful!", Alert.AlertType.INFORMATION).show();
                    } catch (SQLException ex) {
                        Utils.getAlertInfo("Edit question failed: " + ex.getMessage(), Alert.AlertType.INFORMATION).show();
                    }
                }
            }
        } else {
            Utils.getAlertInfo("Please, select the trip you want to edit!", Alert.AlertType.ERROR).show();
        }
    }
}