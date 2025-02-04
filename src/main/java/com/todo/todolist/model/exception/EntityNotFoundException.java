package com.todo.todolist.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends RuntimeException {

  private final Integer statusCode = HttpStatus.NOT_FOUND.value();

  public EntityNotFoundException(String message) {
    super(message);
  }
}
