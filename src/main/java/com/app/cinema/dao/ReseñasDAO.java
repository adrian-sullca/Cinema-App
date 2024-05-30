package com.app.cinema.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.app.cinema.enums.Estado;
import com.app.cinema.enums.Genero;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Reseña;

import java.sql.Date;

/**
 * Clase para gestionar el acceso a datos de las reseñas en la base de datos.
 * Esta clase proporciona métodos para insertar, actualizar, eliminar y seleccionar reseñas
 * en la base de datos.
 * 
 * @author Adrian.
 */
public class ReseñasDAO extends DBConnection implements DAO<Reseña, Integer> {
    private final String INSERT = "INSERT INTO RESEÑAS(id_pelicula_r, codi_cliente_r, contenido, fecha_reseña, estado) VALUES(?,?,?,?,?)";
    private final String UPDATE = "UPDATE RESEÑAS SET estado=? WHERE id_reseña=?";
    private final String DELETE = "DELETE FROM PELICULA WHERE id_pelicula=?";
    private final String SELECTBYID = "SELECT * FROM RESEÑAS WHERE id_pelicula_r=?";
    private final String SELECTALL = "SELECT * FROM RESEÑAS";

    /**
     * Inserta una nueva reseña en la base de datos.
     * 
     * @param t La reseña a insertar.
     */
    @Override
    public void insert(Reseña t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getPelicula().getIdPelicula());
            ps.setInt(2, t.getCliente().getCodiCliente());
            ps.setString(3, t.getContenido());
            java.util.Date utilFechaReseña = t.getFechaReseña();
            java.sql.Date sqlFechaReseña = new java.sql.Date(utilFechaReseña.getTime());
            ps.setDate(4, sqlFechaReseña);
            ps.setString(5, Estado.REVISION.name());

            if (ps.executeUpdate() != 0) {
                int id_resena = ps.getGeneratedKeys().getInt(1);
                t.setIdReseña(id_resena);
                System.out.println("Reseña insertada correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza una reseña en la base de datos.
     * 
     * @param t La reseña a actualizar.
     */
    @Override
    public void update(Reseña t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getEstado().name());
            ps.setInt(2, t.getIdReseña());
            
            if (ps.executeUpdate() != 0) {
                System.out.println("Reseña modificada correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(Reseña t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    @Override
    public Reseña selectById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    /**
     * Selecciona reseñas de la base de datos por el ID de la película.
     * 
     * @param pelicula La película asociada a las reseñas a seleccionar.
     * @return Una lista de reseñas asociadas a la película especificada.
     */
    public List<Reseña> reseñasSelectByPeliculaId(Pelicula pelicula) {
        ArrayList<Reseña> reseñasPelicula = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, pelicula.getIdPelicula());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_reseña = rs.getInt("id_reseña");
                String contenido = rs.getString("contenido");
                Date fecha_reseña = rs.getDate("fecha_reseña");
                String estadoString = rs.getString("estado");
                Estado estado = Estado.valueOf(estadoString);
                int codi_cliente = rs.getInt("codi_cliente_r");
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente clienteReseña = clienteDAO.selectById(codi_cliente);
                Reseña reseña = new Reseña(id_reseña, contenido, fecha_reseña, estado, clienteReseña, pelicula);
                reseñasPelicula.add(reseña);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Reseñas de pelicula select by id reseñasdao");
        for (Reseña reseña : reseñasPelicula) {
            System.out.println(reseña);
        }
        return reseñasPelicula;
    }

    /**
     * Selecciona reseñas aprobadas de la base de datos por el ID de la película.
     * 
     * @param pelicula La película asociada a las reseñas a seleccionar.
     * @return Una lista de reseñas aprobadas asociadas a la película especificada.
     */
    public List<Reseña> reseñasAprobadasByPeliculaId(Pelicula pelicula) {
        ArrayList<Reseña> reseñasAprobadas = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, pelicula.getIdPelicula());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_reseña = rs.getInt("id_reseña");
                String contenido = rs.getString("contenido");
                Date fecha_reseña = rs.getDate("fecha_reseña");
                String estadoString = rs.getString("estado");
                Estado estado = Estado.valueOf(estadoString);
                if (estado == Estado.APROBADA) {
                    int codi_cliente = rs.getInt("codi_cliente_r");
                    ClienteDAO clienteDAO = new ClienteDAO();
                    Cliente clienteReseña = clienteDAO.selectById(codi_cliente);
                    Reseña reseña = new Reseña(id_reseña, contenido, fecha_reseña, estado, clienteReseña, pelicula);
                    reseñasAprobadas.add(reseña);
                }
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Reseñas aprobadas de la película select by id reseñasdao");
        for (Reseña reseña : reseñasAprobadas) {
            System.out.println(reseña);
        }
        return reseñasAprobadas;
    }

    /**
     * Selecciona todas las reseñas de la base de datos.
     * 
     * @return Una lista de todas las reseñas almacenadas en la base de datos.
     */

    @Override
    public List<Reseña> selectAll() {
        ArrayList<Reseña> reseñas = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_reseña = rs.getInt("id_reseña");
                String contenido = rs.getString("contenido");
                Date fecha_reseña = rs.getDate("fecha_reseña");
                String estadoString = rs.getString("estado");
                Estado estado = Estado.valueOf(estadoString);
                int codi_cliente = rs.getInt("codi_cliente_r");
                int id_pelicula = rs.getInt("id_pelicula_r");
                PeliculaDAO peliculaDAO = new PeliculaDAO();
                Pelicula peliculaReseña = peliculaDAO.selectById(id_pelicula);
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente clienteReseña = clienteDAO.selectById(codi_cliente);
                Reseña reseña = new Reseña(id_reseña, contenido, fecha_reseña, estado, clienteReseña, peliculaReseña);
                reseñas.add(reseña);
                }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reseñas;
    }
}
