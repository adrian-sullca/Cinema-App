package com.app.cinema.dao;

import java.util.List;

/**
 * Interfaz genérica para acceder y manipular los datos en la base de datos.
 * 
 * @param <T> El tipo de objeto que maneja la interfaz DAO.
 * @param <K> El tipo de la clave primaria de los objetos manejados.
 * 
 * @author Adrian.
 */
public interface DAO<T, k> {   
    public void insert(T t);
    public void update(T t);
    public void delete(T t);
    public T selectById(k id);
    public List<T> selectAll();
}
