package com.app.cinema.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos.
 * 
 * @author Adrian.
 */
public class DBConnection {

    public Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/cinema_db_adrian";
    private final String user = "root";
    private final String password = "informatica1506";

    /**
     * Constructor de la clase DBConnection.
     */
    public DBConnection() {
    }

    /**
     * Establece una conexión con la base de datos.
     */
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida correctamente.");
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar a la base de datos. " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha podido cargar el driver de MySQL. " + ex.getMessage());
        }
    }

    /**
     * Obtiene la conexión actual.
     * 
     * @return La conexión actual.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException ex) {
                System.out.println("No se ha podido cerrar la conexión. " + ex.getMessage());
            }
        }
    }
}
