package com.app.cinema.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.app.cinema.dao.TrabajadorDAO;
import com.app.cinema.dao.UsuarioDAO;
import com.app.cinema.enums.Rol;
import com.app.cinema.enums.TipoUsuario;
import com.app.cinema.model.DNI;
import com.app.cinema.model.Trabajador;
import com.app.cinema.model.Usuario;
import com.app.cinema.util.AlertUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * Esta clase es un controlador para la gestión de trabajadores en la aplicación Cinema.
 * Permite al administrador añadir, actualizar o eliminar trabajadores del sistema.
 * 
 * La vista muestra una tabla con la lista de trabajadores registrados en el sistema.
 * El administrador puede seleccionar un trabajador de la tabla para ver o modificar sus detalles.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class GestionDeTrabajadoresViewController {
    @FXML
    private ComboBox<Rol> cmbxRolTrabajador;
    @FXML
    private TableView<Trabajador> tableViewTrabajadores;
    @FXML
    private TableColumn<Trabajador, Rol> tableColumnRol;
    @FXML
    private TableColumn<Trabajador, String> tableColumnNombre;
    @FXML
    private TableColumn<Trabajador, String> tableColumnApellido;
    @FXML
    private TableColumn<Trabajador, String> tableColumnCorreo;
    @FXML
    private TableColumn<Trabajador, String> tableColumnContraseña;
    @FXML
    private TableColumn<Trabajador, Date> tableColumnFechaNacimiento;
    @FXML
    private TableColumn<Trabajador, String> tableColumnDNI;
    @FXML
    private TableColumn<Trabajador,Date> tableColumnFechaAlta;
    @FXML
    private TableColumn<Trabajador, Double> tableColumnSalario;
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
    private TextField fieldDNITxt;  
    @FXML
    private DatePicker datePickerFechaAlta;
    @FXML
    private TextField fieldSalarioTxt;

    Usuario usuario = new Usuario();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    List<Usuario> usuarios = usuarioDAO.selectAll();

    TrabajadorDAO trabajadorDAO = new TrabajadorDAO();
    List<Trabajador> listaTrabajadores = trabajadorDAO.selectAll();

    private ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList(listaTrabajadores);


    /**
     * Inicializa la vista de gestión de trabajadores.
     * Carga la lista de trabajadores desde la base de datos y la muestra en la tabla.
     */
    public void initialize(){
        initCmbxRolTrabajador();
        this.tableColumnRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        this.tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.tableColumnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.tableColumnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        this.tableColumnContraseña.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
        this.tableColumnFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        
        this.tableColumnDNI.setCellValueFactory(cellData -> {
        DNI dni = cellData.getValue().getdNI();
        return new SimpleStringProperty(dni != null ? dni.toString() : "");
        });

        this.tableColumnFechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        this.tableColumnSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        
        this.tableViewTrabajadores.setItems(trabajadores);
    }

    /**
     * Maneja el evento de selección de un trabajador en la tabla.
     * Muestra los detalles del trabajador seleccionado en los campos de texto correspondientes.
     * 
     * @param event El evento de clic del mouse.
     * @throws IOException Si hay un error de entrada/salida.
     */
    @FXML
    private void seleccionarTrabajadorTabla(MouseEvent event) throws IOException {
        Trabajador trabajador = this.tableViewTrabajadores.getSelectionModel().getSelectedItem();
        if (trabajador != null) {
            this.cmbxRolTrabajador.setValue(trabajador.getRol());
            this.fieldNombreTxt.setText(trabajador.getNombre());
            this.fieldApellidoTxt.setText(trabajador.getApellido());
            this.fieldCorreoTxt.setText(trabajador.getCorreo());
            this.fieldContraseñaPsswrd.setText(trabajador.getContraseña());
            java.util.Date fechaNacimientoUtil = trabajador.getFechaNacimiento();
            java.sql.Date fechaNacimientoSql = new java.sql.Date(fechaNacimientoUtil.getTime());
            LocalDate localDateFNAC = fechaNacimientoSql.toLocalDate();
            this.datePickerFechaNacimiento.setValue(localDateFNAC);
            this.fieldDNITxt.setText(String.format("%08d%c", trabajador.getdNI().getNumero(), trabajador.getdNI().getLetra()));
            java.util.Date fechaAltaUtil = trabajador.getFechaAlta();
            java.sql.Date fechaAltaSql = new java.sql.Date(fechaAltaUtil.getTime());
            LocalDate localDateFALT = fechaAltaSql.toLocalDate();
            this.datePickerFechaAlta.setValue(localDateFALT);
            this.fieldSalarioTxt.setText(trabajador.getSalario() + "");
        }
    }

    /**
     * Maneja el evento del botón para añadir un nuevo trabajador al sistema.
     * Valida los datos ingresados, añade el nuevo trabajador a la base de datos y actualiza la tabla.
     * 
     * @param event El evento de acción del botón.
     */
    @FXML
    public void accionAñadirBoton(ActionEvent event) {
        try {
            Rol rol = this.cmbxRolTrabajador.getValue();
            String nombre = this.fieldNombreTxt.getText();
            String apellido = this.fieldApellidoTxt.getText();
            String correo = this.fieldCorreoTxt.getText();
            String contraseña = this.fieldContraseñaPsswrd.getText();
            LocalDate fechaNacimiento = this.datePickerFechaNacimiento.getValue();
            String dni = this.fieldDNITxt.getText();
            // MODIFICAR: PERMITIR QUE EL DNI SOLO SEA DE 8 NUMEROS Y UNA LETRA
            String numeroDNI = dni.substring(0, dni.length() - 1);
            char letraDNI = dni.charAt(dni.length() - 1);
            DNI dniTrabajador = new DNI(Integer.parseInt(numeroDNI), letraDNI);
            LocalDate fechaAlta = this.datePickerFechaAlta.getValue();
            String salario = this.fieldSalarioTxt.getText();
            java.sql.Date fechaNacimientoSql = java.sql.Date.valueOf(fechaNacimiento);
            java.sql.Date fechaAltaSql = java.sql.Date.valueOf(fechaAlta);
            Usuario usuario = new Usuario(0, nombre, apellido, fechaNacimientoSql, correo, contraseña, TipoUsuario.TRABAJADOR);
            if (trabajadorDAO.existsByDni(dniTrabajador)) {
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Trabajador ya registrado en el sistema con este DNI");
            } else {
                List<Usuario> usuarios = usuarioDAO.selectAll();
                boolean usuarioExists = usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo));
    
                if (usuarioExists) {
                    AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Usuario ya registrado en el sistema con este correo");
                } else {
                    int idUsuarioGenerado = usuarioDAO.insertAndReturnId(usuario);
                    Trabajador trabajador = new Trabajador(0, nombre, apellido, fechaNacimientoSql, correo, contraseña, TipoUsuario.TRABAJADOR, 0, fechaAltaSql, Double.parseDouble(salario), rol, dniTrabajador);
                    trabajador.setIdUsuario(idUsuarioGenerado);
                    trabajadorDAO.insert(trabajador);
                    trabajadores.add(trabajador);
                    this.tableViewTrabajadores.setItems(FXCollections.observableArrayList(trabajadores));
                    this.tableViewTrabajadores.refresh();
                    AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Trabajador añadido correctamente");
                }
            }
        } catch (NumberFormatException e) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Formato de Dato no válido");
        }
    }
    

    /**
     * Maneja el evento del botón para actualizar los datos de un trabajador existente.
     * Valida los datos modificados, actualiza los detalles del trabajador en la base de datos y refresca la tabla.
     * 
     * @param event El evento de acción del botón.
     * @throws IOException Si hay un error de entrada/salida.
     */
    @FXML
    private void accionActualizarBoton(ActionEvent event) throws IOException {
        Trabajador trabajador = this.tableViewTrabajadores.getSelectionModel().getSelectedItem();
        if (trabajador == null) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Trabajador no seleccionado");
        } else {
            try {
                Rol rol = this.cmbxRolTrabajador.getValue();
                String nombre = this.fieldNombreTxt.getText();
                String apellido = this.fieldApellidoTxt.getText();
                String correo = this.fieldCorreoTxt.getText();
                String contraseña = this.fieldContraseñaPsswrd.getText();
                LocalDate fechaNacimiento = this.datePickerFechaNacimiento.getValue();
                String dni = this.fieldDNITxt.getText();
                // MODIFICAR: PERMITIR QUE EL DNI SOLO SEA DE 8 NUMEROS Y UNA LETRA
                String numeroDNI = dni.substring(0, dni.length() - 1);
                char letraDNI = dni.charAt(dni.length() - 1);
                DNI dniTrabajador = new DNI(Integer.parseInt(numeroDNI), letraDNI);
                LocalDate fechaAlta = this.datePickerFechaAlta.getValue();
                String salario = this.fieldSalarioTxt.getText();
                java.sql.Date fechaNacimientoSql = java.sql.Date.valueOf(fechaNacimiento);
                java.sql.Date fechaAltaSql = java.sql.Date.valueOf(fechaAlta);

                boolean correoDuplicado = trabajadores.stream().anyMatch(t -> !t.equals(trabajador) && t.getCorreo().equals(correo));
                boolean dniDuplicado = trabajadores.stream().anyMatch(t -> !t.equals(trabajador) && t.getdNI().equals(dniTrabajador));
    
                if (correoDuplicado || dniDuplicado) {
                    AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Correo o DNI duplicado");
                } else {
                    trabajador.setRol(rol);
                    trabajador.setNombre(nombre);
                    trabajador.setApellido(apellido);
                    trabajador.setCorreo(correo);
                    trabajador.setContraseña(contraseña);
                    trabajador.setFechaNacimiento(fechaNacimientoSql);
                    trabajador.setdNI(dniTrabajador);
                    trabajador.setFechaAlta(fechaAltaSql);
                    trabajador.setSalario(Double.parseDouble(salario));
                    trabajadorDAO.update(trabajador);
                    AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Trabajador actualizado correctamente");
                    this.tableViewTrabajadores.refresh();

                }
            } catch (NumberFormatException e) {
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Formato de dato no valido");
            }
        }
    }
    
    /**
     * Maneja el evento del botón para eliminar un trabajador del sistema.
     * Elimina al trabajador seleccionado de la base de datos y actualiza la tabla.
     * 
     * @param event El evento de acción del botón.
     */
    @FXML
    private void accionEliminarBoton(ActionEvent event) {
        Trabajador Trabajador = this.tableViewTrabajadores.getSelectionModel().getSelectedItem();
        if (Trabajador == null) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Trabajador no seleccionado");
        } else {
            this.trabajadores.remove(Trabajador);
            this.tableViewTrabajadores.setItems(trabajadores);
            trabajadorDAO.delete(Trabajador);
            AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Trabajador eliminado correctamente");
            this.tableViewTrabajadores.refresh();
        }
    }
    
    /**
     * Inicializa el ComboBox para seleccionar el rol del trabajador.
     */
    private void initCmbxRolTrabajador() {
        this.cmbxRolTrabajador.setItems(FXCollections.observableArrayList(Rol.values()));
    }


}
