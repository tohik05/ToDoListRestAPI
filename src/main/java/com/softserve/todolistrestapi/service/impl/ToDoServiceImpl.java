package com.softserve.todolistrestapi.service.impl;

import com.softserve.todolistrestapi.dto.todo.CollaboratorRequest;
import com.softserve.todolistrestapi.dto.todo.ToDoRequest;
import com.softserve.todolistrestapi.exception.EntityNotCreatedException;
import com.softserve.todolistrestapi.exception.NullEntityReferenceException;
import com.softserve.todolistrestapi.model.ToDo;
import com.softserve.todolistrestapi.model.User;
import com.softserve.todolistrestapi.repository.ToDoRepository;
import com.softserve.todolistrestapi.service.ToDoService;
import com.softserve.todolistrestapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.softserve.todolistrestapi.util.ErrorMessageBuilder.errorMessage;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository todoRepository;
    private final UserService userService;

    @Autowired
    public ToDoServiceImpl(ToDoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    @Override
    public ToDo readById(long id) {
        return todoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("ToDo with id '%s' not found", id)));
    }

    @Override
    public ToDo create(long ownerId, ToDoRequest toDo, BindingResult bindingResult) {
        if (toDo == null) {
            throw new NullEntityReferenceException("ToDo cannot be 'null'");
        }
        if (bindingResult.hasErrors()) {
            throw new EntityNotCreatedException(errorMessage(bindingResult));
        }
        ToDo newToDo = new ToDo();
        newToDo.setTitle(toDo.getTitle());
        newToDo.setOwner(userService.readById(ownerId));
        newToDo.setCreatedAt(LocalDateTime.now());
        return todoRepository.save(newToDo);
    }

    @Override
    public ToDo update(long toDoId, ToDoRequest toDo, BindingResult bindingResult) {
        if (toDo == null) {
            throw new NullEntityReferenceException("ToDo cannot be 'null'");
        }
        if (bindingResult.hasErrors()) {
            throw new EntityNotCreatedException(errorMessage(bindingResult));
        }
        ToDo oldToDo = readById(toDoId);
        oldToDo.setTitle(toDo.getTitle());
        return todoRepository.save(oldToDo);
    }

    @Override
    public void delete(long id) {
        todoRepository.delete(readById(id));
    }

    @Override
    public List<ToDo> getAll() {
        List<ToDo> todos = todoRepository.findAll();
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        List<ToDo> todos = todoRepository.getByUserId(userId);
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }

    @Override
    public HttpStatus addCollaborator(long toDoId, CollaboratorRequest collaboratorRequest) {
        boolean isCollaboratorExist = false;
        ToDo toDo = readById(toDoId);
        List<User> collaborators = toDo.getCollaborators();

        for (User user : collaborators) {
            if (user.getId() == collaboratorRequest.getCollaborator_id()) {
                isCollaboratorExist = true;
                break;
            }
        }
        if (isCollaboratorExist) {
            return HttpStatus.CONFLICT;
        }
        collaborators.add(userService.readById(collaboratorRequest.getCollaborator_id()));
        toDo.setCollaborators(collaborators);
        todoRepository.save(toDo);
        return HttpStatus.CREATED;
    }

    @Override
    public HttpStatus deleteCollaborator(long toDoId, long collaboratorId) {
        boolean isCollaboratorExist = false;
        ToDo toDo = readById(toDoId);
        List<User> collaborators = toDo.getCollaborators();

        for (User user : collaborators) {
            if (user.getId() == collaboratorId) {
                isCollaboratorExist = true;
                break;
            }
        }
        if (isCollaboratorExist) {
            collaborators.remove(userService.readById(collaboratorId));
            toDo.setCollaborators(collaborators);
            todoRepository.save(toDo);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}
