package com.app.cinema.model;

import java.util.Date;

public class Cuenta {

    private int idCuenta;

    private long numeroCuenta;

    private Date caducidad;

    private int CVC;

    private double saldoDiponible;

    public Cuenta() {
    }

    public Cuenta(int idCuenta, long numeroCuenta, Date caducidad, int cVC, double saldoDiponible) {
        this.idCuenta = idCuenta;
        this.numeroCuenta = numeroCuenta;
        this.caducidad = caducidad;
        CVC = cVC;
        this.saldoDiponible = saldoDiponible;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public int getCVC() {
        return CVC;
    }

    public void setCVC(int cVC) {
        CVC = cVC;
    }

    public double getSaldoDiponible() {
        return saldoDiponible;
    }

    public void setSaldoDiponible(double saldoDiponible) {
        this.saldoDiponible = saldoDiponible;
    }

    @Override
    public String toString() {
        return "Cuenta [idCuenta=" + idCuenta + ", numeroCuenta=" + numeroCuenta + ", caducidad=" + caducidad + ", CVC="
                + CVC + ", saldoDiponible=" + saldoDiponible + "]";
    }

}
