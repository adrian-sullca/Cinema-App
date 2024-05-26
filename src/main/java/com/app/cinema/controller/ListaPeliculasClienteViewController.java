package com.app.cinema.controller;


import com.app.cinema.enums.TipoTransaccion;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.LineaTransaccion;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.SesionUsuario;
import com.app.cinema.model.Transaccion;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ListaPeliculasClienteViewController {

    @FXML
    private AnchorPane anchorPaneListaPeliculas;

    @FXML
    private VBox vboxContainer;

    @FXML
    private ScrollPane scrollPaneListaPeliculas;

    Cliente clienteLogeado = SesionUsuario.getClienteLogeado();

    List<Transaccion> listaTransaccionesCliente = clienteLogeado.getTransaccion();
    List<Pelicula> listaPeliculasCompra = new ArrayList<>();
    
    public void initialize() {
        cargarPeliculasEnLista();
    }
    void cargarPeliculasEnLista() {

        for (Transaccion transaccion : listaTransaccionesCliente) {
            if (transaccion.getTipoTransaccion() == TipoTransaccion.COMPRA) {
                for (LineaTransaccion linea : transaccion.getLineaTransaccion()) {
                    Pelicula pelicula = linea.getPelicula();
                    listaPeliculasCompra.add(pelicula);
                }
            }
     
        }
        vboxContainer.getChildren().clear();
    
        for (Pelicula pelicula : listaPeliculasCompra) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/peliculaCardListaView.fxml"));
                Pane pane = loader.load();
    
                PeliculaCardListaViewController controller = loader.getController();
                controller.setPelicula(pelicula);
    
                vboxContainer.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
