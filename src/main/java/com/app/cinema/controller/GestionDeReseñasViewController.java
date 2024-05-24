package com.app.cinema.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.app.cinema.dao.ReseñasDAO;
import com.app.cinema.enums.Estado;
import com.app.cinema.model.Reseña;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class GestionDeReseñasViewController {
    @FXML
    private TableView<Reseña> tableViewReseñas;
    @FXML
    private TableColumn<Reseña, Integer> tableColumnCodiCliente;
    @FXML
    private TableColumn<Reseña, String> tableColumnNombre;
    @FXML
    private TableColumn<Reseña, String> tableColumnApellido;
    @FXML
    private TableColumn<Reseña, String> tableColumnPelicula;
    @FXML
    private TableColumn<Reseña, String> tableColumnContenido;
    @FXML
    private TableColumn<Reseña, Estado> tableColumnEstado;
    @FXML
    private TableColumn<Reseña, Date> tableColumnFechaReseña;
    @FXML
    private Button aprobarBoton;
    @FXML
    private Button desaprobarBoton;

    ReseñasDAO reseñasDAO = new ReseñasDAO();
    List<Reseña> listaReseñas = reseñasDAO.selectAll();

    private ObservableList<Reseña> reseñas = FXCollections.observableArrayList(listaReseñas);

    public void initialize(){
        this.tableColumnCodiCliente.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCliente().getCodiCliente()));
        this.tableColumnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        this.tableColumnApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getApellido()));
        this.tableColumnPelicula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPelicula().getTitulo()));
        this.tableColumnContenido.setCellValueFactory(new PropertyValueFactory<>("contenido"));
        this.tableColumnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        this.tableColumnFechaReseña.setCellValueFactory(new PropertyValueFactory<>("fechaReseña"));
        this.tableViewReseñas.setItems(reseñas);
    }

    @FXML
    private void seleccionarReseñaTabla(MouseEvent event) throws IOException {
    }

    @FXML
    public void accionAprobarBoton(ActionEvent event) {
        Reseña reseñaSeleccionada = this.tableViewReseñas.getSelectionModel().getSelectedItem();
        if (reseñaSeleccionada != null) {
            reseñaSeleccionada.setEstado(Estado.APROBADA);
            reseñasDAO.update(reseñaSeleccionada);
            tableViewReseñas.refresh();
        }
    }

    @FXML
    public void accionDesaprobarBoton(ActionEvent event) {
        Reseña reseñaSeleccionada = this.tableViewReseñas.getSelectionModel().getSelectedItem();
        if (reseñaSeleccionada != null) {
            reseñaSeleccionada.setEstado(Estado.DESAPROBADA);
            reseñasDAO.update(reseñaSeleccionada);
            tableViewReseñas.refresh();
        }
    }
}
