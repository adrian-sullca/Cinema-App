package com.app.cinema.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import com.app.cinema.App;
import com.app.cinema.dao.ClienteDAO;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.SesionUsuario;
import com.app.cinema.util.AlertUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Este controlador maneja la vista del perfil de un cliente en la aplicación Cinema.
 * Permite al cliente ver y editar su información personal, vincular una tarjeta,
 * recargar saldo, cambiar su foto de perfil, entre otras funcionalidades.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class PerfilClienteViewController {

    @FXML
    private TextField fieldSaldoTxt;

    @FXML
    private TextField fieldComentarioPrefTxt;

    @FXML
    private ImageView imageViewFotoCliente;

    @FXML
    private TextField fieldTarjetaVinculadaTxt;

    @FXML
    private TextField fieldApellidoTxt;

    @FXML
    private DatePicker datePickerFechaNacimiento;

    @FXML
    private Button botonActualizarDatos;

    @FXML
    private TextField fieldTelefonoTxt;

    @FXML
    private Button botonVincularTarjeta;

    @FXML
    private Button botonCambiarFoto;

    @FXML
    private TextField fieldCorreoTxt;

    @FXML
    private TextField fieldNombreTxt;

    @FXML
    private Button botonRecargarSaldo;

    Cliente cliente = new Cliente();
    ClienteDAO clienteDAO = new ClienteDAO();
    List<Cliente> listaCliente = clienteDAO.selectAll();

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList(listaCliente);

    Cliente clienteLogeado = SesionUsuario.getClienteLogeado();
    
    /**
     * Inicializa la vista del perfil del cliente.
     */
    @FXML
    public void initialize() {
        System.out.println("Cliente logeado desde perfil view");
        System.out.println(clienteLogeado);
        actualizarCamposCliente(clienteLogeado);
    }

    /**
     * Actualiza los campos de la vista con los datos del cliente.
     * 
     * @param cliente El cliente cuyos datos se van a mostrar.
     */
    private void actualizarCamposCliente(Cliente cliente) {
        fieldNombreTxt.setText(cliente.getNombre());
        fieldApellidoTxt.setText(cliente.getApellido());
        fieldCorreoTxt.setText(cliente.getCorreo());
        
        if (cliente.getTarjeta() != null) {
            fieldTarjetaVinculadaTxt.setText(cliente.getTarjeta().getNumeroCuenta() + "");
            fieldSaldoTxt.setText(cliente.getTarjeta().getSaldoDiponible() + "");
        } else {
            fieldTarjetaVinculadaTxt.setText("No vinculada");
            fieldSaldoTxt.setText("0");
        }
        
        fieldTelefonoTxt.setText(cliente.getTelefono() + "");
        fieldComentarioPrefTxt.setText(cliente.getComentarioPref());
        
        java.util.Date fechaNacimientoUtil = cliente.getFechaNacimiento();
        if (fechaNacimientoUtil != null) {
            java.sql.Date fechaNacimientoSql = new java.sql.Date(fechaNacimientoUtil.getTime());
            LocalDate localDateFN = fechaNacimientoSql.toLocalDate();
            this.datePickerFechaNacimiento.setValue(localDateFN);
        } else {
            this.datePickerFechaNacimiento.setValue(null);
        }
        
        String rutaImagen = "/com/app/cinema/img/" + cliente.getFotoPerfil();
        if (cliente.getFotoPerfil() != null && !cliente.getFotoPerfil().isEmpty()) {
            URL imageUrl = getClass().getResource(rutaImagen);
            if (imageUrl != null) {
                Image image = new Image(imageUrl.toString());
                ajustarImagen(image);
            } else {
                System.out.println("No se pudo encontrar la imagen en la ruta: " + rutaImagen);
            }
        } else {
            // Imagen por defecto si no hay foto de perfil
            Image image = new Image(getClass().getResource("/com/app/cinema/img/perfilDefault.jpg").toString());
            ajustarImagen(image);
        }
    }

    private void ajustarImagen(Image image) {
        imageViewFotoCliente.setImage(image);
        imageViewFotoCliente.setFitWidth(194);
        imageViewFotoCliente.setFitHeight(261);
        imageViewFotoCliente.setPreserveRatio(false);
        imageViewFotoCliente.setSmooth(true);
        imageViewFotoCliente.setCache(true);
    }
    
    /**
     * Maneja el evento de vincular una tarjeta al cliente.
     * 
     * @param event El evento que desencadena esta acción.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @FXML
    void accionVincularTarjetaBoton(ActionEvent event) throws IOException {
        String rutaFXML = "/com/app/cinema/view/vincularTarjetaView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
        AnchorPane vincularTarjetaPane = loader.load();
        VincularTarjetaViewController vincularTarjetaViewController = loader.getController();
        Stage vincularTarjetaStage = new Stage();
        vincularTarjetaStage.initModality(Modality.APPLICATION_MODAL);
        vincularTarjetaStage.initStyle(StageStyle.DECORATED); 
        vincularTarjetaStage.setTitle("Vincular Tarjeta"); 
        Scene scene = new Scene(vincularTarjetaPane);
        vincularTarjetaStage.setScene(scene);
        vincularTarjetaStage.setResizable(false);
        vincularTarjetaStage.showAndWait();
    }

    /**
     * Maneja el evento de editar los datos del cliente.
     * 
     * @param event El evento que desencadena esta acción.
     */
    @FXML
    void accionEditarDatosBoton(ActionEvent event) {
        String nombreNuevo = this.fieldNombreTxt.getText();
        String apellidoNuevo = this.fieldApellidoTxt.getText();
        String correo = this.fieldCorreoTxt.getText();
        String telefono = this.fieldTelefonoTxt.getText();
        LocalDate fechaNacimientoNueva = this.datePickerFechaNacimiento.getValue();
        java.sql.Date fechaNacimientoSql = null;
        
        if (fechaNacimientoNueva != null) {
            fechaNacimientoSql = java.sql.Date.valueOf(fechaNacimientoNueva);
        }

        boolean correoDuplicado = clientes.stream().anyMatch(c -> !c.equals(clienteLogeado) && c.getCorreo().equals(correo));
        if (correoDuplicado) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Correo duplicado");
        } else {
            clienteLogeado.setNombre(nombreNuevo);
            clienteLogeado.setApellido(apellidoNuevo);
            clienteLogeado.setCorreo(correo);
            clienteLogeado.setTelefono(Integer.parseInt(telefono));
            clienteLogeado.setFechaNacimiento(fechaNacimientoSql);
            AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Perfil actualizado correctamente");
            clienteDAO.update(clienteLogeado);
            actualizarCamposCliente(clienteLogeado);
        }
    }

    /**
     * Maneja el evento de cambiar la foto de perfil del cliente.
     * 
     * @param event El evento que desencadena esta acción.
     */
    @FXML
    void accionCambiarFotoBoton(ActionEvent event) {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*.png", "*.jpg"));

        File file = openFile.showOpenDialog(App.getPrimaryStage());
        if (file != null) {
            try {
                String destinationDir = "src/main/resources/com/app/cinema/img/";
                File destinationDirFile = new File(destinationDir);
                if (!destinationDirFile.exists()) {
                    destinationDirFile.mkdirs();
                }
                File destinationFile = new File(destinationDir + file.getName());

                Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                String relativePath = "/com/app/cinema/img/" + file.getName();
                Image image = new Image(destinationFile.toURI().toString());
                this.imageViewFotoCliente.setImage(image);
                clienteLogeado.setFotoPerfil(file.getName());
                clienteDAO.update(clienteLogeado);
            } catch (IOException e) {
                e.printStackTrace();
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Ocurrió un error al guardar la imagen.");
            }
        }
    }
}