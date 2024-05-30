package com.app.cinema.controller;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.cinema.App;
import com.app.cinema.dao.ClienteDAO;
import com.app.cinema.dao.DBConnection;
import com.app.cinema.enums.TipoUsuario;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.SesionUsuario;
import com.app.cinema.model.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Este controlador maneja la vista de inicio de sesión en la aplicación Cinema.
 * Permite a los usuarios iniciar sesión con su correo electrónico y contraseña.
 * Dependiendo del tipo de usuario (cliente o trabajador), los redirige a la vista principal correspondiente.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class IniciarSesionViewController {

    @FXML
    private TextField correoUsuarioField;
    @FXML
    private PasswordField contraseñaUsuarioField;
    @FXML
    private Button iniciarSesionBoton;
    @FXML
    private Button registrarseBoton;

    private DBConnection dbConnection = new DBConnection();

    /**
     * Maneja el evento de clic en el botón "Registrarse".
     * Abre la vista de registro de usuario cuando se hace clic en el botón.
     * 
     * @param event El evento de acción del botón.
     */
    @FXML
    private void accionRegistrarseBoton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/registroView.fxml"));
            
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);

            Stage currentStage = (Stage) registrarseBoton.getScene().getWindow();
            currentStage.close();

            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Maneja el evento de clic en el botón "Iniciar Sesión".
     * Valida las credenciales ingresadas por el usuario y redirige a la vista principal correspondiente.
     * 
     * @param event El evento de acción del botón.
     * @throws IOException Si ocurre un error durante la carga de la vista principal.
     */
    @FXML
    private void accionIniciarSesionBoton(ActionEvent event) throws IOException {
        Usuario usuario = validarUsuario(correoUsuarioField.getText(), contraseñaUsuarioField.getText());
        if (usuario != null) {
            if (usuario.getTipoUsuario() == TipoUsuario.CLIENTE) {
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente clienteLogeado = clienteDAO.selectByIdUsuario(usuario.getIdUsuario());
                System.out.println(clienteLogeado);
                SesionUsuario.setClienteLogeado(clienteLogeado);
                FXMLLoader loader = new FXMLLoader(App.class.getResource("view/RootLayoutClienteView.fxml"));
                Parent root = loader.load();
                RootLayoutClienteViewController controllerRLCV = loader.getController();
                controllerRLCV.mostrarRootLayoutCliente();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Vista Principal");
                stage.setResizable(false);
                Stage currentStage = (Stage) iniciarSesionBoton.getScene().getWindow();
                System.out.println(usuario);
                currentStage.close();
                stage.show();
            } else if (usuario.getTipoUsuario() == TipoUsuario.TRABAJADOR) {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("view/RootLayoutTrabajadorView.fxml"));
                Parent root = loader.load();
                RootLayoutTrabajadorViewController controllerRLTV = loader.getController();
                controllerRLTV.mostrarRootLayoutTrabajador();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Vista Principal");
                stage.setResizable(false);
                Stage currentStage = (Stage) iniciarSesionBoton.getScene().getWindow();
                currentStage.close();
                stage.show();
            }
        } else {
            // Usuario no encontrado o credenciales incorrectas
        }
    }

    /**
     * Valida las credenciales del usuario en la base de datos.
     * 
     * @param correo El correo electrónico del usuario.
     * @param contraseña La contraseña del usuario.
     * @return El objeto Usuario si las credenciales son válidas, o null si no lo son.
     */
    private Usuario validarUsuario(String correo, String contraseña) {
        dbConnection.connect();
        Connection conn = dbConnection.getConnection();
        Usuario usuario = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USUARIO WHERE correo = ? AND contraseña = ?");
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                String correoUsuario = rs.getString("correo");
                String contraseñaUsuario = rs.getString("contraseña");
                TipoUsuario tipoUsuario = TipoUsuario.valueOf(rs.getString("tipo_usuario"));
                usuario = new Usuario(idUsuario, nombre, apellido, fechaNacimiento, correoUsuario, contraseñaUsuario, tipoUsuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar las credenciales: " + e.getMessage());
        } finally {
            dbConnection.closeConnection();
        }
        return usuario;
    }
}
