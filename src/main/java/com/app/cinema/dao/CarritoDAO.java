package com.app.cinema.dao;

import java.util.List;
import com.app.cinema.enums.Genero;
import com.app.cinema.model.Carrito;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Cuenta;
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

/**
 * Esta clase proporciona métodos para acceder y manipular los datos de los carritos en la base de datos.
 * 
 * @author Adrian.
 */
public class CarritoDAO extends DBConnection implements DAO<Carrito, Integer> {
    CarritoPeliculasDAO carritoPeliculasDAO = new CarritoPeliculasDAO();

    private final String SELECTBYID = "SELECT * FROM CARRITO WHERE id_carrito=?";
    
     /**
     * Inserta un nuevo carrito en la base de datos.
     * 
     * @param t El carrito a insertar.
     */
    @Override
    public void insert(Carrito t) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connect();
            ps = connection.prepareStatement("INSERT INTO CARRITO VALUES (NULL)", Statement.RETURN_GENERATED_KEYS);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    t.setId_carrito(generatedId);
                    System.out.println("Carrito insertado en la BD con ID: " + generatedId);
                }
            } else {
                System.err.println("Error: La inserción del carrito falló, no se afectaron filas.");
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
    public void update(Carrito t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Carrito t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    /**
     * Selecciona un carrito de la base de datos por su ID.
     * 
     * @param id El ID del carrito a seleccionar.
     * @return El carrito seleccionado.
     */
    @Override
    public Carrito selectById(Integer id) {
        Carrito carrito = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_carrito = rs.getInt("id_carrito");
                List<Pelicula> peliculasDelCarrito = carritoPeliculasDAO.selectByCarritoId(id);
                carrito = new Carrito(id_carrito, peliculasDelCarrito);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carrito;
    }

    @Override
    public List<Carrito> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }
    
}
