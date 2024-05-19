package com.app.cinema.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.app.cinema.model.Usuario;
import java.sql.Date;
import com.app.cinema.enums.TipoUsuario;
public class UsuarioDAO extends DBConnection implements DAO<Usuario, Integer> {

    private final String INSERT = "INSERT INTO USUARIO(nombre, apellido, correo, contraseña, fecha_nacimiento, tipo_usuario) VALUES(?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE USUARIO SET nombre=?, apellido=?, correo=?, contraseña=?, fecha_nacimiento=? WHERE id_usuario=?";
    private final String DELETE = "DELETE FROM USUARIO WHERE id_usuario=?";
    private final String SELECTBYID = "SELECT * FROM USUARIO WHERE id_usuario=?";
    private final String SELECTALL = "SELECT * FROM USUARIO";

    @Override
    public void insert(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getApellido());
            ps.setString(3, t.getCorreo());
            ps.setString(4, t.getContraseña());
            java.util.Date utilFechaNacimiento = t.getFechaNacimiento();
            java.sql.Date sqlFechaNacimiento = new java.sql.Date(utilFechaNacimiento.getTime());
            ps.setDate(5, sqlFechaNacimiento);
            ps.setString(6, t.getTipoUsuario().toString());
            if (ps.executeUpdate() != 0) {
                int id_usuario = ps.getGeneratedKeys().getInt(1);
                t.setIdUsuario(id_usuario);
                System.out.println("Usuario insertado correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertAndReturnId(Usuario t) {
        int idGenerado = 0;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getApellido());
            ps.setString(3, t.getCorreo());
            ps.setString(4, t.getContraseña());
            java.sql.Date sqlFechaNacimiento = new java.sql.Date(t.getFechaNacimiento().getTime());
            ps.setDate(5, sqlFechaNacimiento);
            ps.setString(6, t.getTipoUsuario().toString());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("La creación del usuario ha fallado, no se ha insertado ninguna fila.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGenerado = generatedKeys.getInt(1);
                    t.setIdUsuario(idGenerado);
                } else {
                    throw new SQLException("La creación del usuario ha fallado, no se pudo obtener el ID generado.");
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
    public void update(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getApellido());
            ps.setString(3, t.getCorreo());
            ps.setString(4, t.getContraseña());
            java.sql.Date sqlFechaNacimiento = new java.sql.Date(t.getFechaNacimiento().getTime());
            ps.setDate(5, sqlFechaNacimiento);
            ps.setInt(6, t.getIdUsuario());
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
        Usuario usuario = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                int id_usuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String contraseña = rs.getString("contraseña");
                Date fecha_nacimiento = rs.getDate("fecha_nacimiento"); 
                String tipoUsuarioStr = rs.getString("tipo_usuario");

                TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipoUsuarioStr);

                usuario = new Usuario(id_usuario, nombre, apellido, fecha_nacimiento, correo, contraseña, tipoUsuario);
                //usuario.setIdUsuario(id_usuario);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public List<Usuario> selectAll() {
        ArrayList<Usuario> Usuarios = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String contraseña = rs.getString("contraseña");
                Date fecha_nacimiento = rs.getDate("fecha_nacimiento");
                String tipoUsuarioStr = rs.getString("tipo_usuario");

                TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipoUsuarioStr);

                Usuario usuario = new Usuario(id_usuario, nombre, apellido, fecha_nacimiento, correo, contraseña, tipoUsuario);
                Usuarios.add(usuario);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Usuarios;
    }
}