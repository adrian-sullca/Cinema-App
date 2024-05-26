package com.app.cinema.dao;

import java.util.List;

import com.app.cinema.model.Carrito;
import com.app.cinema.model.Pelicula;
import java.util.List;
import java.util.ArrayList;
import com.app.cinema.enums.Genero;
import com.app.cinema.model.Carrito;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Cuenta;
import com.app.cinema.model.LineaTransaccion;
import com.app.cinema.model.ListaPeliculas;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Transaccion;
import com.app.cinema.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.app.cinema.model.Carrito;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Cuenta;
import com.app.cinema.model.Usuario;

public class CarritoPeliculasDAO extends DBConnection implements DAO<Carrito, Integer> {

    PeliculaDAO peliculaDAO = new PeliculaDAO();
    private final String INSERT = "INSERT INTO CARRITO_PELICULA (id_carrito_cp, id_pelicula_cp) VALUES (?, ?)";
    private final String SELECTBYIDCARRITO = "SELECT * FROM CARRITO_PELICULA WHERE id_carrito_cp = ?";
    private final String UPDATE = "UPDATE CARRITO_PELICULA SET id_pelicula_cp = ? WHERE id_carrito_cp = ?";
    private final String DELETE = "DELETE FROM CARRITO_PELICULA WHERE id_carrito_cp = ?";
    private final String DELETEPORPELICULA = "DELETE FROM CARRITO_PELICULA WHERE id_carrito_cp = ? AND id_pelicula_cp = ?";
    @Override
    public void insert(Carrito t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            int idCarrito = t.getId_carrito();
            List<Pelicula> peliculas = t.getPeliculasCarrito();
            Pelicula nuevaPelicula = peliculas.get(peliculas.size() - 1);
            ps.setInt(1, idCarrito);
            ps.setInt(2, nuevaPelicula.getIdPelicula());
            ps.executeUpdate();
            System.out.println("Película agregada al carrito correctamente.");
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Carrito t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Carrito t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getId_carrito());
            ps.executeUpdate();
            System.out.println("Se han eliminado todas las películas del carrito correctamente.");
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Carrito t, int idPelicula) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETEPORPELICULA);
            ps.setInt(1, t.getId_carrito());
            ps.setInt(2, idPelicula);
            ps.executeUpdate();
            System.out.println("Se ha eliminado la película del carrito correctamente.");
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Carrito selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    public List<Pelicula> selectByCarritoId(Integer idCarrito) {
        List<Pelicula> peliculasPorCarrito = new ArrayList<Pelicula>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYIDCARRITO);
            ps.setInt(1, idCarrito);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idPelicula = rs.getInt("id_pelicula_cp");
                Pelicula pelicula = peliculaDAO.selectById(idPelicula);
                if (pelicula != null) {
                    peliculasPorCarrito.add(pelicula);
                }
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return peliculasPorCarrito;
    }
    @Override
    public List<Carrito> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    
    
}
