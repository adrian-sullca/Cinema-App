package com.app.cinema.controller;

import com.app.cinema.util.gestionarVista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class RootLayoutTrabajadorViewController {

    @FXML
    private BorderPane rootLayoutTrabajadorView;

    @FXML
    private Button gestionDeUsuariosBoton;
    @FXML
    private Button gestionDeTrabajadoresBoton;

    public void mostrarRootLayoutCliente() {
        gestionarVista.cargarVista("view/gestionDeUsuariosView.fxml", rootLayoutTrabajadorView);
    }

    @FXML
    private void accionGestionDeUsuariosBoton(ActionEvent event) {
        gestionarVista.cargarVista("view/gestionDeUsuariosView.fxml", rootLayoutTrabajadorView);
    }

    @FXML
    private void accionGestionDeTrabajadoresBoton(ActionEvent event) {
        gestionarVista.cargarVista("view/gestionDeTrabajadoresView.fxml", rootLayoutTrabajadorView);
    }
}
