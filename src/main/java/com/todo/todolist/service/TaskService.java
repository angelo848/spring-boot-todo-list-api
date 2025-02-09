package com.todo.todolist.service;

import com.todo.todolist.domain.TaskEntity;
import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.model.exception.UnauthorizedResourceException;
import com.todo.todolist.model.request.TaskCreateRequest;
import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  private final UserService userService;

  private final HttpServletRequest request;

  private final JwtUtil jwtUtil;

  public void create(TaskCreateRequest taskCreate) {
    UserEntity user = userService.getById(taskCreate.userId());

    String operatorId = getOperatorId();
    if (!operatorId.equals(user.getEmail())) {
      throw new UnauthorizedResourceException("You are not allowed to create a task for this user");
    }

    taskRepository.save(TaskEntity.builder()
        .description(taskCreate.description())
            .user(user)
        .build());
  }

  private String getOperatorId() {
    String token = request.getHeader("Authorization");
    return jwtUtil.extractUserEmail(token.substring(7));
  }
}
