package com.project.model.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
