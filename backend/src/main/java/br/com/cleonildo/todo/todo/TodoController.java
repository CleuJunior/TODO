package br.com.cleonildo.todo.todo;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;
    private final ModelMapper mapper;

    @GetMapping
    public List<TodoResponse> getAllTodos(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sort,
                                          @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        Page<Todo> todos = this.service.getAllTodos(pageable);

        List<TodoResponse> response = todos.stream()
                .map(todo -> TodoMapper.toTodoResponse(todo, this.mapper))
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageable, todos.getTotalElements()).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        Optional<Todo> todo = this.service.getTodoById(id);

        if (todo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        TodoResponse response = TodoMapper.toTodoResponse(todo.get(), this.mapper);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoRequest request) {
        Todo todoResponse = TodoMapper.toTodo(request, this.mapper);

        TodoResponse response = TodoMapper.toTodoResponse(
                this.service.createOrUpdateTodo(todoResponse),
                this.mapper
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id, @RequestBody TodoRequest request) {
        Optional<Todo> existingTodo = this.service.getTodoById(id);

        if (existingTodo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Todo update = existingTodo.get();
        update.setDescription(request.getDescription());
        update.setDone(request.getDone());
        update.setDoneDate(request.getDoneDate());

        Todo updatedTodo = this.service.createOrUpdateTodo(update);
        TodoResponse response = TodoMapper.toTodoResponse(updatedTodo, this.mapper);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        Optional<Todo> todo = this.service.getTodoById(id);

        if (todo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        this.service.deleteTodoById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}