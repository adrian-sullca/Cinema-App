package com.app.cinema.dao;

import java.util.List;

import com.app.cinema.model.Trabajador;

public class TrabajadorDAO extends DBConnection implements DAO<Trabajador, Integer> {
    private final String INSERT = "INSERT INTO USUARIO(correu,password) VALUES(?,?)";
    private final String UPDATE = "UPDATE TRABAJADOR SET correu=?, password=? WHERE id=?";
    private final String DELETE = "DELETE FROM TRABAJADOR WHERE id=?";
    private final String SELECTBYID = "SELECT * FROM TRABAJADOR WHERE id=?";
    private final String SELECTALL = "SELECT * FROM TRABAJADOR";

    @Override
    public void insert(Trabajador t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }
    @Override
    public void update(Trabajador t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public void delete(Trabajador t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    @Override
    public Trabajador selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }
    @Override
    public List<Trabajador> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }


}
