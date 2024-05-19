package com.app.cinema.model;

import java.util.Objects;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DNI dni = (DNI) obj;
        return numero == dni.numero && letra == dni.letra;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, letra);
    }

    @Override
    public String toString() {
        return String.format("%08d%c", numero, letra);
    }
}
