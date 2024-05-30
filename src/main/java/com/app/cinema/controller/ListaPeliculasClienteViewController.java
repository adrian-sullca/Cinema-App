package com.app.cinema.controller;


import com.app.cinema.dao.TransaccionDAO;
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

/**
 * Este controlador maneja la vista de la lista de películas para un cliente en la aplicación Cinema.
 * Carga las películas compradas por el cliente en un contenedor y muestra cada película como una tarjeta.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class ListaPeliculasClienteViewController {

    @FXML
    private AnchorPane anchorPaneListaPeliculas;

    @FXML
    private VBox vboxContainer;

    @FXML
    private ScrollPane scrollPaneListaPeliculas;

    Cliente clienteLogeado = SesionUsuario.getClienteLogeado();

    TransaccionDAO transaccionDAO = new TransaccionDAO();
    
    List<Transaccion> listaTransaccionesCliente = transaccionDAO.selectByCodiCliente(clienteLogeado.getCodiCliente());

    //List<Transaccion> listaTransaccionesCliente2 = clienteLogeado.getTransaccion();
    List<Pelicula> listaPeliculasCompra = new ArrayList<>();

    /**
     * Inicializa la vista de la lista de películas para el cliente.
     * Carga las películas compradas por el cliente en la lista.
     */
    public void initialize() {
        cargarPeliculasEnLista();
        System.out.println("LISTA DE PELICULAS DEL CLIENTE O TRANSACCIONES");
        for (Transaccion transaccion : listaTransaccionesCliente) {
            System.out.println(transaccion+ "\n");
        }
    }

    /**
     * Carga las películas compradas por el cliente en el contenedor de la vista.
     */
    void cargarPeliculasEnLista() {
        // Crear la lista de transacciones de compra
        List<Transaccion> listaTransaccionesCompra = new ArrayList<>();
    
        for (Transaccion transaccion : listaTransaccionesCliente) {
            if (transaccion.getTipoTransaccion() == TipoTransaccion.COMPRA) {
                listaTransaccionesCompra.add(transaccion);
            }
        }
    
        // Limpiar el contenedor antes de agregar nuevas películas
        vboxContainer.getChildren().clear();
    
        // Iterar sobre las transacciones de compra y agregar las películas al contenedor
        for (Transaccion transaccion : listaTransaccionesCompra) {
            for (LineaTransaccion linea : transaccion.getLineaTransaccion()) {
                Pelicula pelicula = linea.getPelicula();
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
    
}
