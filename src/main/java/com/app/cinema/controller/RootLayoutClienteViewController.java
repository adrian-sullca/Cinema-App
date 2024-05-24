// RootLayoutClienteController.java
package com.app.cinema.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.app.cinema.App;
import com.app.cinema.util.GestionarVistaUtils;

public class RootLayoutClienteViewController {

    @FXML
    private BorderPane rootLayoutClienteView;
    @FXML
    private Button perfilBoton;
    @FXML
    private Button catalogoBoton;
    @FXML
    private Button carritoBoton;
    @FXML
    private Button historialBoton;
    @FXML
    private Button listaDePeliculasBoton;
    @FXML
    private Button cerrarSesionBoton;

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
    @FXML
    private void accionCarritoBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/catalogoView.fxml", rootLayoutClienteView); //cambiar view
    }
    @FXML
    private void accionHistorialBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/historialClienteView.fxml", rootLayoutClienteView);
    }
    @FXML
    private void accionListaDePeliculasBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/catalogoView.fxml", rootLayoutClienteView); //cambiar view
    }

    @FXML
    private void accionCerrarSesionBoton(ActionEvent event) {
        Stage actualStage = (Stage) cerrarSesionBoton.getScene().getWindow();
        actualStage.hide();
        App.abrirInicioSesionView();
    }
}
