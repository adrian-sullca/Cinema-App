package com.app.cinema.controller;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.cinema.dao.ClienteDAO;
import com.app.cinema.dao.LineasTransaccionDAO;
import com.app.cinema.dao.PeliculaDAO;
import com.app.cinema.dao.TransaccionDAO;
import com.app.cinema.enums.TipoTransaccion;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.LineaTransaccion;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Transaccion;
import com.app.cinema.util.AlertUtils;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

/**
 * Esta clase es un controlador para la gestión de transacciones en la aplicación Cinema.
 * Permite al personal registrar nuevas transacciones de compra de películas y visualizar transacciones existentes.
 * 
 * La vista muestra un formulario para registrar una nueva transacción y una tabla con las transacciones registradas.
 * También muestra los detalles de las líneas de transacción asociadas a una transacción seleccionada.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class GestionDeTransaccionesViewController {

    @FXML
    private ComboBox<Pelicula> cmbxPelicula;
    @FXML
    private ComboBox<TipoTransaccion> cmbxTipoTransaccion;
    @FXML
    private ComboBox<Cliente> cmbxCliente;
    @FXML
    private TextField fieldPrecio;
    @FXML
    private DatePicker datePickerFTransaccion;
    @FXML
    private Button botonAñadir;
    @FXML
    private DatePicker datePickerFActual;
    @FXML
    private Button botonDevolver;
    @FXML
    private TableView<Transaccion> tableViewTransacciones;
    @FXML
    private TableColumn<Transaccion, Integer> tableColumnIdTransaccion;
    @FXML
    private TableColumn<Transaccion, Double> tableColumnTotal;
    @FXML
    private TableColumn<Transaccion, TipoTransaccion> tableColumnTipoTransaccion;
    @FXML
    private TableColumn<Transaccion, Date> tableColumnFecha;
    @FXML
    private TableColumn<Transaccion, String> tableColumnCliente;

    @FXML
    private TableView<LineaTransaccion> tableViewLineasTransacciones;
    @FXML
    private TableColumn<LineaTransaccion, Double> tableColumnPrecioPelicula;
    @FXML
    private TableColumn<LineaTransaccion, Integer> tableColumnCantidad;
    @FXML
    private TableColumn<LineaTransaccion, Double> tableColumnTotalLT;
    @FXML
    private TableColumn<LineaTransaccion, String> tableColumnTituloPelicula;
    @FXML
    private TableColumn<LineaTransaccion, Integer> tableColumnIdLineaTransaccion;

    ClienteDAO clienteDAO = new ClienteDAO();
    List<Cliente> listaClientes = clienteDAO.selectAll();

    PeliculaDAO peliculaDAO = new PeliculaDAO();
    List<Pelicula> listaPeliculas = peliculaDAO.selectAll();
    private ObservableList<Pelicula> peliculas = FXCollections.observableArrayList(listaPeliculas);
    private ObservableList<Cliente> clientes = FXCollections.observableArrayList(listaClientes);

    TransaccionDAO transaccionDAO = new TransaccionDAO();
    List<Transaccion> listaTransacciones = transaccionDAO.selectAll();
    private ObservableList<Transaccion> transacciones = FXCollections.observableArrayList(listaTransacciones);

    LineasTransaccionDAO lineasTransaccionDAO = new LineasTransaccionDAO();
    List<LineaTransaccion> listaLineaTransaccions = lineasTransaccionDAO.selectAll();
    private ObservableList<LineaTransaccion> lineasTransacciones = FXCollections.observableArrayList(listaLineaTransaccions);

    /**
     * Inicializa la vista de gestión de transacciones.
     * Carga los datos de las películas, clientes y transacciones desde la base de datos y los muestra en la vista.
     */
    public void initialize() {
        initComboBox();
        cargarDatosTableViewTransacciones();
        cargarDatosTableViewLineasTransacciones();
        setearPrecioSegunPeliculaComboBox();
        setearFechaActual();
    }

    @FXML
    void accionDevolverBoton(ActionEvent event) throws IOException {
        //MODIFICAR: IMPLEMENTAR ACCION DE DEVOLVER
    }

    /**
     * Maneja el evento del botón para añadir una nueva transacción al sistema.
     * Valida los datos ingresados, crea la nueva transacción y la guarda en la base de datos.
     * 
     * @param event El evento de acción del botón.
     */
    @FXML
    void accionAñadirBoton(ActionEvent event) throws IOException {
        TipoTransaccion tipoTransaccionSeleccionado = cmbxTipoTransaccion.getValue();
        if (tipoTransaccionSeleccionado == TipoTransaccion.COMPRA) {
        Pelicula peliculaSeleccionada = cmbxPelicula.getValue();
        Cliente clienteSeleccionado = cmbxCliente.getValue();
            String pelicula = cmbxPelicula.getValue().getTitulo();
            String cliente = cmbxCliente.getValue().getNombre() + " " + cmbxCliente.getValue().getApellido();
            String tipoTransaccion = tipoTransaccionSeleccionado.toString();
            double precio = Double.parseDouble(fieldPrecio.getText());
            Date fechaActual = Date.valueOf(datePickerFActual.getValue());
            double total = precio;
            int cantidad = 1;
            List<LineaTransaccion> listLineaTransaccionNueva = new ArrayList<>();
            LineaTransaccion lineaTransaccion = new LineaTransaccion(0, 0, peliculaSeleccionada, precio, cantidad, total);
            listLineaTransaccionNueva.add(lineaTransaccion);
            Transaccion transaccion = new Transaccion(0, clienteSeleccionado, fechaActual, tipoTransaccionSeleccionado, total, listLineaTransaccionNueva);
            // Insertar transacción en la base de datos y obtener su ID generado
            int idTransaccionGenerada = transaccionDAO.insertAndReturnId(transaccion);
            // Asignar el ID de la transacción a la transacción
            transaccion.setIdTransaccion(idTransaccionGenerada);
            System.out.println("TRANSACICOM CON ID SETEADA");
            System.out.println(transaccion);
            System.out.println("TRANSACCION GENERADA ID");
            System.out.println(idTransaccionGenerada);
            // Asignar el ID de la transacción a la línea de transacción
            lineaTransaccion.setIdTransaccionLT(transaccion.getIdTransaccion());
            System.out.println("LINEA TRANSACCION antes de inserta a bd");
            System.out.println(lineaTransaccion);
            // Insertar la línea de transacción en la base de datos
            lineasTransaccionDAO.insert(lineaTransaccion);
            // Actualizar la lista de transacciones
            transacciones.add(transaccion);
            // Refrescar la tabla de transacciones
            tableViewTransacciones.setItems(FXCollections.observableArrayList(transacciones));
            tableViewTransacciones.refresh();
        } else {
            AlertUtils.mostrarVentanaError(javafx.scene.control.Alert.AlertType.ERROR, "Error", "Solo se pueden añadir compras");
        }
    }

    /**
     * Carga los datos de las transacciones en la tabla de transacciones.
     */
    private void cargarDatosTableViewTransacciones() {
        this.tableColumnIdTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));
        this.tableColumnTipoTransaccion.setCellValueFactory(new PropertyValueFactory<>("tipoTransaccion"));
        this.tableColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaTransaccion"));
        this.tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        this.tableColumnCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNombre() + " " + cellData.getValue().getCliente().getApellido()));
        this.tableViewTransacciones.setItems(transacciones);
    }

    private void cargarDatosTableViewLineasTransacciones(){
        this.tableColumnIdLineaTransaccion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdLineaTransaccion()));
        this.tableColumnTituloPelicula.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPelicula().getTitulo()));
        this.tableColumnPrecioPelicula.setCellValueFactory(new PropertyValueFactory<>("precioPelicula"));
        this.tableColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        this.tableColumnTotalLT.setCellValueFactory(new PropertyValueFactory<>("totalLT"));
    }

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
     * Inicializa los ComboBox de la vista con información de la base de datos de los clientes y películas
     * y el combobox de tipo de transacción.
     */
    private void initComboBox() {
        cmbxCliente.setItems(clientes);
        cmbxCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente != null ? cliente.toComboBoxString() : "";
            }

            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });
        this.cmbxTipoTransaccion.setItems(FXCollections.observableArrayList(TipoTransaccion.values()));
        cmbxPelicula.setItems(peliculas);
        cmbxPelicula.setConverter(new StringConverter<Pelicula>() {
            @Override
            public String toString(Pelicula pelicula) {
                return pelicula != null ? pelicula.toComboBoxString() : "";
            }

            @Override
            public Pelicula fromString(String string) {
                return null;
            }
        });
    }

    /**
     * Establece el precio de la película seleccionada en el campo de texto de precio.
     */
    private void setearPrecioSegunPeliculaComboBox() {
        cmbxPelicula.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fieldPrecio.setText(String.valueOf(newValue.getPrecio()));
            } else {
                fieldPrecio.setText("");
            }
        });
    }

    /**
     * Establece la fecha actual en el campo de fecha de la transacción.
     */
    private void setearFechaActual() {
        datePickerFActual.setValue(LocalDate.now());
        datePickerFActual.setDisable(true);
    }
}