package com.softserve.todolistrestapi.controller;

import com.softserve.todolistrestapi.dto.todo.CollaboratorRequest;
import com.softserve.todolistrestapi.dto.todo.CollaboratorResponse;
import com.softserve.todolistrestapi.dto.todo.ToDoRequest;
import com.softserve.todolistrestapi.dto.todo.ToDoResponse;
import com.softserve.todolistrestapi.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/todos")
public class ToDoController {

    private final ToDoService todoService;

    @Autowired
    public ToDoController(ToDoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> create(@PathVariable("userId") long ownerId, @RequestBody @Valid ToDoRequest toDo, BindingResult bindingResult) {
        todoService.create(ownerId, toDo, bindingResult);
        return new ResponseEntity<>("ToDo successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoResponse> read(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(new ToDoResponse(todoService.readById(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long toDoId,
                                    @Valid @RequestBody ToDoRequest toDo, BindingResult bindingResult) {
        todoService.update(toDoId, toDo, bindingResult);
        return new ResponseEntity<>("ToDo successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long todoId) {
        todoService.delete(todoId);
        return new ResponseEntity<>("ToDo successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ToDoResponse>> getAllToDos() {
        return new ResponseEntity<>(todoService.getAll()
                .stream()
                .map(ToDoResponse::new)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<ToDoResponse>> getUserToDos(@PathVariable("userId") long ownerId) {
        return new ResponseEntity<>(todoService.getByUserId(ownerId)
                .stream()
                .map(ToDoResponse::new)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}/collaborators")
    public ResponseEntity<?> getToDoCollaborators(@PathVariable(name = "id") long toDoId) {
        List<CollaboratorResponse> collaboratorResponses = todoService.readById(toDoId).getCollaborators()
                .stream()
                .map(CollaboratorResponse::new)
                .collect(Collectors.toList());
        return collaboratorResponses.isEmpty()?
                new ResponseEntity<>("Collaborators still not assigned", HttpStatus.OK):
                new ResponseEntity<>(collaboratorResponses, HttpStatus.OK);
    }

    @PostMapping("/{id}/collaborators")
    public ResponseEntity<?> addCollaborator(@PathVariable(name = "id") long toDoId, @RequestBody CollaboratorRequest collaboratorRequest) {
        HttpStatus responseStatus = todoService.addCollaborator(toDoId, collaboratorRequest);
        return responseStatus.equals(HttpStatus.CREATED) ?
                new ResponseEntity<>("Collaborators successfully added", HttpStatus.CREATED) :
                new ResponseEntity<>("Collaborator has already assigned", HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}/collaborators/{c_id}")
    public ResponseEntity<?> deleteCollaborator(@PathVariable(name = "id") long toDoId,
                                                @PathVariable(name = "c_id") long collaboratorId) {
        HttpStatus responseStatus = todoService.deleteCollaborator(toDoId, collaboratorId);
        return responseStatus.equals(HttpStatus.OK) ?
                new ResponseEntity<>("Collaborators successfully deleted", HttpStatus.OK) :
                new ResponseEntity<>("Collaborator not found", HttpStatus.NOT_FOUND);
    }
}