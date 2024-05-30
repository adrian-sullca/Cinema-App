package com.app.cinema.controller;

import com.app.cinema.model.Pelicula;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;

/**
 * Este controlador maneja la vista de una tarjeta de película en una lista de películas en la aplicación Cinema.
 * Permite mostrar los detalles de una película, como su título, descripción, género, duración y foto de portada.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class PeliculaCardListaViewController {

    @FXML
    private AnchorPane anchorPaneImageView;

    @FXML
    private AnchorPane peliculaCardListaPane;

    @FXML
    private TextField fieldGeneroCardTxt;

    @FXML
    private ImageView imageViewCardPeliculaCarrito;

    @FXML
    private TextField fieldDuracionCardTxt;

    @FXML
    private TextArea fieldDescripcionCardTxt;

    @FXML
    private TextField fieldTituloCardTxt;

    /**
     * Establece los datos de la película en la tarjeta de la vista.
     * 
     * @param pelicula La película cuyos datos se mostrarán en la tarjeta.
     */
    public void setPelicula(Pelicula pelicula) {
        fieldTituloCardTxt.setText(pelicula.getTitulo());
        fieldDescripcionCardTxt.setText(pelicula.getDescripcion());
        fieldGeneroCardTxt.setText(pelicula.getGenero().name());
        fieldDuracionCardTxt.setText(pelicula.getDuracion() + " min");

        String rutaImagen = "/com/app/cinema/img/" + pelicula.getFotoPortada();
        URL imageUrl = getClass().getResource(rutaImagen);
        if (imageUrl != null) {
            Task<Image> loadImageTask = new Task<>() {
                @Override
                protected Image call() throws Exception {
                    return new Image(imageUrl.toString());
                }
            };

            loadImageTask.setOnSucceeded(event -> {
                Image image = loadImageTask.getValue();
                imageViewCardPeliculaCarrito.setImage(image);
                imageViewCardPeliculaCarrito.setFitWidth(138);
                imageViewCardPeliculaCarrito.setFitHeight(168);
                imageViewCardPeliculaCarrito.setPreserveRatio(false);
                imageViewCardPeliculaCarrito.setSmooth(true);
                imageViewCardPeliculaCarrito.setCache(true);
            });

            new Thread(loadImageTask).start();
        } else {
            System.out.println("No se pudo encontrar la imagen en la ruta: " + rutaImagen);
        }

        peliculaCardListaPane.setId(String.valueOf(pelicula.getIdPelicula()));
    }
}