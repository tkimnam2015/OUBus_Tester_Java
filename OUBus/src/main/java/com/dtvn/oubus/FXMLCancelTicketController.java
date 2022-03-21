/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dtvn.oubus;

import com.dtvn.pojo.Tickets;
import com.dtvn.config.JDBCUtils;
import com.dtvn.config.StaffUtils;
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

/**
 * FXML Controller class
 *
 * @author datph
 */
public class FXMLCancelTicketController implements Initializable {
    
    @FXML
    private TableView<Tickets> tableCancelTicket;
    @FXML
    private TableColumn<Tickets, Integer> columnID;
    @FXML
    private TableColumn<Tickets, String> columnTripName;
    @FXML
    private TableColumn<Tickets, String> columnBus;
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
    private TableColumn<Tickets, String> columnLastName;
    @FXML
    private TableColumn<Tickets, String> columnFirstName;
    @FXML
    private TableColumn<Tickets, String> columnSex;
    @FXML
    private TableColumn<Tickets, String> columnPhone;
    @FXML
    private TableColumn<Tickets, String> columnAddress;
    @FXML
    private TableColumn<Tickets, String> columnSeat;
    @FXML
    private TextField textFieldPhoneSearch;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDatabase();
    }
    
    public void loadDatabase() {
        try {
            ObservableList<Tickets> ticketsList = FXCollections.observableArrayList();
            Connection conn = JDBCUtils.getConn();
            String sql = " SELECT ve.maVeXe, ve.tenVeXe, chuyen.tenChuyen, xe.bienSoXe, chuyen.diemKhoiHanh, chuyen.diemDen, chuyen.ngayDi, chuyen.gioDi, chuyen.giaTien, ve.hoKhachHang, ve.tenKhachHang, ve.gioiTinh, ve.soDienThoai, ve.diaChi, ve.viTriGheNgoi, ve.ghiChu FROM ChuyenDi chuyen, Xe xe, VeXe ve WHERE ve.maXe = xe.maXe AND ve.maChuyenDi = chuyen.maChuyen AND ve.ghiChu = 'Ordered'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ticketsList.add(new Tickets(rs.getInt("maVeXe"), rs.getString("tenVeXe"), rs.getString("tenChuyen"),
                        rs.getString("bienSoXe"), rs.getString("diemKhoiHanh"), rs.getString("diemDen"), rs.getString("ngayDi"),
                        rs.getString("gioDi"), rs.getString("viTriGheNgoi"), rs.getDouble("giaTien"), rs.getString("hoKhachHang"),
                        rs.getString("tenKhachHang"), rs.getString("gioiTinh"), rs.getString("soDienThoai"), rs.getString("diaChi"),
                        rs.getString("ghiChu")));
            }
            columnID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
            columnTripName.setCellValueFactory(new PropertyValueFactory<>("tripName"));
            columnBus.setCellValueFactory(new PropertyValueFactory<>("licensePlates"));
            columnDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
            columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
            columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastNamePassenger"));
            columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstNamePassenger"));
            columnSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
            columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            columnSeat.setCellValueFactory(new PropertyValueFactory<>("seat"));
            tableCancelTicket.setItems(ticketsList);
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void searchTicket(ActionEvent event) {
        if (textFieldPhoneSearch.getText().equals("")) {
            StaffUtils.getAlertInfo("Please, enter phone number!", Alert.AlertType.ERROR).show();
        } else {
            try {
                ObservableList<Tickets> listSellTickets = FXCollections.observableArrayList();
                Connection conn = JDBCUtils.getConn();
                String sql = "SELECT ve.maVeXe, ve.tenVeXe, chuyen.tenChuyen, xe.bienSoXe, chuyen.diemKhoiHanh, chuyen.diemDen, chuyen.ngayDi, chuyen.gioDi, chuyen.giaTien, ve.hoKhachHang, ve.tenKhachHang, ve.gioiTinh, ve.soDienThoai, ve.diaChi, ve.viTriGheNgoi, ve.ghiChu FROM ChuyenDi chuyen, Xe xe, VeXe ve WHERE ve.maXe = xe.maXe AND ve.maChuyenDi = chuyen.maChuyen AND ve.soDienThoai = ? AND ve.ghiChu = 'Ordered'";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, textFieldPhoneSearch.getText());
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    listSellTickets.add(new Tickets(rs.getInt("maVeXe"), rs.getString("tenVeXe"), rs.getString("tenChuyen"),
                            rs.getString("bienSoXe"), rs.getString("diemKhoiHanh"), rs.getString("diemDen"), rs.getString("ngayDi"),
                            rs.getString("gioDi"), rs.getString("viTriGheNgoi"), rs.getDouble("giaTien"), rs.getString("hoKhachHang"),
                            rs.getString("tenKhachHang"), rs.getString("gioiTinh"), rs.getString("soDienThoai"), rs.getString("diaChi"),
                            rs.getString("ghiChu")));
                }
                if (listSellTickets == null || listSellTickets.isEmpty()) {
                    StaffUtils.getAlertInfo("Ticket not found!", Alert.AlertType.ERROR).show();
                    tableCancelTicket.getItems().clear();
                    loadDatabase();
                } else {
                    tableCancelTicket.getItems().clear();
                    columnID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
                    columnTripName.setCellValueFactory(new PropertyValueFactory<>("tripName"));
                    columnBus.setCellValueFactory(new PropertyValueFactory<>("licensePlates"));
                    columnDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
                    columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
                    columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                    columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
                    columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
                    columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastNamePassenger"));
                    columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstNamePassenger"));
                    columnSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
                    columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                    columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                    columnSeat.setCellValueFactory(new PropertyValueFactory<>("seat"));
                    tableCancelTicket.setItems(listSellTickets);
                }
                rs.close();
                stm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public void backHandler(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLStaff.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Staff");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void resetHandler(ActionEvent event) {
        textFieldPhoneSearch.setText("");
        tableCancelTicket.getItems().clear();
        loadDatabase();
    }
    
    public void cancelTicket(ActionEvent event) {
        Tickets ticket = tableCancelTicket.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            Alert confirmSell = StaffUtils.getAlertInfo("Are you sure to cancel this ticket?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = confirmSell.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    String sqlUpdate = "UPDATE VeXe SET hoKhachHang = null, tenKhachHang = null, gioiTinh = null, diaChi = null, soDienThoai = null, viTriGheNgoi = null, ghiChu = 'Empty' WHERE maVeXe = ?";
                    Connection conn = JDBCUtils.getConn();
                    conn.setAutoCommit(false);
                    PreparedStatement stm = conn.prepareStatement(sqlUpdate);
                    stm.setInt(1, ticket.getTicketID());
                    stm.executeUpdate();
                    conn.commit();
                    StaffUtils.getAlertInfo("Sell ticket successful!", Alert.AlertType.INFORMATION).show();
                    tableCancelTicket.getItems().clear();
                    loadDatabase();
                    stm.close();
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        } else {
            StaffUtils.getAlertInfo("Please, select ticket!", Alert.AlertType.ERROR).show();
        }
    }
    
    public void chooseNewTicket(ActionEvent event) throws IOException {
        Tickets oldTicket = tableCancelTicket.getSelectionModel().getSelectedItem();
        if (oldTicket == null) {
            StaffUtils.getAlertInfo("Please, select your ticket!", Alert.AlertType.ERROR).show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLChangeTicket.fxml"));
            Parent root = loader.load();
            FXMLChangeTicketController changeTicket = loader.getController();
            changeTicket.getInformationPass(oldTicket);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("Change Ticket");
            stage.show();
        }
    }
    
    public void sqlTakeTicket(String lastName, String firstName, String sex, String address, String phone, String seat, int id) {
        try {
            String sqlUpdate = "UPDATE VeXe SET hoKhachHang = ?, tenKhachHang = ?, gioiTinh = ?, diaChi = ?, soDienThoai = ?, viTriGheNgoi = ?, ghiChu = 'Sold' WHERE maVeXe = ?";
            Connection conn = JDBCUtils.getConn();
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement(sqlUpdate);
            stm.setString(1, lastName);
            stm.setString(2, firstName);
            stm.setString(3, sex);
            stm.setString(4, address);
            stm.setString(5, phone);
            stm.setString(6, seat);
            stm.setInt(7, id);
            stm.executeUpdate();
            conn.commit();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void takeTicketHandler(ActionEvent event) throws IOException {
        Tickets ticket = tableCancelTicket.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            Alert confirmSell = StaffUtils.getAlertInfo("Are you sure to sell this ticket?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = confirmSell.showAndWait();
            if (result.get() == ButtonType.OK) {
                sqlTakeTicket(ticket.getLastNamePassenger(), ticket.getFirstNamePassenger(), ticket.getSex(), ticket.getAddress(), ticket.getPhone(), ticket.getSeat(), ticket.getTicketID());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPrintTicket.fxml"));
                Parent root = loader.load();
                FXMLPrintTicketController printTicketController = loader.getController();
                printTicketController.printTicket(ticket.getDeparture(), ticket.getDestination(), ticket.getLicensePlates(), Integer.toString(ticket.getTicketID()), ticket.getDate(), ticket.getTime(), ticket.getSeat(), Double.toString(ticket.getPrice()), ticket.getLastNamePassenger(), ticket.getFirstNamePassenger(), ticket.getPhone());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
            }
        } else {
            StaffUtils.getAlertInfo("Please, select ticket!", Alert.AlertType.ERROR).show();
        }
    }
}