package com.todo.todolist.service;

import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.model.exception.EntityNotFoundException;
import com.todo.todolist.model.exception.ExistingEntityException;
import com.todo.todolist.model.request.UserCreateRequest;
import com.todo.todolist.model.request.UserLoginRequest;
import com.todo.todolist.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public void create(UserCreateRequest userCreate) {
    Optional<UserEntity> optionalUser = findByEmail(userCreate.email());
    if (optionalUser.isPresent()) {
      throw new ExistingEntityException("User already exists with this email");
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(14);
    userRepository.save(UserEntity.builder()
        .name(userCreate.name())
        .email(userCreate.email())
        .password(encoder.encode(userCreate.password()))
        .build());
  }

  public Optional<UserEntity> findById(Long id) {
    return userRepository.findById(id);
  }

  public UserEntity getById(Long id) {
    return findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  public Optional<UserEntity> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public boolean login(UserLoginRequest loginRequest) {
    Optional<UserEntity> optionalUser = findByEmail(loginRequest.email());
    if (optionalUser.isEmpty()) {
      return false;
    }

    UserEntity user = optionalUser.get();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(loginRequest.password(), user.getPassword());
  }
}
