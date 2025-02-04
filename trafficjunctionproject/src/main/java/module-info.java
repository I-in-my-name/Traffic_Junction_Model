module com.trafficjunction {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.trafficjunction to javafx.fxml;
    exports com.trafficjunction;
}
