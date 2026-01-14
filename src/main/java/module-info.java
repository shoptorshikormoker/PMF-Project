module com.seu.pfmfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.seu.pfmfx to javafx.fxml;
    exports com.seu.pfmfx;

    opens com.seu.pfmfx.controller to javafx.fxml;
    exports com.seu.pfmfx.controller;
}