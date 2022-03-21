package com.dtvn.oubus;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FXMLStartController implements Initializable {

    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private Button btnManager;
    @FXML
    private Button btnStaff;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void switchToManagerLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToStaffLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXMLStaffLogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
