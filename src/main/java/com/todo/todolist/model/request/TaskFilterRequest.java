package com.todo.todolist.model.request;

import com.todo.todolist.domain.TaskStatusEnum;

public record TaskFilterRequest(Long id,
                                TaskStatusEnum status,
                                String description) {
}
