package com.app.cinema.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.app.cinema.enums.Genero;


public class Pelicula {

    private int idPelicula;

    private String fotoPortada;

    private String titulo;

    private double duracion;

    private String descripcion;

    private Genero genero;

    private List<Reseña> reseña = new ArrayList<Reseña> ();

    public Pelicula(int idPelicula, String fotoPortada, String titulo, double duracion, String descripcion,
            Genero genero, List<Reseña> reseña) {
        this.idPelicula = idPelicula;
        this.fotoPortada = fotoPortada;
        this.titulo = titulo;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.genero = genero;
        this.reseña = reseña;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getFotoPortada() {
        return fotoPortada;
    }

    public void setFotoPortada(String fotoPortada) {
        this.fotoPortada = fotoPortada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<Reseña> getReseña() {
        return reseña;
    }

    public void setReseña(List<Reseña> reseña) {
        this.reseña = reseña;
    }

    @Override
    public String toString() {
        return "Pelicula [idPelicula=" + idPelicula + ", fotoPortada=" + fotoPortada + ", titulo=" + titulo
                + ", duracion=" + duracion + ", descripcion=" + descripcion + ", genero=" + genero + ", reseña="
                + reseña + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pelicula pelicula = (Pelicula) obj;
        return Objects.equals(fotoPortada, pelicula.fotoPortada);
    }
}
