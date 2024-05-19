package com.app.cinema.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class GestionDeTrabajadoresViewController {
    @FXML
    private ComboBox<String> cmbxRolTrabajador;

    @FXML
    public void initialize() {
        initCmbxRolTrabajador();
    }
    
    private void initCmbxRolTrabajador() {
        ObservableList<String> roles = FXCollections.observableArrayList("EMPLEADO", "ADMIN");
        cmbxRolTrabajador.setItems(roles);
    }


}
