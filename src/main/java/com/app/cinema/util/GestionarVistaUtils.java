package com.app.cinema.util;

import java.io.IOException;

import com.app.cinema.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class GestionarVistaUtils {
    public static void cargarVista(String fxmlPath, BorderPane rootLayot) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent view = loader.load();
            rootLayot.setRight(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void cargarVistaSinRootLayout(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent view = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
