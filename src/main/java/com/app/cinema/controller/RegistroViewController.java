package com.app.cinema.controller;

import java.io.IOException;

import com.app.cinema.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegistroViewController {
    @FXML
    private Button registrarseBoton;

    @FXML
    private Button iniciarSesionBoton;

    @FXML
    private void accionRegistrarseBoton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/registroView.fxml"));
            
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void accionIniciarSesionBoton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/iniciarSesionView.fxml"));
            
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            Stage currentStage = (Stage) registrarseBoton.getScene().getWindow();
            currentStage.close();

            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
