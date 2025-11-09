package com.icons.exception;

import java.io.Serial;

public class InvalidFieldException extends BusinessValidationException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String fieldName;

    public InvalidFieldException(String fieldName, String message) {
        super(String.format("The %s is invalid or was not provided. %s", fieldName, message));
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
