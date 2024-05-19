package com.app.cinema.model;

import java.util.Date;
import java.util.Objects;

import com.app.cinema.enums.Rol;
import com.app.cinema.enums.TipoUsuario;

public class Trabajador extends Usuario {

    private int codiTrabajador;

    private Date fechaAlta;

    private double salario;

    private Rol rol;

    private DNI dNI;

    public Trabajador(int idUsuario, String nombre, String apellido, Date fechaNacimiento, String correo,
            String contraseña, TipoUsuario tipoUsuario, int codiTrabajador, Date fechaAlta, double salario, Rol rol,
            DNI dNI) {
        super(idUsuario, nombre, apellido, fechaNacimiento, correo, contraseña, tipoUsuario);
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
        return "Trabajador [codiTrabajador=" + codiTrabajador + ", idUsuario=" + super.idUsuario  + ", fechaAlta=" + fechaAlta + ", salario=" + salario
                + ", rol=" + rol + ", dNI=" + dNI + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trabajador trabajador = (Trabajador) obj;
        return Objects.equals(dNI, trabajador.dNI) || Objects.equals(super.getCorreo(), trabajador.getCorreo());
    }

}
