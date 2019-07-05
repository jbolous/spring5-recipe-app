package com.bolous.exceptions;

public class CategoryException extends RuntimeException {

    public CategoryException() {
        super("Expected Category is missing");
    }

    public CategoryException(String message) {
        super(message);
    }
}
