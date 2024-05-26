package com.app.cinema.controller;
import java.net.URL;

import com.app.cinema.dao.CarritoPeliculasDAO;
import com.app.cinema.dao.PeliculaDAO;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.SesionUsuario;
import com.app.cinema.util.AlertUtils;

import java.util.List;
import java.util.ArrayList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PeliculaCardViewController {

    @FXML
    private AnchorPane anchorPaneImageView;

    @FXML
    private Button añadirCarritoBoton;

    @FXML
    private ImageView imageViewCardPelicula;

    @FXML
    private TextField fieldGeneroCardTxt;

    @FXML
    private TextArea fieldDescripcionCardTxt;

    @FXML
    private TextField fieldDuracionCardTxt;

    @FXML
    private TextField fieldTituloCardTxt;

    @FXML
    private AnchorPane peliculaCardPane;

    @FXML
    private Button dejarReseñaBoton;

    @FXML
    private Button verReseñasBoton;

    PeliculaDAO peliculaDAO = new PeliculaDAO();
    private Pelicula peliculaSeleccionada;

    CarritoPeliculasDAO carritoPeliculasDAO = new CarritoPeliculasDAO();

    Cliente clienteLogeado = SesionUsuario.getClienteLogeado();
    List<Pelicula> listaPeliculasCarrito = clienteLogeado.getCarrito().getPeliculasCarrito();

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
                imageViewCardPelicula.setImage(image);
                imageViewCardPelicula.setFitWidth(138);
                imageViewCardPelicula.setFitHeight(168);
                imageViewCardPelicula.setPreserveRatio(false);
                imageViewCardPelicula.setSmooth(true);
                imageViewCardPelicula.setCache(true);
            });

            new Thread(loadImageTask).start();
        } else {
            System.out.println("No se pudo encontrar la imagen en la ruta: " + rutaImagen);
        }

        peliculaCardPane.setId(String.valueOf(pelicula.getIdPelicula()));
    }

    @FXML
    public void seleccionarPelicula(MouseEvent event) {
        if (event.getSource() instanceof AnchorPane) {
            AnchorPane peliculaCardPane = (AnchorPane) event.getSource();
            String idPelicula = peliculaCardPane.getId();
            if (idPelicula != null && !idPelicula.isEmpty()) {
                Pelicula pelicula = peliculaDAO.selectById(Integer.parseInt(idPelicula));
                if (pelicula != null) {
                    System.out.println("Se ha seleccionado la película: " + pelicula.getTitulo());
                    this.peliculaSeleccionada = pelicula;
                } else {
                    System.out.println("No se ha encontrado ninguna película asociada a esta tarjeta.");
                }
            }
        }
    }

    @FXML
    void accionAñadirCarritoBoton(ActionEvent event) {
        try {
            Button boton = (Button) event.getSource();
            Node currentNode = boton;
            while (currentNode != null && !(currentNode instanceof AnchorPane)) {
                currentNode = currentNode.getParent();
            }
            if (currentNode != null) {
                AnchorPane tarjetaPelicula = (AnchorPane) currentNode;
                String idPelicula = tarjetaPelicula.getId();
                Pelicula pelicula = peliculaDAO.selectById(Integer.parseInt(idPelicula));
                if (pelicula != null) {
                    this.listaPeliculasCarrito.add(pelicula);
                    AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Añadido al carrito", "Se ha anadido la película '" + pelicula.getTitulo() + "' al carrito.");
                    System.out.println("Se ha anadido la película '" + pelicula.getTitulo() + "' al carrito.");
                    carritoPeliculasDAO.insert(clienteLogeado.getCarrito());
                } else {
                    System.out.println("No se ha encontrado ninguna película asociada a esta tarjeta.");
                }
            } else {
                System.out.println("No se pudo encontrar el AnchorPane contenedor de la película.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void accionVerReseñasBoton(ActionEvent event) {
        try {
            Button boton = (Button) event.getSource();
            Node currentNode = boton;
            while (currentNode != null && !(currentNode instanceof AnchorPane)) {
                currentNode = currentNode.getParent();
            }
            if (currentNode != null) {
                AnchorPane tarjetaPelicula = (AnchorPane) currentNode;
                String idPelicula = tarjetaPelicula.getId();
                Pelicula pelicula = peliculaDAO.selectById(Integer.parseInt(idPelicula));
                if (pelicula != null) {
                    String rutaFXML = "/com/app/cinema/view/reseñasPeliculaView.fxml";
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
                    AnchorPane reseñasPane = loader.load();
                    ReseñasViewController reseñasController = loader.getController();
                    reseñasController.initialize(pelicula);
                    Stage reseñasStage = new Stage();
                    reseñasStage.initModality(Modality.APPLICATION_MODAL);
                    reseñasStage.initStyle(StageStyle.DECORATED); 
                    reseñasStage.setTitle("Reseñas"); 
                    Scene scene = new Scene(reseñasPane);
                    reseñasStage.setScene(scene);
                    reseñasStage.setResizable(false);
                    reseñasStage.showAndWait();
                } else {
                    System.out.println("No se ha encontrado ninguna película asociada a esta tarjeta.");
                }
            } else {
                System.out.println("No se pudo encontrar el AnchorPane contenedor de la película.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pelicula getPeliculaSeleccionada() {
        return peliculaSeleccionada;
    }
    @FXML
    void accionDejarReseñaBoton(ActionEvent event) {
        try {
            Button boton = (Button) event.getSource();
            Node currentNode = boton;
            while (currentNode != null && !(currentNode instanceof AnchorPane)) {
                currentNode = currentNode.getParent();
            }

            if (currentNode != null) {
                AnchorPane tarjetaPelicula = (AnchorPane) currentNode;
                String idPelicula = tarjetaPelicula.getId();
                Pelicula pelicula = peliculaDAO.selectById(Integer.parseInt(idPelicula));

                if (pelicula != null) {
                    String rutaFXML = "/com/app/cinema/view/escribirReseñaView.fxml";
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
                    AnchorPane escribirReseñaPane = loader.load();
                    EscribirReseñaViewController escribirReseñaViewController = loader.getController();
                    escribirReseñaViewController.initialize(pelicula);
                    Stage escribirReseñaStage = new Stage();
                    escribirReseñaStage.initModality(Modality.APPLICATION_MODAL);
                    escribirReseñaStage.initStyle(StageStyle.DECORATED); 
                    escribirReseñaStage.setTitle("Reseñas"); 
                    Scene scene = new Scene(escribirReseñaPane);
                    escribirReseñaStage.setScene(scene);
                    escribirReseñaStage.setResizable(false);
                    escribirReseñaStage.showAndWait();
                } else {
                    System.out.println("No se ha encontrado ninguna película asociada a esta tarjeta.");
                }
            } else {
                System.out.println("No se pudo encontrar el AnchorPane contenedor de la película.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}