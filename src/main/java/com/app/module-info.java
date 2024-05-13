module com.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.app.cinema.view to javafx.fxml;
    exports com.app.cinema;
}
