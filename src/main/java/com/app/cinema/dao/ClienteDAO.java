package com.app.cinema.dao;

import java.util.List;
import java.util.ArrayList;

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

/**
 * Esta clase proporciona métodos para acceder y manipular los datos de los clientes en la base de datos.
 * 
 * @author Adrian.
 */
public class ClienteDAO extends DBConnection implements DAO<Cliente, Integer> {

    CarritoDAO carritoDAO = new CarritoDAO();

    private final String INSERT = "INSERT INTO CLIENTE(id_usuario_c, telefono, tarjeta_vinculada, comentario_pref, foto_perfil_cliente, id_carrito_c) VALUES(?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE CLIENTE SET telefono=?, tarjeta_vinculada=?, foto_perfil_cliente=? WHERE codi_cliente=?";
    private final String DELETE = "DELETE FROM CLIENTE WHERE id_usuario_c=?";
    private final String SELECTBYID = "SELECT * FROM CLIENTE WHERE codi_cliente=?";
    private final String SELECTALL = "SELECT * FROM CLIENTE";

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Inserta un nuevo cliente en la base de datos.
     * 
     * @param t El cliente a insertar.
     */
    @Override
    public void insert(Cliente t) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connect();
            Carrito nuevoCarrito = new Carrito();
            carritoDAO.insert(nuevoCarrito);

            ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getIdUsuario());
            ps.setLong(2, t.getTelefono());
            
            if (t.getTarjeta() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, t.getTarjeta().getIdCuenta());
            }

            ps.setString(4, t.getComentarioPref());
            ps.setString(5, t.getFotoPerfil());
            ps.setInt(6, nuevoCarrito.getId_carrito());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int codiCliente = rs.getInt(1);
                    t.setCodiCliente(codiCliente);
                    t.setCarrito(nuevoCarrito);
                    System.out.println("Cliente insertado en la BD con ID: " + codiCliente);
                } else {
                    System.err.println("Error: No se pudo obtener el ID generado para el cliente.");
                }
            } else {
                System.err.println("Error: La inserción del cliente falló, no se afectaron filas.");
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
     * Actualiza un cliente en la base de datos.
     * 
     * @param t El cliente a actualizar.
     */
    @Override
    public void update(Cliente t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setInt(1, t.getTelefono());
            ps.setInt(2, t.getTarjeta().getIdCuenta());
            ps.setString(3, t.getFotoPerfil());
            ps.setInt(4, t.getCodiCliente());
            usuarioDAO.update(t);
            if (ps.executeUpdate() != 0) {
                System.out.println("Cliente modificado correctamente en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un cliente de la base de datos.
     * 
     * @param t El cliente a eliminar.
     */
    @Override
    public void delete(Cliente t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdUsuario());
            usuarioDAO.delete(t);
            if (ps.executeUpdate() != 0) {
                System.out.println("Cliente eliminado correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Selecciona un cliente de la base de datos por su ID.
     * 
     * @param id El ID del cliente a seleccionar.
     * @return El cliente seleccionado.
     */
    @Override
    public Cliente selectById(Integer id) {
        Cliente cliente = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int codi_cliente = rs.getInt("codi_cliente");
                int id_usuario_c = rs.getInt("id_usuario_c");
                int telefono = rs.getInt("telefono");
                int id_tarjeta_vinculada = rs.getInt("tarjeta_vinculada");
                String comentario_pref = rs.getString("comentario_pref");
                String foto_perfil_cliente = rs.getString("foto_perfil_cliente");
                int id_carrito = rs.getInt("id_carrito_c");

                Usuario usuario = usuarioDAO.selectById(id_usuario_c);

                TarjetaDAO tarjetaDAO = new TarjetaDAO();
                Cuenta tarjetaCliente = null;
                if (id_tarjeta_vinculada != 0) {
                    Cuenta tarjetaClientePorId = tarjetaDAO.selectById(id_tarjeta_vinculada);
                    if (tarjetaClientePorId != null) {
                        tarjetaCliente = new Cuenta(
                            tarjetaClientePorId.getIdCuenta(),
                            tarjetaClientePorId.getNumeroCuenta(),
                            tarjetaClientePorId.getCaducidad(),
                            tarjetaClientePorId.getCVC(),
                            tarjetaClientePorId.getSaldoDiponible()
                        );
                    }
                }

                Carrito carritoCliente = carritoDAO.selectById(id_carrito);

                List<Transaccion> lisTransaccion = new ArrayList<>();
                List<Pelicula> listPeliculas = new ArrayList<>();
                ListaPeliculas listaPeliculasCliente = new ListaPeliculas(listPeliculas);

                if (usuario != null) {
                    cliente = new Cliente(
                        usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                        usuario.getFechaNacimiento(), usuario.getCorreo(), usuario.getContraseña(),
                        usuario.getTipoUsuario(), codi_cliente, foto_perfil_cliente, telefono,
                        comentario_pref, carritoCliente, tarjetaCliente, lisTransaccion, listaPeliculasCliente
                    );
                }
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cliente;
    }

    /**
     * Selecciona un cliente de la base de datos por su ID de usuario.
     * 
     * @param idUsuario El ID de usuario del cliente a seleccionar.
     * @return El cliente seleccionado.
     */
    public Cliente selectByIdUsuario(Integer idUsuario) {
        Cliente cliente = null;
        try {
            connect();
            String query = "SELECT * FROM CLIENTE WHERE id_usuario_c=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int codi_cliente = rs.getInt("codi_cliente");
                int id_usuario_c = rs.getInt("id_usuario_c");
                int telefono = rs.getInt("telefono");
                int id_tarjeta_vinculada = rs.getInt("tarjeta_vinculada");
                String comentario_pref = rs.getString("comentario_pref");
                String foto_perfil_cliente = rs.getString("foto_perfil_cliente");
                int id_carrito = rs.getInt("id_carrito_c");

                Usuario usuario = usuarioDAO.selectById(id_usuario_c);

                TarjetaDAO tarjetaDAO = new TarjetaDAO();
                Cuenta tarjetaCliente = null;
                if (id_tarjeta_vinculada != 0) {
                    Cuenta tarjetaClientePorId = tarjetaDAO.selectById(id_tarjeta_vinculada);
                    if (tarjetaClientePorId != null) {
                        tarjetaCliente = new Cuenta(
                            tarjetaClientePorId.getIdCuenta(),
                            tarjetaClientePorId.getNumeroCuenta(),
                            tarjetaClientePorId.getCaducidad(),
                            tarjetaClientePorId.getCVC(),
                            tarjetaClientePorId.getSaldoDiponible()
                        );
                    }
                }

                Carrito carritoCliente = carritoDAO.selectById(id_carrito);

                List<Transaccion> lisTransaccion = new ArrayList<>();
                List<Pelicula> listPeliculas = new ArrayList<>();
                ListaPeliculas listaPeliculasCliente = new ListaPeliculas(listPeliculas);

                if (usuario != null) {
                    cliente = new Cliente(
                        usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                        usuario.getFechaNacimiento(), usuario.getCorreo(), usuario.getContraseña(),
                        usuario.getTipoUsuario(), codi_cliente, foto_perfil_cliente, telefono,
                        comentario_pref, carritoCliente, tarjetaCliente, lisTransaccion, listaPeliculasCliente
                    );
                }
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cliente;
    }

    /**
     * Selecciona todos los clientes de la base de datos.
     * 
     * @return Una lista de todos los clientes.
     */
    @Override
    public List<Cliente> selectAll() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int codi_cliente = rs.getInt("codi_cliente");
                int id_usuario_c = rs.getInt("id_usuario_c");
                int telefono = rs.getInt("telefono");
                int id_tarjeta_vinculada = rs.getInt("tarjeta_vinculada");
                String comentario_pref = rs.getString("comentario_pref");
                String foto_perfil_cliente = rs.getString("foto_perfil_cliente");
                int id_carrito = rs.getInt("id_carrito_c");
    
                Usuario usuario = usuarioDAO.selectById(id_usuario_c);
    
                TarjetaDAO tarjetaDAO = new TarjetaDAO();
                Cuenta tarjetaCliente = null;
                if (id_tarjeta_vinculada != 0) {
                    Cuenta tarjetaClientePorId = tarjetaDAO.selectById(id_tarjeta_vinculada);
                    if (tarjetaClientePorId != null) {
                        tarjetaCliente = new Cuenta(
                            tarjetaClientePorId.getIdCuenta(),
                            tarjetaClientePorId.getNumeroCuenta(),
                            tarjetaClientePorId.getCaducidad(),
                            tarjetaClientePorId.getCVC(),
                            tarjetaClientePorId.getSaldoDiponible()
                        );
                    }
                }
    
                Carrito carritoCliente = carritoDAO.selectById(id_carrito);
    
                List<Transaccion> lisTransaccion = new ArrayList<>();
                ListaPeliculas listaPeliculasCliente = new ListaPeliculas(null);
    
                if (usuario != null) {
                    Cliente cliente = new Cliente(
                        usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                        usuario.getFechaNacimiento(), usuario.getCorreo(), usuario.getContraseña(),
                        usuario.getTipoUsuario(), codi_cliente, foto_perfil_cliente, telefono,
                        comentario_pref, carritoCliente, tarjetaCliente, lisTransaccion, listaPeliculasCliente
                    );
                    clientes.add(cliente);
                }
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
    
}
