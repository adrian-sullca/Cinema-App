package com.app.cinema.controller;

import com.app.cinema.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {
    private App app;

    @FXML
    private void handleIniciarSesionButtonAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/iniciarSesion.fxml"));
            AnchorPane iniciarSesion = (AnchorPane) loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            Scene scene = new Scene(iniciarSesion);
            stage.setScene(scene);

            LoginController loginController = loader.getController();
            loginController.setApp(app);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setApp(App app) {
        this.app = app;
    }

}