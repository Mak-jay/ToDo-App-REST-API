package com.JayMak.ToDo_App.service;

import com.JayMak.ToDo_App.model.ToDo;
import com.JayMak.ToDo_App.repository.TODO_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {
    @Autowired
    private TODO_Repo repo;

    public List<ToDo> getAllToDos() {
        return repo.findAll();

    }

    public ToDo createTodo(ToDo todo) {
        return repo.save(todo);
    }

    public Optional<ToDo> getToDoById(Long id) {
        return repo.findById(id);
    }

    public ToDo updateToDO(Long id, ToDo details) {
     ToDo toDo = repo.findById(id)
             .orElseThrow(() -> new RuntimeException("ToDo not found"+id));
            toDo.setTitle(details.getTitle());
            toDo.setCompleted(details.isCompleted());

            return repo.save(toDo);
    }

    public void deleteToDo(Long id) {
        repo.deleteById(id);
    }
}
