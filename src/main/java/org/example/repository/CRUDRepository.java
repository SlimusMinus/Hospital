package org.example.repository;

import org.example.entity.Client;

import java.util.List;

public interface CRUDRepository <T> {
    List<T> findAll();
    T findById(int id);
    T save(T entity);
    T update(T entity);
    boolean delete(int id);
}
