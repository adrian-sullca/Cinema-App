package com.app.cinema.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.cinema.enums.Rol;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.Cuenta;
import com.app.cinema.model.DNI;
import com.app.cinema.model.Trabajador;
import com.app.cinema.model.Usuario;

public class TrabajadorDAO extends DBConnection implements DAO<Trabajador, Integer> {
    private final String INSERT = "INSERT INTO TRABAJADOR(id_usuario_t, DNI, rol, fecha_alta, salario) VALUES(?,?,?,?,?)";
    private final String UPDATE = "UPDATE TRABAJADOR SET rol=?, DNI=?, fecha_alta=?, salario=? WHERE codi_trabajador=?";
    private final String DELETE = "DELETE FROM TRABAJADOR WHERE id_usuario_t=?";
    private final String SELECTBYID = "SELECT * FROM TRABAJADOR WHERE codi_trabajador=?";
    private final String SELECTALL = "SELECT * FROM TRABAJADOR";

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void insert(Trabajador t) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connect();
            ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getIdUsuario());
            ps.setString(2, t.getdNI().toString());
            ps.setString(3, t.getRol().name());
            java.sql.Date sqlFechaAlta = new java.sql.Date(t.getFechaAlta().getTime());
            ps.setDate(4, sqlFechaAlta);
            ps.setDouble(5, t.getSalario());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int codiTrabajador = rs.getInt(1);
                    t.setCodiTrabajador(codiTrabajador);
                    System.out.println("Trabajador insertado en la BD");
                } else {
                    System.err.println("Error: No se pudo obtener el ID generado para el Trabajador.");
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
    public void update(Trabajador t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getRol().name());
            ps.setString(2, t.getdNI().toString());
            java.sql.Date sqlFechaAlta = new java.sql.Date(t.getFechaAlta().getTime());
            ps.setDate(3, sqlFechaAlta);
            ps.setDouble(4, t.getSalario());
            ps.setInt(5, t.getCodiTrabajador());
            usuarioDAO.update(t);
            if (ps.executeUpdate() != 0) {
                System.out.println("Trabajador modificado correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(Trabajador t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdUsuario());
            usuarioDAO.delete(t);
            if (ps.executeUpdate() != 0) {
                System.out.println("Trabajador eliminado correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Trabajador selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }
    @Override
    public List<Trabajador> selectAll() {
        ArrayList<Trabajador> trabajadores = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int codi_trabajador = rs.getInt("codi_trabajador");
                int id_usuario_t = rs.getInt("id_usuario_t");
                String dniString = rs.getString("DNI");
                int numeroDni = Integer.parseInt(dniString.substring(0, 8));
                char letraDni = dniString.charAt(8);
                DNI dni = new DNI(numeroDni, letraDni);
                String rolString = rs.getString("rol");
                Rol rol = Rol.valueOf(rolString);
                Date fechaAlta = rs.getDate("fecha_alta");
                double salario = rs.getDouble("salario");
                Usuario usuario = usuarioDAO.selectById(id_usuario_t);
                if (usuario != null) {
                    Trabajador trabajador = new Trabajador(usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getFechaNacimiento(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipoUsuario(), codi_trabajador, fechaAlta, salario, rol, dni);
                    trabajadores.add(trabajador);
                }
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trabajadores;
    }
    public boolean existsByDni(DNI dni) {
        try {
            connect();
            String query = "SELECT COUNT(*) FROM TRABAJADOR WHERE DNI = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dni.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }
}
