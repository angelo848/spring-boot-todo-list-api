package com.todo.todolist.config;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CurrentOperator {

  private static final ThreadLocal<String> CURRENT_OPERATOR = new ThreadLocal<>();

  public static String getOperator() {
    return Optional.ofNullable(CURRENT_OPERATOR.get())
        .orElse("unknown");
  }

  public static void setOperator(String operatorId) {
    CURRENT_OPERATOR.set(operatorId);
  }

  public static void removeOperator() {
    CURRENT_OPERATOR.remove();
  }
}
