package com.todo.todolist.service;

import com.todo.todolist.domain.TaskEntity;
import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.model.request.TaskCreateRequest;
import com.todo.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  private final UserService userService;

  public void create(TaskCreateRequest taskCreate) {
    UserEntity user = userService.getById(taskCreate.userId());

    taskRepository.save(TaskEntity.builder()
        .description(taskCreate.description())
            .user(user)
        .build());
  }
}
