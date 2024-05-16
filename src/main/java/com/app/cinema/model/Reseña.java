package com.app.cinema.model;

import java.util.Date;

import com.app.cinema.enums.Estado;

public class Reseña {
    private int idReseña;

    private String contenido;

    private Date fechaReseña;

    private Estado estado;

    private Cliente cliente;

    private Pelicula pelicula;

    public Reseña(int idReseña, String contenido, Date fechaReseña, Estado estado, Cliente cliente, Pelicula pelicula) {
        this.idReseña = idReseña;
        this.contenido = contenido;
        this.fechaReseña = fechaReseña;
        this.estado = estado;
        this.cliente = cliente;
        this.pelicula = pelicula;
    }

    public int getIdReseña() {
        return idReseña;
    }

    public void setIdReseña(int idReseña) {
        this.idReseña = idReseña;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaReseña() {
        return fechaReseña;
    }

    public void setFechaReseña(Date fechaReseña) {
        this.fechaReseña = fechaReseña;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    @Override
    public String toString() {
        return "Reseña [idReseña=" + idReseña + ", contenido=" + contenido + ", fechaReseña=" + fechaReseña
                + ", estado=" + estado + ", cliente=" + cliente + ", pelicula=" + pelicula + "]";
    }

}
