package com.todo.todolist.config;

import com.todo.todolist.model.exception.ApiError;
import com.todo.todolist.model.exception.EntityNotFoundException;
import com.todo.todolist.model.exception.ExistingEntityException;
import com.todo.todolist.model.exception.InvalidLoginException;
import com.todo.todolist.model.exception.InvalidTokenException;
import com.todo.todolist.model.exception.UnauthorizedResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ExistingEntityException.class)
  protected ResponseEntity<ApiError> handleExistingEntityException(ExistingEntityException e) {
    Integer statusCode = e.getStatusCode();

    ApiError apiError = new ApiError(e.getMessage(), statusCode);
    return ResponseEntity.status(apiError.status())
        .body(apiError);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException e) {
    Integer statusCode = e.getStatusCode();

    ApiError apiError = new ApiError(e.getMessage(), statusCode);
    return ResponseEntity.status(apiError.status())
        .body(apiError);
  }

  @ExceptionHandler(InvalidLoginException.class)
  protected ResponseEntity<ApiError> handleInvalidLoginException(InvalidLoginException e) {
    Integer statusCode = e.getStatusCode();

    ApiError apiError = new ApiError(e.getMessage(), statusCode);
    return ResponseEntity.status(apiError.status())
        .body(apiError);
  }

  @ExceptionHandler(InvalidTokenException.class)
  protected ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException e) {
    Integer statusCode = e.getStatusCode();

    ApiError apiError = new ApiError(e.getMessage(), statusCode);
    return ResponseEntity.status(apiError.status())
        .body(apiError);
  }

  @ExceptionHandler(UnauthorizedResourceException.class)
  protected ResponseEntity<ApiError> handleUnauthorizedResourceException(UnauthorizedResourceException e) {
    Integer statusCode = e.getStatusCode();

    ApiError apiError = new ApiError(e.getMessage(), statusCode);
    return ResponseEntity.status(apiError.status())
        .body(apiError);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException e) {
    Integer statusCode = HttpStatus.BAD_REQUEST.value();

    ApiError apiError = new ApiError(e.getMessage(), statusCode);
    return ResponseEntity.status(apiError.status())
        .body(apiError);
  }
}
