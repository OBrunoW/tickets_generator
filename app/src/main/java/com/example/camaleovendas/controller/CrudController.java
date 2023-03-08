package com.example.camaleovendas.controller;

import java.util.List;

public interface CrudController<T> {

    boolean save(T obj);

    boolean update(T obj);

    boolean delete(T obj);

    List<T> getAll();

    T getByID(int id);

}
