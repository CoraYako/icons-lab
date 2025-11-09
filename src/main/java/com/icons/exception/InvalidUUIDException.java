package com.icons.exception;

import java.io.Serial;

public class InvalidUUIDException extends BusinessValidationException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidUUIDException(String uuidValue) {
        super(String.format("The value '%s' doesn't represents a valid UUID.", uuidValue));
    }
}
