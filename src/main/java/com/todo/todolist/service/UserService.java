package com.todo.todolist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.model.exception.EntityNotFoundException;
import com.todo.todolist.model.exception.ExistingEntityException;
import com.todo.todolist.model.exception.InvalidLoginException;
import com.todo.todolist.model.mapper.UserMapper;
import com.todo.todolist.model.request.UserCreateRequest;
import com.todo.todolist.model.request.UserLoginRequest;
import com.todo.todolist.model.response.UserResponse;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.util.DateUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  private final ObjectMapper objectMapper;

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
      UserResponse userPayload = UserMapper.INSTANCE.toResponse(user);
      // TODO: refactor jwt section to a separeted class and use a secret key
      SecretKey secretKey = Keys.hmacShaKeyFor("mySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKey".getBytes());
      return Jwts.builder()
          .subject(objectMapper.writeValueAsString(userPayload))
          .expiration(DateUtils.convertFromLocalDateTimeAdjustedToZoneTime(LocalDateTime.now().plusHours(4)))
          .signWith(secretKey)
          .compact();

      // EXAMPLE OF PARSING JWT
//      SecretKey key = Keys.hmacShaKeyFor("mySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKey".getBytes());
//      String subject = Jwts.parser().verifyWith(key).build().parseSignedClaims(
//              "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJuYW1lXCI6XCJMdWNpYW5vXCIsXCJlbWFpbFwiOlwibHVjaWFubzE3QGxpdmUuY29tXCJ9IiwiZXhwIjoxNzM4ODI2Mzc0fQ.U9mgmV80nh74-ZfnIXA2SvgLFetFuleFpQGdrj-OwbISeK3hu5NBOBlwNU1elgQTeZNmTkaco1ePjJf3hNYJAA")
//          .getPayload().getSubject();
    } else {
      throw new InvalidLoginException("Invalid login: email or password is incorrect");
    }
  }
}
