package com.todo.todolist.model.response;

import com.todo.todolist.domain.TaskStatusEnum;
import java.time.LocalDateTime;

public record TaskResponse(Long id,
                           String description,
                           TaskStatusEnum status,
                           LocalDateTime createdDate) {
}
