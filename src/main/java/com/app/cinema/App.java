package com.app.cinema;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        abrirInicioSesionView();
    }

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

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}


// borrar esto
// lucia.martinez@example.com