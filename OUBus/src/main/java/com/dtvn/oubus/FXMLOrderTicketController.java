/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtvn.oubus;

import com.dtvn.config.JDBCUtils;
import com.dtvn.config.StaffUtils;
import com.dtvn.pojo.Tickets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author datph
 */
public class FXMLOrderTicketController implements Initializable {

    @FXML
    private ComboBox<String> comboBoxFrom;
    @FXML
    private ComboBox<String> comboBoxTo;
    @FXML
    private DatePicker datePickerSearch;
    @FXML
    private ComboBox<String> comboBoxSex;
    @FXML
    private TableView<Tickets> tableOrderTicket;
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
    private TextField textFieldTripName;
    @FXML
    private TextField textFieldBus;
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
    private TextField textFieldTicketId;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private TextField textFieldAddress;
    @FXML
    private TextField textFieldSeat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxFromData();
        comboBoxToData();
        setComboBoxSex();
        mouseClicked();
        loadDatabase();
    }

    public void orderTicKet(ActionEvent event) {
        Tickets ticket = tableOrderTicket.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            if (textFieldLastName.getText().equals("")
                    || textFieldFirstName.getText().equals("")
                    || textFieldPhone.getText().equals("")
                    || textFieldAddress.getText().equals("")
                    || textFieldSeat.getText().equals("")
                    || comboBoxSex.getSelectionModel().getSelectedItem() == null) {
                StaffUtils.getAlertInfo("Please, input infomation passenger!", Alert.AlertType.ERROR).show();
            } else {
                Alert confirmSell = StaffUtils.getAlertInfo("Are you sure to order this ticket?", Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = confirmSell.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        String sqlUpdate = "UPDATE VeXe SET hoKhachHang = ?, tenKhachHang = ?, gioiTinh = ?, diaChi = ?, soDienThoai = ?, viTriGheNgoi = ?, ghiChu = 'Ordered' WHERE maVeXe = ?";
                        Connection conn = JDBCUtils.getConn();
                        conn.setAutoCommit(false);
                        PreparedStatement stm = conn.prepareStatement(sqlUpdate);
                        stm.setString(1, textFieldLastName.getText());
                        stm.setString(2, textFieldFirstName.getText());
                        stm.setString(3, comboBoxSex.getSelectionModel().getSelectedItem());
                        stm.setString(4, textFieldAddress.getText());
                        stm.setString(5, textFieldPhone.getText());
                        stm.setString(6, textFieldSeat.getText());
                        stm.setInt(7, ticket.getTicketID());
                        stm.executeUpdate();
                        conn.commit();
                        StaffUtils.getAlertInfo("Order ticket successful!", Alert.AlertType.INFORMATION).show();
                        tableOrderTicket.getItems().clear();
                        loadDatabase();
                        stm.close();
                        conn.close();
                    } catch (SQLException ex) {
                        StaffUtils.getAlertInfo("Order ticket faild!", Alert.AlertType.INFORMATION).show();
                    }
                }
            }
        } else {
            StaffUtils.getAlertInfo("Please, select ticket!", Alert.AlertType.ERROR).show();
        }
    }

    public void setComboBoxSex() {
        comboBoxSex.getItems().addAll("Male", "Female");
    }

    public void comboBoxFromData() {
        ObservableList<String> listFrom = FXCollections.observableArrayList();
        try {
            Connection conn = JDBCUtils.getConn();
            String sql = "SELECT chuyen.diemKhoiHanh FROM VeXe ve, ChuyenDi chuyen WHERE ve.maChuyenDi = chuyen.maChuyen";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                if (!listFrom.contains(rs.getString("diemKhoiHanh"))) {
                    listFrom.add(rs.getString("diemKhoiHanh"));
                }
            }
            comboBoxFrom.setItems(listFrom);
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void comboBoxToData() {
        ObservableList<String> listTo = FXCollections.observableArrayList();
        try {
            Connection conn = JDBCUtils.getConn();
            String sql = "SELECT chuyen.diemDen FROM VeXe ve, ChuyenDi chuyen WHERE ve.maChuyenDi = chuyen.maChuyen";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                if (!listTo.contains(rs.getString("diemDen"))) {
                    listTo.add(rs.getString("diemDen"));
                }
            }
            comboBoxTo.setItems(listTo);
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void searchTicket(ActionEvent event) {
        if (comboBoxFrom.getSelectionModel().getSelectedItem() == null) {
            StaffUtils.getAlertInfo("Please, select from!", Alert.AlertType.ERROR).show();
        } else if (comboBoxTo.getSelectionModel().getSelectedItem() == null) {
            StaffUtils.getAlertInfo("Please, select to!", Alert.AlertType.ERROR).show();
        } else if (datePickerSearch.getValue() == null) {
            StaffUtils.getAlertInfo("Please, choose a date!", Alert.AlertType.ERROR).show();
        } else {
            try {
                ObservableList<Tickets> listSellTickets = FXCollections.observableArrayList();
                Connection conn = JDBCUtils.getConn();
                String sql = "SELECT ve.maVeXe, ve.tenVeXe, chuyen.tenChuyen, xe.bienSoXe, chuyen.diemKhoiHanh, chuyen.diemDen, chuyen.ngayDi, chuyen.gioDi,ve.viTriGheNgoi, chuyen.giaTien, ve.hoKhachHang, ve.tenKhachHang, ve.gioiTinh, ve.soDienThoai, ve.diaChi, ve.ghiChu FROM ChuyenDi chuyen, Xe xe, VeXe ve WHERE ve.maXe = xe.maXe AND ve.maChuyenDi = chuyen.maChuyen AND chuyen.diemKhoiHanh LIKE ? AND chuyen.diemDen LIKE ? AND chuyen.ngayDi = ? AND ve.ghiChu LIKE 'Empty'";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, comboBoxFrom.getSelectionModel().getSelectedItem());
                stm.setString(2, comboBoxTo.getSelectionModel().getSelectedItem());
                stm.setString(3, datePickerSearch.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    listSellTickets.add(new Tickets(rs.getInt("maVeXe"), rs.getString("tenVeXe"), rs.getString("tenChuyen"),
                            rs.getString("bienSoXe"), rs.getString("diemKhoiHanh"), rs.getString("diemDen"), rs.getString("ngayDi"),
                            rs.getString("gioDi"), rs.getString("viTriGheNgoi"), rs.getDouble("giaTien"), rs.getString("hoKhachHang"),
                            rs.getString("tenKhachHang"), rs.getString("gioiTinh"), rs.getString("soDienThoai"), rs.getString("diaChi"),
                            rs.getString("ghiChu")));
                }
                if (listSellTickets == null || listSellTickets.isEmpty()) {
                    StaffUtils.getAlertInfo("Trip not found!", Alert.AlertType.ERROR).show();
                    tableOrderTicket.getItems().clear();
                } else {
                    tableOrderTicket.getItems().clear();
                    columnID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
                    columnTripName.setCellValueFactory(new PropertyValueFactory<>("tripName"));
                    columnBus.setCellValueFactory(new PropertyValueFactory<>("licensePlates"));
                    columnDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
                    columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
                    columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                    columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
                    columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
                    tableOrderTicket.setItems(listSellTickets);
                }
                rs.close();
                stm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void mouseClicked() {
        this.tableOrderTicket.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                Tickets ticket = this.tableOrderTicket.getSelectionModel().getSelectedItem();
                if (ticket != null) {
                    textFieldTripName.setText(ticket.getTripName());
                    textFieldBus.setText(ticket.getLicensePlates());
                    textFieldDeparture.setText(ticket.getDeparture());
                    textFieldDestination.setText(ticket.getDestination());
                    datePickerDate.setValue(LocalDate.parse(ticket.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    textFieldTime.setText(ticket.getTime());
                    textFieldPrice.setText(Double.toString(ticket.getPrice()));
                    textFieldTicketId.setText(Integer.toString(ticket.getTicketID()));
                } else {
                    textFieldTripName.setText("");
                    textFieldBus.setText("");
                    textFieldDeparture.setText("");
                    textFieldDestination.setText("");
                    datePickerDate.setValue(null);
                    textFieldTime.setText("");
                    textFieldPrice.setText("");
                    textFieldTicketId.setText("");
                }
            });
            return row;
        });
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
        comboBoxFrom.getSelectionModel().clearSelection();
        comboBoxTo.getSelectionModel().clearSelection();
        datePickerSearch.setValue(null);
        tableOrderTicket.getItems().clear();
        loadDatabase();
        textFieldTripName.setText("");
        textFieldBus.setText("");
        textFieldDeparture.setText("");
        textFieldDestination.setText("");
        datePickerDate.setValue(null);
        textFieldTime.setText("");
        textFieldPrice.setText("");
        textFieldTicketId.setText("");
        textFieldLastName.setText("");
        textFieldFirstName.setText("");
        comboBoxSex.getSelectionModel().clearSelection();
        textFieldPhone.setText("");
        textFieldAddress.setText("");
        textFieldSeat.setText("");
    }

    public void loadDatabase() {
        try {
            ObservableList<Tickets> ticketsList = FXCollections.observableArrayList();
            Connection conn = JDBCUtils.getConn();
            String sql = "SELECT ve.maVeXe, ve.tenVeXe, chuyen.tenChuyen, xe.bienSoXe, chuyen.diemKhoiHanh, chuyen.diemDen, chuyen.ngayDi, chuyen.gioDi,ve.viTriGheNgoi, chuyen.giaTien, ve.hoKhachHang, ve.tenKhachHang, ve.gioiTinh, ve.soDienThoai, ve.diaChi, ve.ghiChu FROM ChuyenDi chuyen, Xe xe, VeXe ve WHERE ve.maXe = xe.maXe AND ve.maChuyenDi = chuyen.maChuyen AND ve.ghiChu = 'Empty'";
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
            tableOrderTicket.setItems(ticketsList);
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
