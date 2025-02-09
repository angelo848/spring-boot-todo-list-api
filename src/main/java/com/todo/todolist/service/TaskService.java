package com.todo.todolist.service;

import com.todo.todolist.domain.TaskEntity;
import com.todo.todolist.domain.TaskStatusEnum;
import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.model.exception.EntityNotFoundException;
import com.todo.todolist.model.exception.UnauthorizedResourceException;
import com.todo.todolist.model.request.TaskCreateRequest;
import com.todo.todolist.model.request.TaskFilterRequest;
import com.todo.todolist.model.response.TaskResponse;
import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.specification.TaskSpecification;
import com.todo.todolist.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  private final UserService userService;

  private final HttpServletRequest request;

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

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

  public void update(Long taskId, TaskStatusEnum newStatus) {
    TaskEntity task = taskRepository.findById(taskId)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Task with id: %d not found", taskId)));

    String operatorId = getOperatorId();
    if (!operatorId.equals(task.getUser().getEmail())) {
      throw new UnauthorizedResourceException("You are not allowed to update this task");
    }

    boolean canUpdateTaskStatus = TaskStatusMachine.statusChangeAllowed(task.getStatus(), newStatus);
    if (!canUpdateTaskStatus) {
      throw new IllegalArgumentException(String.format("Cannot change task status from %s to %s", task.getStatus(), newStatus));
    }

    task.setStatus(newStatus);
    taskRepository.save(task);
  }

  public void delete(Long taskId) {
    TaskEntity task = taskRepository.findById(taskId)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Task with id: %d not found", taskId)));

    String operatorId = getOperatorId();
    if (!operatorId.equals(task.getUser().getEmail())) {
      throw new UnauthorizedResourceException("You are not allowed to delete this task");
    }

    taskRepository.delete(task);
  }

  public List<TaskEntity> getTasksByUserId(Long userId, TaskFilterRequest filter) {
    UserEntity user = userService.getById(userId);
    String operatorId = getOperatorId();
    if (!operatorId.equals(user.getEmail())) {
      throw new UnauthorizedResourceException("You are not allowed to get tasks for this user");
    }

    Specification<TaskEntity> spec = Specification.where(TaskSpecification.hasUserId(userId));
    if (Objects.nonNull(filter.status())) {
      spec = spec.and(TaskSpecification.hasStatus(filter.status()));
    }
    if (StringUtils.isNotBlank(filter.description())) {
      spec = spec.and(TaskSpecification.hasDescription(filter.description()));
    }

    return taskRepository.findAll(spec);
  }

  private String getOperatorId() {
    String token = request.getHeader("Authorization");
    return jwtUtil.extractUserEmail(token.substring(7));
  }
}
