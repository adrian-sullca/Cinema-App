package com.app.cinema.controller;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.app.cinema.dao.PeliculaDAO;
import com.app.cinema.dao.TransaccionDAO;
import com.app.cinema.enums.Genero;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Transaccion;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CatalogoViewController {

    @FXML
    private ScrollPane scrollPaneCatalogo;

    @FXML
    private Button botonCienciaFiccion;

    @FXML
    private AnchorPane anchorPaneCatalogo;

    @FXML
    private Button botonComedia;

    @FXML
    private Button botonTerror;

    @FXML
    private VBox vboxContainer;

    @FXML
    private Button botonAccion;

    @FXML
    private Button botonDrama;

    @FXML
    private TextField fieldBuscadorPelicula;
    

    private PeliculaDAO peliculaDAO = new PeliculaDAO();

    TransaccionDAO transaccionDAO = new TransaccionDAO();
    List<Transaccion> transaciones = transaccionDAO.selectAll();
    private List<Pelicula> carrito = new ArrayList<>();

    public void initialize() {
        Task<List<Pelicula>> loadPeliculasTask = new Task<>() {
            @Override
            protected List<Pelicula> call() throws Exception {
                return peliculaDAO.selectAll();
            }
        };

        loadPeliculasTask.setOnSucceeded(event -> {
            List<Pelicula> listaPeliculas = loadPeliculasTask.getValue();
            for (Pelicula pelicula : listaPeliculas) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/peliculaCardView.fxml"));
                    Pane pane = loader.load();

                    PeliculaCardViewController controller = loader.getController();
                    controller.setPelicula(pelicula);

                    vboxContainer.getChildren().add(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(loadPeliculasTask).start();
    }
    @FXML
    void accionAccionBoton(ActionEvent event) {
        vboxContainer.getChildren().clear();

        Task<List<Pelicula>> loadPeliculasTask = new Task<>() {
            @Override
            protected List<Pelicula> call() throws Exception {
                return peliculaDAO.selectPeliculasByGenero(Genero.ACCION);
            }
        };

        loadPeliculasTask.setOnSucceeded(e -> {
            List<Pelicula> peliculasAccion = loadPeliculasTask.getValue();
            for (Pelicula pelicula : peliculasAccion) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/peliculaCardView.fxml"));
                    Pane pane = loader.load();
        
                    PeliculaCardViewController controller = loader.getController();
                    controller.setPelicula(pelicula);
        
                    vboxContainer.getChildren().add(pane);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        new Thread(loadPeliculasTask).start();
    }

    @FXML
    void accionComediaBoton(ActionEvent event) {
        vboxContainer.getChildren().clear();

        Task<List<Pelicula>> loadPeliculasTask = new Task<>() {
            @Override
            protected List<Pelicula> call() throws Exception {
                return peliculaDAO.selectPeliculasByGenero(Genero.COMEDIA);
            }
        };

        loadPeliculasTask.setOnSucceeded(e -> {
            List<Pelicula> peliculasComedia = loadPeliculasTask.getValue();
            for (Pelicula pelicula : peliculasComedia) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/peliculaCardView.fxml"));
                    Pane pane = loader.load();
        
                    PeliculaCardViewController controller = loader.getController();
                    controller.setPelicula(pelicula);
        
                    vboxContainer.getChildren().add(pane);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        new Thread(loadPeliculasTask).start();
    }

    @FXML
    void accionCienciaFiccionBoton(ActionEvent event) {
        vboxContainer.getChildren().clear();

        Task<List<Pelicula>> loadPeliculasTask = new Task<>() {
            @Override
            protected List<Pelicula> call() throws Exception {
                return peliculaDAO.selectPeliculasByGenero(Genero.CIENCIA_FICCION);
            }
        };

        loadPeliculasTask.setOnSucceeded(e -> {
            List<Pelicula> peliculasCienciaFiccion = loadPeliculasTask.getValue();
            for (Pelicula pelicula : peliculasCienciaFiccion) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/peliculaCardView.fxml"));
                    Pane pane = loader.load();
        
                    PeliculaCardViewController controller = loader.getController();
                    controller.setPelicula(pelicula);
        
                    vboxContainer.getChildren().add(pane);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        new Thread(loadPeliculasTask).start();
    }

    @FXML
    void accionTerrorBoton(ActionEvent event) {
        vboxContainer.getChildren().clear();

        Task<List<Pelicula>> loadPeliculasTask = new Task<>() {
            @Override
            protected List<Pelicula> call() throws Exception {
                return peliculaDAO.selectPeliculasByGenero(Genero.TERROR);
            }
        };

        loadPeliculasTask.setOnSucceeded(e -> {
            List<Pelicula> peliculasTerror = loadPeliculasTask.getValue();
            for (Pelicula pelicula : peliculasTerror) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/peliculaCardView.fxml"));
                    Pane pane = loader.load();
        
                    PeliculaCardViewController controller = loader.getController();
                    controller.setPelicula(pelicula);
        
                    vboxContainer.getChildren().add(pane);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        new Thread(loadPeliculasTask).start();
    }

    @FXML
    void accionDramaBoton(ActionEvent event) {
        vboxContainer.getChildren().clear();

        Task<List<Pelicula>> loadPeliculasTask = new Task<>() {
            @Override
            protected List<Pelicula> call() throws Exception {
                return peliculaDAO.selectPeliculasByGenero(Genero.DRAMA);
            }
        };

        loadPeliculasTask.setOnSucceeded(e -> {
            List<Pelicula> peliculasDrama = loadPeliculasTask.getValue();
            for (Pelicula pelicula : peliculasDrama) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/peliculaCardView.fxml"));
                    Pane pane = loader.load();
        
                    PeliculaCardViewController controller = loader.getController();
                    controller.setPelicula(pelicula);
        
                    vboxContainer.getChildren().add(pane);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        new Thread(loadPeliculasTask).start();
    }
    @FXML
    void buscarPeliculasPorTitulo(ActionEvent event) {
        String titulo = fieldBuscadorPelicula.getText();
        vboxContainer.getChildren().clear();

        Task<List<Pelicula>> buscarPeliculasTask = new Task<>() {
            @Override
            protected List<Pelicula> call() throws Exception {
                return peliculaDAO.buscarPeliculasPorTitulo(titulo);
            }
        };

        buscarPeliculasTask.setOnSucceeded(e -> {
            List<Pelicula> peliculasEncontradas = buscarPeliculasTask.getValue();
            for (Pelicula pelicula : peliculasEncontradas) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/peliculaCardView.fxml"));
                    Pane pane = loader.load();

                    PeliculaCardViewController controller = loader.getController();
                    controller.setPelicula(pelicula);

                    vboxContainer.getChildren().add(pane);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        new Thread(buscarPeliculasTask).start();
    }
}
