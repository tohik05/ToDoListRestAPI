package com.softserve.todolistrestapi.service;

import com.softserve.todolistrestapi.dto.task.TaskRequest;
import com.softserve.todolistrestapi.model.Task;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface TaskService {
    Task create(long toDoId, TaskRequest taskRequest, BindingResult bindingResult);
    Task readById(long id);
    Task update(long taskId, TaskRequest taskRequest, BindingResult bindingResult);
    void delete(long id);
    List<Task> getAll();
    List<Task> getByTodoId(long todoId);
}
