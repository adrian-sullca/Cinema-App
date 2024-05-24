package com.app.cinema.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.cinema.enums.TipoTransaccion;

public class Transaccion {

    private int idTransaccion;

    private Cliente cliente;

    private Date fechaTransaccion;

    private TipoTransaccion tipoTransaccion;

    private double total;
    
    private List<LineaTransaccion> lineaTransaccion = new ArrayList<LineaTransaccion> ();

    public Transaccion() {
    }
    
    public Transaccion(int idTransaccion, Cliente cliente, Date fechaTransaccion, TipoTransaccion tipoTransaccion, double total,
            List<LineaTransaccion> lineaTransaccion) {
        this.idTransaccion = idTransaccion;
        this.cliente = cliente;
        this.fechaTransaccion = fechaTransaccion;
        this.tipoTransaccion = tipoTransaccion;
        this.total = total;
        this.lineaTransaccion = lineaTransaccion;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public List<LineaTransaccion> getLineaTransaccion() {
        return lineaTransaccion;
    }

    public void setLineaTransaccion(List<LineaTransaccion> lineaTransaccion) {
        this.lineaTransaccion = lineaTransaccion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Transaccion [idTransaccion=" + idTransaccion + ", cliente=" + cliente.getCodiCliente()  + ", fechaTransaccion=" + fechaTransaccion
                + ", tipoTransaccion=" + tipoTransaccion + ", total=" + total + ", lineaTransaccion=" + lineaTransaccion + "]\n";
    }

}
