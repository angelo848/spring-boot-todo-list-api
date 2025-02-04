package com.todo.todolist.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExistingEntityException extends RuntimeException {

  private final Integer statusCode = HttpStatus.CONFLICT.value();

  public ExistingEntityException(String message) {
    super(message);
  }
}
