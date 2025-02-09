package com.todo.todolist.controller;

import com.todo.todolist.domain.TaskStatusEnum;
import com.todo.todolist.model.request.TaskCreateRequest;
import com.todo.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @PostMapping("/create")
  public ResponseEntity<Void> createTask(@RequestBody TaskCreateRequest taskCreate) {
    taskService.create(taskCreate);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestParam TaskStatusEnum taskStatus) {
    taskService.update(id, taskStatus);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
