package com.softserve.todolistrestapi.dto.todo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CollaboratorRequest {
    @NotBlank(message = "The 'id' cannot be empty")
    private Long collaborator_id;

    public CollaboratorRequest() {
    }

    public CollaboratorRequest(Long collaborator_id) {
        this.collaborator_id = collaborator_id;
    }

    public Long getCollaborator_id() {
        return collaborator_id;
    }

    public void setCollaborator_id(Long collaborator_id) {
        this.collaborator_id = collaborator_id;
    }
}
