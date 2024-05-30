package com.app.cinema.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.app.cinema.dao.ClienteDAO;
import com.app.cinema.dao.TarjetaDAO;
import com.app.cinema.dao.UsuarioDAO;
import com.app.cinema.enums.TipoUsuario;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Cuenta;
import com.app.cinema.model.Usuario;
import com.app.cinema.util.AlertUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * Esta clase es un controlador para la gestión de clientes en la aplicación Cinema.
 * Permite al administrador agregar, actualizar y eliminar clientes.
 * 
 * La vista muestra una tabla con la lista de clientes registrados en el sistema.
 * El administrador puede seleccionar un cliente de la tabla para ver y modificar sus detalles.
 * También puede agregar un nuevo cliente o eliminar un cliente existente.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class GestionDeClientesViewController {
    
    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn<Cliente, String> tableColumnNombre;
    @FXML
    private TableColumn<Cliente, String> tableColumnApellido;
    @FXML
    private TableColumn<Cliente, String> tableColumnCorreo;
    @FXML
    private TableColumn<Cliente, String> tableColumnContraseña;
    @FXML
    private TableColumn<Cliente, Date> tableColumnFechaNacimiento;
    @FXML
    private TableColumn<Cliente, Integer> tableColumnTelefono;
    @FXML
    private TableColumn<Cliente,Long> tableColumnTarjeta;
    @FXML
    private Button añadirBoton;
    @FXML
    private Button actualizarBoton;
    @FXML
    private Button eliminarBoton;
    @FXML
    private TextField fieldNombreTxt;
    @FXML
    private TextField fieldApellidoTxt;
    @FXML
    private TextField fieldCorreoTxt;    
    @FXML
    private PasswordField fieldContraseñaPsswrd;
    @FXML
    private DatePicker datePickerFechaNacimiento;
    @FXML
    private TextField fieldTelefonoTxt;

    Usuario usuario = new Usuario();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    List<Usuario> usuarios = usuarioDAO.selectAll();

    ClienteDAO clienteDAO = new ClienteDAO();
    List<Cliente> listaClientes = clienteDAO.selectAll();

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList(listaClientes);

    /**
     * Inicializa la vista de gestión de clientes.
     * Carga la lista de clientes desde la base de datos y la muestra en la tabla.
     */
    public void initialize(){
        this.tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.tableColumnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.tableColumnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        this.tableColumnContraseña.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
        this.tableColumnFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        this.tableColumnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        this.tableColumnTarjeta.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        this.tableViewClientes.setItems(clientes);
    }

    /**
     * Maneja el evento de selección de un cliente en la tabla.
     * Muestra los detalles del cliente seleccionado en los campos de texto correspondientes.
     * 
     * @param event El evento de clic del mouse.
     * @throws IOException Si hay un error de entrada/salida.
     */
    @FXML
    private void seleccionarClienteTabla(MouseEvent event) throws IOException {
        Cliente cliente = this.tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            this.fieldNombreTxt.setText(cliente.getNombre());
            this.fieldApellidoTxt.setText(cliente.getApellido());
            this.fieldCorreoTxt.setText(cliente.getCorreo());
            this.fieldContraseñaPsswrd.setText(cliente.getContraseña());

            java.util.Date fechaNacimientoUtil = cliente.getFechaNacimiento();
            java.sql.Date fechaNacimientoSql = new java.sql.Date(fechaNacimientoUtil.getTime());
            LocalDate localDate = fechaNacimientoSql.toLocalDate();

            this.datePickerFechaNacimiento.setValue(localDate);
            this.fieldTelefonoTxt.setText(cliente.getTelefono() + "");
        }
    }

    /**
     * Maneja el evento del botón para añadir un nuevo cliente.
     * Agrega un nuevo cliente a la base de datos y actualiza la tabla de clientes.
     * 
     * @param event El evento de acción del botón.
     */
    @FXML
    public void accionAñadirBoton(ActionEvent event) {
        try {
            String nombre = this.fieldNombreTxt.getText();
            String apellido = this.fieldApellidoTxt.getText();
            String correo = this.fieldCorreoTxt.getText();
            String contraseña = this.fieldContraseñaPsswrd.getText();
            LocalDate fechaNacimiento = this.datePickerFechaNacimiento.getValue();
            String telefono = this.fieldTelefonoTxt.getText();
            java.sql.Date fechaNacimientoSql = java.sql.Date.valueOf(fechaNacimiento);
            usuario = new Usuario(0, nombre, apellido, fechaNacimientoSql, correo, contraseña, TipoUsuario.CLIENTE);
            usuarios = usuarioDAO.selectAll();
            TarjetaDAO tarjetaDAO = new TarjetaDAO();
                // implementar algo aqui para asignarle una tarjeta a un cliente
            Cuenta tarjeta = tarjetaDAO.selectById(2);
            Cliente cliente = new Cliente(0, nombre, apellido, fechaNacimientoSql, correo, contraseña, TipoUsuario.CLIENTE, 0, null, Integer.parseInt(telefono), null, tarjeta);
            if (usuarios.contains(usuario) || clientes.contains(cliente) ) {
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Cliente ya registrado en el sistema");
            } else {
                int idUsuarioGenerado = usuarioDAO.insertAndReturnId(usuario);
                cliente.setIdUsuario(idUsuarioGenerado);
                clienteDAO.insert(cliente);
                List<Cliente> listClienteBD = clienteDAO.selectAll();
                Cliente ultimoClienteBD = listClienteBD.get(listClienteBD.size() - 1);
                Cliente clienteNuevo = new Cliente(idUsuarioGenerado, nombre, apellido, fechaNacimientoSql, correo, contraseña, TipoUsuario.CLIENTE, ultimoClienteBD.getCodiCliente(), null, Integer.parseInt(telefono), null, tarjeta);
                this.clientes.add(clienteNuevo);
                this.tableViewClientes.setItems(this.clientes);
                this.tableViewClientes.refresh();
                AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Cliente añadido correctamente");
            }
        } catch (NumberFormatException e) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Formato de Dato no válido");
        }
    }

    /**
     * Maneja el evento del botón para actualizar un cliente existente.
     * Actualiza los detalles del cliente en la base de datos y refresca la tabla de clientes.
     * 
     * @param event El evento de acción del botón.
     * @throws IOException Si hay un error de entrada/salida.
     */
    @FXML
    private void accionActualizarBoton(ActionEvent event) throws IOException {
        Cliente cliente = this.tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Cliente no seleccionado");
        } else {
            try {
                String nombre = this.fieldNombreTxt.getText();
                String apellido = this.fieldApellidoTxt.getText();
                String correo = this.fieldCorreoTxt.getText();
                String contraseña = this.fieldContraseñaPsswrd.getText();
                LocalDate fechaNacimiento = this.datePickerFechaNacimiento.getValue();
                String telefono = this.fieldTelefonoTxt.getText();
                java.sql.Date fechaNacimientoSql = java.sql.Date.valueOf(fechaNacimiento);

                boolean correoDuplicado = clientes.stream().anyMatch(c -> !c.equals(cliente) && c.getCorreo().equals(correo));
                boolean telefonoDuplicado = clientes.stream().anyMatch(c -> !c.equals(cliente) && c.getTelefono() == Integer.parseInt(telefono));
    
                if (correoDuplicado || telefonoDuplicado) {
                    AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Correo o Telefono duplicado");
                } else {
                    cliente.setNombre(nombre);
                    cliente.setApellido(apellido);
                    cliente.setCorreo(correo);
                    cliente.setContraseña(contraseña);
                    cliente.setFechaNacimiento(fechaNacimientoSql);
                    cliente.setTelefono(Integer.parseInt(telefono));
                    clienteDAO.update(cliente);
                    AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Cliente actualizado correctamente");
                    this.tableViewClientes.refresh();

                }
            } catch (NumberFormatException e) {
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Formato de dato no valido");
            }
        }
    }
    
    /**
     * Maneja el evento del botón para eliminar un cliente.
     * Elimina el cliente seleccionado de la base de datos y actualiza la tabla de clientes.
     * 
     * @param event El evento de acción del botón.
     */
    @FXML
    private void accionEliminarBoton(ActionEvent event) {
        Cliente cliente = this.tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Cliente no seleccionado");
        } else {
            this.clientes.remove(cliente);
            this.tableViewClientes.setItems(clientes);
            clienteDAO.delete(cliente);
            AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Cliente eliminado correctamente");
            this.tableViewClientes.refresh();
        }
    }
}
