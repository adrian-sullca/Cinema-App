package com.app.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private int id_carrito;
    private List<Pelicula> peliculasCarrito = new ArrayList<Pelicula> ();

   

    public Carrito(int id_carrito, List<Pelicula> peliculasCarrito) {
        this.id_carrito = id_carrito;
        this.peliculasCarrito = peliculasCarrito;
    }



    public int getId_carrito() {
        return id_carrito;
    }



    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }



    public List<Pelicula> getPeliculasCarrito() {
        return peliculasCarrito;
    }



    public void setPeliculasCarrito(List<Pelicula> peliculasCarrito) {
        this.peliculasCarrito = peliculasCarrito;
    }



    @Override
    public String toString() {
        return "Carrito [totalCarrito=" + id_carrito + ", pelicula=" + peliculasCarrito + "]";
    }

}
