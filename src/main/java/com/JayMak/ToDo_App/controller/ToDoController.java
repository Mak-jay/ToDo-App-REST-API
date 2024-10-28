package com.JayMak.ToDo_App.controller;

import com.JayMak.ToDo_App.model.ToDo;
import com.JayMak.ToDo_App.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {
    @Autowired
    private ToDoService service;

    @RequestMapping("/hi")
    public String greet()
    {
        return "Hello Jay";
    }
    @GetMapping
    public List<ToDo> getAllToDos()
    {
     return service.getAllToDos();
    }

    @PostMapping("/add")
    public ToDo createTodo(@RequestBody ToDo todo){
        return service.createTodo(todo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable("id") Long id){
        return service.getToDoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable("id") Long id,ToDo details)
    {
        ToDo updatedToDo= service.updateToDO(id,details);
        return ResponseEntity.ok(updatedToDo);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable("id") Long id)
    {
        service.deleteToDo(id);
        return ResponseEntity.ok().build();
    }
}
