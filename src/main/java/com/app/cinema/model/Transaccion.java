package com.app.cinema.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.cinema.enums.TipoTransaccion;

public class Transaccion {

    private int idTransaccion;

    private Date fechaTransaccion;

    private TipoTransaccion tipoTransaccion;
    
    private List<LineaTransaccion> lineaTransaccion = new ArrayList<LineaTransaccion> ();

    public Transaccion(int idTransaccion, Date fechaTransaccion, TipoTransaccion tipoTransaccion,
            List<LineaTransaccion> lineaTransaccion) {
        this.idTransaccion = idTransaccion;
        this.fechaTransaccion = fechaTransaccion;
        this.tipoTransaccion = tipoTransaccion;
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

    @Override
    public String toString() {
        return "Transaccion [idTransaccion=" + idTransaccion + ", fechaTransaccion=" + fechaTransaccion
                + ", tipoTransaccion=" + tipoTransaccion + ", lineaTransaccion=" + lineaTransaccion + "]";
    }

}
