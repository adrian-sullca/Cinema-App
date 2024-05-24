package com.app.cinema.controller;

import com.app.cinema.model.Pelicula;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class EscribirReseñaViewController {

    @FXML
    private TextArea fieldReseña;
    @FXML
    private AnchorPane anchorPaneEscribirReseña;
    @FXML
    private TextField fieldTitulo;
    @FXML
    private TextArea fieldDescripcion;
    @FXML
    private TextField fieldDuracion;
    @FXML
    private Button botonEnviar;
    @FXML
    private ImageView imageViewFotoPelicula;
    @FXML
    private TextField fieldGenero;

    public void initialize(Pelicula pelicula) {

        fieldTitulo.setText(pelicula.getTitulo());
        fieldDescripcion.setText(pelicula.getDescripcion());
        fieldGenero.setText(pelicula.getGenero().name());
        fieldDuracion.setText(pelicula.getDuracion() + " min");
        String rutaImagen = "/com/app/cinema/img/" + pelicula.getFotoPortada();
        URL imageUrl = getClass().getResource(rutaImagen);
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            imageViewFotoPelicula.setImage(image);
            imageViewFotoPelicula.setFitWidth(138);
            imageViewFotoPelicula.setFitHeight(168);
            imageViewFotoPelicula.setPreserveRatio(true);
            imageViewFotoPelicula.setSmooth(true);
            imageViewFotoPelicula.setCache(true);
        } else {
            System.out.println("No se pudo encontrar la imagen en la ruta: " + rutaImagen);
        }
    }

    @FXML
    void accionEnviarBoton(ActionEvent event) {
        //MODIFICAR : hacer que el boton de enviar envie la reseña a la base de datos en REVISION y que se selecicone el cliente o usuario que mando la reseña
    }

}
