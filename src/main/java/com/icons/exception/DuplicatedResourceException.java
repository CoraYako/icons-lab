package com.icons.exception;

import java.io.Serial;

public class DuplicatedResourceException extends BusinessValidationException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String resourceName;
    private final String resourceId;

    public DuplicatedResourceException(String resourceName, String resourceId) {
        super(String.format("The %s with the identifier '%s' already exists.", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }
}
