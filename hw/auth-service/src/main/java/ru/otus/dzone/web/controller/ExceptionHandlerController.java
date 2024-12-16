package ru.otus.dzone.web.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.otus.dzone.web.dto.error.ApiError;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "HTTP method not supported";
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, error, ex);
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> handleEntityNotFound(RuntimeException ex) {
        String error = "Entity doesn't exists";
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, error, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = "Invalid HTTP method arguments";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleAIllegalState(IllegalStateException ex) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, error, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String error = "User with this email or username already exists";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}