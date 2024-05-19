package com.app.cinema.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;

public class AlertUtils {

    public static void mostrarVentanaError(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setX(700);
        alert.setY(350);
        alert.showAndWait();
    }

    public static void mostrarVentanaExito(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setOnMouseClicked(event -> alert.close());
        alert.setX(700);
        alert.setY(350);
        alert.showAndWait();
    }
}
