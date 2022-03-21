/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtvn.oubus;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 *
 * @author datph
 */
public class FXMLPrintTicketController implements Initializable {

    @FXML
    private Text textTripName;
    @FXML
    private Text textFrom;
    @FXML
    private Text textTo;
    @FXML
    private Text textBus;
    @FXML
    private Text textIdTicket;
    @FXML
    private Text textDateTime;
    @FXML
    private Text textSeat;
    @FXML
    private Text textPrice;
    @FXML
    private Text textLastFirstName;
    @FXML
    private Text textPhone;
    @FXML
    private Text textPrintDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void printTicket(String from, String to, String bus, String idTicket, String date, String time, String seat, String price, String lastName, String firstName, String phone) {
        textTripName.setText(from + " - " + to);
        textFrom.setText(from);
        textTo.setText(to);
        textBus.setText(bus);
        textDateTime.setText(time + " " + date);
        textSeat.setText(seat);
        textPrice.setText(price);
        textIdTicket.setText(idTicket);
        textLastFirstName.setText(lastName + " " + firstName);
        textPhone.setText(phone);
        textPrintDate.setText(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd").format(LocalDateTime.now()));
    }
}