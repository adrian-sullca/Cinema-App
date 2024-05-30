package com.app.cinema.controller;

import java.time.LocalDate;

import com.app.cinema.dao.ClienteDAO;
import com.app.cinema.dao.TarjetaDAO;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Cuenta;
import com.app.cinema.model.SesionUsuario;
import com.app.cinema.util.AlertUtils;
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Este controlador maneja la vista para vincular una tarjeta de crédito/débito a un cliente en la aplicación Cinema.
 * Permite al cliente ingresar los detalles de la tarjeta y vincularla a su cuenta.
 * 
 * Se utiliza una interfaz gráfica de usuario (GUI) basada en JavaFX para interactuar con el usuario.
 * 
 * @author Adrian
 */
public class VincularTarjetaViewController {
    
    @FXML
    private DatePicker datePickerFechaCaducidad;

    @FXML
    private Button botonVincular;

    @FXML
    private TextField fieldCVC;

    @FXML
    private TextField fieldNumCuenta;

    TarjetaDAO tarjetaDAO = new TarjetaDAO();
    Cliente clienteLogeado = SesionUsuario.getClienteLogeado();
    ClienteDAO clienteDAO = new ClienteDAO();

    /**
     * Acción realizada al presionar el botón de vincular tarjeta.
     * Valida los campos ingresados por el usuario y vincula la tarjeta a la cuenta del cliente.
     * 
     * @param event El evento de acción asociado al botón.
     */
    @FXML
    void accionVincularBoton(ActionEvent event) {
        try {
            String numCuenta = this.fieldNumCuenta.getText();
            LocalDate fechaCaducidad = this.datePickerFechaCaducidad.getValue();
            String cvc = this.fieldCVC.getText();

            if (numCuenta.isEmpty() || fechaCaducidad == null || cvc.isEmpty()) {
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                return;
            }

            long numeroCuenta = Long.parseLong(numCuenta);
            Date sqlFechaCaducidad = Date.valueOf(fechaCaducidad);
            int cvcInt = Integer.parseInt(cvc);

            Cuenta tarjeta = tarjetaDAO.selectByNumeroFechaCVC(numeroCuenta, sqlFechaCaducidad, cvcInt);
            if (tarjeta == null) {
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Numero de cuenta ,fecha de caducidad o  CVC invalidos.");
            } else {
                AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Éxito", "Tarjeta vinculada correctamente.");
                clienteLogeado.setTarjeta(tarjeta);
                clienteDAO.update(clienteLogeado);
            }
        } catch (NumberFormatException e) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Número de cuenta, fecha de caducidado  CVC invalidos.");
        } catch (Exception e) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Ocurrió un error inesperado.");
            e.printStackTrace();
        }
    }
}
