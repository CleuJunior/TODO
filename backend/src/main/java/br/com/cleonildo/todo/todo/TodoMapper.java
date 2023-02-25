package br.com.cleonildo.todo.todo;

import org.modelmapper.ModelMapper;

import java.util.List;

public class TodoMapper {

    public static List<TodoResponse> todoToListResponse(final List<Todo> todoList, final ModelMapper mapper) {
        return todoList.stream()
        .map(todo -> mapper.map(todo, TodoResponse.class))
        .toList();
    }

    public static TodoResponse toTodoResponse(final Todo todo, final ModelMapper mapper) {
        return mapper.map(todo, TodoResponse.class);
    }

    public static Todo toTodo(final TodoRequest request, final ModelMapper mapper) {
        return mapper.map(request, Todo.class);
    }
}
