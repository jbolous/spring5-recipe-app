package com.bolous.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException() {
        super("Recipe Not Found!");
    }

    public RecipeNotFoundException(Long id){
        super("Recipe not Found! For ID value: " + id.toString());
    }

    public RecipeNotFoundException(String message){
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
