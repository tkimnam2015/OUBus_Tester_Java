package com.dtvn.oubus;

import com.dtvn.config.JDBCUtils;
import com.dtvn.config.Utils;
import com.dtvn.pojo.Trip;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLSearchTripController implements Initializable {

    @FXML
    private TextField txtKeywords;
    @FXML
    private TableView<Trip> tblTrip;
    @FXML
    private TableColumn<Trip, Integer> colIdTrip;
    @FXML
    private TableColumn<Trip, String> colNameTrip;
    @FXML
    private TableColumn<Trip, String> colDeparture;
    @FXML
    private TableColumn<Trip, String> colDestination;
    @FXML
    private TableColumn<Trip, String> colDate;
    @FXML
    private TableColumn<Trip, String> colTime;
    @FXML
    private TableColumn<Trip, Double> colPrice;

    ObservableList<Trip> tripSearchModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filteredData();
    }

    public void resetHandler(ActionEvent event) {
        txtKeywords.setText("");
        this.tblTrip.getSelectionModel().select(null);
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

    public void filteredData() {
        try {
            Connection conn = JDBCUtils.getConn();
            String sql = "SELECT * FROM ChuyenDi";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int tripId = rs.getInt("maChuyen");
                String tripName = rs.getString("tenChuyen");
                String departure = rs.getString("diemKhoiHanh");
                String destination = rs.getString("diemDen");
                String date = rs.getString("ngayDi");
                String time = rs.getString("gioDi");
                double price = rs.getDouble("giaTien");
                tripSearchModelObservableList.add(new Trip(tripId, tripName, departure, destination, date, time, price));
            }
            colIdTrip.setCellValueFactory(new PropertyValueFactory<>("idTrip"));
            colNameTrip.setCellValueFactory(new PropertyValueFactory<>("nameTrip"));
            colDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
            colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tblTrip.setItems(tripSearchModelObservableList);
            FilteredList<Trip> filteredData = new FilteredList<>(tripSearchModelObservableList, b -> true);
            txtKeywords.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Trip -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (Trip.getNameTrip().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (Trip.getDeparture().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (Trip.getDestination().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (Trip.getDate().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (Trip.getTime().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (Double.toString(Trip.getPrice()).contains(searchKeyword)) {
                        return true;
                    } else {
                        return false;
                    }

                });
            });
            SortedList<Trip> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tblTrip.comparatorProperty());
            tblTrip.setItems(sortedData);
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void deleteHandler(ActionEvent event) throws IOException {
        Trip trip = tblTrip.getSelectionModel().getSelectedItem();
        if (trip != null) {
            Alert confirmDelete = Utils.getAlertInfo("Are you sure to delete this trip?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = confirmDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    Utils.deleteTrip(trip.getIdTrip());
                    Utils.getAlertInfo("Delete trip successful!", Alert.AlertType.INFORMATION).show();
                } catch (SQLException ex) {
                    Utils.getAlertInfo("Delete trip failed: " + ex.getMessage(), Alert.AlertType.INFORMATION).show();
                }
            }
        } else {
            Utils.getAlertInfo("Please, select the trip you want to delete!", Alert.AlertType.ERROR).show();
        }
    }
}