package com.app.cinema.controller;

import com.app.cinema.dao.CarritoPeliculasDAO;
import com.app.cinema.dao.LineasTransaccionDAO;
import com.app.cinema.dao.TransaccionDAO;
import com.app.cinema.enums.TipoTransaccion;
import com.app.cinema.model.Carrito;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.LineaTransaccion;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.SesionUsuario;
import com.app.cinema.model.Transaccion;
import com.app.cinema.util.AlertUtils;

import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import java.util.List;
import java.net.URL;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PeliculaCardCarritoViewController {

    @FXML
    private AnchorPane anchorPaneImageView;

    @FXML
    private Button quitarPeliculaBoton;

    @FXML
    private AnchorPane peliculaCardCarritoPane;

    @FXML
    private TextField fieldGeneroCardTxt;

    @FXML
    private Button comprarPeliculaBoton;

    @FXML
    private ImageView imageViewCardPeliculaCarrito;

    @FXML
    private TextArea fieldDescripcionCardTxt;

    @FXML
    private TextField fieldDuracionCardTxt;

    @FXML
    private TextField fieldTituloCardTxt;

    @FXML
    private TextField fieldPrecioCardTxt;

    Cliente clienteLogeado = SesionUsuario.getClienteLogeado();
    CarritoPeliculasDAO carritoPeliculasDAO = new CarritoPeliculasDAO();
    TransaccionDAO transaccionDAO = new TransaccionDAO();
    LineasTransaccionDAO lineasTransaccionDAO = new LineasTransaccionDAO();

    private CarritoClienteViewController carritoClienteViewController;

    public void setCarritoClienteViewController(CarritoClienteViewController controller) {
        this.carritoClienteViewController = controller;
    }

    @FXML
    void accionComprarPeliculaBoton(ActionEvent event) {
        LineaTransaccion lineaTransaccionNueva = new LineaTransaccion();
        Transaccion transaccionNueva = new Transaccion();
        try {
            String idPeliculaString = peliculaCardCarritoPane.getId();
            int idPelicula = Integer.parseInt(idPeliculaString);
    
            Carrito carrito = clienteLogeado.getCarrito();

            List<Pelicula> listaPeliculasCarrito = carrito.getPeliculasCarrito();
            for (Pelicula pelicula : listaPeliculasCarrito) {
                if (pelicula.getIdPelicula() == idPelicula) {
                    double saldoDiponible = clienteLogeado.getTarjeta().getSaldoDiponible();
                    double totalPelicula = pelicula.getPrecio();
                    if (totalPelicula <= saldoDiponible) {
                        transaccionNueva.setCliente(clienteLogeado);
                        LocalDate localDate = LocalDate.now();
                        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        transaccionNueva.setFechaTransaccion(date);
                        transaccionNueva.setTipoTransaccion(TipoTransaccion.COMPRA);
                        transaccionNueva.setTotal(pelicula.getPrecio());
    
                        int idTransaccionNueva = transaccionDAO.insertAndReturnId(transaccionNueva);
                        lineaTransaccionNueva.setIdTransaccionLT(idTransaccionNueva);
                        lineaTransaccionNueva.setPelicula(pelicula);
                        lineaTransaccionNueva.setPrecioPelicula(pelicula.getPrecio());
                        lineaTransaccionNueva.setCantidad(1);
                        lineaTransaccionNueva.setTotalLT(pelicula.getPrecio());
                        lineasTransaccionDAO.insert(lineaTransaccionNueva);
                        listaPeliculasCarrito.remove(pelicula);
    
                        carritoPeliculasDAO.delete(carrito, idPelicula);
                        double saldoActualizado = saldoDiponible - totalPelicula;
                        clienteLogeado.getTarjeta().setSaldoDiponible(saldoActualizado);
                        carritoClienteViewController.initialize();
                        break;   
                    } else {
                        AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "No hay saldo suficiente");
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void accionQuitarPeliculaBoton(ActionEvent event) {
        try {
            String idPeliculaString = peliculaCardCarritoPane.getId();
            int idPelicula = Integer.parseInt(idPeliculaString);
    
            Carrito carrito = clienteLogeado.getCarrito();

            List<Pelicula> listaPeliculasCarrito = carrito.getPeliculasCarrito();
            for (Pelicula pelicula : listaPeliculasCarrito) {
                if (pelicula.getIdPelicula() == idPelicula) {
                    listaPeliculasCarrito.remove(pelicula);
                    carritoPeliculasDAO.delete(carrito, idPelicula);
                    carritoClienteViewController.initialize();
                    break;
                }
            }


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void setPelicula(Pelicula pelicula) {
        fieldTituloCardTxt.setText(pelicula.getTitulo());
        fieldDescripcionCardTxt.setText(pelicula.getDescripcion());
        fieldGeneroCardTxt.setText(pelicula.getGenero().name());
        fieldPrecioCardTxt.setText(pelicula.getPrecio() + "");
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

        peliculaCardCarritoPane.setId(String.valueOf(pelicula.getIdPelicula()));
    }
}