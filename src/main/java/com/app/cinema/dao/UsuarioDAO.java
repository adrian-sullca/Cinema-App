package com.app.cinema.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.app.cinema.model.Usuario;

public class UsuarioDAO extends DBConnection implements DAO<Usuario, Integer> {

    private final String INSERT = "INSERT INTO USUARIO(correu,password) VALUES(?,?)";
    private final String UPDATE = "UPDATE USUARIO SET correu=?, password=? WHERE id=?";
    private final String DELETE = "DELETE FROM USUARIO WHERE id=?";
    private final String SELECTBYID = "SELECT * FROM USUARIO WHERE id=?";
    private final String SELECTALL = "SELECT * FROM USUARIO";

    @Override
    public void insert(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, t.getCorreo());
            ps.setString(2, t.getContraseña());
            if (ps.executeUpdate() != 0) {
                int id = ps.getGeneratedKeys().getInt(1);
                t.setIdUsuario(id);
                System.out.println("Usuario insertado correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getCorreo());
            ps.setString(2, t.getContraseña());
            ps.setInt(3, t.getIdUsuario());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuario modificado correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdUsuario());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuario eliminado correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario selectById(Integer id) {
        Usuario Usuario = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idUsuario = rs.getInt("id");
                String correo = rs.getString("correu");
                String contraseña = rs.getString("password");
                Usuario = new Usuario(correo, contraseña);
                Usuario.setIdUsuario(idUsuario);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Usuario;
    }

    @Override
    public List<Usuario> selectAll() {
        ArrayList<Usuario> Usuarios = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idUsuario = rs.getInt("id");
                String correo = rs.getString("correu");
                String contraseña = rs.getString("password");
                Usuario Usuario = new Usuario(correo, contraseña);
                Usuario.setIdUsuario(idUsuario);
                Usuarios.add(Usuario);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Usuarios;
    }

}