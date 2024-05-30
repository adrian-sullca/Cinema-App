package com.app.cinema.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;

/**
 * Clase utilitaria para mostrar ventanas de alerta en la interfaz de usuario.
 * 
 * @author Adrian
 */
public class AlertUtils {

    /**
     * Método estático para mostrar una ventana de error.
     * @param alertType El tipo de alerta (por ejemplo, AlertType.ERROR).
     * @param title El título de la ventana de alerta.
     * @param message El mensaje de error a mostrar.
     */
    public static void mostrarVentanaError(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setX(700);
        alert.setY(350);
        alert.showAndWait();
    }

    /**
     * Método estático para mostrar una ventana de éxito.
     * @param alertType El tipo de alerta (por ejemplo, AlertType.INFORMATION).
     * @param title El título de la ventana de alerta.
     * @param message El mensaje de éxito a mostrar.
     */
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
