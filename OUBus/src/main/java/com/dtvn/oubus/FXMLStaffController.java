/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtvn.oubus;

import com.dtvn.config.JDBCUtils;
import com.dtvn.pojo.Tickets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author datph
 */
public class FXMLStaffController implements Initializable {

    @FXML
    private TableView<Tickets> tableTickets;
    @FXML
    private TableColumn<Tickets, Integer> columnTicketID;
    @FXML
    private TableColumn<Tickets, String> columnTicketName;
    @FXML
    private TableColumn<Tickets, String> columnTripName;
    @FXML
    private TableColumn<Tickets, String> columnDeparture;
    @FXML
    private TableColumn<Tickets, String> columnDestination;
    @FXML
    private TableColumn<Tickets, String> columnDate;
    @FXML
    private TableColumn<Tickets, String> columnTime;
    @FXML
    private TableColumn<Tickets, Double> columnPrice;
    @FXML
    private ComboBox<String> comboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataComboBox();
        loadTicketFromDatabase();
    }

    public void loadTicketFromDatabase() {
        try {
            ObservableList<Tickets> ticketsList = FXCollections.observableArrayList();
            Connection conn = JDBCUtils.getConn();
            String sql = "SELECT ve.maVeXe, ve.tenVeXe, chuyen.tenChuyen, xe.bienSoXe, chuyen.diemKhoiHanh, chuyen.diemDen, chuyen.ngayDi, chuyen.gioDi,ve.viTriGheNgoi, chuyen.giaTien, ve.hoKhachHang, ve.tenKhachHang, ve.gioiTinh, ve.soDienThoai, ve.diaChi, ve.ghiChu FROM ChuyenDi chuyen, Xe xe, VeXe ve WHERE ve.maXe = xe.maXe and ve.maChuyenDi = chuyen.maChuyen";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ticketsList.add(new Tickets(rs.getInt("maVeXe"), rs.getString("tenVeXe"), rs.getString("tenChuyen"),
                        rs.getString("bienSoXe"), rs.getString("diemKhoiHanh"), rs.getString("diemDen"), rs.getString("ngayDi"),
                        rs.getString("gioDi"), rs.getString("viTriGheNgoi"), rs.getDouble("giaTien"), rs.getString("hoKhachHang"),
                        rs.getString("tenKhachHang"), rs.getString("gioiTinh"), rs.getString("soDienThoai"), rs.getString("diaChi"),
                        rs.getString("ghiChu")));
            }
            columnTicketID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
            columnTicketName.setCellValueFactory(new PropertyValueFactory<>("ticketsName"));
            columnTripName.setCellValueFactory(new PropertyValueFactory<>("tripName"));
            columnDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
            columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
            columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableTickets.setItems(ticketsList);
            FilteredList<Tickets> filteredData = new FilteredList<>(ticketsList, b -> true);
            comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Tickets -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String selectKeyword = newValue.toLowerCase();
                    if (Tickets.getNote().toLowerCase().contains(selectKeyword)) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Tickets> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableTickets.comparatorProperty());
            tableTickets.setItems(sortedData);
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void dataComboBox() {
        ObservableList<String> listDataComboBox = FXCollections.observableArrayList();
        Connection conn;
        try {
            conn = JDBCUtils.getConn();
            String sql = "SELECT ghiChu FROM VeXe";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                if (!listDataComboBox.contains(rs.getString("ghiChu"))) {
                    listDataComboBox.add(rs.getString("ghiChu"));
                }
            }
            comboBox.setItems(listDataComboBox);
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void sellTicket(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLSellTickets.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Sell tickets");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void orderTicket(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLOrderTicket.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Order tickets");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void cancelTicket(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCancelTicket.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Cancel tickets");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void changeTicket(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLChangeTicket.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Change tickets");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void logOutHandler(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLStaffLogin.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("LogIn");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}