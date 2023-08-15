package com.softserve.todolistrestapi.controller;

import com.softserve.todolistrestapi.dto.task.TaskRequest;
import com.softserve.todolistrestapi.dto.task.TaskResponse;
import com.softserve.todolistrestapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/todos/{toDoId}/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> create(@PathVariable(name = "toDoId") long toDoId,
                                    @RequestBody @Valid TaskRequest taskRequest, BindingResult bindingResult) {
        taskService.create(toDoId, taskRequest, bindingResult);
        return new ResponseEntity<>("Task successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> read(@PathVariable(name = "id") long taskId) {
        return new ResponseEntity<>(new TaskResponse(taskService.readById(taskId)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long taskId, @RequestBody @Valid TaskRequest taskRequest, BindingResult bindingResult) {
        taskService.update(taskId, taskRequest, bindingResult);
        return new ResponseEntity<>("Task successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long taskId) {
        taskService.delete(taskId);
        return new ResponseEntity<>("Task successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskResponse>> getAllTasks(@PathVariable(name = "toDoId") long toDoId) {
        return new ResponseEntity<>(taskService.getAll()
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<TaskResponse>> getToDoTasks(@PathVariable(name = "toDoId") long toDoId) {
        return new ResponseEntity<>(taskService.getByTodoId(toDoId)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
