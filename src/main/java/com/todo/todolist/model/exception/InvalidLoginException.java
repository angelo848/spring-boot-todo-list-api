package com.todo.todolist.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidLoginException extends RuntimeException {

  private final Integer statusCode = HttpStatus.BAD_REQUEST.value();

  public InvalidLoginException(String message) {
    super(message);
  }
}
