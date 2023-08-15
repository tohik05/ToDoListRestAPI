package com.softserve.todolistrestapi.dto.todo;

import jakarta.validation.constraints.NotBlank;

public class ToDoRequest {
    @NotBlank(message = "The 'title' cannot be empty")
    private String title;

    public ToDoRequest() {
    }

    public ToDoRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
