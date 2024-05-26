package com.app.cinema.controller;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.app.cinema.dao.CarritoPeliculasDAO;
import com.app.cinema.dao.LineasTransaccionDAO;
import com.app.cinema.dao.TarjetaDAO;
import com.app.cinema.dao.TransaccionDAO;
import com.app.cinema.enums.TipoTransaccion;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.LineaTransaccion;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.SesionUsuario;
import com.app.cinema.model.Transaccion;
import com.app.cinema.util.AlertUtils;

import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CarritoClienteViewController {

    @FXML
    private Button botonComprar;

    @FXML
    private TextField textFieldTotalCarrito;

    @FXML
    private Button botonVaciar;

    @FXML
    private ScrollPane scrollPaneCarrito;

    @FXML
    private VBox vboxContainer;

    @FXML
    private TextField textFieldSaldoCuenta;

    @FXML
    private AnchorPane anchorPaneCarrito;
    CarritoPeliculasDAO carritoPeliculasDAO = new CarritoPeliculasDAO();
    TransaccionDAO transaccionDAO = new TransaccionDAO();
    LineasTransaccionDAO lineasTransaccionDAO = new LineasTransaccionDAO();
    Cliente clienteLogeado = SesionUsuario.getClienteLogeado();
    TarjetaDAO tarjetaDAO = new TarjetaDAO();
    List<Pelicula> listaPeliculasCarrito = clienteLogeado.getCarrito().getPeliculasCarrito();

    public void initialize() {
        cargarPeliculasEnCarrito();
        actualizarTotalCarrito();
        actualizarSaldoCuenta();
    }
    @FXML
    void accionVaciarBoton(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Vaciar carrito");
        alert.setHeaderText("¿Seguro que quieres vaciar el carrito?");
        alert.setContentText("Esta acción no se puede deshacer.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            listaPeliculasCarrito.clear();
            clienteLogeado.getCarrito().setPeliculasCarrito(listaPeliculasCarrito);
            carritoPeliculasDAO.delete(clienteLogeado.getCarrito());
            cargarPeliculasEnCarrito();
            actualizarTotalCarrito();
        }
    }

    @FXML
    void accionComprarBoton(ActionEvent event) {
        double totalCarrito = Double.parseDouble(textFieldTotalCarrito.getText());
        double saldoCuenta = Double.parseDouble(textFieldSaldoCuenta.getText());
        if (totalCarrito <= saldoCuenta) {
            Transaccion transaccionNueva = new Transaccion();
            transaccionNueva.setCliente(clienteLogeado);
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            transaccionNueva.setFechaTransaccion(date);
            transaccionNueva.setTipoTransaccion(TipoTransaccion.COMPRA);
            int idTransaccionNueva = transaccionDAO.insertAndReturnId(transaccionNueva);
            Iterator<Pelicula> iterator = listaPeliculasCarrito.iterator();
            while (iterator.hasNext()) {
                Pelicula pelicula = iterator.next();
        
                LineaTransaccion lineaTransaccionNueva = new LineaTransaccion();
                lineaTransaccionNueva.setPelicula(pelicula);
                lineaTransaccionNueva.setPrecioPelicula(pelicula.getPrecio());
                lineaTransaccionNueva.setCantidad(1);
                lineaTransaccionNueva.setTotalLT(pelicula.getPrecio());
        
                lineaTransaccionNueva.setIdTransaccionLT(idTransaccionNueva);
                lineasTransaccionDAO.insert(lineaTransaccionNueva);
        
                iterator.remove();
                carritoPeliculasDAO.delete(clienteLogeado.getCarrito(), pelicula.getIdPelicula());
            }
            double saldoDiponible = saldoCuenta - totalCarrito;
            clienteLogeado.getTarjeta().setSaldoDiponible(saldoDiponible);
            actualizarSaldoCuenta();
            cargarPeliculasEnCarrito();
            actualizarTotalCarrito();
        } else {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "No tienes suficiente saldo");
        }

    }

    void actualizarTotalCarrito() {
        double total = 0;
        for (Pelicula pelicula : listaPeliculasCarrito) {
            total += pelicula.getPrecio();
        }
        textFieldTotalCarrito.setText(String.valueOf(total));
    }

    void actualizarSaldoCuenta() {
        tarjetaDAO.update(clienteLogeado.getTarjeta());
        textFieldSaldoCuenta.setText(String.valueOf(clienteLogeado.getTarjeta().getSaldoDiponible()));
    }

    void cargarPeliculasEnCarrito() {
        vboxContainer.getChildren().clear();
    
        for (Pelicula pelicula : listaPeliculasCarrito) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/cinema/view/peliculaCardCarritoView.fxml"));
                Pane pane = loader.load();
    
                PeliculaCardCarritoViewController controller = loader.getController();
                controller.setCarritoClienteViewController(this);
    
                controller.setPelicula(pelicula);
    
                vboxContainer.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
