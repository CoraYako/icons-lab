package com.icons.util;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        HttpStatus status,
        String message,
        String path
) {
}
