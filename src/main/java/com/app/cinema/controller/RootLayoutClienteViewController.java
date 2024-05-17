// RootLayoutClienteController.java
package com.app.cinema.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import com.app.cinema.App;
import com.app.cinema.util.gestionarVista;

public class RootLayoutClienteViewController {

    @FXML
    private BorderPane rootLayoutClienteView;

    @FXML
    private Button perfilBoton;
    @FXML
    private Button catalogoBoton;

    public void mostrarRootLayoutCliente() {
        gestionarVista.cargarVista("view/perfilClienteView.fxml", rootLayoutClienteView);
    }

    @FXML
    private void accionPerfilBoton(ActionEvent event) {
        gestionarVista.cargarVista("view/perfilClienteView.fxml", rootLayoutClienteView);
    }

    @FXML
    private void accionCatalogoBoton(ActionEvent event) {
        gestionarVista.cargarVista("view/catalogoView.fxml", rootLayoutClienteView);
    }
}
