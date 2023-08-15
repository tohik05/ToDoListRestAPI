package com.softserve.todolistrestapi.service;

import com.softserve.todolistrestapi.dto.todo.CollaboratorRequest;
import com.softserve.todolistrestapi.dto.todo.ToDoRequest;
import com.softserve.todolistrestapi.model.ToDo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ToDoService {
    ToDo create(long ownerId, ToDoRequest toDo, BindingResult bindingResult);
    ToDo readById(long id);
    ToDo update(long toDoId, ToDoRequest toDo, BindingResult bindingResult);
    void delete(long id);
    List<ToDo> getAll();
    List<ToDo> getByUserId(long userId);
    HttpStatus addCollaborator(long toDoId, CollaboratorRequest collaboratorRequest);
    HttpStatus deleteCollaborator(long toDoId, long collaboratorId);
}
