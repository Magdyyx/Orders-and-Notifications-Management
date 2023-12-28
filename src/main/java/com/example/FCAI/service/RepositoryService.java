package com.example.FCAI.service;

import java.util.List;

public interface RepositoryService <T> {
    T create(T t);
    T update(T t);
    T delete(T t);
    T findById(Long id);
    List<T> findAll();
}
