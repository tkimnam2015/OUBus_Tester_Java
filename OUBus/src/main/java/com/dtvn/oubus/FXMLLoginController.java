/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dtvn.oubus;

import com.dtvn.config.JDBCUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TRUONG VU
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtUserManager;
    @FXML
    private PasswordField pasPasswordManager;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void cancel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXMLStart.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public void loginOfManager(ActionEvent event) throws SQLException, IOException {
        Connection conn;
        PreparedStatement ptm;
        ResultSet rs;
        String userManager = txtUserManager.getText();
        String passwordManager = pasPasswordManager.getText();
        if (userManager.equals("") && passwordManager.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User and password cannot be empty!");
            alert.show();
        } else if (userManager.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User cannot be empty!");
            alert.show();
        } else if (passwordManager.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password cannot be empty!");
            alert.show();
        } else {
            conn = JDBCUtils.getConn();
            ptm = conn.prepareStatement("SELECT tk.*, nv.maChucVu FROM NhanVien nv, TaiKhoan tk WHERE nv.maNhanVien = tk.maNhanVien AND tk.tenDangNhap = ? AND tk.matKhau = ? AND nv.maChucVu = 2");
            ptm.setString(1, userManager);
            ptm.setString(2, passwordManager);
            rs = ptm.executeQuery();
            if (rs.next()) {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLTripManager.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.setTitle("Trips Managerment");
                stage.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Accout not found!");
                alert.show();
                txtUserManager.setText("");
                pasPasswordManager.setText("");
                txtUserManager.requestFocus();
            }
            rs.close();
            ptm.close();
            conn.close();
        }
    }
}
