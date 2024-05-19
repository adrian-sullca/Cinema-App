package com.app.cinema.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.app.cinema.model.Cuenta;

public class TarjetaDAO extends DBConnection implements DAO<Cuenta, Integer> {

    private final String INSERT = "INSERT INTO TARJETA(nom,cognom,telefon,nif) VALUES(?,?,?,?)"; //modificar esto
    private final String UPDATE = "UPDATE TARJETA SET telefono=?, tarjeta_vinculada=?, comentario_pref=?, foto_perfil_cliente=? WHERE codi_cliente=?"; //modificar
    private final String DELETE = "DELETE FROM TARJETA WHERE codi_cliente=?"; //modificar
    private final String SELECTBYID = "SELECT * FROM TARJETA WHERE id_tarjeta=?"; //modificar
    private final String SELECTALL = "SELECT * FROM TARJETA";


    @Override
    public void insert(Cuenta t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Cuenta t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Cuenta t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

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

    @Override
    public List<Cuenta> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }
    
}