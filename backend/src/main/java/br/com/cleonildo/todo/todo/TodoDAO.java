package br.com.cleonildo.todo.todo;

import br.com.cleonildo.todo.dao.DAO;

import java.util.List;

public interface TodoDAO extends DAO<Todo> {

    List<Todo> findByDone(Boolean done);
}
