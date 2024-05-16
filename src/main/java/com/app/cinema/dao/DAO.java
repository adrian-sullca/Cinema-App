package com.app.cinema.dao;

import java.util.List;

public interface DAO<T, k> {   
    public void insert(T t);
    public void update(T t);
    public void delete(T t);
    public T selectById(k id);
    public List<T> selectAll();
}
