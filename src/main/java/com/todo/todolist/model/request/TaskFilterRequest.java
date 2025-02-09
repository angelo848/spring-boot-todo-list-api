package com.todo.todolist.model.request;

import com.todo.todolist.domain.TaskStatusEnum;
import org.springframework.web.bind.annotation.RequestParam;

public record TaskFilterRequest(@RequestParam Long id,
                                @RequestParam TaskStatusEnum status,
                                @RequestParam String description,
                                @RequestParam int page,
                                @RequestParam int size) {
}
