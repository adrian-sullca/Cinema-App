package com.app.cinema.controller;

import java.io.IOException;
import java.util.List;

import com.app.cinema.App;
import com.app.cinema.dao.ClienteDAO;
import com.app.cinema.dao.UsuarioDAO;
import com.app.cinema.enums.TipoUsuario;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Trabajador;
import com.app.cinema.model.Usuario;
import com.app.cinema.util.AlertUtils;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Este controlador maneja la vista de registro de usuarios en la aplicación Cinema.
 * Permite a los usuarios crear una cuenta como cliente.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author [Tu nombre]
 */
public class RegistroViewController {

    @FXML
    private PasswordField fieldConfirmarContraseña;

    @FXML
    private TextField fieldApellidoTxt;

    @FXML
    private Button registrarseBoton;

    @FXML
    private Button iniciarSesionBoton;

    @FXML
    private PasswordField fieldContraseña;

    @FXML
    private DatePicker datePickerFechaNacimiento;

    @FXML
    private TextField fieldTelefono;

    @FXML
    private TextField fieldCorreoTxt;

    @FXML
    private TextField fieldNombreTxt;

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    ClienteDAO clienteDAO = new ClienteDAO();

    /**
     * Maneja el evento de registro de usuario.
     * 
     * @param event El evento que desencadena esta acción.
     */
    @FXML
    void accionRegistrarseBoton(ActionEvent event) {
        try {
            String nombre = fieldNombreTxt.getText();
            String apellido = fieldApellidoTxt.getText();
            String correo = fieldCorreoTxt.getText();
            String contraseña = fieldContraseña.getText();
            String confirmarContraseña = fieldConfirmarContraseña.getText();
            String telefono = fieldTelefono.getText();
            String fechaNacimiento = datePickerFechaNacimiento.getValue().toString();
            java.sql.Date fechaNacimientoSql = java.sql.Date.valueOf(fechaNacimiento);
            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty() || fechaNacimiento.isEmpty() || telefono.isEmpty()) {
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Todos los campos son obligatorios");
            } else {
                if (!contraseña.equals(confirmarContraseña)) {
                    AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Las contraseñas no coinciden");
                } else {
                    List<Usuario> usuarios = usuarioDAO.selectAll();
                    List<Cliente> clientes = clienteDAO.selectAll();

                    Cliente cliente = new Cliente(0, nombre, apellido, fechaNacimientoSql, correo, contraseña, TipoUsuario.CLIENTE, 0, Integer.parseInt(telefono));
                    Usuario usuario = new Usuario(0, nombre, apellido, fechaNacimientoSql, correo, contraseña, TipoUsuario.CLIENTE);
                    if (usuarios.contains(usuario) || clientes.contains(cliente)) {
                        AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Correo o telefono ya registrado");
                    } else{
                        int idUsuarioGenerado = usuarioDAO.insertAndReturnId(usuario);
                        cliente.setIdUsuario(idUsuarioGenerado);
                        clienteDAO.insert(cliente);
                        AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Registro completado correctamente");
                    }
                }    
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Maneja el evento de iniciar sesión.
     * 
     * @param event El evento que desencadena esta acción.
     */
    @FXML
    void accionIniciarSesionBoton(ActionEvent event) {
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