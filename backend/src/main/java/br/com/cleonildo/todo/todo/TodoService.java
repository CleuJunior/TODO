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
public class TodoService implements TodoDAO {

    private final TodoRepository repository;

    @Override
    public Page<Todo> getAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Optional<Todo> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<Todo> findListOfNames(String name) {
        return this.repository.findByNameContains(name);
    }

    @Override
    public List<Todo> findByDone(Boolean done) {
        return this.repository.findByDone(done);
    }

    @Override
    public Todo createOrUpdateTodo(Todo todo) {
        return this.repository.save(todo);
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

}
