module com.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires mysql.connector.java;

    opens com.app.cinema.controller to javafx.fxml;
    opens com.app.cinema to javafx.fxml;
    exports com.app.cinema;
}
