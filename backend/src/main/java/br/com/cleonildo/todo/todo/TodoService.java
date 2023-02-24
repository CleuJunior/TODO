package br.com.cleonildo.todo.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;

    public List<Todo> getAllTodos() {
        return repository.findAll();
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
