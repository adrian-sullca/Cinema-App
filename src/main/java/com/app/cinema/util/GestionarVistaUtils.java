package com.app.cinema.util;

import java.io.IOException;

import com.app.cinema.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * Clase utilitaria para gestionar la carga de vistas en la interfaz de usuario.
 * 
 * @author Adrian
 */
public class GestionarVistaUtils {

    /**
     * Método estático para cargar una vista en un BorderPane.
     * @param fxmlPath La ruta del archivo FXML de la vista.
     * @param rootLayout El BorderPane donde se cargará la vista.
     */
    public static void cargarVista(String fxmlPath, BorderPane rootLayot) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent view = loader.load();
            rootLayot.setRight(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Método estático para cargar una vista sin un BorderPane.
     * @param fxmlPath La ruta del archivo FXML de la vista.
     * @return El nodo raíz de la vista cargada.
     */
    public static void cargarVistaSinRootLayout(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent view = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
