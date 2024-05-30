package com.app.cinema.controller;

import com.app.cinema.dao.ReseñasDAO;
import com.app.cinema.enums.Estado;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Reseña;
import com.app.cinema.model.SesionUsuario;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Esta clase es un controlador para la vista de escritura de reseñas de películas en la aplicación Cinema.
 * Permite al usuario escribir y enviar reseñas para películas específicas.
 * 
 * La vista muestra información sobre la película seleccionada, como título, descripción, género, duración y foto de portada.
 * El usuario puede escribir una reseña en el campo de texto y enviarla para su revisión.
 * 
 * @author Adrian
 */
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

    Cliente clienteLogeado = SesionUsuario.getClienteLogeado();

    private Pelicula pelicula;

    /**
     * Inicializa la vista de escritura de reseñas con la información de la película seleccionada.
     * 
     * @param pelicula La película para la cual se escribirá la reseña.
     */
    public void initialize(Pelicula pelicula) {
        this.pelicula = pelicula;
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

    /**
     * Maneja la acción del botón para enviar una reseña.
     * Crea una nueva reseña con el contenido proporcionado por el usuario y la envía para su revisión.
     * 
     * @param event El evento de acción del botón.
     */
    @FXML
    void accionEnviarBoton(ActionEvent event) {
        String contenido = fieldReseña.getText();
        if (contenido.isEmpty()) {
            System.out.println("La reseña no puede estar vacía.");
            return;
        }

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Reseña reseñaNueva = new Reseña(0, contenido, date, Estado.REVISION, clienteLogeado, pelicula);
        ReseñasDAO reseñasDAO = new ReseñasDAO();
        reseñasDAO.insert(reseñaNueva);
        System.out.println("Reseña enviada para revisión.");
    }
    
}
