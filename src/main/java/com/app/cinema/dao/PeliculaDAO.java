package com.app.cinema.dao;

import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.app.cinema.enums.Genero;
import com.app.cinema.enums.Rol;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Cuenta;
import com.app.cinema.model.DNI;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Trabajador;
import com.app.cinema.model.Usuario;

public class PeliculaDAO extends DBConnection implements DAO<Pelicula, Integer> {
    private final String INSERT = "INSERT INTO PELICULA(portada, titulo, genero, duracion, descripcion) VALUES(?,?,?,?,?)";
    private final String UPDATE = "UPDATE PELICULA SET portada=?, titulo=?, genero=?, duracion=?, descripcion=? WHERE id_pelicula=?";
    private final String DELETE = "DELETE FROM PELICULA WHERE id_pelicula=?";
    private final String SELECTBYID = "SELECT * FROM PELICULA WHERE id_pelicula=?";
    private final String SELECTALL = "SELECT * FROM PELICULA";

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    @Override
    public void insert(Pelicula t) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connect();
            ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getFotoPortada());
            ps.setString(2, t.getTitulo());
            ps.setString(3, t.getGenero().name());
            ps.setDouble(4, t.getDuracion());
            ps.setString(5, t.getDescripcion());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id_pelicula = rs.getInt(1);
                    t.setIdPelicula(id_pelicula);
                    System.out.println("Pelicula insertado en la BD");
                } else {
                    System.err.println("Error: No se pudo obtener el ID generado para la Pelicula.");
                }
            } else {
                System.err.println("Error: La inserción del trabajador falló, no se afectaron filas.");
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
    public void update(Pelicula t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getFotoPortada());
            ps.setString(2, t.getTitulo());
            ps.setString(3, t.getGenero().name());
            ps.setDouble(4, t.getDuracion());
            ps.setString(5, t.getDescripcion());
            ps.setInt(6, t.getIdPelicula());
            if (ps.executeUpdate() != 0) {
                System.out.println("Pelicula modificada correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Pelicula t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdPelicula());
            if (ps.executeUpdate() != 0) {
                System.out.println("Pelicula eliminada correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Pelicula selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }
    @Override
    public List<Pelicula> selectAll() {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int id_pelicula = rs.getInt("id_pelicula");
                String fotoPortada = rs.getString("portada");
                String titulo = rs.getString("titulo");
                double duracion = rs.getDouble("duracion");
                String descripcion = rs.getString("descripcion");
                String generoString = rs.getString("genero");
                Genero genero = Genero.valueOf(generoString);
                //Asignar reseñas a cada pelicula 
                //MODIFICAR: De momento lo dejare en null
                //list reseñas
                Pelicula pelicula = new Pelicula(id_pelicula, fotoPortada, titulo, duracion, descripcion, genero, null);
                peliculas.add(pelicula);
                }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return peliculas;
    }
}
