package com.parammart.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.AuthenticationException;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // =========================================================
    // Resource Not Found
    // =========================================================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError();

        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }


    // =========================================================
    // Duplicate Resource
    // =========================================================

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleDuplicate(
            ResourceAlreadyExistsException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError();

        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError(HttpStatus.CONFLICT.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    // =========================================================
    // Bad Request
    // =========================================================

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError();

        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    // =========================================================
    // Validation Errors
    // =========================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> validationErrors =
                new LinkedHashMap<>();

        for (FieldError fieldError :
                ex.getBindingResult().getFieldErrors()) {

            validationErrors.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        ApiError error = new ApiError();

        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Validation Failed");
        error.setMessage("Please correct the validation errors.");
        error.setValidationErrors(validationErrors);
        error.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    // =========================================================
    // Request Parameter Validation
    // =========================================================

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError();

        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    // =========================================================
    // Authentication Failure
    // =========================================================

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError();

        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        error.setMessage("Invalid email or password.");
        error.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }


    // =========================================================
    // All Other Exceptions
    // =========================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected Exception", ex);

        ApiError error = new ApiError();

        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        /*
         * For development we can expose the exception message.
         * Later, for production, we should return a generic message.
         */
        error.setMessage(ex.getMessage());

        error.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}