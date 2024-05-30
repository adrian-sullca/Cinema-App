package com.app.cinema.dao;

import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import com.app.cinema.enums.Genero;
import com.app.cinema.enums.Rol;
import com.app.cinema.enums.TipoUsuario;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Cuenta;
import com.app.cinema.model.DNI;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Reseña;
import com.app.cinema.model.Trabajador;
import com.app.cinema.model.Usuario;

/**
 * Clase para gestionar el acceso a datos de las películas en la base de datos.
 * Esta clase proporciona métodos para insertar, actualizar, eliminar y seleccionar películas
 * en la base de datos.
 * 
 * @author Adrian
 */
public class PeliculaDAO extends DBConnection implements DAO<Pelicula, Integer> {
    private final String INSERT = "INSERT INTO PELICULA(portada, titulo, genero, duracion, descripcion, precio) VALUES(?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE PELICULA SET portada=?, titulo=?, genero=?, duracion=?, descripcion=?, precio=? WHERE id_pelicula=?";
    private final String DELETE = "DELETE FROM PELICULA WHERE id_pelicula=?";
    private final String SELECTBYID = "SELECT * FROM PELICULA WHERE id_pelicula=?";
    private final String SELECTBYGENERO = "SELECT * FROM PELICULA WHERE genero=?";
    private final String SELECTBYTITULO = "SELECT * FROM PELICULA WHERE titulo LIKE ?";
    private final String SELECTALL = "SELECT * FROM PELICULA";
    
    /**
     * Inserta una nueva película en la base de datos.
     * 
     * @param t La película a insertar.
     */
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
            ps.setDouble(6, t.getPrecio());
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

    /**
     * Actualiza una película en la base de datos.
     * 
     * @param t La película a actualizar.
     */
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
            ps.setDouble(6, t.getPrecio());
            ps.setInt(7, t.getIdPelicula());
            if (ps.executeUpdate() != 0) {
                System.out.println("Pelicula modificada correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una película de la base de datos.
     * 
     * @param t La película a eliminar.
     */
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

    /**
     * Selecciona una película de la base de datos por su ID.
     * 
     * @param id El ID de la película a seleccionar.
     * @return La película seleccionada.
     */
    @Override
    public Pelicula selectById(Integer id) {
        Pelicula pelicula = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                int id_pelicula = rs.getInt("id_pelicula");
                String fotoPortada = rs.getString("portada");
                String titulo = rs.getString("titulo");
                double duracion = rs.getDouble("duracion");
                String descripcion = rs.getString("descripcion");
                String generoString = rs.getString("genero");
                Genero genero = Genero.valueOf(generoString);
                double precio = rs.getDouble("precio");
                //Asignar reseñas a cada pelicula 
                //MODIFICAR: De momento lo dejare en null
                //list reseñas
                pelicula = new Pelicula(id_pelicula, fotoPortada, titulo, precio, duracion, descripcion, genero, null);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pelicula;
    }

    /**
     * Selecciona películas de la base de datos por su género.
     * 
     * @param genero El género de las películas a seleccionar.
     * @return Una lista de películas del género especificado.
     */
    public List<Pelicula> selectPeliculasByGenero(Genero genero) {
        List<Pelicula> peliculasGenero = new ArrayList<>();
    try {
        connect();
        PreparedStatement ps = connection.prepareStatement(SELECTBYGENERO);
        ps.setString(1, genero.name());
        ResultSet rs = ps.executeQuery();
        ReseñasDAO reseñasDAO = new ReseñasDAO();
        List<Reseña> reseñasList = reseñasDAO.selectAll();
        while (rs.next()) {
            int id_pelicula = rs.getInt("id_pelicula");
            String fotoPortada = rs.getString("portada");
            String titulo = rs.getString("titulo");
            double duracion = rs.getDouble("duracion");
            String descripcion = rs.getString("descripcion");
            double precio = rs.getDouble("precio");
            List<Reseña> reseñasDeLaPelicula = reseñasList.stream().filter(reseña -> reseña.getPelicula().getIdPelicula() == id_pelicula).collect(Collectors.toList());
            Pelicula pelicula = new Pelicula(id_pelicula, fotoPortada, titulo, precio, duracion, descripcion, genero, reseñasDeLaPelicula);
            peliculasGenero.add(pelicula);
        }
        closeConnection();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return peliculasGenero;
    }

    /**
     * Busca películas en la base de datos por su título.
     * 
     * @param titulo El título o parte del título de las películas a buscar.
     * @return Una lista de películas que coinciden con el título especificado.
     */
    public List<Pelicula> buscarPeliculasPorTitulo(String titulo) {
        List<Pelicula> peliculas = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYTITULO);
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            ReseñasDAO reseñasDAO = new ReseñasDAO();
            List<Reseña> reseñasList = reseñasDAO.selectAll();
            while (rs.next()) {
                int id_pelicula = rs.getInt("id_pelicula");
                String fotoPortada = rs.getString("portada");
                String tituloPelicula = rs.getString("titulo");
                double duracion = rs.getDouble("duracion");
                String descripcion = rs.getString("descripcion");
                String generoString = rs.getString("genero");
                Genero genero = Genero.valueOf(generoString);
                double precio = rs.getDouble("precio");
                List<Reseña> reseñasDeLaPelicula = reseñasList.stream()
                        .filter(reseña -> reseña.getPelicula().getIdPelicula() == id_pelicula)
                        .collect(Collectors.toList());
                Pelicula pelicula = new Pelicula(id_pelicula, fotoPortada, tituloPelicula, precio, duracion, descripcion, genero, reseñasDeLaPelicula);
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return peliculas;
    }

    /**
     * Selecciona todas las películas de la base de datos.
     * 
     * @return Una lista de todas las películas almacenadas en la base de datos.
     */
    @Override
    public List<Pelicula> selectAll() {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            ReseñasDAO reseñasDAO = new ReseñasDAO();
            List<Reseña> reseñasList = reseñasDAO.selectAll();
            List<Reseña> reseñasPelicula = new ArrayList<>();
            while (rs.next()) {

                int id_pelicula = rs.getInt("id_pelicula");
                String fotoPortada = rs.getString("portada");
                String titulo = rs.getString("titulo");
                double duracion = rs.getDouble("duracion");
                String descripcion = rs.getString("descripcion");
                String generoString = rs.getString("genero");
                Genero genero = Genero.valueOf(generoString);    
                double precio = rs.getDouble("precio");
                List<Reseña> reseñasDeLaPelicula = reseñasList.stream()
                .filter(reseña -> reseña.getPelicula().getIdPelicula() == id_pelicula).collect(Collectors.toList());

                // Crear la película con las reseñas correspondientes
                Pelicula pelicula = new Pelicula(id_pelicula, fotoPortada, titulo, precio, duracion, descripcion, genero, reseñasDeLaPelicula);
                peliculas.add(pelicula);
                }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return peliculas;
    }
}
