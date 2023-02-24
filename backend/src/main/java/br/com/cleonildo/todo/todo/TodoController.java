package br.com.cleonildo.todo.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = service.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Optional<Todo> todo = service.getTodoById(id);

        return todo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo createdTodo = service.createOrUpdateTodo(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        Optional<Todo> existingTodo = service.getTodoById(id);

        if (existingTodo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Todo update = existingTodo.get();
        update.setDone(todo.getDone());
        update.setDescription(todo.getDescription());
        update.setDoneDate(todo.getDoneDate());

        Todo updatedTodo = service.createOrUpdateTodo(update);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        Optional<Todo> todo = service.getTodoById(id);

        if (todo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        service.deleteTodoById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}