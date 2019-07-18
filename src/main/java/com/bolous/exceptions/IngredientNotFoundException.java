package com.bolous.exceptions;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(){
        super();
    }

    public IngredientNotFoundException(String message) {
        super(message);
    }
}
