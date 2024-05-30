package com.app.cinema.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.app.cinema.model.Cuenta;

/**
 * Clase para gestionar el acceso a datos de las cuentas (tarjetas) en la base de datos.
 * Esta clase proporciona métodos para insertar, actualizar, eliminar y seleccionar cuentas
 * en la base de datos.
 * 
 * @author Adrian.
 */
public class TarjetaDAO extends DBConnection implements DAO<Cuenta, Integer> {

    private final String INSERT = "INSERT INTO TARJETA(nom,cognom,telefon,nif) VALUES(?,?,?,?)"; //modificar esto
    private final String UPDATE = "UPDATE TARJETA SET saldo_disponible=? WHERE id_tarjeta=?";
    private final String DELETE = "DELETE FROM TARJETA WHERE codi_cliente=?"; //modificar
    private final String SELECTBYID = "SELECT * FROM TARJETA WHERE id_tarjeta=?"; //modificar
    private final String SELECTALL = "SELECT * FROM TARJETA";
    private final String SELECTBYNUMEROFECHACVC = "SELECT * FROM TARJETA WHERE numero=? AND caducidad=? AND cvc=?";


    @Override
    public void insert(Cuenta t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    /**
     * Actualiza el saldo disponible de una cuenta en la base de datos.
     * 
     * @param cuenta La cuenta cuyo saldo se actualizará.
     */
    @Override
    public void update(Cuenta cuenta) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setDouble(1, cuenta.getSaldoDiponible());
            ps.setInt(2, cuenta.getIdCuenta());
            ps.executeUpdate();
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Cuenta t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    /**
     * Selecciona una cuenta de la base de datos por su ID.
     * 
     * @param id El ID de la cuenta a seleccionar.
     * @return La cuenta seleccionada, o null si no se encuentra.
     */
    @Override
    public Cuenta selectById(Integer id) {
        Cuenta tarjeta = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                int idCuenta = rs.getInt("id_tarjeta");
                long numeroCuenta = rs.getLong("numero");
                Date caducidad = rs.getDate("caducidad");
                int CVC = rs.getInt("cvc");
                double saldoDiponible = rs.getDouble("saldo_disponible");

                tarjeta = new Cuenta(idCuenta, numeroCuenta, caducidad, CVC, saldoDiponible);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tarjeta;
    }

    /**
     * Selecciona una cuenta de la base de datos por su número, fecha de caducidad y CVC.
     * 
     * @param numero El número de la cuenta.
     * @param caducidad La fecha de caducidad de la cuenta.
     * @param cvc El CVC (Código de Verificación de la Tarjeta) de la cuenta.
     * @return La cuenta seleccionada, o null si no se encuentra.
     */
    public Cuenta selectByNumeroFechaCVC(long numero, Date caducidad, int cvc) {
        Cuenta tarjeta = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYNUMEROFECHACVC);
            ps.setLong(1, numero);
            ps.setDate(2, caducidad);
            ps.setInt(3, cvc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idCuenta = rs.getInt("id_tarjeta");
                long numeroCuenta = rs.getLong("numero");
                Date fechaCaducidad = rs.getDate("caducidad");
                int CVC = rs.getInt("cvc");
                double saldoDiponible = rs.getDouble("saldo_disponible");

                tarjeta = new Cuenta(idCuenta, numeroCuenta, fechaCaducidad, CVC, saldoDiponible);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tarjeta;
    }

    @Override
    public List<Cuenta> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }
    
}