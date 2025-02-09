package com.todo.todolist.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedResourceException extends RuntimeException {

  private final Integer statusCode = HttpStatus.FORBIDDEN.value();

  public UnauthorizedResourceException(String message) {
    super(message);
  }
}
