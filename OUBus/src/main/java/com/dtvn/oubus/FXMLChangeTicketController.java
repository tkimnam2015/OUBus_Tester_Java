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
 * FXML Controller class
 *
 * @author datph
 */
public class FXMLChangeTicketController implements Initializable {

    @FXML
    private TableView<Tickets> tableChange;
    @FXML
    private TableColumn<Tickets, Integer> columnTicketsID;
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
    private ComboBox<String> comboBoxFrom;
    @FXML
    private ComboBox<String> comboBoxTo;
    @FXML
    private DatePicker datePickerDateSearch;
    @FXML
    private TextField textFieldTripName;
    @FXML
    private TextField textFieldBus;
    @FXML
    private TextField textFieldFrom;
    @FXML
    private TextField textFieldTo;
    @FXML
    private TextField textFieldTime;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private DatePicker datePickerDate;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldSex;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private TextField textFieldAddress;
    @FXML
    private TextField textFieldSeat;
    @FXML
    private TextField textFieldTicketId;
    @FXML
    private TextField textFieldOldTicketID;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDatabase();
        comboBoxDepartureData();
        comboBoxDestinationData();
        mouseClicked();
    }

    public void loadDatabase() {
        try {
            ObservableList<Tickets> ticketsList = FXCollections.observableArrayList();
            Connection conn = JDBCUtils.getConn();
            String sql = " SELECT ve.maVeXe, ve.tenVeXe, chuyen.tenChuyen, xe.bienSoXe, chuyen.diemKhoiHanh, chuyen.diemDen, chuyen.ngayDi, chuyen.gioDi, chuyen.giaTien, ve.hoKhachHang, ve.tenKhachHang, ve.gioiTinh, ve.soDienThoai, ve.diaChi, ve.viTriGheNgoi, ve.ghiChu FROM ChuyenDi chuyen, Xe xe, VeXe ve WHERE ve.maXe = xe.maXe AND ve.maChuyenDi = chuyen.maChuyen AND ve.ghiChu = 'Empty'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ticketsList.add(new Tickets(rs.getInt("maVeXe"), rs.getString("tenVeXe"), rs.getString("tenChuyen"),
                        rs.getString("bienSoXe"), rs.getString("diemKhoiHanh"), rs.getString("diemDen"), rs.getString("ngayDi"),
                        rs.getString("gioDi"), rs.getString("viTriGheNgoi"), rs.getDouble("giaTien"), rs.getString("hoKhachHang"),
                        rs.getString("tenKhachHang"), rs.getString("gioiTinh"), rs.getString("soDienThoai"), rs.getString("diaChi"),
                        rs.getString("ghiChu")));
            }
            columnTicketsID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
            columnTripName.setCellValueFactory(new PropertyValueFactory<>("tripName"));
            columnBus.setCellValueFactory(new PropertyValueFactory<>("licensePlates"));
            columnDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
            columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
            columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableChange.setItems(ticketsList);
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void searchNewTrip(ActionEvent event) {
        if (comboBoxFrom.getSelectionModel().getSelectedItem() == null) {
            StaffUtils.getAlertInfo("Please, select from!", Alert.AlertType.ERROR).show();
        } else if (comboBoxTo.getSelectionModel().getSelectedItem() == null) {
            StaffUtils.getAlertInfo("Please, select to!", Alert.AlertType.ERROR).show();
        } else if (datePickerDateSearch.getValue() == null) {
            StaffUtils.getAlertInfo("Please, choose a date!", Alert.AlertType.ERROR).show();
        } else {
            try {
                ObservableList<Tickets> listSellTickets = FXCollections.observableArrayList();
                Connection conn = JDBCUtils.getConn();
                String sql = "SELECT ve.maVeXe, ve.tenVeXe, chuyen.tenChuyen, xe.bienSoXe, chuyen.diemKhoiHanh, chuyen.diemDen, chuyen.ngayDi, chuyen.gioDi,ve.viTriGheNgoi, chuyen.giaTien, ve.hoKhachHang, ve.tenKhachHang, ve.gioiTinh, ve.soDienThoai, ve.diaChi, ve.ghiChu FROM ChuyenDi chuyen, Xe xe, VeXe ve WHERE ve.maXe = xe.maXe AND ve.maChuyenDi = chuyen.maChuyen AND chuyen.diemKhoiHanh LIKE ? AND chuyen.diemDen LIKE ? AND chuyen.ngayDi = ? AND ve.ghiChu LIKE 'Empty'";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, comboBoxFrom.getSelectionModel().getSelectedItem());
                stm.setString(2, comboBoxTo.getSelectionModel().getSelectedItem());
                stm.setString(3, datePickerDateSearch.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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
                    tableChange.getItems().clear();
                } else {
                    tableChange.getItems().clear();
                    columnTicketsID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
                    columnTripName.setCellValueFactory(new PropertyValueFactory<>("tripName"));
                    columnBus.setCellValueFactory(new PropertyValueFactory<>("licensePlates"));
                    columnDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
                    columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
                    columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                    columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
                    columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
                    tableChange.setItems(listSellTickets);
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
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCancelTicket.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Tickets Ordered");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void comboBoxDepartureData() {
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

    public void comboBoxDestinationData() {
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

    public void mouseClicked() {
        this.tableChange.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                Tickets ticket = this.tableChange.getSelectionModel().getSelectedItem();
                if (ticket != null) {
                    textFieldTripName.setText(ticket.getTripName());
                    textFieldBus.setText(ticket.getLicensePlates());
                    textFieldFrom.setText(ticket.getDeparture());
                    textFieldTo.setText(ticket.getDestination());
                    datePickerDate.setValue(LocalDate.parse(ticket.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    textFieldTime.setText(ticket.getTime());
                    textFieldPrice.setText(Double.toString(ticket.getPrice()));
                    textFieldTicketId.setText(Integer.toString(ticket.getTicketID()));
                } else {
                    textFieldTripName.setText("");
                    textFieldBus.setText("");
                    textFieldFrom.setText("");
                    textFieldTo.setText("");
                    datePickerDate.setValue(null);
                    textFieldTime.setText("");
                    textFieldPrice.setText("");
                    textFieldTicketId.setText("");
                }
            });
            return row;
        });
    }
    
    public void resetHandler(ActionEvent event) {
        comboBoxFrom.getSelectionModel().clearSelection();
        comboBoxTo.getSelectionModel().clearSelection();
        datePickerDateSearch.setValue(null);
        textFieldTripName.setText("");
        textFieldBus.setText("");
        textFieldFrom.setText("");
        textFieldTo.setText("");
        datePickerDate.setValue(null);
        textFieldTime.setText("");
        textFieldPrice.setText("");
        textFieldTicketId.setText("");
        tableChange.getItems().clear();
        loadDatabase();
    }
    public void getInformationPass(Tickets ticket){
        textFieldLastName.setText(ticket.getLastNamePassenger());
        textFieldFirstName.setText(ticket.getFirstNamePassenger());
        textFieldSex.setText(ticket.getSex());
        textFieldPhone.setText(ticket.getPhone());
        textFieldAddress.setText(ticket.getAddress());
        textFieldSeat.setText(ticket.getSeat());
        textFieldOldTicketID.setText(Integer.toString(ticket.getTicketID()));
    }
    public void changeTicket(ActionEvent event) {
        Tickets ticket = tableChange.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            Alert confirmSell = StaffUtils.getAlertInfo("Are you sure to change this ticket?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = confirmSell.showAndWait();
            if (result.get() == ButtonType.OK) {
                String SQLReset = "UPDATE VeXe SET hoKhachHang = '', tenKhachHang = '', gioiTinh = '', diaChi = '', soDienThoai = '', viTriGheNgoi = '', ghiChu = 'Empty' WHERE maVeXe = ?";
                String SQLChange = "UPDATE VeXe SET hoKhachHang = ?, tenKhachHang = ?, gioiTinh = ?, diaChi = ?, soDienThoai = ?, viTriGheNgoi = ?, ghiChu = 'Ordered' WHERE maVeXe = ?";
                Connection conn;
                try {
                    conn = JDBCUtils.getConn();
                    conn.setAutoCommit(false);
                    PreparedStatement stm = conn.prepareStatement(SQLReset);
                    stm.setInt(1, Integer.parseInt(textFieldOldTicketID.getText()));
                    stm.executeUpdate();
                    conn.commit();
                    PreparedStatement stm2 = conn.prepareStatement(SQLChange);
                    stm2.setString(1, textFieldLastName.getText());
                    stm2.setString(2, textFieldFirstName.getText());
                    stm2.setString(3, textFieldSex.getText());
                    stm2.setString(4, textFieldAddress.getText());
                    stm2.setString(5, textFieldPhone.getText());
                    stm2.setString(6, textFieldSeat.getText());
                    stm2.setInt(7, ticket.getTicketID());
                    stm2.executeUpdate();
                    conn.commit();
                    stm.close();
                    conn.close();
                    StaffUtils.getAlertInfo("Change ticket successfull!", Alert.AlertType.INFORMATION).show();
                    loadDatabase();
                } catch (SQLException ex) {
                    StaffUtils.getAlertInfo("Change ticket faild!", Alert.AlertType.ERROR).show();
                }
            }
        } else {
            StaffUtils.getAlertInfo("Please, select new ticket", Alert.AlertType.ERROR).show();
        }
    }
}
