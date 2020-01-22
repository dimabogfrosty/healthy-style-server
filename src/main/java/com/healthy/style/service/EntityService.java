package com.healthy.style.service;

import java.util.List;
import java.util.Optional;

public interface EntityService<T> {

    List<T> getAll();

    Optional<T> getById(Long id);

    T save(T entity);

    void delete(Long id);

}
