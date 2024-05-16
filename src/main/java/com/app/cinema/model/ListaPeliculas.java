package com.app.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class ListaPeliculas {
    private List<Pelicula> pelicula = new ArrayList<Pelicula> ();

    public ListaPeliculas(List<Pelicula> pelicula) {
        this.pelicula = pelicula;
    }

    public List<Pelicula> getPelicula() {
        return pelicula;
    }

    public void setPelicula(List<Pelicula> pelicula) {
        this.pelicula = pelicula;
    }

    @Override
    public String toString() {
        return "ListaPeliculas [pelicula=" + pelicula + "]";
    }
    
}
