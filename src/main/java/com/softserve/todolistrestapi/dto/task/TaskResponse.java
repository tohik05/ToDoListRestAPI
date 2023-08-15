package com.softserve.todolistrestapi.dto.task;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.softserve.todolistrestapi.model.Priority;
import com.softserve.todolistrestapi.model.Task;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskResponse {
    private long id;
    private String name;
    private Priority priority;
    private String todoTitle;
    private String stateName;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.priority = task.getPriority();
        this.todoTitle = task.getTodo().getTitle();
        this.stateName = task.getState().getName();
    }

    public TaskResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getState() {
        return stateName;
    }

    public void setState(String state) {
        this.stateName = state;
    }
}
