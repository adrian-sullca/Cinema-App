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

/**
 * Esta clase es un controlador para la vista del carrito de compras del cliente en la aplicación de cinema.
 * Permite al cliente ver las películas que ha agregado al carrito, realizar compras y vaciar el carrito.
 * También muestra el saldo disponible en la cuenta del cliente y verifica si tiene una tarjeta vinculada.
 * 
 * Las operaciones disponibles incluyen:
 * - Mostrar las películas agregadas al carrito.
 * - Actualizar el total del carrito.
 * - Actualizar el saldo de la cuenta del cliente.
 * - Verificar si hay una tarjeta vinculada.
 * - Vaciar el carrito de compras.
 * - Realizar compras con las películas del carrito.
 * 
 * Esta clase utiliza instancias de DAO (Data Access Object) para interactuar con la base de datos
 * y realizar operaciones como insertar, eliminar y actualizar datos.
 * 
 * @author Adrian
 */
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

    /**
     * Inicializa la vista del carrito de compras.
     * Carga las películas agregadas al carrito, actualiza el total del carrito,
     * actualiza el saldo de la cuenta del cliente y verifica si hay una tarjeta vinculada.
     */
    public void initialize() {
        cargarPeliculasEnCarrito();
        actualizarTotalCarrito();
        actualizarSaldoCuenta();
        verificarTarjetaVinculada();
    }

    /**
     * Verifica si el cliente tiene una tarjeta vinculada.
     * Si no hay una tarjeta vinculada, muestra un mensaje en el campo de saldo y desactiva el botón de compra.
     */
    void verificarTarjetaVinculada() {
        if (clienteLogeado.getTarjeta() == null) {
            textFieldSaldoCuenta.setText("Tarjeta no vinculada");
            botonComprar.setDisable(true);
        } else {
            botonComprar.setDisable(false);
        }
    }

    /**
     * Maneja la acción del botón para vaciar el carrito de compras.
     * Muestra un mensaje de confirmación y vacía el carrito si el usuario confirma.
     * Actualiza la vista del carrito y el total del carrito.
     * 
     * @param event El evento de acción del botón.
     */
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

    /**
     * Maneja la acción del botón para realizar una compra.
     * Verifica si el cliente tiene una tarjeta vinculada y si tiene saldo suficiente.
     * Realiza la transacción y actualiza el saldo de la cuenta del cliente.
     * 
     * @param event El evento de acción del botón.
     */
    @FXML
    void accionComprarBoton(ActionEvent event) {
        if (clienteLogeado.getTarjeta() == null) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "No tienes una tarjeta vinculada.");
            return;
        }

        double totalCarrito = Double.parseDouble(textFieldTotalCarrito.getText());
        double saldoCuenta = Double.parseDouble(textFieldSaldoCuenta.getText());

        if (totalCarrito <= saldoCuenta) {
            Transaccion transaccionNueva = new Transaccion();
            transaccionNueva.setCliente(clienteLogeado);
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            transaccionNueva.setFechaTransaccion(date);
            transaccionNueva.setTipoTransaccion(TipoTransaccion.COMPRA);
            transaccionNueva.setTotal(totalCarrito);
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

    /**
     * Actualiza el total del carrito de compras.
     * Calcula el total sumando los precios de todas las películas en el carrito.
     */
    void actualizarTotalCarrito() {
        double total = 0;
        for (Pelicula pelicula : listaPeliculasCarrito) {
            total += pelicula.getPrecio();
        }
        textFieldTotalCarrito.setText(String.valueOf(total));
    }

    /**
     * Actualiza el saldo de la cuenta del cliente.
     * Actualiza el saldo disponible en la tarjeta del cliente y muestra el nuevo saldo en la vista.
     */
    void actualizarSaldoCuenta() {
        if (clienteLogeado.getTarjeta() != null) {
            tarjetaDAO.update(clienteLogeado.getTarjeta());
            textFieldSaldoCuenta.setText(String.valueOf(clienteLogeado.getTarjeta().getSaldoDiponible()));
        } else {
            textFieldSaldoCuenta.setText("Tarjeta no vinculada");
        }
    }

    /**
     * Carga las películas agregadas al carrito en la vista.
     * Utiliza un VBox para mostrar las tarjetas de películas en el carrito.
     * Cada tarjeta de película muestra información como el título, el precio y la cantidad.
     */
    void cargarPeliculasEnCarrito() {
        vboxContainer.getChildren().clear();

        for (Pelicula pelicula : listaPeliculasCarrito) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/app/cinema/view/peliculaCardCarritoView.fxml"));
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
