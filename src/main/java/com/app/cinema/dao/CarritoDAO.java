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
public class CarritoDAO extends DBConnection implements DAO<Carrito, Integer> {
    CarritoPeliculasDAO carritoPeliculasDAO = new CarritoPeliculasDAO();

    private final String SELECTBYID = "SELECT * FROM CARRITO WHERE id_carrito=?";
    @Override
    public void insert(Carrito t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
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
