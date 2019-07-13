package com.bolous.exceptions;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException() {
        super("Recipe Not Found!");
    }
}
