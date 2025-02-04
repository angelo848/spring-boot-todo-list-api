package com.todo.todolist.service;

import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public void saveUser() {
    userRepository.save(UserEntity.builder()
        .name("Test")
        .email("email@gmail.com")
        .password("password")
        .build());
  }
}
