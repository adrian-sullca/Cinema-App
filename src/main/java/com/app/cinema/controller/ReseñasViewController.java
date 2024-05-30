package com.app.cinema.controller;
import com.app.cinema.dao.PeliculaDAO;
import com.app.cinema.dao.ReseñasDAO;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Reseña;

import java.io.IOException;
import java.util.List;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Este controlador maneja la vista de las reseñas de una película en la aplicación Cinema.
 * Permite mostrar las reseñas aprobadas de una película, incluyendo el título, la descripción, el género, la duración y la imagen de portada de la película.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class ReseñasViewController {
    
    @FXML
    private AnchorPane scrollPaneReseñas;

    @FXML
    private TextField fieldGeneroPelicula;
    @FXML
    private VBox vboxContainer;
    @FXML
    private ImageView imageViewPelicula;

    @FXML
    private TextArea fieldDescripcionPelicula;

    @FXML
    private TextField fieldTituloPelicula;

    @FXML
    private TextField fieldDuracionPelicula;

    PeliculaDAO peliculaDAO = new PeliculaDAO();
    ReseñasDAO reseñasDAO = new ReseñasDAO();
    List<Pelicula> listaPeliculas = peliculaDAO.selectAll();
    private ObservableList<Pelicula> peliculas = FXCollections.observableArrayList(listaPeliculas);

    /**
     * Inicializa la vista de las reseñas de una película con la información de la película proporcionada.
     * 
     * @param pelicula La película para la cual se mostrarán las reseñas.
     */
    public void initialize(Pelicula pelicula) {
        fieldTituloPelicula.setText(pelicula.getTitulo());
        fieldDescripcionPelicula.setText(pelicula.getDescripcion());
        fieldGeneroPelicula.setText(pelicula.getGenero().name());
        fieldDuracionPelicula.setText(pelicula.getDuracion() + " min");
        String rutaImagen = "/com/app/cinema/img/" + pelicula.getFotoPortada();
        URL imageUrl = getClass().getResource(rutaImagen);
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            imageViewPelicula.setImage(image);
            imageViewPelicula.setFitWidth(138);
            imageViewPelicula.setFitHeight(168);
            imageViewPelicula.setPreserveRatio(true);
            imageViewPelicula.setSmooth(true);
            imageViewPelicula.setCache(true);
        } else {
            System.out.println("No se pudo encontrar la imagen en la ruta: " + rutaImagen);
        }
        
        Task<List<Reseña>> loadReseñasTask = new Task<>() {
            @Override
            protected List<Reseña> call() throws Exception {
                return reseñasDAO.reseñasAprobadasByPeliculaId(pelicula);
            }
        };

        loadReseñasTask.setOnSucceeded(event -> {
            List<Reseña> listaReseñas = loadReseñasTask.getValue();
            for (Reseña reseña : listaReseñas) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/reseñaCardView.fxml"));
                    Pane pane = loader.load();

                    ReseñaCardViewController controller = loader.getController();
                    controller.setReseñaPelicula(reseña);

                    vboxContainer.getChildren().add(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(loadReseñasTask).start();

    }
    
}