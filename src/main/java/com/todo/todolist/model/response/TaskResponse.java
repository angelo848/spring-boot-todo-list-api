package com.todo.todolist.model.response;

import com.todo.todolist.domain.TaskStatusEnum;

public record TaskResponse(Long id,
                           String description,
                           TaskStatusEnum status) {
}
