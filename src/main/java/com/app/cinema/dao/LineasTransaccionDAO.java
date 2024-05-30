package com.app.cinema.dao;

import java.util.List;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.app.cinema.enums.Rol;
import com.app.cinema.enums.TipoTransaccion;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.DNI;
import com.app.cinema.model.Trabajador;
import com.app.cinema.model.Transaccion;
import com.app.cinema.model.Usuario;
import com.app.cinema.model.LineaTransaccion;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Transaccion;

/**
 * Clase para gestionar el acceso a datos de las líneas de transacción en la base de datos.
 * 
 * @author Adrian.
 */
public class LineasTransaccionDAO extends DBConnection implements DAO<LineaTransaccion, Integer>{

    private final String INSERT = "INSERT INTO LINEA_TRANSACCION(id_transaccion_lt, id_pelicula_lt, precio_pelicula, cantidad, total_lt) VALUES(?,?,?,?,?)";
    private final String UPDATE = "UPDATE LINEA_TRANSACCION SET rol=?, DNI=?, fecha_alta=?, salario=? WHERE codi_trabajador=?";
    private final String DELETE = "DELETE FROM LINEA_TRANSACCION WHERE id_usuario_t=?";
    private final String SELECTBYTRANSACCIONID = "SELECT * FROM LINEA_TRANSACCION WHERE id_transaccion_lt=?";
    private final String SELECTALL = "SELECT * FROM LINEA_TRANSACCION";

    PeliculaDAO peliculaDAO = new PeliculaDAO();

    /**
     * Inserta una nueva línea de transacción en la base de datos.
     * 
     * @param t La línea de transacción a insertar.
     */
    @Override
    public void insert(LineaTransaccion t) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connect();
            ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getIdTransaccionLT());
            ps.setInt(2, t.getPelicula().getIdPelicula());
            ps.setDouble(3, t.getPrecioPelicula());
            ps.setInt(4, t.getCantidad());
            ps.setDouble(5, t.getTotalLT());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id_linea_transaccion = rs.getInt(1);
                    t.setIdLineaTransaccion(id_linea_transaccion);
                    System.out.println("Linea Transaccion insertado en la BD");
                } else {
                    System.err.println("Error: No se pudo obtener el ID generado para la Linea transaccion.");
                }
            } else {
                System.err.println("Error: La inserción de la Linea transaccion falló, no se afectaron filas.");
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

    @Override
    public void update(LineaTransaccion t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(LineaTransaccion t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public LineaTransaccion selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    /**
     * Selecciona todas las líneas de transacción asociadas a una transacción.
     * 
     * @param idTransaccion El ID de la transacción asociada.
     * @return Una lista de líneas de transacción asociadas a la transacción especificada.
     */
    public List<LineaTransaccion> selectByTransaccionId(int idTransaccion) {
        List<LineaTransaccion> lineasTransaccionesPorID = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYTRANSACCIONID);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idTransaccion_lt = rs.getInt("id_transaccion_lt");
                int idLineaTransaccion = rs.getInt("id_linea_transaccion");
                int idPelicula_lt = rs.getInt("id_pelicula_lt");
                double precioPelicula = rs.getDouble("precio_pelicula");
                int cantidad = rs.getInt("cantidad");
                double total_lt = rs.getDouble("total_lt");
                Pelicula peliculaTransaccion = peliculaDAO.selectById(idPelicula_lt);
                LineaTransaccion lineaTransaccion = new LineaTransaccion(idTransaccion_lt, idLineaTransaccion, peliculaTransaccion, precioPelicula, cantidad, total_lt);
                lineasTransaccionesPorID.add(lineaTransaccion);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineasTransaccionesPorID;
    }

    /**
     * Selecciona todas las líneas de transacción de la base de datos.
     * 
     * @return Una lista de todas las líneas de transacción almacenadas en la base de datos.
     */
    @Override
    public List<LineaTransaccion> selectAll() {
        ArrayList<LineaTransaccion> lineasTransacciones = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idTransaccion_lt = rs.getInt("id_transaccion_lt");
                int idLineaTransaccion = rs.getInt("id_linea_transaccion");
                int idPelicula_lt = rs.getInt("id_pelicula_lt");
                double precioPelicula = rs.getDouble("precio_pelicula");
                int cantidad = rs.getInt("cantidad");
                double total_lt = rs.getDouble("total_lt");
                Pelicula peliculaTransaccion = peliculaDAO.selectById(idPelicula_lt);
                LineaTransaccion lineaTransaccion = new LineaTransaccion(idTransaccion_lt, idLineaTransaccion, peliculaTransaccion, precioPelicula, cantidad, total_lt);
                lineasTransacciones.add(lineaTransaccion);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineasTransacciones;
    }
    
}
