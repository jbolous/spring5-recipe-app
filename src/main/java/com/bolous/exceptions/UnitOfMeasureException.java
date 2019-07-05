package com.bolous.exceptions;

public class UnitOfMeasureException extends RuntimeException {

    public UnitOfMeasureException() {
        super("Expected UOM not present");
    }

    public UnitOfMeasureException(String message) {
        super(message);
    }
}
