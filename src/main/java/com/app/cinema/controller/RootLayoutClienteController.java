// RootLayoutClienteController.java
package com.app.cinema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RootLayoutClienteController {

    private BorderPane rootLayoutCliente;

    public void setRootLayoutCliente(BorderPane rootLayoutCliente) {
        this.rootLayoutCliente = rootLayoutCliente;
    }

    @FXML
    private void handleCatalogoButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/cinema/view/catalogoView.fxml")); // Verifica la ruta del archivo FXML
            AnchorPane catalogoAnchorPane = (AnchorPane) loader.load();

            rootLayoutCliente.setRight(catalogoAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCarritoButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/cinema/view/catalogoView.fxml")); // Verifica la ruta del archivo FXML
            AnchorPane catalogoAnchorPane = (AnchorPane) loader.load();

            rootLayoutCliente.setRight(catalogoAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleHistorialButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/cinema/view/catalogoView.fxml")); // Verifica la ruta del archivo FXML
            AnchorPane catalogoAnchorPane = (AnchorPane) loader.load();

            rootLayoutCliente.setRight(catalogoAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleListaDePeliculasButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/cinema/view/catalogoView.fxml")); // Verifica la ruta del archivo FXML
            AnchorPane catalogoAnchorPane = (AnchorPane) loader.load();

            rootLayoutCliente.setRight(catalogoAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCerrarSesionButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/cinema/view/catalogoView.fxml")); // Verifica la ruta del archivo FXML
            AnchorPane catalogoAnchorPane = (AnchorPane) loader.load();

            rootLayoutCliente.setRight(catalogoAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
