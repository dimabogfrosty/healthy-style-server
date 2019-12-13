package com.healthy.style.service;

import java.util.List;

public interface EntityService<T> {

    List<T> getAll();

    T getById(Long id);

    T save(T entity);

    void delete(Long id);

}
