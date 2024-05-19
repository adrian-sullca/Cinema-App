// RootLayoutClienteController.java
package com.app.cinema.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import com.app.cinema.util.GestionarVistaUtils;

public class RootLayoutClienteViewController {

    @FXML
    private BorderPane rootLayoutClienteView;
    @FXML
    private Button perfilBoton;
    @FXML
    private Button catalogoBoton;

    public void mostrarRootLayoutCliente() {
        GestionarVistaUtils.cargarVista("view/perfilClienteView.fxml", rootLayoutClienteView);
    }

    @FXML
    private void accionPerfilBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/perfilClienteView.fxml", rootLayoutClienteView);
    }

    @FXML
    private void accionCatalogoBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/catalogoView.fxml", rootLayoutClienteView);
    }
}
