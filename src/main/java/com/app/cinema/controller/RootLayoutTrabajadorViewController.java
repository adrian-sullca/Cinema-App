package com.app.cinema.controller;

import com.app.cinema.App;
import com.app.cinema.util.GestionarVistaUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Este controlador maneja la vista del diseño raíz para el trabajador en la aplicación Cinema.
 * Permite la navegación entre diferentes vistas de gestión, como la gestión de usuarios, trabajadores,
 * películas, transacciones y reseñas.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
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
     * Muestra el diseño raíz para el trabajador cargando la vista de gestión de usuarios.
     */
    public void mostrarRootLayoutTrabajador() {
        GestionarVistaUtils.cargarVista("view/gestionDeUsuariosView.fxml", rootLayoutTrabajadorView);
    }

    /**
     * Acción realizada al presionar el botón de gestión de usuarios.
     * Carga la vista de gestión de usuarios y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionGestionDeUsuariosBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDeUsuariosView.fxml", rootLayoutTrabajadorView);
        seleccionarBoton(gestionDeUsuariosBoton);
    }

    /**
     * Acción realizada al presionar el botón de gestión de trabajadores.
     * Carga la vista de gestión de trabajadores y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionGestionDeTrabajadoresBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDeTrabajadoresView.fxml", rootLayoutTrabajadorView);
        seleccionarBoton(gestionDeTrabajadoresBoton);
    }

    /**
     * Acción realizada al presionar el botón de gestión de películas.
     * Carga la vista de gestión de películas y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionGestionDePeliculasBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDePeliculasView.fxml", rootLayoutTrabajadorView);
        seleccionarBoton(gestionDePeliculasBoton);
    }

    /**
     * Acción realizada al presionar el botón de gestión de transacciones.
     * Carga la vista de gestión de transacciones y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionGestionDeTransaccionesBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDeTransaccionesView.fxml", rootLayoutTrabajadorView);
        seleccionarBoton(gestionDeTransaccionesBoton);
    }

    /**
     * Acción realizada al presionar el botón de gestión de reseñas.
     * Carga la vista de gestión de reseñas y selecciona el botón correspondiente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    private void accionGestionDeReseñasBoton(ActionEvent event) {
        GestionarVistaUtils.cargarVista("view/gestionDeReseñasView.fxml", rootLayoutTrabajadorView);
        seleccionarBoton(gestionDeReseñasBoton);
    }

    /**
     * Acción realizada al presionar el botón de cerrar sesión.
     * Cierra la sesión del trabajador y abre la vista de inicio de sesión.
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
