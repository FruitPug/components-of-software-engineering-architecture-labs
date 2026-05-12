package com.example.lab3.presentation.exception;

import com.example.lab3.domain.error.DomainError;
import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ApiError(
            Instant timestamp,
            int status,
            String error,
            String message
    ) {}

    @ExceptionHandler(DomainError.class)
    public ResponseEntity<ApiError> handleDomainError(DomainError ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex.getMessage().contains("NOT_FOUND")) {
            status = HttpStatus.NOT_FOUND;
        }

        ApiError body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({OptimisticLockException.class, ObjectOptimisticLockingFailureException.class})
    public ResponseEntity<ApiError> handleOptimisticLock() {
        ApiError body = new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                "Concurrent update detected. Please retry."
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        ApiError body = new ApiError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex) {
        ApiError body = new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElement(NoSuchElementException ex) {
        ApiError body = new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ApiError body = new ApiError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Data integrity violation: " + ex.getMostSpecificCause().getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
