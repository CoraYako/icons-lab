package com.icons.exception;

import java.io.Serial;

public class NullRequestBodyException extends BusinessValidationException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NullRequestBodyException(String resource) {
        super("The provided object to create a " + resource + " can't be null.");
    }
}
