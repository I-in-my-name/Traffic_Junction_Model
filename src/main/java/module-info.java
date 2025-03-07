module com.trafficjunction.View_and_Controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires org.apache.commons.lang3;

    opens com.trafficjunction.View_and_Controller to javafx.fxml;

    exports com.trafficjunction.View_and_Controller;
}
