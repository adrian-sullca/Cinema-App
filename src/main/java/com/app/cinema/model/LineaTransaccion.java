package com.app.cinema.model;

public class LineaTransaccion {

    private int idTransaccionLT;
    private int idLineaTransaccion;
    private Pelicula pelicula;
    private double precioPelicula;
    private int cantidad;
    private double totalLT;

    public LineaTransaccion(){
    }
    
    public LineaTransaccion(int idTransaccionLT, int idLineaTransaccion, Pelicula pelicula, double precioPelicula,
            int cantidad, double totalLT) {
        this.idTransaccionLT = idTransaccionLT;
        this.idLineaTransaccion = idLineaTransaccion;
        this.pelicula = pelicula;
        this.precioPelicula = precioPelicula;
        this.cantidad = cantidad;
        this.totalLT = totalLT;
    }

    public int getIdTransaccionLT() {
        return idTransaccionLT;
    }

    public void setIdTransaccionLT(int idTransaccionLT) {
        this.idTransaccionLT = idTransaccionLT;
    }

    public int getIdLineaTransaccion() {
        return idLineaTransaccion;
    }

    public void setIdLineaTransaccion(int idLineaTransaccion) {
        this.idLineaTransaccion = idLineaTransaccion;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public double getPrecioPelicula() {
        return precioPelicula;
    }

    public void setPrecioPelicula(double precioPelicula) {
        this.precioPelicula = precioPelicula;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotalLT() {
        return totalLT;
    }

    public void setTotalLT(double totalLT) {
        this.totalLT = totalLT;
    }

    @Override
    public String toString() {
        return "LineaTransaccion [idTransaccion=" + idTransaccionLT + ", idLineaTransaccion=" + idLineaTransaccion
                + ", pelicula=" + pelicula.getTitulo() + ", precioPelicula=" + precioPelicula + ", cantidad=" + cantidad
                + ", totalLT=" + totalLT + "]";
    }
}
