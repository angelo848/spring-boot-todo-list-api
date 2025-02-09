package com.todo.todolist.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidTokenException extends RuntimeException {

  private final Integer statusCode = HttpStatus.FORBIDDEN.value();

  public InvalidTokenException(String message) {
    super(message);
  }
}
