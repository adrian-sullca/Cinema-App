package com.app.cinema.controller;

import com.app.cinema.util.GestionarVistaUtils;

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
    @FXML
    private Button gestionDePeliculasBoton;
    @FXML
    private Button gestionDeTransaccionesBoton;
    @FXML
    private Button gestionDeReseñasBoton;

    public void mostrarRootLayoutCliente() {
        GestionarVistaUtils.cargarVista("view/gestionDeUsuariosView.fxml", rootLayoutTrabajadorView);
    }

    @FXML
    private void accionGestionDeUsuariosBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDeUsuariosView.fxml", rootLayoutTrabajadorView);
    }

    @FXML
    private void accionGestionDeTrabajadoresBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDeTrabajadoresView.fxml", rootLayoutTrabajadorView);
    }

    @FXML
    private void accionGestionDePeliculasBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDePeliculasView.fxml", rootLayoutTrabajadorView);
    }

    @FXML
    private void accionGestionDeTransaccionesBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDeTransaccionesView.fxml", rootLayoutTrabajadorView);
    }

    @FXML
    private void accionGestionDeReseñasBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDeReseñasView.fxml", rootLayoutTrabajadorView);
    }
}
