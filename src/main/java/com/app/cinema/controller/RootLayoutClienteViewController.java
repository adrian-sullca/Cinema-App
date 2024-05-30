// RootLayoutClienteController.java
package com.app.cinema.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.app.cinema.App;
import com.app.cinema.util.GestionarVistaUtils;

/**
 * Este controlador maneja la vista del diseño raíz para el cliente en la aplicación Cinema.
 * Permite la navegación entre diferentes vistas, como el perfil del cliente, el catálogo de películas,
 * el carrito de compras, el historial de compras y la lista de películas.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
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

    private Button selectedButton;

    private void seleccionarBoton(Button botonSeleccionado) {
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("button-selected");
            selectedButton.getStyleClass().add("buttonOscuro");
        }
        botonSeleccionado.getStyleClass().add("button-selected");
        selectedButton = botonSeleccionado;
    }

    /**
     * Muestra el diseño raíz para el cliente cargando la vista del perfil del cliente.
     */
    public void mostrarRootLayoutCliente() {
        GestionarVistaUtils.cargarVista("view/perfilClienteView.fxml", rootLayoutClienteView);
    }

    /**
     * Acción realizada al presionar el botón de perfil.
     * Carga la vista del perfil del cliente y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionPerfilBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/perfilClienteView.fxml", rootLayoutClienteView);
        seleccionarBoton(perfilBoton);
    }

    /**
     * Acción realizada al presionar el botón del catálogo.
     * Carga la vista del catálogo de películas y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionCatalogoBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/catalogoView.fxml", rootLayoutClienteView);
        seleccionarBoton(catalogoBoton);
    }

    /**
     * Acción realizada al presionar el botón del carrito.
     * Carga la vista del carrito de compras y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionCarritoBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/carritoClienteView.fxml", rootLayoutClienteView);
        seleccionarBoton(carritoBoton);
    }

    /**
     * Acción realizada al presionar el botón del historial.
     * Carga la vista del historial de compras y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionHistorialBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/historialClienteView.fxml", rootLayoutClienteView);
        seleccionarBoton(historialBoton);
    }

    /**
     * Acción realizada al presionar el botón de la lista de películas.
     * Carga la vista de la lista de películas y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionListaDePeliculasBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/listaPeliculasClienteView.fxml", rootLayoutClienteView);
        seleccionarBoton(listaDePeliculasBoton);
    }

    /**
     * Acción realizada al presionar el botón de cerrar sesión.
     * Cierra la sesión del cliente y abre la vista de inicio de sesión.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionCerrarSesionBoton(ActionEvent event) {
        Stage actualStage = (Stage) cerrarSesionBoton.getScene().getWindow();
        actualStage.hide();
        App.abrirInicioSesionView();
    }
}
