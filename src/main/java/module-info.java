module com.example.gameengine2d {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gameengine2d to javafx.fxml;
    exports com.example.gameengine2d;
}