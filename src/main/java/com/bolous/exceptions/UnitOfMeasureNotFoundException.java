package com.bolous.exceptions;

public class UnitOfMeasureNotFoundException extends RuntimeException {

    public UnitOfMeasureNotFoundException() {
        super("Unit of measure not found");
    }
}
