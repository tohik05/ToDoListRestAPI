package com.softserve.todolistrestapi.exception;

public class EntityNotCreatedException extends RuntimeException{
    public EntityNotCreatedException(String message) {
        super(message);
    }
}
