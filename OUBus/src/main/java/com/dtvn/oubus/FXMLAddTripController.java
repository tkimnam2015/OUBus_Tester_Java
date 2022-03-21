package com.dtvn.oubus;

import com.dtvn.config.JDBCUtils;
import com.dtvn.config.Utils;
import com.dtvn.pojo.Trip;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLAddTripController implements Initializable {

    @FXML
    private TextField txtDeparture;
    @FXML
    private TextField txtDestination;
    @FXML
    private DatePicker datePickerDate;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtPrice;
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

    public void saveHandler(ActionEvent event) throws SQLException, ParseException {
        if (txtDeparture.getText().equals("") || txtDestination.getText().equals("") || datePickerDate.getValue() == null || txtTime.getText().equals("") || txtPrice.getText().equals("")) {
            Utils.getAlertInfo("Please, input data!", Alert.AlertType.ERROR).show();
        } else {
            try (Connection conn = JDBCUtils.getConn()) {
                String sql = "INSERT INTO ChuyenDi(tenChuyen, diemKhoiHanh, diemDen, ngayDi, gioDi, giaTien) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, txtDeparture.getText() + " - " + txtDestination.getText());
                stm.setString(2, txtDeparture.getText());
                stm.setString(3, txtDestination.getText());
                stm.setString(4, datePickerDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                stm.setString(5, txtTime.getText());
                stm.setString(6, txtPrice.getText());
                stm.executeUpdate();
                stm.close();
                Utils.getAlertInfo("Add trip successful!", Alert.AlertType.INFORMATION).show();
                this.tableTrip.getItems().clear();
                this.tableTrip.setItems(FXCollections.observableArrayList(Utils.getTrip()));
            } catch (SQLException ex) {
                Utils.getAlertInfo("Add trip failed! " + ex.getMessage(), Alert.AlertType.ERROR).show();
            }
        }
    }

    public void resetHandler(ActionEvent event) {
        this.txtDeparture.setText("");
        this.txtDestination.setText("");
        this.datePickerDate.setValue(null);
        this.txtTime.setText("");
        this.txtPrice.setText("");
        this.tableTrip.getSelectionModel().select(null);
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
}