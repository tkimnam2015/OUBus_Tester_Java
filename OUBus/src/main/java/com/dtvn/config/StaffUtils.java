/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtvn.config;

import javafx.scene.control.Alert;

/**
 *
 * @author datph
 */
public class StaffUtils {
    public static Alert getAlertInfo(String content, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setResizable(false);
        a.setContentText(content);
        return a;
    }
}
