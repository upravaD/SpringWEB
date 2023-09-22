package com.aston.rest.service;

import java.util.List;

public interface RestService<E, D> {
    void create(E value);
    List<D> getAll();
    D findById(Long id);
    void update(E value);
    void delete(E value);
}
