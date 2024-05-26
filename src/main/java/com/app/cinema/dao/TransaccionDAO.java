package com.app.cinema.dao;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.cinema.enums.Genero;
import com.app.cinema.enums.Rol;
import com.app.cinema.enums.TipoTransaccion;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.DNI;
import com.app.cinema.model.LineaTransaccion;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Trabajador;
import com.app.cinema.model.Transaccion;
import com.app.cinema.model.Usuario;

public class TransaccionDAO extends DBConnection implements DAO<Transaccion, Integer> {

    private final String INSERT = "INSERT INTO TRANSACCION(codi_cliente_t, tipo_transaccion, fecha_transaccion, total_t) VALUES(?,?,?,?)";
    private final String UPDATE = "UPDATE TRABAJADOR SET rol=?, DNI=?, fecha_alta=?, salario=? WHERE codi_trabajador=?";
    private final String DELETE = "DELETE FROM TRABAJADOR WHERE id_usuario_t=?";
    private final String SELECTBYID = "SELECT * FROM TRANSACCION WHERE id_transaccion=?";
    private final String SELECTBYCODICLIENTE = "SELECT * FROM TRANSACCION WHERE codi_cliente_t=?";
    private final String SELECTALL = "SELECT * FROM TRANSACCION";

    LineasTransaccionDAO lineasTransaccionDAO = new LineasTransaccionDAO();
    @Override
    public void insert(Transaccion t) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connect();
            ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getCliente().getCodiCliente());
            ps.setString(2, t.getTipoTransaccion().name());
            java.sql.Date sqlFechaTransaccion = new java.sql.Date(t.getFechaTransaccion().getTime());
            ps.setDate(3, sqlFechaTransaccion);
            ps.setDouble(4, t.getTotal());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id_transaccion = rs.getInt(1);
                    t.setIdTransaccion(id_transaccion);
                    System.out.println("Transaccion insertado en la BD");
                } else {
                    System.err.println("Error: No se pudo obtener el ID generado para la transaccion.");
                }
            } else {
                System.err.println("Error: La inserción de la transaccion falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int insertAndReturnId(Transaccion t) {
        int idGenerado = 0;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getCliente().getCodiCliente());
            ps.setString(2, t.getTipoTransaccion().name());
            java.sql.Date sqlFechaTransaccion = new java.sql.Date(t.getFechaTransaccion().getTime());
            ps.setDate(3, sqlFechaTransaccion);
            ps.setDouble(4, t.getTotal());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("La creación de la transaccion ha fallado, no se ha insertado ninguna fila.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGenerado = generatedKeys.getInt(1);
                    t.setIdTransaccion(idGenerado);
                } else {
                    throw new SQLException("La creación de la transaccion ha fallado, no se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return idGenerado;
    }

    @Override
    public void update(Transaccion t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Transaccion t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    @Override
    public Transaccion selectById(Integer id) {
        Transaccion transaccion = null;
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idTransaccion = rs.getInt("id_transaccion");
                int codi_cliente_t = rs.getInt("codi_cliente_t");
                String tipoTransaccionString = rs.getString("tipo_transaccion");
                TipoTransaccion tipoTransaccion = TipoTransaccion.valueOf(tipoTransaccionString);
                Date fechaTransaccion = rs.getDate("fecha_transaccion");
                double total = rs.getDouble("total_t");
                Cliente clienteTransaccion = clienteDAO.selectById(codi_cliente_t);
                //MODIFICAR: LINEAS TRANSACCION NO NULL
                transaccion = new Transaccion(idTransaccion, clienteTransaccion, fechaTransaccion, tipoTransaccion, total, null);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transaccion;
    }

    public List<Transaccion> selectByCodiCliente(int codiCliente) {
        List<Transaccion> transacciones = new ArrayList<>();
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYCODICLIENTE);
            ps.setInt(1, codiCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idTransaccion = rs.getInt("id_transaccion");
                int codi_cliente_t = rs.getInt("codi_cliente_t");
                String tipoTransaccionString = rs.getString("tipo_transaccion");
                TipoTransaccion tipoTransaccion = TipoTransaccion.valueOf(tipoTransaccionString);
                Date fechaTransaccion = rs.getDate("fecha_transaccion");
                double total = rs.getDouble("total_t");
                Cliente clienteTransaccion = clienteDAO.selectById(codi_cliente_t);
                List<LineaTransaccion> lineasTransaccionList = lineasTransaccionDAO.selectByTransaccionId(idTransaccion);
                Transaccion transaccion = new Transaccion(idTransaccion, clienteTransaccion, fechaTransaccion, tipoTransaccion, total, lineasTransaccionList);
                
                transacciones.add(transaccion);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transacciones;
    }
    
    @Override
    public List<Transaccion> selectAll() {
        ArrayList<Transaccion> transacciones = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            LineasTransaccionDAO lineasTransaccionDAO = new LineasTransaccionDAO();
            while (rs.next()) {
                int idTransaccion = rs.getInt("id_transaccion");
                int codi_cliente_t = rs.getInt("codi_cliente_t");
                String tipoTransaccionString = rs.getString("tipo_transaccion");
                TipoTransaccion tipoTransaccion = TipoTransaccion.valueOf(tipoTransaccionString);
                Date fechaTransaccion = rs.getDate("fecha_transaccion");
                double total = rs.getDouble("total_t");
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente clienteTransaccion = clienteDAO.selectById(codi_cliente_t);
                List<LineaTransaccion> lineasTransaccionList = lineasTransaccionDAO.selectByTransaccionId(idTransaccion);
                Transaccion transaccion = new Transaccion(idTransaccion, clienteTransaccion, fechaTransaccion, tipoTransaccion, total, lineasTransaccionList);
                
                transacciones.add(transaccion);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("METODO SELECT ALL DE TRANSACIONES DAO");
        System.out.println(transacciones);
        return transacciones;
    }
}
