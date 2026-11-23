module com.example.computerandfishstore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.computerandfishstore to javafx.fxml;
    exports com.example.computerandfishstore;
}