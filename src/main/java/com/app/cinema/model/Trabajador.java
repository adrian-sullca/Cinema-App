package com.app.cinema.model;

import java.util.Date;

import com.app.cinema.enums.Rol;

public class Trabajador extends Usuario {

    private int codiTrabajador;

    private Date fechaAlta;

    private double salario;

    private Rol rol;

    private DNI dNI;

    public Trabajador(int idUsuario, String nombre, String apellido, Date fechaNacimiento, String correo,
            String contraseña, int codiTrabajador, Date fechaAlta, double salario, Rol rol, DNI dNI) {
        super(idUsuario, nombre, apellido, fechaNacimiento, correo, contraseña);
        this.codiTrabajador = codiTrabajador;
        this.fechaAlta = fechaAlta;
        this.salario = salario;
        this.rol = rol;
        this.dNI = dNI;
    }

    public int getCodiTrabajador() {
        return codiTrabajador;
    }

    public void setCodiTrabajador(int codiTrabajador) {
        this.codiTrabajador = codiTrabajador;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public DNI getdNI() {
        return dNI;
    }

    public void setdNI(DNI dNI) {
        this.dNI = dNI;
    }

    @Override
    public String toString() {
        return "Trabajador [codiTrabajador=" + codiTrabajador + ", fechaAlta=" + fechaAlta + ", salario=" + salario
                + ", rol=" + rol + ", dNI=" + dNI + "]";
    }

}
