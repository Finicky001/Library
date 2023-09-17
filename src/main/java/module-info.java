module com.filip.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.google.gson;

    opens com.filip.library to javafx.fxml, com.google.gson;
    opens com.filip.beans to com.google.gson, javafx.base;
    exports com.filip.library;
}
