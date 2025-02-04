package com.todo.todolist.controller;

import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.model.mapper.UserMapper;
import com.todo.todolist.model.request.UserCreateRequest;
import com.todo.todolist.model.response.UserResponse;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<UserResponse> getUser(@RequestParam Long userId) {
    UserEntity user = userService.getById(userId);
    return ResponseEntity.ok(UserMapper.INSTANCE.toResponse(user));
  }

  @PostMapping("/create")
  public ResponseEntity<Void> createUser(@RequestBody UserCreateRequest userCreate) {
    userService.create(userCreate);
    return ResponseEntity.noContent().build();
  }
}
