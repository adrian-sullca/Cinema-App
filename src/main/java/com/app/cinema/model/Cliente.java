package com.app.cinema.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.cinema.enums.TipoUsuario;

public class Cliente extends Usuario {

    private int codiCliente;

    private String fotoPerfil;

    private int telefono;

    private String comentarioPref;

    private Carrito carrito;

    private List<Cuenta> cuenta = new ArrayList<Cuenta> ();

    private List<Transaccion> transaccion = new ArrayList<Transaccion> ();

    private ListaPeliculas listaPeliculas;

    public Cliente(int idUsuario, String nombre, String apellido, Date fechaNacimiento, String correo,
            String contraseña, TipoUsuario tipoUsuario, int codiCliente, String fotoPerfil, int telefono,
            String comentarioPref, Carrito carrito, List<Cuenta> cuenta, List<Transaccion> transaccion,
            ListaPeliculas listaPeliculas) {
        super(idUsuario, nombre, apellido, fechaNacimiento, correo, contraseña, tipoUsuario);
        this.codiCliente = codiCliente;
        this.fotoPerfil = fotoPerfil;
        this.telefono = telefono;
        this.comentarioPref = comentarioPref;
        this.carrito = carrito;
        this.cuenta = cuenta;
        this.transaccion = transaccion;
        this.listaPeliculas = listaPeliculas;
    }

    public Cliente(String correo, String contraseña, int codiCliente, String fotoPerfil, int telefono,
            String comentarioPref, Carrito carrito, List<Cuenta> cuenta, List<Transaccion> transaccion,
            ListaPeliculas listaPeliculas) {
        super(correo, contraseña);
        this.codiCliente = codiCliente;
        this.fotoPerfil = fotoPerfil;
        this.telefono = telefono;
        this.comentarioPref = comentarioPref;
        this.carrito = carrito;
        this.cuenta = cuenta;
        this.transaccion = transaccion;
        this.listaPeliculas = listaPeliculas;
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
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public List<Cuenta> getCuenta() {
        return cuenta;
    }

    public void setCuenta(List<Cuenta> cuenta) {
        this.cuenta = cuenta;
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

    @Override
    public String toString() {
        return "Cliente [codiCliente=" + codiCliente + ", fotoPerfil=" + fotoPerfil + ", telefono=" + telefono
                + ", comentarioPref=" + comentarioPref + ", carrito=" + carrito + ", cuenta=" + cuenta
                + ", transaccion=" + transaccion + ", listaPeliculas=" + listaPeliculas + "]";
    }

}
