module com.example.project4fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project4fx to javafx.fxml;
    exports com.example.project4fx;
}