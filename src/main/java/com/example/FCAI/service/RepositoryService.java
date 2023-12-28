package com.example.FCAI.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RepositoryService <T> {
    T create(T t);
    T update(T t);
    T delete(T t);
    T findById(int id);
    List<T> findAll();
}
