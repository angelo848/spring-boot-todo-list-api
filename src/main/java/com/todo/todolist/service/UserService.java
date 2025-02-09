package com.todo.todolist.service;

import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.model.exception.EntityNotFoundException;
import com.todo.todolist.model.exception.ExistingEntityException;
import com.todo.todolist.model.exception.InvalidLoginException;
import com.todo.todolist.model.request.UserCreateRequest;
import com.todo.todolist.model.request.UserLoginRequest;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.util.JwtUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  private final JwtUtil jwtUtil;

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

  @SneakyThrows
  public String login(UserLoginRequest loginRequest) {
    UserEntity user = findByEmail(loginRequest.email())
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    boolean passwordMatch = encoder.matches(loginRequest.password(), user.getPassword());

    if (passwordMatch) {
      return jwtUtil.generateToken(user.getEmail());
    } else {
      throw new InvalidLoginException("Invalid login: email or password is incorrect");
    }
  }
}
