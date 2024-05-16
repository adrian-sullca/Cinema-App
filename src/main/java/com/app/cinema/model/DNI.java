package com.app.cinema.model;

public class DNI {

    private int numero;

    private char letra;

    public DNI(int numero, char letra) {
        this.numero = numero;
        this.letra = letra;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    @Override
    public String toString() {
        return "DNI [numero=" + numero + ", letra=" + letra + "]";
    }
    
}
