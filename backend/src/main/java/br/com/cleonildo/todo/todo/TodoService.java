package br.com.cleonildo.todo.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;

    public Page<Todo> getAllTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Todo> getTodoById(Long id) {
        return repository.findById(id);
    }

    public Todo createOrUpdateTodo(Todo todo) {
        return repository.save(todo);
    }

    public void deleteTodoById(Long id) {
        repository.deleteById(id);
    }

}
