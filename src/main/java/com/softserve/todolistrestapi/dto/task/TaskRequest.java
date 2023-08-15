package com.softserve.todolistrestapi.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class TaskRequest implements Serializable {
    @NotBlank(message = "The 'name' cannot be empty")
    private String name;
    @NotNull
    private String priority;
    @NotNull
    private String state;

    public TaskRequest() {
    }

    public TaskRequest(String name, String priority, String state) {
        this.name = name;
        this.priority = priority;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
