package com.app.cinema.model;

public class LineaTransaccion {

    private int idLineaTransaccion;

    private double cantidad;

    private double precioUnitario;

    private double precioFinal;

    private Pelicula pelicula;

    public LineaTransaccion(int idLineaTransaccion, double cantidad, double precioUnitario, double precioFinal,
            Pelicula pelicula) {
        this.idLineaTransaccion = idLineaTransaccion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.precioFinal = precioFinal;
        this.pelicula = pelicula;
    }

    public int getIdLineaTransaccion() {
        return idLineaTransaccion;
    }

    public void setIdLineaTransaccion(int idLineaTransaccion) {
        this.idLineaTransaccion = idLineaTransaccion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    @Override
    public String toString() {
        return "LineaTransaccion [idLineaTransaccion=" + idLineaTransaccion + ", cantidad=" + cantidad
                + ", precioUnitario=" + precioUnitario + ", precioFinal=" + precioFinal + ", pelicula=" + pelicula
                + "]";
    }

}
