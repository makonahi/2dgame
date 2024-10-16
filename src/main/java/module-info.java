module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires java.desktop;


    opens com.example.demo to javafx.fxml, com.fasterxml.jackson.databind, com.fasterxml.jackson.core;
    opens com.example.demo.gameobject to com.fasterxml.jackson.databind, com.fasterxml.jackson.core;
    exports com.example.demo;
}