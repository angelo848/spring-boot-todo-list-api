package com.todo.todolist.service;

import com.todo.todolist.domain.TaskStatusEnum;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TaskStatusMachine {

  private static final Map<TaskStatusEnum, Set<TaskStatusEnum>> ALLOWED_STATUSES_CHANGES = Map.of(
      TaskStatusEnum.PENDING, Set.of(TaskStatusEnum.DONE, TaskStatusEnum.DOING),
      TaskStatusEnum.DOING, Set.of(TaskStatusEnum.DONE),
      TaskStatusEnum.DONE, Set.of()
  );

  public static boolean statusChangeAllowed(TaskStatusEnum statusFrom, TaskStatusEnum statusTo) {
    return ALLOWED_STATUSES_CHANGES.get(statusFrom).contains(statusTo);
  }
}
