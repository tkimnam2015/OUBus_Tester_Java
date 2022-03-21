module com.dtvn.oubus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.dtvn.oubus to javafx.fxml;
    opens com.dtvn.pojo to javafx.base;
    exports com.dtvn.oubus;
}
