package com.todo.todolist.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.todo.todolist.domain.TaskStatusEnum;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TaskDetailResponse(Long id,
                                 String description,
                                 TaskStatusEnum status,
                                 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdDate,
                                 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedDate,
                                 String createdBy,
                                 String updatedBy) {
}
