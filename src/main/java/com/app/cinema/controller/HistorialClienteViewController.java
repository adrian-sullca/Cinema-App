package com.app.cinema.controller;

import java.sql.Date;
import java.util.List;

import com.app.cinema.dao.LineasTransaccionDAO;
import com.app.cinema.dao.TransaccionDAO;
import com.app.cinema.enums.TipoTransaccion;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.LineaTransaccion;
import com.app.cinema.model.SesionUsuario;
import com.app.cinema.model.Transaccion;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * Este controlador maneja la vista del historial de transacciones de un cliente en la aplicación Cinema.
 * Permite al cliente visualizar las transacciones realizadas y las líneas de transacción asociadas.
 * También proporciona la funcionalidad para devolver películas en caso de compras.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class HistorialClienteViewController {
    
    @FXML
    private TableView<Transaccion> tableViewTransacciones;
    @FXML
    private TableView<LineaTransaccion> tableViewLineasTransacciones;
    @FXML
    private TableColumn<LineaTransaccion, Integer> tableColumnIdLineaTransaccion;
    @FXML
    private TableColumn<LineaTransaccion, String> tableColumnTituloPelicula;
    @FXML
    private TableColumn<LineaTransaccion, Double> tableColumnPrecioPelicula;
    @FXML
    private TableColumn<LineaTransaccion, Integer> tableColumnCantidad;
    @FXML
    private TableColumn<LineaTransaccion, Double> tableColumnTotalLT;
    
    @FXML
    private TableColumn<Transaccion, Integer> tableColumnIdTransaccion;
    @FXML
    private TableColumn<Transaccion, TipoTransaccion> tableColumnTipoTransaccion;
    @FXML
    private TableColumn<Transaccion, Date> tableColumnFecha;
    @FXML
    private TableColumn<Transaccion, Double> tableColumnTotal;

    @FXML
    private Button botonDevolver;

    Cliente clienteLogeado = SesionUsuario.getClienteLogeado();
    TransaccionDAO transaccionDAO = new TransaccionDAO();
    List<Transaccion> listaTransacciones = transaccionDAO.selectByCodiCliente(clienteLogeado.getCodiCliente());

    private ObservableList<Transaccion> transacciones = FXCollections.observableArrayList(listaTransacciones);

    LineasTransaccionDAO lineasTransaccionDAO = new LineasTransaccionDAO();
    List<LineaTransaccion> listaLineaTransaccions = lineasTransaccionDAO.selectAll();
    private ObservableList<LineaTransaccion> lineasTransacciones = FXCollections.observableArrayList(listaLineaTransaccions);

    /**
     * Inicializa la vista del historial de transacciones del cliente.
     * Carga los datos de las transacciones realizadas por el cliente desde la base de datos y los muestra en la vista.
     */
    public void initialize() {
        cargarDatosTableViewTransacciones();
        cargarDatosTableViewLineasTransacciones();
    }

    /**
     * Carga los datos de las transacciones del cliente en la tabla de transacciones.
     */
    private void cargarDatosTableViewTransacciones() {
        this.tableColumnIdTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));
        this.tableColumnTipoTransaccion.setCellValueFactory(new PropertyValueFactory<>("tipoTransaccion"));
        this.tableColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaTransaccion"));
        this.tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        this.tableViewTransacciones.setItems(transacciones);
    }

    /**
     * Carga los datos de las líneas de transacción en la tabla de líneas de transacción.
     */
    private void cargarDatosTableViewLineasTransacciones(){
        this.tableColumnIdLineaTransaccion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdLineaTransaccion()));
        this.tableColumnTituloPelicula.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPelicula().getTitulo()));
        this.tableColumnPrecioPelicula.setCellValueFactory(new PropertyValueFactory<>("precioPelicula"));
        this.tableColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        this.tableColumnTotalLT.setCellValueFactory(new PropertyValueFactory<>("totalLT"));
    }

    /**
     * Maneja el evento de selección de una transacción en la tabla de transacciones.
     * Carga las líneas de transacción asociadas a la transacción seleccionada.
     * 
     * @param event El evento de clic del mouse.
     */
    @FXML
    void seleccionarTransaccionTabla(MouseEvent event) {
        Transaccion seleccionada = tableViewTransacciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            cargarLineasDeTransaccionSeleccionada(seleccionada);
        }
    }

    /**
     * Carga los datos de las líneas de transacción asociadas a una transacción seleccionada en la tabla.
     * 
     * @param transaccion La transacción seleccionada.
     */
    private void cargarLineasDeTransaccionSeleccionada(Transaccion transaccion) {
        List<LineaTransaccion> lineasFiltradas = lineasTransaccionDAO.selectByTransaccionId(transaccion.getIdTransaccion());
        ObservableList<LineaTransaccion> lineas = FXCollections.observableArrayList(lineasFiltradas);
        tableViewLineasTransacciones.setItems(lineas);
    }

    /**
     * Maneja el evento del botón para devolver una película en caso de compra.
     * Permite al cliente devolver una película adquirida en una transacción de compra.
     * 
     * @param event El evento de acción del botón.
     */
    @FXML
    void accionDevolverBoton(ActionEvent event) {
        Transaccion transaccionSeleccionada = tableViewTransacciones.getSelectionModel().getSelectedItem();
        LineaTransaccion lineaSeleccionada = tableViewLineasTransacciones.getSelectionModel().getSelectedItem();
        
        if (transaccionSeleccionada != null && lineaSeleccionada != null) {
            if (transaccionSeleccionada.getTipoTransaccion() == TipoTransaccion.COMPRA) {
                // Proceder con la devolución
                Transaccion nuevaTransaccion = new Transaccion();
                nuevaTransaccion.setTipoTransaccion(TipoTransaccion.DEVOLUCION);
                nuevaTransaccion.setFechaTransaccion(new Date(System.currentTimeMillis()));
                nuevaTransaccion.setCliente(transaccionSeleccionada.getCliente());
 
                LineaTransaccion nuevaLineaTransaccion = new LineaTransaccion();
                nuevaLineaTransaccion.setPelicula(lineaSeleccionada.getPelicula());
                nuevaLineaTransaccion.setCantidad(lineaSeleccionada.getCantidad());
                nuevaLineaTransaccion.setPrecioPelicula(lineaSeleccionada.getPrecioPelicula());
                nuevaLineaTransaccion.setTotalLT(-lineaSeleccionada.getTotalLT());

                //transaccionDAO.insert(nuevaTransaccion); // Asegúrate de que este método establece el ID de la nueva transacción
                //lineasTransaccionDAO.insert(nuevaLineaTransaccion);
                // Actualizar la vista o mostrar mensaje de confirmación
                tableViewTransacciones.getItems().add(nuevaTransaccion);
                tableViewLineasTransacciones.getItems().add(nuevaLineaTransaccion);
                System.out.println("Devolución realizada con éxito.");
                System.out.println(nuevaTransaccion);
                System.out.println("linea generada");
                System.out.println(nuevaLineaTransaccion);
            } else {
                System.out.println("Solo se pueden devolver compras.");
            }
        } else {
            System.out.println("Debe seleccionar una transacción y una línea de transacción para devolver.");
        }
    }

}
