package com.app.cinema.controller;
import java.util.List;

import com.app.cinema.dao.PeliculaDAO;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Reseña;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReseñaCardViewController {
    @FXML
    private TextField fieldNombreCliente;

    @FXML
    private TextField fieldFechaPublicacion;

    @FXML
    private TextArea fieldContenidoReseña;
    
    PeliculaDAO peliculaDAO = new PeliculaDAO();
    List<Pelicula> listaPeliculas = peliculaDAO.selectAll();
    private ObservableList<Pelicula> peliculas = FXCollections.observableArrayList(listaPeliculas);

    public void setReseñaPelicula(Reseña reseña) {
        fieldNombreCliente.setText(reseña.getCliente().getNombre());
        fieldFechaPublicacion.setText(reseña.getFechaReseña().toString());
        fieldContenidoReseña.setText(reseña.getContenido());
    }
}