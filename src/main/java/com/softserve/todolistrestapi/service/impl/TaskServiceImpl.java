package com.softserve.todolistrestapi.service.impl;

import com.softserve.todolistrestapi.dto.task.TaskRequest;
import com.softserve.todolistrestapi.exception.EntityNotCreatedException;
import com.softserve.todolistrestapi.exception.NullEntityReferenceException;
import com.softserve.todolistrestapi.model.Priority;
import com.softserve.todolistrestapi.model.Task;
import com.softserve.todolistrestapi.repository.TaskRepository;
import com.softserve.todolistrestapi.service.StateService;
import com.softserve.todolistrestapi.service.TaskService;
import com.softserve.todolistrestapi.service.ToDoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static com.softserve.todolistrestapi.util.ErrorMessageBuilder.errorMessage;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final StateService stateService;
    private final ToDoService toDoService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, StateService stateService, ToDoService toDoService) {
        this.taskRepository = taskRepository;
        this.stateService = stateService;
        this.toDoService = toDoService;
    }

    @Override
    public Task readById(long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Task with id '%s' not found", id)));
    }

    @Override
    public Task create(long toDoId, TaskRequest taskRequest, BindingResult bindingResult) {
        if (taskRequest == null) {
            throw new NullEntityReferenceException("Task cannot be 'null'");
        }
        if (bindingResult.hasErrors()) {
            throw new EntityNotCreatedException(errorMessage(bindingResult));
        }
        Task newTask = new Task();
        newTask.setName(taskRequest.getName());
        newTask.setPriority(Priority.valueOf(taskRequest.getPriority()));
        newTask.setState(stateService.getByName("New"));
        newTask.setTodo(toDoService.readById(toDoId));
        return taskRepository.save(newTask);
    }

    @Override
    public Task update(long taskId, TaskRequest taskRequest, BindingResult bindingResult) {
        if (taskRequest == null) {
            throw new NullEntityReferenceException("Task cannot be 'null'");
        }
        if (bindingResult.hasErrors()) {
            throw new EntityNotCreatedException(errorMessage(bindingResult));
        }
        Task oldTask = readById(taskId);
        oldTask.setName(taskRequest.getName());
        oldTask.setPriority(Priority.valueOf(taskRequest.getPriority()));
        oldTask.setState(stateService.getByName(taskRequest.getState()));
        return taskRepository.save(oldTask);
    }

    @Override
    public void delete(long id) {
        taskRepository.delete(readById(id));
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        List<Task> tasks = taskRepository.getByTodoId(todoId);
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }
}
