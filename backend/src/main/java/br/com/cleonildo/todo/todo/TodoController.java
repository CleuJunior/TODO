package br.com.cleonildo.todo.todo;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        List<TodoResponse> response = this.service.getAllTodos()
                .stream()
                .map(todo -> this.mapper.map(todo, TodoResponse.class))
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        Optional<Todo> todo = this.service.getTodoById(id);

        if (todo.isPresent()) {
            TodoResponse response = this.mapper.map(todo.get(), TodoResponse.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody Todo todo) {
        Todo createdTodo = this.service.createOrUpdateTodo(todo);
        TodoResponse response = this.mapper.map(createdTodo, TodoResponse.class);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        Optional<Todo> existingTodo = service.getTodoById(id);

        if (existingTodo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Todo update = existingTodo.get();
        update.setDone(todo.getDone());
        update.setDescription(todo.getDescription());
        update.setDoneDate(todo.getDoneDate());

        Todo updatedTodo = service.createOrUpdateTodo(update);

        TodoResponse response = this.mapper.map(updatedTodo, TodoResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
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