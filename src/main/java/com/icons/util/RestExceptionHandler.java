package com.icons.util;

import com.icons.exception.*;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<@NonNull Object> buildResponse(HttpStatus status, String message, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                status,
                message,
                request.getDescription(false));
        return ResponseEntity.status(status).body(apiError);
    }

    @Override
    protected ResponseEntity<@NonNull Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                           @NonNull HttpHeaders headers,
                                                                           @NonNull HttpStatusCode status,
                                                                           @NonNull WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @Override
    protected ResponseEntity<@NonNull Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                           @NonNull HttpHeaders headers,
                                                                           @NonNull HttpStatusCode status,
                                                                           @NonNull WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errors.put(fieldName, message);
                });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value = {JwtException.class, BadCredentialsException.class})
    protected ResponseEntity<@NonNull Object> handleExpiredJwt(RuntimeException ex, @NonNull WebRequest request) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(value = {DisabledException.class, SignatureException.class})
    protected ResponseEntity<@NonNull Object> handleDisabled(RuntimeException ex, WebRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<@NonNull Object> handleEntityNotFound(ResourceNotFoundException ex, WebRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(value = DuplicatedResourceException.class)
    protected ResponseEntity<@NonNull Object> handleEntityExists(DuplicatedResourceException ex, WebRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(value = {
            NullRequestBodyException.class,
            InvalidFieldException.class,
            InvalidUUIDException.class
    })
    protected ResponseEntity<@NonNull Object> handleEntityExists(BusinessValidationException ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
}
