package com.app.cinema.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.cinema.App;
import com.app.cinema.dao.DBConnection;

public class LoginController {
    private App app;
    private DBConnection dbConnection;

    @FXML
    private TextField correoUsuarioField;
    @FXML
    private PasswordField contraseñaUsuarioField;
    @FXML
    private Label errorMessageLabel;

    public LoginController() {
        dbConnection = new DBConnection();
    }

    @FXML
    private void handleRegistroButton() {
        app.mostrarRegistroView();
    }

    @FXML
    private void handleIniciarSesionButton() {
        if (validarUsuario(correoUsuarioField.getText(), contraseñaUsuarioField.getText())) {
            app.mostrarRootLayoutCliente();
        } else {
            errorMessageLabel.setText("Credenciales incorrectas. Intente nuevamente.");
        }
    }

    private boolean validarUsuario(String correo, String contraseña) {
        dbConnection.connect();
        Connection conn = dbConnection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USUARIO WHERE correo = ? AND contraseña = ?");
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar las credenciales: " + e.getMessage());
        } finally {
            dbConnection.closeConnection();
        }
        return false;
    }

    public void setApp(App app) {
        this.app = app;
    }

}
