package com.app.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private double totalCarrito;
    private List<Pelicula> pelicula = new ArrayList<Pelicula> ();

    public Carrito(double totalCarrito, List<Pelicula> pelicula) {
        this.totalCarrito = totalCarrito;
        this.pelicula = pelicula;
    }
    
    public double getTotalCarrito() {
        return totalCarrito;
    }
    public void setTotalCarrito(double totalCarrito) {
        this.totalCarrito = totalCarrito;
    }
    public List<Pelicula> getPelicula() {
        return pelicula;
    }
    public void setPelicula(List<Pelicula> pelicula) {
        this.pelicula = pelicula;
    }

    @Override
    public String toString() {
        return "Carrito [totalCarrito=" + totalCarrito + ", pelicula=" + pelicula + "]";
    }

}
