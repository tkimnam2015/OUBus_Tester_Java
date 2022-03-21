package com.dtvn.oubus;

import com.dtvn.config.Utils;
import com.dtvn.pojo.Trip;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLTripManagerController implements Initializable {

    @FXML
    private TableView<Trip> tableTrip;
    @FXML
    private TableColumn<Trip, Integer> columnID;
    @FXML
    private TableColumn<Trip, String> columnNameTrip;
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
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void loadTripFromDatabase() throws SQLException {
        columnID.setCellValueFactory(new PropertyValueFactory("idTrip"));
        columnNameTrip.setCellValueFactory(new PropertyValueFactory("nameTrip"));
        columnDeparture.setCellValueFactory(new PropertyValueFactory("departure"));
        columnDestination.setCellValueFactory(new PropertyValueFactory("destination"));
        columnDate.setCellValueFactory(new PropertyValueFactory("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory("time"));
        columnPrice.setCellValueFactory(new PropertyValueFactory("price"));
        this.tableTrip.setItems(FXCollections.observableArrayList(Utils.getTrip()));
    }

    public void logOutHandler(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("Login");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void searchHandler(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLSearchTrip.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("Search trip");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void addTrip(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLAddTrip.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("Add trip");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void deleteHandler(ActionEvent event) throws IOException {
        Trip trip = tableTrip.getSelectionModel().getSelectedItem();
        if (trip != null) {
            Alert confirmDelete = Utils.getAlertInfo("Are you sure to delete this trip?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = confirmDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    Utils.deleteTrip(trip.getIdTrip());
                    this.tableTrip.getItems().clear();
                    this.tableTrip.setItems(FXCollections.observableArrayList(Utils.getTrip()));
                    Utils.getAlertInfo("Delete trip successful!", Alert.AlertType.INFORMATION).show();
                } catch (SQLException ex) {
                    Utils.getAlertInfo("Delete trip failed: " + ex.getMessage(), Alert.AlertType.INFORMATION).show();
                }
            }
        } else {
            Utils.getAlertInfo("Please, select the trip you want to delete!", Alert.AlertType.ERROR).show();
        }
    }

    public void editHandler(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLUpdateTrip.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("Update trip");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}