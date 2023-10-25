package com.icons.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.resolve(status.value()),
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errors.put(fieldName, message);
                });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value = JwtException.class)
    protected ResponseEntity<Object> handleExpiredJwt(JwtException ex, @NonNull HttpHeaders headers,
                                                      @NonNull HttpStatusCode status,
                                                      @NonNull WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(value = SignatureException.class)
    protected ResponseEntity<Object> handleSignature(SignatureException ex, WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.FORBIDDEN,
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(value = DisabledException.class)
    protected ResponseEntity<Object> handleDisabled(DisabledException ex, WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.FORBIDDEN,
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(value = NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointer(NullPointerException ex, WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(value = EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityExists(EntityExistsException ex, WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(value = InvalidParameterException.class)
    protected ResponseEntity<Object> handleInvalidParameter(InvalidParameterException ex, WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(apiError.status()).body(apiError);
    }
}
