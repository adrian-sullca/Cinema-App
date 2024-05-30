package com.app.cinema;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación que inicia la interfaz gráfica.
 * Extiende la clase Application de JavaFX para manejar la interfaz de usuario.
 *
 * @author Adrian
 */
public class App extends Application {
    
    private static Stage primaryStage;

    /**
     * Método principal para iniciar la aplicación.
     * @param stage El escenario principal de la aplicación.
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        abrirInicioSesionView();
    }

    /**
     * Método estático para abrir la vista de inicio de sesión.
     */
    public static void abrirInicioSesionView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/iniciarSesionView.fxml"));
            AnchorPane ventana = (AnchorPane) loader.load();
            Scene scene = new Scene(ventana);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método estático para obtener el escenario principal de la aplicación.
     * @return El escenario principal de la aplicación.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}