package com.dtvn.config;

import com.dtvn.pojo.Trip;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class Utils {

    public static List<Trip> getTrip() throws SQLException {
        String sql = "SELECT * FROM ChuyenDi";
        Connection conn = JDBCUtils.getConn();
        PreparedStatement stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<Trip> trip = new ArrayList<>();
        while (rs.next()) {
            Trip t = new Trip(rs.getInt("maChuyen"), rs.getString("tenChuyen"),
                    rs.getString("diemKhoiHanh"), rs.getString("diemDen"),rs.getString("ngayDi"), rs.getString("gioDi"), rs.getDouble("giaTien"));
            trip.add(t);
        }
        rs.close();
        stm.close();
        conn.close();
        return trip;
    }

    public static void deleteTrip(int tripID) throws SQLException {
        String sql = "DELETE FROM ChuyenDi WHERE maChuyen = ?";
        Connection conn = JDBCUtils.getConn();
        conn.setAutoCommit(false);
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, tripID);
        stm.executeUpdate();
        conn.commit();
        stm.close();
        conn.close();
    }

    public static Alert getAlertInfo(String content, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setResizable(false);
        a.setContentText(content);
        return a;
    }
}
