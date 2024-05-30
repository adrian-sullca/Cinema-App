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

/**
 * Este controlador maneja la vista de una tarjeta de reseña de película en la aplicación Cinema.
 * Permite mostrar la información de una reseña de película, incluyendo el nombre del cliente, la fecha de publicación y el contenido de la reseña.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
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

    /**
     * Establece la información de la reseña de la película en la vista de la tarjeta de reseña.
     * 
     * @param reseña La reseña de la película que se va a mostrar.
     */
    public void setReseñaPelicula(Reseña reseña) {
        fieldNombreCliente.setText(reseña.getCliente().getNombre());
        fieldFechaPublicacion.setText(reseña.getFechaReseña().toString());
        fieldContenidoReseña.setText(reseña.getContenido());
    }
}