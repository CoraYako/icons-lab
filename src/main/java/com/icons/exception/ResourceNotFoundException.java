package com.icons.exception;

import java.io.Serial;

public class ResourceNotFoundException extends BusinessValidationException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String resourceType;
    private final String resourceId;

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(String.format("%s not found for the ID '%s'", resourceType, resourceId));
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }
}
