package br.com.cleonildo.todo.todo;

import br.com.cleonildo.todo.dao.DAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService implements DAO<Todo> {

    private final TodoRepository repository;

    @Override
    public Page<Todo> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Todo> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Todo createOrUpdateTodo(Todo todo) {
        return repository.save(todo);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
