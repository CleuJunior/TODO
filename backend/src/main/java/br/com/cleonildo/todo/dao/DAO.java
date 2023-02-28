package br.com.cleonildo.todo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Page<T> getAll(Pageable pageable);

    Optional<T> getById(Long id);

    List<T> findListOfNames(String name);

    T createOrUpdateTodo(T t);

    void deleteById(Long id);
}
