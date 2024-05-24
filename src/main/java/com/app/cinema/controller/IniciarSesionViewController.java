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
            ex.printStackTrace(); // Imprimir el stack trace para diagnosticar el problema
        }
    }

    @FXML
    private void accionIniciarSesionBoton(ActionEvent event) throws IOException {
        Usuario usuario = validarUsuario(correoUsuarioField.getText(), contraseñaUsuarioField.getText());
        if (usuario != null) {
            if (usuario.getTipoUsuario() == TipoUsuario.CLIENTE) {
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente clienteLogeado = clienteDAO.selectById(usuario.getIdUsuario());
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
