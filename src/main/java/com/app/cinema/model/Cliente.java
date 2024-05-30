package com.app.cinema.model;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Objects;

import com.app.cinema.enums.TipoUsuario;

public class Cliente extends Usuario {

    private int codiCliente;

    private String fotoPerfil;

    private int telefono;

    private String comentarioPref;

    private Carrito carrito;

    private Cuenta tarjeta;

    private List<Transaccion> transaccion = new ArrayList<Transaccion> ();

    private ListaPeliculas listaPeliculas;

    public Cliente() {
    }


    public Cliente(int idUsuario, String nombre, String apellido, Date fechaNacimiento, String correo,
            String contraseña, TipoUsuario tipoUsuario, int codiCliente, String fotoPerfil, int telefono,
            String comentarioPref, Carrito carrito) {
        super(idUsuario, nombre, apellido, fechaNacimiento, correo, contraseña, tipoUsuario);
        this.codiCliente = codiCliente;
        this.fotoPerfil = fotoPerfil;
        this.telefono = telefono;
        this.comentarioPref = comentarioPref;
        this.carrito = carrito;
    }

    //constructo para el registro de cliente
    public Cliente(int idUsuario, String nombre, String apellido, Date fechaNacimiento, String correo,
            String contraseña, TipoUsuario tipoUsuario, int codiCliente, int telefono) {
        super(idUsuario, nombre, apellido, fechaNacimiento, correo, contraseña, tipoUsuario);
        this.codiCliente = codiCliente;
        this.telefono = telefono;
    }

    public Cliente(int idUsuario, String nombre, String apellido, Date fechaNacimiento, String correo,
            String contraseña, TipoUsuario tipoUsuario, int codiCliente, String fotoPerfil, int telefono,
            String comentarioPref) {
        super(idUsuario, nombre, apellido, fechaNacimiento, correo, contraseña, tipoUsuario);
        this.codiCliente = codiCliente;
        this.fotoPerfil = fotoPerfil;
        this.telefono = telefono;
        this.comentarioPref = comentarioPref;
    }

    public Cliente(String correo, String contraseña, int codiCliente, String fotoPerfil, int telefono,
            String comentarioPref, Carrito carrito) {
        super(correo, contraseña);
        this.codiCliente = codiCliente;
        this.fotoPerfil = fotoPerfil;
        this.telefono = telefono;
        this.comentarioPref = comentarioPref;
        this.carrito = carrito;
    }

    

    public Cliente(int idUsuario, String nombre, String apellido, Date fechaNacimiento, String correo,
            String contraseña, TipoUsuario tipoUsuario, int codiCliente, String fotoPerfil, int telefono,
            String comentarioPref, Carrito carrito, Cuenta tarjeta, List<Transaccion> transaccion,
            ListaPeliculas listaPeliculas) {
        super(idUsuario, nombre, apellido, fechaNacimiento, correo, contraseña, tipoUsuario);
        this.codiCliente = codiCliente;
        this.fotoPerfil = fotoPerfil;
        this.telefono = telefono;
        this.comentarioPref = comentarioPref;
        this.carrito = carrito;
        this.tarjeta = tarjeta;
        this.transaccion = transaccion;
        this.listaPeliculas = listaPeliculas;
    }

    public Cliente(int idUsuario, String nombre, String apellido, Date fechaNacimiento, String correo,
    String contraseña, TipoUsuario tipoUsuario, int codiCliente, String fotoPerfil, int telefono,
    String comentarioPref, Cuenta tarjeta) {
    super(idUsuario, nombre, apellido, fechaNacimiento, correo, contraseña, tipoUsuario);
    this.codiCliente = codiCliente;
    this.fotoPerfil = fotoPerfil;
    this.telefono = telefono;
    this.comentarioPref = comentarioPref;
    this.tarjeta = tarjeta;
    }   

    public int getCodiCliente() {
        return codiCliente;
    }

    public void setCodiCliente(int codiCliente) {
        this.codiCliente = codiCliente;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getComentarioPref() {
        return comentarioPref;
    }

    public void setComentarioPref(String comentarioPref) {
        this.comentarioPref = comentarioPref;
    }

    public Carrito getCarrito() {
        //DESCOMENTAR PARA INICIALIZAR CARRITO VACIO y no nulo
        /* if (this.carrito == null) {
            this.carrito = new Carrito(, new ArrayList<>());
        } */
        return this.carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Cuenta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Cuenta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public List<Transaccion> getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(List<Transaccion> transaccion) {
        this.transaccion = transaccion;
    }

    public ListaPeliculas getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(ListaPeliculas listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }
    //metodo para recuperar solo el numero de tarjeta del objeto Cuenta
    public Long getNumeroCuenta() {
        return tarjeta != null ? tarjeta.getNumeroCuenta() : null;
    }

    @Override
    public String toString() {
        return "Cliente [codiCliente=" + codiCliente + ", idUsuario=" + super.idUsuario  + ", fotoPerfil=" + fotoPerfil + ", telefono=" + telefono
                + ", comentarioPref=" + comentarioPref + ", carrito=" + carrito + ", tarjeta=" + tarjeta
                + ", transaccion=" + transaccion + ", listaPeliculas=" + listaPeliculas + "]\n";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) obj;
        return Objects.equals(correo, cliente.correo) || Objects.equals(telefono, cliente.telefono);
    }

    public String toComboBoxString() {
        return nombre + " " + apellido;
    }

}
