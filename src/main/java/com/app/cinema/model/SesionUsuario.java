package com.app.cinema.model;

public class SesionUsuario {
    private static Cliente clienteLogeado;

    public static Cliente getClienteLogeado() {
        return clienteLogeado;
    }

    public static void setClienteLogeado(Cliente cliente) {
        clienteLogeado = cliente;
    }
}
