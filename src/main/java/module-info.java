module com.example.computerandfishstore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.computerandfishstore to javafx.fxml;
    exports com.example.computerandfishstore;
    exports com.example.computerandfishstore.fish;
    opens com.example.computerandfishstore.fish to javafx.fxml;
	exports com.example.computerandfishstore.classes;
	opens com.example.computerandfishstore.classes to javafx.fxml;
}