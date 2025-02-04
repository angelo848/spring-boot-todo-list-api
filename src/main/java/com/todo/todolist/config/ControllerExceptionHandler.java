package com.todo.todolist.config;

import com.todo.todolist.model.exception.ApiError;
import com.todo.todolist.model.exception.EntityNotFoundException;
import com.todo.todolist.model.exception.ExistingEntityException;
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
}
