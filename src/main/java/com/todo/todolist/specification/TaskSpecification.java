package com.todo.todolist.specification;

import com.todo.todolist.domain.TaskEntity;
import com.todo.todolist.domain.TaskStatusEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TaskSpecification {

  public static Specification<TaskEntity> hasStatus(TaskStatusEnum status) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
  }

  public static Specification<TaskEntity> hasDescription(String description) {
    return (root, query, criteriaBuilder) ->
         criteriaBuilder.like(root.get("description"), "%" + description + "%");
  }

  public static Specification<TaskEntity> hasUserId(Long userId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
  }
}
